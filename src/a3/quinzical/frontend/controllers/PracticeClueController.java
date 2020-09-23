package a3.quinzical.frontend.controllers;

import a3.quinzical.backend.Clue;
import a3.quinzical.backend.Speaker;
import a3.quinzical.backend.PracticeDatabase;
import a3.quinzical.frontend.switcher.ScreenType;
import a3.quinzical.frontend.switcher.ScreenSwitcher;

// Java dependencies.
import java.net.URL;
import java.util.ResourceBundle;

// JavaFX dependencies.
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;


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
        updateAttempts();

        Speaker speaker = Speaker.init();
        speaker.setSpeech(_clue.getQuestion());
        speaker.speak();
    };

    @FXML
    private void onKeyPressed(KeyEvent event) {
        switch(event.getCode()) {
            case B:
                backButton.fire();
                break;
            case R:
                respeakButton.fire();
                break;
            case ENTER:
                submitButton.fire();
                break;
        }
    }

    @FXML
    private void handleDontKnowButton() {
        stopInput();
        incorrectAnswer();
    }

    @FXML
    private void handleSubmitButton() {
        boolean isCorrect = _clue.checkAnswer(answerTextField.getText());
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

    @FXML
    private void handleBackButton () {
        Speaker.init().kill();
        ScreenSwitcher.getInstance().setScreen(ScreenType.PRACTICE_MODULE);
    }

    @FXML
    private void handleRespeakButton() {
        Speaker.init().setSpeech(_clue.getQuestion());
        Speaker.init().speak();
    }

    private void updateAttempts() {
        String text = "You have " + String.valueOf(_attemptsRemaining) + " attempts remaining.";
        attemptsLabel.setText(text);
    }

    private void stopInput() {
        Speaker.init().kill();
        answerTextField.setDisable(true);
        dontKnowButton.setDisable(true);
        submitButton.setDisable(true);
    }

    private void correctAnswer() {
        Speaker.init().setSpeech("Ka pai, you got it correct!");
        Speaker.init().speak();
        attemptsLabel.setText("Ka pai, you got it correct!");
    }

    private void incorrectAnswer() {
        Speaker.init().setSpeech("The correct answer was " + _clue.getAnswer());
        Speaker.init().speak();
        attemptsLabel.setText("The correct answer was '" + _clue.getAnswer() + "'");
    }

}
