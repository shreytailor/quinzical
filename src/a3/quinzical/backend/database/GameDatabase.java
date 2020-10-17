package a3.quinzical.backend.database;
import a3.quinzical.backend.IO;
import a3.quinzical.backend.models.Clue;
import a3.quinzical.backend.models.Category;
import a3.quinzical.backend.tasks.Formatting;

// Java API dependencies.
import java.io.File;
import java.util.List;
import java.util.Random;
import java.io.IOException;
import java.util.ArrayList;
import java.nio.file.Files;

/**
 * This class is used to create a module that represents the record of a player, 
 * including the selectable category, their questions and the winnings of the player.
 * 
 * This class uses PracticeDatabase class to construct itself if a readable file that 
 * records the player's data does not exist.
 * 
 * Note that it is using the Singleton pattern because in a single instance of a game, we would
 * only want to instantiate a single object for GameDatabase.
 * 
 * @author Shrey Tailor, Jason Wang
 */
public class GameDatabase {
	
	// Fields belonging to the static context.
	private static GameDatabase _gameDatabase;	
	private final static File _gameFile = new File(System.getProperty("user.dir")+"/.config/GameData");
	
	// Fields belonging to the non-static context.
    private List<Category> _categories = new ArrayList<Category>();
	private int _winning = 0;
	private final static int _cateNum = 5;
	private final static int _clueNum = 5;
	private final static int _startPrice = 100;
	private final static int _priceIncrement = 100;
	private Clue _currentClue = null;
    
    /**
     * The constructor for GameDatabase object which is private, because it can only be
     * accessed by the getInstance method (according to the principles of Singleton pattern).
     */
    private GameDatabase(List<Category> categoryList) {
        initialize(categoryList);
    }
    
    /**
     * This method is used to check if the GameData file exist
     * @return if the GameData file exist
     */
    public static boolean gameFileExist() {
    	return (_gameFile.exists() && _gameFile.isFile());
    }
    
    /**
     * This method is used to check if an instance of GameDatabase exist
     * @return if the GameData file exist
     */
    public static boolean singletonExist() {
    	return (_gameDatabase != null);
    }
    
    /**
     * This method is used to return the singleton object of the GameDatabase object.
     * If the GameDatabase is not initialized this method will create a GameDatabase 
     * object with random categories and random clues.
     * @return GameDatabase the instance of our game database.
     */
    public static GameDatabase getInstance() {
        if (!singletonExist()) {
        	List<Category> categoryList = new ArrayList<Category>();
        	_gameDatabase = new GameDatabase(categoryList);
        }

        return _gameDatabase;
    }
    
    /**
     * This method is used to return the singleton object of the GameDatabase object.
     * If the GameDatabase is not initialized this method will create a GameDatabase 
     * object using the specified list of categories passed in.
     * @return GameDatabase the instance of our game database.
     */
    public static GameDatabase getInstance(List<Category> categoryList) {
    	kill();
    	_gameDatabase = new GameDatabase(categoryList);

        return _gameDatabase;
    }
    
    /**
     * This method is used to initialize the instance of GameDatabase object.
     * The method will select different actions depending on if a GameData file already exists
     */
    private void initialize(List<Category> categoryList) {
    	// If the game data file already exists
    	if (gameFileExist()) {
    		readGameData();
    	} else {
			// If the game data file does not exists
    		generateGameData(categoryList);
    	}
    }
    
