package a3.quinzical.frontend.controllers;

import a3.quinzical.backend.Clue;

// Java dependencies.
import java.net.URL;
import java.util.ResourceBundle;

// JavaFX dependencies.
import a3.quinzical.backend.PracticeDatabase;
import a3.quinzical.backend.Speaker;
import a3.quinzical.frontend.switcher.ScreenSwitcher;
import a3.quinzical.frontend.switcher.ScreenType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


/**
 * This class is the controller class for the Practice Question screen.
 * @author Shrey Tailor, Jason Wang
 */
public class PracticeQuestionController implements Initializable {

    private Clue _clue = PracticeDatabase.getInstance().getSelected();
    private int _attemptsRemaining = 3;

    @FXML
    Label clueLabel;
    @FXML
    Label categoryLabel;
    @FXML
    TextField answerTextField;
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
        updateAttempts();

        Speaker speaker = Speaker.init();
        speaker.setSpeech(_clue.getQuestion());
        speaker.speak();
    };

    @FXML
    private void handleSubmitButton() {
        _attemptsRemaining--;
        updateAttempts();
    }

    @FXML
    private void handleBackButton () {
        Speaker.init().kill();
        ScreenSwitcher.getInstance().setScreen(ScreenType.PRACTICE_MODULE);
    }

    @FXML
    private void handleRespeakButton() {
        Speaker.init().speak();
    }

    private void updateAttempts() {
        String text = "You have " + String.valueOf(_attemptsRemaining) + " attempts remaining.";
        attemptsLabel.setText(text);
    }

}
