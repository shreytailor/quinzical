package quinzical.frontend.controllers;
import quinzical.backend.Progression;
import quinzical.backend.models.Clue;
import quinzical.frontend.helper.Speaker;
import quinzical.frontend.helper.ScreenType;
import quinzical.backend.database.GameDatabase;
import quinzical.frontend.helper.ScreenSwitcher;
import quinzical.backend.database.PracticeDatabase;

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
import javafx.scene.input.KeyEvent;
import javafx.scene.image.ImageView;
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

    private Timer timer;
    private int TIME_LIMIT = 30;
    private final Speaker speaker = Speaker.init();
    private final GameDatabase database = GameDatabase.getInstance();
    private final Progression progression = Progression.getInstance();
    private final Clue clue = GameDatabase.getInstance().getCurrentClue();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Create and start the timer.
        createTimer();

        // Display the information about the question.
        categoryLabel.setText(clue.getCategory().getName());
        prizeLabel.setText("$" + clue.getPrize());
        prefixLabel.setText(clue.getPrefix() + "...");

        // Speak the question to the user.
        speaker.setSpeech(clue.getQuestion());
        speaker.speak();

        // Hide certain elements of the screen at the beginning.
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
        speaker.speak();
    }

    @FXML
    private void handleBackButton() {
        // Kill the speaking process.
        speaker.kill();
        ScreenSwitcher switcher = ScreenSwitcher.getInstance();

        // If there are no remaining questions, then go to the completed game screen.
        if (GameDatabase.getInstance().getRemainingClues() > 0) {
            switcher.switchTo(ScreenType.GAME_MODULE);
            switcher.setTitle("Game Module");
        } else {
            // If all the questions are attempted, we update the game statistics.
            progression.gameFinished(database.getWinning());
            switcher.switchTo(ScreenType.GAME_FINISHED);
        }
    }

    @FXML
    private void submitOnEnter(KeyEvent event) {
        // Feature which enables users to press <ENTER> to submit answer.
        switch (event.getCode()) {
            case ENTER:
                submitButton.fire();
                break;
        }
    }

    @FXML
    private void handleKeyboardButton(ActionEvent event) {
        // When a button is pressed on the embedded keyboard, insert that character to the answer.
        Button button = (Button) event.getSource();
        inputField.setText(inputField.getText() + button.getText());
    }

    /**
     * This private method is used for doing specific tasks after the user has finished answering.
     * @param isChecking if we are checking whether the answer is correct or not.
     */
    private void isAnswered(Boolean isChecking) {
        cleanupScreen();

        boolean isCorrect = false;
        if (isChecking) {
            isCorrect = clue.checkAnswer(inputField.getText());
        }

        String message;
        if (isCorrect) {
            // If correct, update their total winnings, and give a nice message.
            database.updateWinning(clue.getPrize());
            message = "Ka pai, your answer was correct!";

            // Adding to the statistics of correct answer, and increasing total XP.
            int earnedXP = (clue.getPrize() / 10) + TIME_LIMIT;
            timerLabel.setText("+" + earnedXP + "XP");
            progression.answeredCorrect(30 - TIME_LIMIT);
            progression.addEXP(earnedXP);
        } else {
            progression.answeredWrongPlus();
            message = "Oh no! The correct answer was " + clue.getAnswersList().get(0);

            // Set this question as "needs practice" on the practice module.
            PracticeDatabase.getInstance().setMarkedCategory(clue.getCategory());

            // Hiding the timer label, because its not needed anymore.
            timerLabel.setVisible(false);
        }

        speaker.setSpeech(message);
        messageLabel.setText(message);
        speaker.speak();
    }

    /**
     * This method is used to do some clean-up on the screen after we stop accepting user input.
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

        // Stopping the timer when the question is answered.
        timer.purge();
        timer.cancel();
    }

    /**
     * This private method is used to start the timer when the user starts to answer a question.
     */
    private void createTimer() {
        timer = new Timer();

        // After each second...
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                if (TIME_LIMIT <= 0) {
                    // If no time remaining, then submit by default.
                    timer.cancel();
                    Platform.runLater(() -> {
                        submitButton.fire();
                    });
                } else {
                    // If time remaining, then update the timer label.
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