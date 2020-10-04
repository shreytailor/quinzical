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

    private GameDatabase _db = GameDatabase.getInstance();
    private ScreenSwitcher _switcher = ScreenSwitcher.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        winningsPlaceholder.setText("You have won a total of $" + _db.getWinning() + ".");
    }

    /**
     * This is the handler used when the "Back" button is pressed.
     */
    @FXML
    public void handleBackButton() {
        _switcher.switchTo(ScreenType.MAIN_MENU);
        _switcher.setTitle("Main Menu");
    }

    /**
     * This is the handler used when the "Reset" button is pressed.
     */
    @FXML
    public void handleResetButton() {
        String message = "Are you sure you want to reset the game?";
        AlertHelper _helper = AlertHelper.getInstance();
        _helper.showAlert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);

        if (_helper.getResult() == ButtonType.YES) {
            GameDatabase.kill();
            _switcher.switchTo(ScreenType.GAME_MODULE);
        }
    }

}
