package quinzical.frontend.controllers;
import quinzical.backend.models.Clue;
import quinzical.frontend.helper.Speaker;
import quinzical.frontend.helper.ScreenType;
import quinzical.frontend.helper.ScreenSwitcher;
import quinzical.backend.database.PracticeDatabase;

// Java dependencies.
import java.net.URL;
import java.util.ResourceBundle;

// JavaFX dependencies.
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;

/**
 * This class is the controller class for the Practice Question screen.
 * @author Shrey Tailor, Jason Wang
 */
public class PracticeClueController implements Initializable {
    @FXML Label clueLabel;
    @FXML Button backButton;
    @FXML Button submitButton;
    @FXML Label categoryLabel;
    @FXML Label attemptsLabel;
    @FXML Button respeakButton;
    @FXML Label hintPlaceholder;
    @FXML Button dontKnowButton;
    @FXML Label prefixPlaceholder;
    @FXML TextField answerTextField;
    @FXML GridPane keyboardGridPane;

    private int attemptsRemaining = 3;
    private final Speaker speaker = Speaker.init();
    private final ScreenSwitcher switcher = ScreenSwitcher.getInstance();
    private final Clue clue = PracticeDatabase.getInstance().getSelected();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Showing all the information about the current question.
        clueLabel.setText(clue.getQuestion());
        categoryLabel.setText(clue.getCategory().getName());
        prefixPlaceholder.setText(clue.getPrefix() + "...");
        hintPlaceholder.setText("Hint: the first character of the answer is '" + clue.getAnswersList().get(0).charAt(0) + "'.");
        hintPlaceholder.setVisible(false);
        updateAttempts();

        // Speaking the question to the user.
        speaker.setSpeech(clue.getQuestion());
        speaker.speak();
    };

    @FXML
    private void handleDontKnowButton() {
        stopInput();
        incorrectAnswer();
    }

    @FXML
    private void handleSubmitButton() {
        // Use the backend to confirm whether the answer is correct.
        boolean isCorrect = clue.checkAnswer(answerTextField.getText());

        // If cororect, then stop the input from the user.
        if (isCorrect) {
            stopInput();
            correctAnswer();
            return;
        }

        // If incorrect, then reduce the number of attempts remaining..
        attemptsRemaining--;
        if (attemptsRemaining < 1) {
            stopInput();
            incorrectAnswer();
        } else {
            answerTextField.clear();
            updateAttempts();
        }
    }

    @FXML
    private void handleBackButton () {
        speaker.kill();
        switcher.switchTo(ScreenType.PRACTICE_MODULE);
    }

    @FXML
    private void handleRespeakButton() {
        // Speak the clue again for the user.
        speaker.setSpeech(clue.getQuestion());
        speaker.speak();
    }

    @FXML
    private void submitOnEnter(KeyEvent event) {
        // Feature to use <ENTER> in order to submit your answer.
        switch (event.getCode()) {
            case ENTER:
                submitButton.fire();
                break;
        }
    }

    @FXML
    private void handleKeyboardButton(ActionEvent event) {
        // This method is used to add the text from the macron keyboard into the answer box.
        Button pressed = (Button) event.getSource();
        answerTextField.setText(answerTextField.getText() + pressed.getText());
    }

    /**
     * This private method is used to tell the user that they were correct. It performs things
     * such as speaks the congratulatory message, and shows the hidden elements on screen.
     */
    private void correctAnswer() {
        String string = "Ka pai, you got it correct!";
        speaker.setSpeech(string);
        speaker.speak();
        attemptsLabel.setText(string);
    }

    /**
     * This private is method used to display the correct answer to the user. It performs things
     * such as telling the user that they were wrong, and speaks the correct answer.
     */
    private void incorrectAnswer() {
        String string = "Oh no! The correct answer was " + clue.getAnswersList().get(0);
        speaker.setSpeech(string);
        speaker.speak();
        attemptsLabel.setText(string);
    }

    /**
     * This private method is used to update the attempts remaining on an incorrect attempt.
     */
    private void updateAttempts() {
        // If there is one remaining attempt, then display the hint.
        if (attemptsRemaining == 1) {
            hintPlaceholder.setVisible(true);
        }

        String text = "Attempts remaining: " + attemptsRemaining;
        attemptsLabel.setText(text);
    }

    /**
     * This private method is to stop inputs from the user, and perform the basic screen clean-up.
     */
    private void stopInput() {
        speaker.kill();
        submitButton.setVisible(false);
        respeakButton.setVisible(false);
        answerTextField.setDisable(true);
        dontKnowButton.setVisible(false);
        hintPlaceholder.setVisible(false);
        keyboardGridPane.setVisible(false);
    }
}
