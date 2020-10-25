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

    private final GameDatabase _db = GameDatabase.getInstance();
    private final ScreenSwitcher _switcher = ScreenSwitcher.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Telling the user the amount that they accumulated.
        winningsPlaceholder.setText("You have won a total of $" + _db.getWinning() + ".");
    }

    @FXML
    public void handleBackButton() {
        // Go back to the main menu.
        _switcher.switchTo(ScreenType.MAIN_MENU);
        _switcher.setTitle("Main Menu");
    }

    @FXML
    public void handleResetButton() {
        /**
         * Here, there is no need to confirm with the user whether they want to reset the game or
         * not, because the game has been completed anyways.
         */
        GameDatabase.kill();
        _switcher.switchTo(ScreenType.CHOOSE_CATEGORIES);
    }
}
