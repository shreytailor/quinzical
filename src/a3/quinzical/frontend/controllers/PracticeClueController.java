package a3.quinzical.frontend.controllers;

import a3.quinzical.frontend.helper.Speaker;
import a3.quinzical.backend.models.Clue;
import a3.quinzical.frontend.helper.ScreenType;
import a3.quinzical.frontend.helper.ScreenSwitcher;
import a3.quinzical.backend.database.PracticeDatabase;

// Java dependencies.
import java.net.URL;
import java.util.ResourceBundle;

// JavaFX dependencies.
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

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

    private int _attemptsRemaining = 3;
    private final Speaker _speaker = Speaker.init();
    private final ScreenSwitcher _switcher = ScreenSwitcher.getInstance();
    private final Clue _clue = PracticeDatabase.getInstance().getSelected();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Displaying the information about the current question.
        clueLabel.setText(_clue.getQuestion());
        categoryLabel.setText(_clue.getCategory().getName());
        prefixPlaceholder.setText(_clue.getPrefix() + "...");
        hintPlaceholder.setText("Hint: the first character of the answer is '" + _clue.getAnswersList().get(0).charAt(0) + "'.");
        hintPlaceholder.setVisible(false);
        updateAttempts();

        // Speaking the question to the user.
        _speaker.setSpeech(_clue.getQuestion());
        _speaker.speak();
    };

    /**
     * This method is the listener for when the user presses "Don't Know" button.
     */
    @FXML
    private void handleDontKnowButton() {
        stopInput();
        incorrectAnswer();
    }

    /**
     * This is the listener for when the user presses the "Submit" button.
     */
    @FXML
    private void handleSubmitButton() {
        boolean isCorrect = _clue.checkAnswer(answerTextField.getText());
        if (isCorrect) {
            stopInput();
            correctAnswer();
            return;
        }

        // If incorrect, then reduce remaining attempts.
        _attemptsRemaining--;
        if (_attemptsRemaining < 1) {
            stopInput();
            incorrectAnswer();
        } else {
            answerTextField.clear();
            updateAttempts();
        }
    }

    /**
     * This is the listener for when the user presses the "Back To Categories" button.
     */
    @FXML
    private void handleBackButton () {
        _speaker.kill();
        _switcher.switchTo(ScreenType.PRACTICE_MODULE);
    }

    /**
     * This is the listener for when the user presses the "Respeak Clue" button.
     */
    @FXML
    private void handleRespeakButton() {
        _speaker.setSpeech(_clue.getQuestion());
        _speaker.speak();
    }


    /**
     * This is the handler for when the "Enter" button is clicked on text box.
     * @param event the key press event.
     */
    @FXML
    private void submitOnEnter(KeyEvent event) {
        switch (event.getCode()) {
            case ENTER:
                submitButton.fire();
                break;
        }
    }

    @FXML
    private void handleKeyboardButton(ActionEvent event) {
        Button pressed = (Button) event.getSource();
        answerTextField.setText(answerTextField.getText() + pressed.getText());
    }

    /**
     * This is a private method used to tell the user that they were correct.
     */
    private void correctAnswer() {
        String string = "Ka pai, you got it correct!";
        _speaker.setSpeech(string);
        _speaker.speak();
        attemptsLabel.setText(string);
    }

    /**
     * This is a private method used to display the correct answer to the user.
     */
    private void incorrectAnswer() {
        String string = "Oh no! The correct answer was " + _clue.getAnswersList().get(0);
        _speaker.setSpeech(string);
        _speaker.speak();
        attemptsLabel.setText(string);
    }

    /**
     * This is a private method used to update the number of attempts remaining on incorrect attempt.
     */
    private void updateAttempts() {
        // If there is one remaining attempt, then display the hint.
        if (_attemptsRemaining == 1) {
            hintPlaceholder.setVisible(true);
        }

        String text = "Attempts remaining: " + _attemptsRemaining;
        attemptsLabel.setText(text);
    }

    /**
     * This is a private method to stop inputs from the user, and clearing the screen.
     */
    private void stopInput() {
        _speaker.kill();
        submitButton.setVisible(false);
        respeakButton.setVisible(false);
        answerTextField.setDisable(true);
        dontKnowButton.setVisible(false);
        hintPlaceholder.setVisible(false);
    }

}
