package quinzical.frontend.controllers;
import quinzical.backend.Progression;
import quinzical.backend.database.PracticeDatabase;
import quinzical.backend.models.Clue;
import quinzical.frontend.helper.Speaker;
import quinzical.frontend.helper.ScreenType;
import quinzical.backend.database.GameDatabase;
import quinzical.frontend.helper.ScreenSwitcher;

// Java dependencies.
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ResourceBundle;

// JavaFX dependencies.
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
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
    @FXML ImageView timerHelp;
    @FXML Button submitButton;
    @FXML Label categoryLabel;
    @FXML TextField inputField;
    @FXML Button respeakButton;
    @FXML Button dontKnowButton;
    @FXML GridPane keyboardGridPane;

    private Timer _timer;
    private int TIME_LIMIT = 30;
    private final Speaker _speaker = Speaker.init();
    private final GameDatabase _db = GameDatabase.getInstance();
    private final Progression progression = Progression.getInstance();
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

    @FXML
    private void handleDontKnowButton() {
        isAnswered(false);
    }

    @FXML
    private void handleSubmitButton() {
        isAnswered(true);
    }

    @FXML
    private void handleRespeakButton() {
        _speaker.speak();
    }

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
            // If all the questions are completed, we are increasing the statistics counter here.
            progression.gameFinished(_db.getWinning());
            _switcher.switchTo(ScreenType.GAME_FINISHED);
        }
    }

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
        Button button = (Button) event.getSource();
        inputField.setText(inputField.getText() + button.getText());
    }

    /**
     * This private method is used for doing certain things, after the user has finished answering.
     * @param isChecking if we are checking whether the answer is correct or not.
     */
    private void isAnswered(Boolean isChecking) {
        cleanupScreen();

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

            // Adding to the statistics of correct answer, and increasing XP.
            int earnedXP = (_clue.getPrize() / 10) + TIME_LIMIT;
            timerLabel.setText("+" + earnedXP + "XP");
            progression.answeredCorrect(30 - TIME_LIMIT);
            progression.addEXP(earnedXP);
        } else {
            // Adding to the count of total incorrect answers.
            progression.answeredWrongPlus();
            message = "Oh no! The correct answer was " + _clue.getAnswersList().get(0);

            // Set this question as "needs practice" on Practice Module.
            PracticeDatabase.getInstance().setMarkedCategory(_clue.getCategory());

            // Hiding the timer label, because its not needed anymore.
            timerLabel.setVisible(false);
        }

        _speaker.setSpeech(message);
        messageLabel.setText(message);
        _speaker.speak();
    }

    /**
     * This method is used to perform the clean-up on the current screen after we stop accepting user
     * input. This is done by hiding some of the elements such as the extra buttons on the screen.
     */
    private void cleanupScreen() {
        // Setting certain elements to hidden to clear the screen.
        inputField.setDisable(true);
        backButton.setVisible(true);
        messageLabel.setVisible(true);
        submitButton.setVisible(false);
        respeakButton.setVisible(false);
        dontKnowButton.setVisible(false);
        keyboardGridPane.setVisible(false);
        timerHelp.setVisible(false);

        // Cancelling the timer when the question is answered.
        _timer.purge();
        _timer.cancel();
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
