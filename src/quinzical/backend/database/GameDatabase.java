package quinzical.backend.database;
import quinzical.backend.IO;
import quinzical.backend.models.Clue;
import quinzical.backend.models.Category;
import quinzical.backend.tasks.Formatting;
import quinzical.backend.tasks.FileManager;
import quinzical.backend.models.InternationalCategory;

// Java API dependencies.
import java.util.List;
import java.util.Random;
import java.io.IOException;
import java.util.ArrayList;
import java.nio.file.Files;

/**
 * This class is used to create database for the GameModule which would consists of categories, and
 * its questions. It uses PracticeDatabase to construct itself if current progress for the user does
 * not exist.
 *
 * @author Shrey Tailor, Jason Wang
 */
public class GameDatabase {
	// Static field(s).
	private static GameDatabase gameDatabase;	
	
	// Non-static field(s).
    private List<Category> categories = new ArrayList<Category>();
    private InternationalCategory intCate = new InternationalCategory();
	private int winning = 0;
	private Clue currentClue = null;
	private final static int CLUE_NUM = 5;
	private final static int START_PRICE = 100;
	private final static int PRICE_INCREMENT = 100;

    /**
     * The constructor for GameDatabase object which is private, because it can only be
     * accessed by the getInstance method (according to the principles of Singleton pattern).
     */
    private GameDatabase(List<Category> categoryList) {
        initialize(categoryList);
    }

	public static boolean singletonExist() {
		return (gameDatabase != null);
	}

    /**
     * This method is used to return the singleton object of the GameDatabase object. If it does not
     * exist, this method will create a singleton object with random categories and clues.
     * @return GameDatabase the instance of our game database.
     */
    public static GameDatabase getInstance() {
        if (!singletonExist()) {
        	List<Category> categoryList = new ArrayList<Category>();
        	gameDatabase = new GameDatabase(categoryList);
        }

        return gameDatabase;
    }
    
    /**
     * This method is used to return the singleton object of this class.
     * @return GameDatabase the instance of our game database.
     */
    public static GameDatabase getInstance(List<Category> categoryList) {
    	kill();
    	gameDatabase = new GameDatabase(categoryList);

        return gameDatabase;
    }
    
    /**
     * This method is used to initialize the instance of GameDatabase object. It will behave differently
	 * depending on whether the data already exists or not.
     */
    private void initialize(List<Category> categoryList) {
    	// If the game data file already exists
    	if (FileManager.gameFileExist()) {
    		readGameData();
    	} else {
			// If the game data file does not exists
    		generateGameData(categoryList);
    	}
    }
    
    /**
     * This method is used to read the user progress into the game, if it already exists on their
	 * local machine.
     */
    private void readGameData() {
		Clue newClue;
		Category newCate = null;

    	// Read the existing progress file using the IO class.
		List<String> gameContent;
		try {
			gameContent = IO.readFile(FileManager.getGameFile());

			// Go through each line of the file.
			for(String line : gameContent) {
				if (line.matches("\\d+")) {
					// If the line is a number, it contains the player's winnings.
					winning = Integer.parseInt(line);
				} else if(!line.contains("|") && !line.isBlank()) {
					// If the line only contains a word, then it is a new category.
					newCate = new Category(line);
					categories.add(newCate);
				} else if (!line.isBlank()) {
					// If the line contains some other formatting, then it is a clue.
					newClue = Formatting.formatClue(line, newCate);
					newClue.setPrize(Integer.parseInt(line.split("[|]")[3].trim()));
					newCate.addClue(newClue);
				}
			}

			// If an International category does exist, then...
			if (categories.get(getCateSize() - 1).getName().equals("International")) {
				intCate = new InternationalCategory(categories.get(getCateSize() - 1));
				categories.remove(getCateSize()-1);
			}
		} catch (IOException e) {
			System.out.println("Failed to initialize the database. Please contact the developer.");
		}
    }
    
    /**
     * This method is used if the game progress does not exist, so then it will choose any random
	 * combination of categories and clues to populate the game.,
     */
    private void generateGameData(List<Category> categoryList) {
    	// Add the International category to the game.
    	categoryList.add(PracticeDatabase.getInstance().getInternationalCategory());

    	Random rand = new Random();
		Category newCate;
		Clue newClue, selectedClue;

		// Go through all the categories in the list.
		for (Category selectedCate: categoryList) {
			int price = START_PRICE;
			newCate = new Category(selectedCate.getName());

			// Go through all the clues in that category.
			for (int j = 0; j < CLUE_NUM; j++) {
				// Select a random clue from the given category.
				int index = rand.nextInt(selectedCate.getClueSize());
				selectedClue = selectedCate.getClue(index);

				// Define the common properties of the clue such as prize etc.
				newClue = new Clue(selectedClue.getQuestion(), selectedClue.getPrefix(), selectedClue.getFullAnswer(), newCate);
				newClue.setPrize(price);
				newCate.addClue(newClue);
				selectedCate.removeClue(index);

				// Increment the prize of the next question.
				price += PRICE_INCREMENT;
			}

			if(selectedCate.getName() == "International") {
				// If we are inserting the International category, then we make an object of that type.
				intCate = new InternationalCategory(newCate);
			}else {
				categories.add(newCate);
			}			
		}

		// Reload the PracticeDatabase after creating the GameDatabase.
		PracticeDatabase.kill();
    }

	/**
     * This method is used to get a particular category from the list of categories in our database.
     * @param position the position of category which we are trying to extract from our list.
     * @return Category the category from that position.
     * @throws IndexOutOfBoundsException if the position requested is out of the valid range.
     */
    public Category getCategory(int position) throws IndexOutOfBoundsException {
        return categories.get(position);
    }
	
	/**
	 * This method returns the number of remaining questions.
	 * @return int number of questions remaining.
	 */
	public int getRemainingClues() {
		categories.add(intCate);
		int count = 0;
		for(int i = 0; i < categories.size(); i++) {
			Category category = categories.get(i);
			count += category.getClueSize();
		}
		categories.remove(intCate);
		return count;
	}

	/**
	 * This method is used to set a given clue as the current clue, so it can be displayed to the
	 * user when they are answering the clue.
	 * @param clue the clue that the user has selected by clicking on it.
	 */
	public void setCurrentClue(Clue clue) {
		currentClue = clue;
	}

	/**
	 * This method will dereference the GameDatabase, so that when the getInstance is called next
	 * time the GameDatabase would be reloaded.
	 */
	public static void kill() {
		gameDatabase = null;
		try {
			Files.deleteIfExists(FileManager.getGameFile().toPath());
		} catch (IOException e) {
		}
	}

	public int getWinning() {
		return winning;
	}

	public Clue getCurrentClue() {
		return currentClue;
	}

	public int getCateSize() {
		return categories.size();
	}

	public void updateWinning(int prize) {
		winning = winning + prize;
	}

	public InternationalCategory getInternationalCategory() {
		return intCate;
	}
}