package a3.quinzical.backend.Database;

//Java API dependencies.
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import a3.quinzical.backend.Category;
import a3.quinzical.backend.Clue;


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
    private static PracticeDatabase _practiceDatabase;
    private final static File _quizFile = new File(System.getProperty("user.dir")+"/Quinzical.txt");

    // Fields belonging to the non-static context.
    private Clue _selected;
    private int _numberOfCategories;
    private List<Category> _categories = new ArrayList<Category>();

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
        if (_practiceDatabase == null) {
            _practiceDatabase = new PracticeDatabase();
        }

        return _practiceDatabase;
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
     * This method is used to initialize the instance of PracticeDatabase object.
     * The method reads in all categories and clues from a specified file and store them in their
     * respective category objects and clue objects. 
     */
    private void initialize() {
		String line;
		try {
			BufferedReader br = new BufferedReader(new FileReader(_quizFile));
			Category newCate = null;
			Clue newClue;
			while(( line = br.readLine()) != null) {
				if(!line.contains("|") && !line.isBlank()){
					newCate = new Category(line);
					_categories.add(newCate);
				}else if(!line.isBlank()){
					newClue = new Clue(line.split("[|]")[0].trim(), line.split("[|]")[1].trim(), line.split("[|]")[2].trim(), newCate);
					newCate.addClue(newClue);
				}
			}
			br.close();		
		}catch(FileNotFoundException e) {
			System.out.println("Quinzical.txt not found in root directory");
		}catch(IOException e) {
			System.out.println("Error occured during reading Quiz file");
		}
    }

    /**
     * This is a private method which basically updates the number of categories there are in our
     * database. Note that it is not accessible from outside this class.
     */
    private void updateRemaining() {
        _numberOfCategories = _categories.size();
    }
    
    /**
     * This is a method that can remove a category from the list.
     * @param index of the category to be removed.
     * @throws IndexOutOfBoundsException this exception is returned if the position requested by
     * the user is out of the valid range of the list.
     */
    public void removeCategory(int index) throws IndexOutOfBoundsException{
		_categories.remove(index);
		updateRemaining();
	}
    
    /**
     * This is a method that will return the number of categories in PracticeDatabase.
     * @return the number of categories.
     */
    public int getCateSize() {
    	updateRemaining();
    	return _numberOfCategories;
    }

    public void select(Clue clue) {
        _selected = clue;
    }

    public Clue getSelected() {
        return _selected;
    }

    /**
     * This is a method that will dereference the _practiceDatabase field, so that when the 
     * getInstance is called next time the PracticeDatabase would be reloaded.
     */
    public static void kill() {
    	_practiceDatabase = null;
    }
}
