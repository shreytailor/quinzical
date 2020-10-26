package quinzical.backend.database;

import quinzical.backend.IO;
import quinzical.backend.models.Clue;
import quinzical.backend.models.InternationalCategory;
import quinzical.backend.tasks.FileManager;
import quinzical.backend.tasks.Formatting;
import quinzical.backend.models.Category;

//Java API dependencies.
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to import all the categories and their corresponding clues from the file
 * provided to us by the client. Therefore, it would contain all the questions which are part of
 * the Quinzical game.
 *
 * Later, this class would be used develop the GameDatabase, if there doesn't exist one on the
 * player's computer.
 *
 * Note that it is using the Singleton pattern because in a single instance of a game, we would
 * only want to instantiate a single object for PracticeDatabase.
 *
 * @author Shrey Tailor, Jason Wang
 */
public class PracticeDatabase {

    // Fields belonging to the static context.
    private static PracticeDatabase practiceDatabase;

    // Fields belonging to the non-static context.
    private Clue selected;
    private int numberOfCategories;
    private Category markedCategory;
    private List<Category> categories = new ArrayList<Category>();
    private InternationalCategory intCate = new InternationalCategory();

    /**
     * The only constructor for PracticeDatabase object which is private, because it can only be
     * accessed by the getInstance method (according to the principles of Singleton pattern).
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
     * This method is used to get a particular category from the list of categories in our database.
     * @param position the position of category which we are trying to extract from our list.
     * @return Category the category that is returned from the particular position.
     * @throws IndexOutOfBoundsException this exception is returned if the position requested by
     * the user is out of the valid range of the list.
     */
    public Category getCategory(int position) throws IndexOutOfBoundsException {
        return categories.get(position);
    }
    
    /**
     * THis method is used to get the international category of the database
     * @return InternationalCategory the international category of the database
     */
    public InternationalCategory getInternationalCategory() {
    	return intCate;
    }

    /**
     * This method is used to initialize the instance of PracticeDatabase object.
     * The method reads in all lines of categories and clues from a readFile in IO class
     * and store them in their respective category objects and clue objects. 
     */
    private void initialize() {
    	//Load categories and clues from the New Zealand database
        List<String> quizContent = IO.readFile(FileManager.getQuizFile());
        Category newCate = null;
    	Clue newClue;
    	for (String line : quizContent) {
    		if (!line.contains("|") && !line.isBlank()) {
				newCate = new Category(line);
				categories.add(newCate);
			} else if(!line.isBlank()) {
				newClue = Formatting.formatClue(line, newCate);
				newCate.addClue(newClue);
			}
    	}
    	//Load clues to International Category 
    	List<String> internationalContent = IO.readFile(FileManager.getInternationalFile());
    	for (String line : internationalContent) {
    		if(!line.isBlank()) {
	    		newClue = Formatting.formatClue(line, intCate);
	    		intCate.addClue(newClue);
    		}
    	}
    }

    /**
     * This is a private method which basically updates the number of categories there are in our
     * database. Note that it is not accessible from outside this class.
     */
    private void updateRemaining() {
        numberOfCategories = categories.size();
    }
    
    /**
     * This is a method that can remove a category from the list.
     * @param index of the category to be removed.
     * @throws IndexOutOfBoundsException this exception is returned if the position requested by
     * the user is out of the valid range of the list.
     */
    public void removeCategory(int index) throws IndexOutOfBoundsException{
		categories.remove(index);
		updateRemaining();
	}
    
    /**
     * This is a method that will return the number of categories in PracticeDatabase.
     * @return the number of categories.
     */
    public int getCateSize() {
    	updateRemaining();
    	return numberOfCategories;
    }

    /** This is a method that is used to select a clue for easier access.
     * @param clue the clue to be selected
     */
    public void select(Clue clue) {
        selected = clue;
    }

    /**
     * This is a method for getting the selected clue
     * @return the selected clue
     */
    public Clue getSelected() {
        return selected;
    }

    /**
     * This is a method that will dereference the practiceDatabase field, so that when the 
     * getInstance is called next time the PracticeDatabase would be reloaded.
     */
    public static void kill() {
    	practiceDatabase = null;
    }
    
    public void setMarkedCategory(Category cate) {
    	markedCategory = cate;
    }
    
    public Category getMarkedCategory() {
    	return markedCategory;
    }
}
