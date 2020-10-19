package a3.quinzical.frontend.controllers;

import a3.quinzical.backend.database.GameDatabase;
import a3.quinzical.frontend.helper.AlertHelper;
import a3.quinzical.frontend.helper.ScreenSwitcher;
import a3.quinzical.frontend.helper.ScreenType;

// JavaFX dependencies.
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ButtonType;

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
        String message = "Are you sure you want to reset the game?";
        AlertHelper _helper = AlertHelper.getInstance();
        _helper.showAlert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);

        // If the user confirmed that they want to reset, then we are recreating the database.
        if (_helper.getResult() == ButtonType.YES) {
            GameDatabase.kill();
            _switcher.switchTo(ScreenType.CHOOSE_CATEGORIES);
        }
    }

}
