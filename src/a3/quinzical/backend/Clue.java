package a3.quinzical.backend;


/**
 * This class is used as a model for all the clues of our Quinzical game. It contains various
 * fields and methods which would make it easier for the developers to interact with these
 * entities.
 *
 * @author Shrey Tailor, Jason Wang
 */
public class Clue {

    private String _question;
    private String _answer;
    private int _prize;

    public Clue(String question, String answer) {
        _question = question;
        _answer = answer;
    }

    public Clue(String question, String answer, int prize) {
        _question = question;
        _answer = answer;
        _prize = prize;
    }

    public String getQuestion() {
        return _question;
    }

    public String getAnswer() {
        return _answer;
    }

    public boolean checkAnswer(String answer) {
        // The logic here would check the argument "answer" against "_answer" field.
        return true;
    }

    public int getPrize() {
        return _prize;
    }

}