    /**
     * This method is used if the GameData file exists
     * The method uses a list returned by readFile in IO class that represents the player's data
     * and all categories and clues and store them in their respective category objects
     * and clue objects. 
     */
    private void readGameData() {
		Clue newClue;
		Category newCate = null;

    	// Read the current Game Database file using the IO method.
		List<String> gameContent = IO.readFile(_gameFile);

		// Go through each line of the returned list of all lines.
		for(String line : gameContent) {
			// If the line only contains number, which is the stored player prize.
			if (line.matches("\\d+")) {
				_winning = Integer.parseInt(line);
			} else if(!line.contains("|")&& !line.isBlank()) {
				// If the line does not have a separator character | and isn't blank, then the line represents a category
				newCate = new Category(line);
				_categories.add(newCate);
			} else if (!line.isBlank()) {
				// Using a helper method in Formatting to construct a clue object
				newClue = Formatting.formatClue(line, newCate);
				newClue.setPrize(Integer.parseInt(line.split("[|]")[3].trim()));
				newCate.addClue(newClue);
			}
		}
    }
    
    /**
     * This method is used if the GameData file does not exist
     * If the file does not exist then this method will randomly select 5 categories with 5 
     * questions from PracticeDatabase and create a file to store the player's record.
     */
    private void generateGameData(List<Category> categoryList) {
    	Random rand = new Random();
		Category newCate;
		Clue newClue, selectedClue;

		// Traverse through all the categories.
		for (Category selectedCate: categoryList) {
			newCate = new Category(selectedCate.getName());
			int price = _startPrice;

			// Traverse through all the clues in that category.
			for(int j = 0; j < _clueNum; j++) {
				// Select random clue from the category.
				int questIndex = rand.nextInt(selectedCate.getClueSize());
				selectedClue = selectedCate.getClue(questIndex);
				newClue = new Clue(selectedClue.getQuestion(), selectedClue.getPrefix(), selectedClue.getFullAnswer(), newCate);
				newClue.setPrize(price);
				newCate.addClue(newClue);
				selectedCate.removeClue(questIndex);

				// Set the prize of the question.
				price += _priceIncrement;
			}
			_categories.add(newCate);
		}

		// Reload the PracticeDatabase after creating the Game Database.
		PracticeDatabase.kill();
    }

    /**
     * This method is used to change the current winning of the player.
     * @param prize the winning to add onto the current winning.
     */
	public void updateWinning(int prize) {
		_winning = _winning + prize;
	}
	
	/**
	 * This method is used to get the current prize of the player.
	 * @return _winning the current winning of the player.
	 */
	public int getWinning() {
		return _winning;
	}
	
	/**
     * This method is used to get a particular category from the list of categories in our database.
     * @param position the position of category which we are trying to extract from our list.
     * @return Category the category that is returned from the particular position.
     * @throws IndexOutOfBoundsException this exception is returned if the position requested by
     * the user is out of the valid range of the list.
     */
    public Category getCategory(int position) throws IndexOutOfBoundsException {
        return _categories.get(position);
    }
    
    /**
     * This is a method that will return the number of categories in GameDatabase.
     * @return the number of categories.
     */
	public int getCateSize() {
		return _categories.size();
	}
	
    /**
     * This is a method that will dereference the _gameDatabase field, so that when the 
     * getInstance is called next time the GameDatabase would be reloaded.
     */
	public static void kill() {
		_gameDatabase = null;
		try {
			Files.deleteIfExists(_gameFile.toPath());
		} catch (IOException e) {
		}
    }
	
	/**
     * This is a method that sets a pointer for the clue that is being answered.
     * @param clue the clue being answered.
     */
	public void setCurrentClue(Clue clue) {
		_currentClue = clue;
	}
	
	/**
	 * This is a method that returns the clue that is currently being answered.
	 * @return the clue that is currently being answered.
	 */
	public Clue getCurrentClue() {
		return _currentClue;
	}
	
	/**
	 * This is a method that will count how many questions remains in the
	 * GameDatabase and return it.
	 * @return number of questions left
	 */
	public int getRemainingClues() {
		int count = 0;
		for(int i = 0; i < _categories.size(); i++) {
			Category category = _categories.get(i);
			count += category.getClueSize();
		}
		return count;
	}
	
	/**
	 * This is a method that will return where the GameData.txt is supposed to be saved.
	 * @return the file of GameData
	 */
	public File getFile() {
		return _gameFile;
	}
}
