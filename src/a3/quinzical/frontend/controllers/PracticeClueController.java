package a3.quinzical.frontend.controllers;

import a3.quinzical.backend.Speaker;
import a3.quinzical.backend.models.Clue;
import a3.quinzical.frontend.switcher.ScreenType;
import a3.quinzical.frontend.switcher.ScreenSwitcher;
import a3.quinzical.backend.database.PracticeDatabase;

// Java dependencies.
import java.net.URL;
import java.util.ResourceBundle;

// JavaFX dependencies.
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


/**
 * This class is the controller class for the Practice Question screen.
 * @author Shrey Tailor, Jason Wang
 */
public class PracticeClueController implements Initializable {

    private int _attemptsRemaining = 3;
    private Clue _clue = PracticeDatabase.getInstance().getSelected();

    @FXML
    Label clueLabel;
    @FXML
    Label categoryLabel;
    @FXML
    Label prefixPlaceholder;
    @FXML
    TextField answerTextField;
    @FXML
    Button dontKnowButton;
    @FXML
    Button submitButton;
    @FXML
    Label attemptsLabel;
    @FXML
    Button backButton;
    @FXML
    Button respeakButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clueLabel.setText(_clue.getQuestion());
        categoryLabel.setText(_clue.getCategory().getName());
        prefixPlaceholder.setText(_clue.getPrefix());
        updateAttempts();

        Speaker speaker = Speaker.init();
        speaker.setSpeech(_clue.getQuestion());
        speaker.speak();
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
        boolean isCorrect = _clue.checkAnswer(answerTextField.getText().trim());
        if (isCorrect) {
            stopInput();
            correctAnswer();
            return;
        }

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
        Speaker.init().kill();
        ScreenSwitcher.getInstance().setScreen(ScreenType.PRACTICE_MODULE);
    }


    /**
     * This is the listener for when the user presses the "Respeak Clue" button.
     */
    @FXML
    private void handleRespeakButton() {
        Speaker.init().setSpeech(_clue.getQuestion());
        Speaker.init().speak();
    }


    /**
     * This is the method which is used when the user gets the answer correct.
     */
    private void correctAnswer() {
        Speaker.init().setSpeech("Ka pai, you got it correct!");
        Speaker.init().speak();
        attemptsLabel.setText("Ka pai, you got it correct!");
    }


    /**
     * This is the method used to display the answer to the user when all attempts are used up.
     */
    private void incorrectAnswer() {
        Speaker.init().setSpeech("The correct answer was " + _clue.getAnswer());
        Speaker.init().speak();
        attemptsLabel.setText("The correct answer was '" + _clue.getAnswer() + "'");
    }


    /**
     * This is a method used to update the number of attempts the user has remaining when they get
     * it incorrect.
     */
    private void updateAttempts() {
        String text = "You have " + String.valueOf(_attemptsRemaining) + " attempts remaining.";
        attemptsLabel.setText(text);
    }


    /**
     * This is a method to stop inputs to all the text fields, and make some of the buttons disabled.
     */
    private void stopInput() {
        Speaker.init().kill();
        submitButton.setVisible(false);
        respeakButton.setVisible(false);
        answerTextField.setDisable(true);
        dontKnowButton.setVisible(false);
    }

}
