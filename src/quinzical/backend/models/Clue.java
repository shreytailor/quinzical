package quinzical.backend.models;
import quinzical.backend.tasks.Formatting;

// Java dependencies.
import java.util.List;
import java.util.ArrayList;

/**
 * This class is used as a model to represent all the Clues within categories.
 *
 * @author Shrey Tailor, Jason Wang
 */
public class Clue {

    // Fields required for the model of our Clue.
    private int prize;
    private String prefix;
    private String answer;
    private String question;
    private Category category;

    /**
     * This is the only constructor for creating an object for Clue.
     * @param questionString the question of the clue.
     * @param prefixString the prefix of the answer so e.g. What is, Who is etc.
     * @param answerString the answer of the clue.
     * @param categoryString the category the clue belongs to.
     */
    public Clue(String questionString, String prefixString, String answerString, Category categoryString) {
        question = questionString;
        prefix = prefixString;
        answer = answerString;
        category = categoryString;
    }

    public String getQuestion() {
        return question;
    }

    /**
     * This method is used to get the non-sanitized answer for the current clue.
     * @return String the answer for the current clue.
     */
    public String getFullAnswer() {
        return answer;
    }

    public String getAnswer() {
        return Formatting.removeTheA(answer);
    }
    
    /**
     * This method is used to get all variations of the answer for the clue.
     * @return a list of strings representing the possible variations.
     */
    public List<String> getAnswersList() {
    	List<String> answerList = new ArrayList<String>();
    	String[] answerArray = answer.split("/");
    	for(int i = 0; i < answerArray.length; i++) {
    		answerList.add(Formatting.removeTheA(answerArray[i]));
    	}
    	return answerList;
    }

    /**
     * This method is used to check the user's answer against variations of the correct answer.
     * It'll do all the processing regarding taking care if the user had (What is), (Who is) etc,
     * or even if it doesn't.
     * @param userAnswer the user's answer which will be checked against set of correct answers.
     * @return boolean true if the answer was correct, otherwise false.
     */
    public boolean checkAnswer(String userAnswer) {
    	// Removing any repeated space characters from the user's input.
        userAnswer = Formatting.sanitize(userAnswer);
    	
        // Loop through the variations of answers, and checking the user's answer.
    	String[] answerList = answer.split("/");
    	for(int i = 0; i < answerList.length; i++) {
    		answerList[i] = Formatting.sanitize(answerList[i]);
    		if (userAnswer.equalsIgnoreCase(answerList[i])){
    			return true;
    		}
    	}
        
        // If no matches, then just return false.
        return false;
    }

    public void setPrize(int p) {
        prize = p;
    }

    public int getPrize() {
        return prize;
    }
    
    public Category getCategory() {
    	return category;
    }
    
    public String getPrefix() {
    	return prefix;
    }
}
