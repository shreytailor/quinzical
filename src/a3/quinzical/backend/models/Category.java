package a3.quinzical.backend.models;

// Java API dependencies.
import java.util.List;
import java.util.Random;
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
    private Clue _currentClue;

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
    public void removeClue(int i) throws IndexOutOfBoundsException{
		_clues.remove(i);
	}

    /**
     * This method is used to get a random clue from the current category. Note that no destructive
     * changes are made to the object unlike the {@link #getRandomPop()} method.
     * @return Clue the random clue that we wanted.
     */
    public Clue getRandom() {
        Random randomizer = new Random();
        int random = randomizer.nextInt(_numberOfClues);
        return _clues.get(random);
    }

    /**
     * This method is used to get a random clue from the current category, but also delete the clue
     * after being returned. Therefore, note that we are performing some destructive changes to the
     * object being dealt with.
     * @return Clue the random blue that we wanted.
     */
    public Clue getRandomPop() {
        Clue randomClue = getRandom();
        _clues.remove(randomClue);
        updateRemaining();
        return randomClue;
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
     * The method is used to get the current attemptable clue in the category.
     * @return the current Clue
     */
    public Clue getCurrentClue() {
    	return _currentClue;
    }
    
    /**
     * The method is used to change the current attemptable clue in the category.
     * @param the clue to be set to current Clue
     */
    public void setCurrentClue(Clue c) {
    	_currentClue = c;
    }

    /**
     * The method is used to move the current Clue to the next Clue in the list
     */
    public void advanceClue() {
    	try {
	    	for(int i = 0; i < _clues.size(); i++) {
	    		if(_currentClue.equals(_clues.get(i))) {
	    			_currentClue = _clues.get(i+1);
	    		}
	    	}
    	}catch(IndexOutOfBoundsException e) {
    		_currentClue = null;
    	}
    }
    
    /**
     * The method is used to get the list of remaining attemptable Clues.
     * @return a list of Clues that has not been attempted.
     */
    public List<Clue> remainingClue() {
    	Boolean attempted = true;
    	List<Clue> remainingClues = new ArrayList<Clue>();
    	for(Clue c : _clues) {
    		if(_currentClue.equals(c)) {
    			attempted = false;
    		}
    		if(!attempted) {
    			remainingClues.add(c);
    		}
    	}
    	return remainingClues;
    }
}
