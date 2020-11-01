package quinzical.backend.models;

// Java API dependencies.
import java.util.List;
import java.util.Random;
import java.util.ArrayList;


/**
 * This class is used as a model that can represent all Categories of clues.
 *
 * @author Shrey Tailor, Jason Wang
 */
public class Category {
    private List<Clue> clues;
    private int numberOfClues;
    private String categoryName;

    /**
     * This is the only constructor for creating an object for Category.
     * @param name the name of the category which we are creating.
     */
    public Category(String name) {
        categoryName = name;
        clues = new ArrayList<Clue>();
    }

    public String getName() {
        return categoryName;
    }

    /**
     * This method is used to get a random clue from the current category.
     * @return Clue the random clue that we wanted.
     */
    public Clue getRandom() {
        Random randomizer = new Random();
        int random = randomizer.nextInt(numberOfClues);
        return clues.get(random);
    }

    /**
     * This method is used to add a clue to the list of clues of this category.
     * @param clue the clue which we want to add to the category.
     */
    public void addClue(Clue clue) {
        clues.add(clue);
        updateRemaining();
    }

    /**
     * This method is used to get a clue from this category.
     * @param position the position of the clue that we want to get.
     * @return Clue the clue at our desired position.
     * @throws IndexOutOfBoundsException if the position specified is out of the valid range.
     */
    public Clue getClue(int position) throws IndexOutOfBoundsException {
        return clues.get(position);
    }

    /**
     * This method is used to remove a specific clue from the existing list of clues.
     * @param index the position of the clue that we want to remove.
     * @throws IndexOutOfBoundsException if the position specified is out of valid range.
     */
    public void removeClue(int index) throws IndexOutOfBoundsException {
        clues.remove(index);
    }

    /**
     * This private method is used to update how many questions are remaining in a category.
     */
    private void updateRemaining() {
        numberOfClues = clues.size();
    }

    public int getClueSize() {
        updateRemaining();
        return numberOfClues;
    }

    /**
     * The method is used to remove the first clue (lowest priced clue) from the list of clues.
     */
    public void nextQuestion() {
        if(clues.size() >= 1) {
            clues.remove(0);
            updateRemaining();
        }
    }

    /**
     * The method is used to get the list of remaining attemptable Clues.
     * @return a list of Clues that has not been attempted.
     */
    public List<Clue> remainingClue() {
        return clues;
    }
}