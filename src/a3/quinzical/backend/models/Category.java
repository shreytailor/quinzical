package a3.quinzical.backend.models;

// Java API dependencies.
import java.util.List;
import java.util.ArrayList;


/**
 * This class is used as a model for all the categories of our Quinzical game. It contains various
 * fields and methods which would make it easier for the developers to interact with these
 * entities.
 *
 * @author Shrey Tailor, Jason Wang
 */
public class Category {

    // Fields required for the model of our Category.
    private String _categoryName;
    private List<Clue> _clues;
    private int _numberOfClues;
    /**
     * This is the only constructor for creating an object for Category.
     * @param name the name of the category which we are creating.
     */
    public Category(String name) {
        _categoryName = name;
        _clues = new ArrayList<Clue>();
    }

    /**
     * This method is used to get the name of the category.
     * @return String the name of the category.
     */
    public String getName() {
        return _categoryName;
    }

    /**
     * This method is used to add a clue to the existing list of clues within the category.
     * @param clue the clue which we want to add to the category.
     */
    public void addClue(Clue clue) {
        _clues.add(clue);
        updateRemaining();
    }

    /**
     * This method is used to get a clue from the existing list of clues within the category.
     * @param position the position of the clue that we want to get.
     * @return Clue the clue at our desired position.
     * @throws IndexOutOfBoundsException this exception is returned if the position specified is
     * out of valid range of the list.
     */
    public Clue getClue(int position) throws IndexOutOfBoundsException {
        return _clues.get(position);
    }
    
    /**
     * This method is used to remove a specific clue from the existing list of clues within the category.
     * @param i the position of the clue that we want to remove.
     * @throws IndexOutOfBoundsException this exception is returned if the position specified is
     * out of valid range of the list.
     */
    public void removeClue(int i) throws IndexOutOfBoundsException {
		_clues.remove(i);
	}

    /**
     * This private is used to just update the field regarding how many questions are remaining in
     * the current category.
     */
    private void updateRemaining() {
        _numberOfClues = _clues.size();
    }
    
    /**
     * The method is used to get the number of clues in this category.
     * @return the number of clues in this category.
     */
    public int getClueSize() {
    	updateRemaining();
    	return _numberOfClues;
    }
    
    /**
     * The method is used to remove the first clue(lowest priced clue) from the list of clues.
     */
    private void popFirstClue() {
    	if(_clues.size() >= 1) {
    		_clues.remove(0);
    		updateRemaining();
    	}
    }

    /**
     * The method is used when a user selected a clue in Game module
     * it removes the first clue and return that clue
     * @return the first clue in the _clues list
     */
    public Clue buttonClicked() {
    	Clue clue = _clues.get(0);
    	popFirstClue();
    	return clue;
    }
    
    /**
     * The method is used to get the list of remaining attemptable Clues.
     * @return a list of Clues that has not been attempted.
     */
    public List<Clue> remainingClue() {
    	return _clues;
    }
}
