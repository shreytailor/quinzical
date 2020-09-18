package a3.quinzical.backend;

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

    private String _categoryName;
    private List<Clue> _clues;
    private int _numberOfClues;

    public Category(String name) {
        _categoryName = name;
        _clues = new ArrayList<Clue>();
    }

    public String getName() {
        return _categoryName;
    }

    public void addClue(Clue clue) {
        _clues.add(clue);
        updateRemaining();
    }

    public Clue getClue(int place) throws IndexOutOfBoundsException {
        return _clues.get(place);
    }

    public Clue getRandom() throws IndexOutOfBoundsException {
        Random randomizer = new Random();
        int random = randomizer.nextInt(_numberOfClues);
        return _clues.get(random);
    }

    public Clue getRandomPop() {
        Clue randomClue = getRandom();
        _clues.remove(randomClue);
        updateRemaining();
        return randomClue;
    }

    private void updateRemaining() {
        _numberOfClues = _clues.size();
    }

}
