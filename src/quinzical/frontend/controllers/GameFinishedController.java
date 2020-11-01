package quinzical.frontend.controllers;
import quinzical.frontend.helper.ScreenType;
import quinzical.backend.database.GameDatabase;
import quinzical.frontend.helper.ScreenSwitcher;

// JavaFX dependencies.
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

// Java dependencies.
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This is the controller class for the GameFinished screen.
 * @author Shrey Tailor, Jason Wang
 */
public class GameFinishedController implements Initializable {
    @FXML Label winningsPlaceholder;

    private final GameDatabase database = GameDatabase.getInstance();
    private final ScreenSwitcher switcher = ScreenSwitcher.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Inform user about their overall winnings.
        winningsPlaceholder.setText("You have won a total of $" + database.getWinning() + ".");
    }

    @FXML
    public void handleBackButton() {
        switcher.switchTo(ScreenType.MAIN_MENU);
        switcher.setTitle("Main Menu");
    }

    @FXML
    public void handleResetButton() {
        /**
         * Here, there is no need to confirm with the user whether they want to reset the game or
         * not, because the game has been completed anyways so we directly perform reset.
         */
        GameDatabase.kill();
        switcher.switchTo(ScreenType.CHOOSE_CATEGORIES);
    }
}
