package quinzical.backend.database;
import quinzical.backend.IO;
import quinzical.backend.models.Clue;
import quinzical.backend.models.Category;
import quinzical.backend.tasks.Formatting;
import quinzical.backend.tasks.FileManager;
import quinzical.backend.models.InternationalCategory;

//Java dependencies.
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

/**
 * This class is used to create a practice database for the user by importing all the categories
 * and their corresponding clues from the file. This is also helpful while creating a game
 * database for the user.
 *
 * @author Shrey Tailor, Jason Wang
 */
public class PracticeDatabase {

    // Static field(s).
    private static PracticeDatabase practiceDatabase;

    // Non-static field(s).
    private Clue selected;
    private int numberOfCategories;
    private Category markedCategory;
    private List<Category> categories = new ArrayList<Category>();
    private InternationalCategory intCate = new InternationalCategory();

    /**
     * A private constructor for creating a PracticeDatabase object.
     */
    private PracticeDatabase() {
        initialize();
    }

    /**
     * This method is used to return the singleton object of the PracticeDatabase object.
     * @return PracticeDatabase the instance of our practice database.
     */
    public static PracticeDatabase getInstance() {
        if (practiceDatabase == null) {
            practiceDatabase = new PracticeDatabase();
        }

        return practiceDatabase;
    }

    /**
     * This method is used to initialize the practice database in the game. It reads in all lines
     * of categories and clues using the IO class, stores them here.
     */
    private void initialize() {

        // Load categories and clues from the New Zealand context.
        List<String> quizContent;
		try {
		    // Firstly read in all the lines from the NZ file.
			quizContent = IO.readFile(FileManager.getQuizFile());

            Clue newClue;
            Category newCate = null;
            for (String line : quizContent) {

	    		if (!line.contains("|") && !line.isBlank()) {
	    		    // If the line is a category, then create a new category.
					newCate = new Category(line);
					categories.add(newCate);
				} else if(!line.isBlank()) {
	    		    // If the line is a clue, then create new clue within category.
					newClue = Formatting.formatClue(line, newCate);
					newCate.addClue(newClue);
				}

	    	}

	    	// Secondly read all clues from the International file.
	    	List<String> internationalContent = IO.readFile(FileManager.getInternationalFile());
	    	for (String line : internationalContent) {
	    		if(!line.isBlank()) {
		    		newClue = Formatting.formatClue(line, intCate);
		    		intCate.addClue(newClue);
	    		}
	    	}
		} catch (IOException e) {
			System.out.println("Please make sure to have the Quinzical.txt and International.txt file in their correct location");
		}
    }

    /**
     * This method is used to get a particular category from the overall list.
     * @param position the position of category that we are trying to access.
     * @throws IndexOutOfBoundsException if the position requested is out of the valid range.
     */
    public Category getCategory(int position) throws IndexOutOfBoundsException {
        return categories.get(position);
    }

    /**
     * This private method updates the number of categories remaining in our database.
     */
    private void updateRemaining() {
        numberOfCategories = categories.size();
    }
    
    /**
     * This method will return the number of categories in PracticeDatabase.
     * @return the number of categories.
     */
    public int getCateSize() {
    	updateRemaining();
    	return numberOfCategories;
    }

    /** This method is used to select a clue for easier access in the game.
     * @param clue the clue to be selected
     */
    public void select(Clue clue) {
        selected = clue;
    }

    /**
     * This method is used to mark a category as needs practice so that we can show it easily in the
     * Practice Module of Quinzical.
     * @param cate the category that we want to mark.
     */
    public void setMarkedCategory(Category cate) {
        markedCategory = cate;
    }

    /**
     * This is a method that will dereference the practiceDatabase field, so that when the
     * getInstance is called next time the PracticeDatabase would be reloaded.
     */
    public static void kill() {
        practiceDatabase = null;
    }

    public Clue getSelected() {
        return selected;
    }
    
    public Category getMarkedCategory() {
    	return markedCategory;
    }

    public InternationalCategory getInternationalCategory() {
        return intCate;
    }
}
