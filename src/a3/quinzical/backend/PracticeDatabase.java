package a3.quinzical.backend;

// Java API dependencies.
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
    private static PracticeDatabase _practiceDatabase;

    // Fields belonging to the non-static context.
    final private String _fileName = "FILENAME";
    private List<Category> _categories;
    private int _numberOfCategories;

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

    public void initialize() {
        /*
        ----------------------------------------------------------------------------------------
                                THE FILE READING PROCESS WOULD GO HERE.
        ----------------------------------------------------------------------------------------
         */
    }

    /**
     * This is a private method which basically updates the number of categories there are in our
     * database. Note that it is not accessible from outside this class.
     */
    private void updateRemaining() {
        _numberOfCategories = _categories.size();
    }

}
