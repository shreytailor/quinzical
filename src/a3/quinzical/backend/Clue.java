package a3.quinzical.backend;


/**
 * This class is used as a model for all the clues of our Quinzical game. It contains various
 * fields and methods which would make it easier for the developers to interact with these
 * entities.
 *
 * @author Shrey Tailor, Jason Wang
 */
public class Clue {

    // Fields required for the model of our Clue.
    private String _question;
    private String _answer;
    private int _prize;
    private boolean _currentQuestion = false;
    private Category _category;

    /**
     * This is the only constructor for creating an object for Clue.
     * @param question the question of the clue.
     * @param answer the answer of the clue. Note that this string can also include (What is) etc
     * @param category the category the clue belongs to.
     * part of the string as well, because there is a method to compare the answer.
     */
    public Clue(String question, String answer, Category category) {
        _question = question.trim();
        _answer = answer.trim();
        _category = category;
    }

    /**
     * This method is used to get the question corresponding the the current clue.
     * @return String the question for this clue.
     */
    public String getQuestion() {
        return _question;
    }

    /**
     * This method is used to get the answer for the current clue.
     * @return String the answer for the current clue.
     */
    public String getAnswer() {
        return _answer;
    }

    /**
     * This method is used to check the user's answer against the correct answer. It'll do all the
     * processing required regarding taking care if the user had (What is), (Who is) etc, or even
     * if it doesn't. The method does the work of trimming the answer firstly, and keeping things
     * case-insensitive while comparing.
     * @param answer the user's answer which will be checked against the correct answer.
     * @return boolean true if the answer was correct, otherwise false.
     */
    public boolean checkAnswer(String answer) {
        // Strings with no parenthesis, and with no prefix overall.
        String correctWithoutParenthesis = _answer.replaceAll("[()]", "");
        String correctNoPrefix = _answer.replaceAll("\\s*\\([^\\)]*\\)\\s*", "");

        // Removing any repeated space characters from the user's input.
        answer = answer.replaceAll("\\s+", " ");

        // Comparing the sanitized user's input against the three strings above.
        if (answer.equalsIgnoreCase(correctWithoutParenthesis) || answer.equalsIgnoreCase(correctNoPrefix) || answer.equalsIgnoreCase(_answer)) {
            return true;
        }

        // If no matches, then just return false.
        return false;
    }

    /**
     * This method will be used when putting a clue from the regular PracticeDatabase to the
     * GameDatabase, because we would need to assign it a prize.
     * @param prize the prize that we want to assign to the question.
     */
    public void setPrize(int prize) {
        _prize = prize;
    }

    /**
     * This method is used to get the prize of the current question object.
     * @return int for the prize of the current clue.
     */
    public int getPrize() {
        return _prize;
    }
    
    /**
     * This method sets the field _currentQuestion true
     */
    public void setCurrentQuestionTrue() {
    	_currentQuestion = true;
    }
    
    /**
     * This method sets the field _currentQuestion false
     */
    public void setCurrentQuestionFalse() {
    	_currentQuestion = false;
    }
    
    /**
     * This method returns the value field _currentQuestion.
     * @return field _currentQuestion.
     */
    public boolean isCurrentQuestion() {
    	return _currentQuestion;
    }
    
    /**
     * This method return the category the clue belongs to.
     * @return the category of this clue
     */
    public Category getCategory() {
    	return _category;
    }
}