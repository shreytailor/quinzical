package a3.quinzical.frontend.controllers;

import a3.quinzical.backend.Speaker;
import a3.quinzical.backend.models.Clue;
import a3.quinzical.frontend.helper.ScreenType;
import a3.quinzical.backend.database.GameDatabase;
import a3.quinzical.frontend.helper.ScreenSwitcher;

// Java dependencies.
import java.net.URL;
import java.io.IOException;
import java.util.ResourceBundle;

// JavaFX dependencies.
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * This class is the controller class for the Game Question screen.
 * @author Shrey Tailor, Jason Wang
 */
public class GameClueController implements Initializable {

    @FXML Label categoryLabel;
    @FXML Label prizeLabel;
    @FXML Label prefixLabel;
    @FXML TextField inputField;
    @FXML Button dontKnowButton;
    @FXML Button submitButton;
    @FXML Label messageLabel;
    @FXML Button respeakButton;
    @FXML Button backButton;

    private Speaker _speaker = Speaker.init();
    private GameDatabase _db = GameDatabase.getInstance();
    private Clue _clue = GameDatabase.getInstance().getCurrentClue();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Showing information about the question.
        categoryLabel.setText(_clue.getCategory().getName());
        prizeLabel.setText("$" + _clue.getPrize());
        prefixLabel.setText(_clue.getPrefix() + "...");

        // Speak the question to the user.
        _speaker.setSpeech(_clue.getQuestion());
        _speaker.speak();

        // Hiding certain elements of the screen on Initialization.
        backButton.setVisible(false);
        messageLabel.setVisible(false);
    }

    /**
     * This is the handler for when the "Don't Know" button is pressed.
     */
    @FXML
    private void handleDontKnowButton() {
        isAnswered(false);
    }

    /**
     * This is the handler for when the "Submit" button is pressed.
     */
    @FXML
    private void handleSubmitButton() {
        isAnswered(true);
    }

    /**
     * This is the handler for when the "Respeak" button is pressed.
     */
    @FXML
    private void handleRespeakButton() {
        _speaker.speak();
    }

    /**
     * This is the handler for when the "Back" button is pressed.
     */
    @FXML
    private void handleBackButton() {
        // Stop the speaking process.
        _speaker.kill();
        ScreenSwitcher _switcher = ScreenSwitcher.getInstance();

        try {
            // If there are no remaining questions, then go to the completed game screen.
            if (GameDatabase.getInstance().getRemainingClues() > 0) {
                _switcher.addScreen(ScreenType.GAME_MODULE, FXMLLoader.load(getClass().getResource("./../fxml/GameModule.fxml")));
                _switcher.setScreen(ScreenType.GAME_MODULE);
                _switcher.setTitle("Game Module");
            } else {
                _switcher.addScreen(ScreenType.GAME_FINISHED, FXMLLoader.load(getClass().getResource("./../fxml/GameFinished.fxml")));
                _switcher.setScreen(ScreenType.GAME_FINISHED);
            }
        } catch (IOException error) {  };
    }

    /**
     * This private method is used for doing certain things, after the user has finished answering.
     * @param isChecking
     */
    private void isAnswered(Boolean isChecking) {
        // Setting certain elements to hidden to clear the screen.
        _speaker.kill();
        inputField.setDisable(true);
        backButton.setVisible(true);
        messageLabel.setVisible(true);
        submitButton.setVisible(false);
        dontKnowButton.setVisible(false);
        respeakButton.setVisible(false);

        // Process of checking the answer to check if it was correct.
        boolean isCorrect = false;
        if (isChecking) {
            isCorrect = _clue.checkAnswer(inputField.getText());
        }

        // Speaking and displaying the message to the user.
        String message;
        if (isCorrect) {
            _db.updateWinning(_clue.getPrize());
            message = "Yay, your answer was correct!";
        } else {
            message = "Oh no! The correct answer was " + _clue.getAnswer();
        }

        _speaker.setSpeech(message);
        messageLabel.setText(message);
        _speaker.speak();

    }

}
