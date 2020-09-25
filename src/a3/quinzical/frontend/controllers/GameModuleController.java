package a3.quinzical.frontend.controllers;

import a3.quinzical.backend.GameDatabase;
import a3.quinzical.frontend.switcher.ScreenType;
import a3.quinzical.frontend.switcher.ScreenSwitcher;

// Java dependencies.
import java.net.URL;
import java.util.ResourceBundle;

// JavaFX dependencies.
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;


public class GameModuleController implements Initializable {

    @FXML
    Label winningsLabel;
    @FXML
    Button backButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupScreen();
    }


    @FXML
    private void handleKeyPressed (KeyEvent event) {
        switch (event.getCode()) {
            case B:
                backButton.fire();
        }
    }


    @FXML
    private void handleBackButton () {
        ScreenSwitcher screenSwitcher = ScreenSwitcher.getInstance();
        screenSwitcher.setScreen(ScreenType.MAIN_MENU);
        screenSwitcher.setTitle("Main Menu");
    }


    /**
     * This is a private method used only within the class, and its used to initialize the screen
     * on two different scenarios.
     * (1) When the screen is first launched by the user from the Main Menu.
     * (2) If the game is reset by the user, we must also reset the screen to display the latest
     *      information about their session.
     *
     * Hence, since we had to do this action in more than one scenario, we are doing some code
     * re-usage by making another method for it.
     */
    private void setupScreen() {
        // Updating the winnings label to show the latest winnings.
        int winnings = GameDatabase.getInstance().getWinning();
        winningsLabel.setText("$" + winnings);
    }

}
