package a3.quinzical.frontend.controllers;

import a3.quinzical.backend.database.GameDatabase;
import a3.quinzical.frontend.helper.ScreenSwitcher;
import a3.quinzical.frontend.helper.ScreenType;

// JavaFX dependencies.
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class GameFinishedController implements Initializable {

    @FXML
    Label winningsPlaceholder;

    private GameDatabase _db = GameDatabase.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        winningsPlaceholder.setText("You have won a total of $" + _db.getWinning() + ".");
    }

    @FXML
    public void handleBackButton() {
        ScreenSwitcher.getInstance().setScreen(ScreenType.MAIN_MENU);
        ScreenSwitcher.getInstance().setTitle("Main Menu");
    }

    @FXML
    public void handleResetButton() {
        String message = "Are you sure you want to reset the game?";
        Alert resetAlert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
        resetAlert.setHeaderText(null);
        resetAlert.showAndWait();

        if (resetAlert.getResult() == ButtonType.YES) {
            GameDatabase.kill();

            try {
                ScreenSwitcher.getInstance().addScreen(ScreenType.GAME_MODULE, FXMLLoader.load(getClass().getResource("./../fxml/GameModule.fxml")));
            } catch (Exception error) {  };
            ScreenSwitcher.getInstance().setScreen(ScreenType.GAME_MODULE);
        }
    }

}
