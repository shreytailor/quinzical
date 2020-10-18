package a3.quinzical.frontend.controllers;

import a3.quinzical.backend.models.Clue;
import a3.quinzical.frontend.helper.Speaker;
import a3.quinzical.frontend.helper.ScreenType;
import a3.quinzical.backend.database.GameDatabase;
import a3.quinzical.frontend.helper.ScreenSwitcher;

// Java dependencies.
import java.net.URL;
import java.util.Timer;
import java.util.ResourceBundle;
import java.util.TimerTask;

// JavaFX dependencies.
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.TextField;

/**
 * This class is the controller class for the Game Question screen.
 * @author Shrey Tailor, Jason Wang
 */
public class GameClueController implements Initializable {

    @FXML Label prizeLabel;
    @FXML Label timerLabel;
    @FXML Label prefixLabel;
    @FXML Button backButton;
    @FXML Label messageLabel;
    @FXML Button submitButton;
    @FXML Label categoryLabel;
    @FXML TextField inputField;
    @FXML Button respeakButton;
    @FXML Button dontKnowButton;

    private Timer _timer;
    private int TIME_LIMIT = 60;
    private final Speaker _speaker = Speaker.init();
    private final GameDatabase _db = GameDatabase.getInstance();
    private final Clue _clue = GameDatabase.getInstance().getCurrentClue();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Create the timer.
        createTimer();

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

        // If there are no remaining questions, then go to the completed game screen.
        if (GameDatabase.getInstance().getRemainingClues() > 0) {
            _switcher.switchTo(ScreenType.GAME_MODULE);
            _switcher.setTitle("Game Module");
        } else {
            _switcher.switchTo(ScreenType.GAME_FINISHED);
        }
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

    /**
     * This private method is used for doing certain things, after the user has finished answering.
     * @param isChecking if we are checking whether the answer is correct or not.
     */
    private void isAnswered(Boolean isChecking) {
        // Setting certain elements to hidden to clear the screen.
        _timer.purge();
        inputField.setDisable(true);
        backButton.setVisible(true);
        messageLabel.setVisible(true);
        submitButton.setVisible(false);
        dontKnowButton.setVisible(false);
        respeakButton.setVisible(false);
        timerLabel.setVisible(false);

        // Process of checking the answer to check if it was correct.
        boolean isCorrect = false;
        if (isChecking) {
            isCorrect = _clue.checkAnswer(inputField.getText());
        }

        // Speaking and displaying the message to the user.
        String message;
        if (isCorrect) {
            _db.updateWinning(_clue.getPrize());
            message = "Ka pai, your answer was correct!";
        } else {
            message = "Oh no! The correct answer was " + _clue.getAnswersList().get(0);
        }

        _speaker.setSpeech(message);
        messageLabel.setText(message);
        _speaker.speak();
    }

    /**
     * This private method is used to start the timer when the user starts to answer a question.
     */
    private void createTimer() {
        _timer = new Timer();

        // Doing something after a given period.
        _timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (TIME_LIMIT <= 0) {
                    _timer.cancel();
                    Platform.runLater(() -> {
                        submitButton.fire();
                    });
                } else {
                    Platform.runLater(() -> {
                        timerLabel.setText(TIME_LIMIT + "s");
                    });

                    // Decreasing the time remaining.
                    TIME_LIMIT--;
                }
            }
        }, 0,1000);
    }

}
