package quinzical.frontend.controllers;
import quinzical.backend.Progression;
import quinzical.frontend.helper.ScreenType;
import quinzical.frontend.helper.ScreenSwitcher;

// Java dependencies.
import java.net.URL;
import java.util.ResourceBundle;

// JavaFX dependencies.
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class StatisticsController implements Initializable {

    @FXML Label successLabel;
    @FXML Label gamesLabel;
    @FXML Label winningsLabel;
    @FXML Label timeLabel;

    private final Progression progression = Progression.getInstance();
    private final ScreenSwitcher switcher = ScreenSwitcher.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Collecting all the data required to show to the user.
        String successPercent = String.valueOf(progression.getPercentage());
        String gamesCompleted = String.valueOf(progression.getGamesCompleted());
        String averageWinnings = String.valueOf(progression.getAverageWinning());
        String averageTime = String.valueOf(progression.getAverageTime());

        // Using the information above to set the new text of the components.
        successLabel.setText(successPercent + "%");
        gamesLabel.setText(gamesCompleted);
        winningsLabel.setText("$" + averageWinnings);
        timeLabel.setText(averageTime + "s");
    }

    @FXML
    private void handleBackButton() {
        switcher.switchTo(ScreenType.MAIN_MENU);
    }

}