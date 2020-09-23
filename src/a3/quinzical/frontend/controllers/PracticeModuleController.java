package a3.quinzical.frontend.controllers;

import a3.quinzical.backend.Clue;
import a3.quinzical.backend.PracticeDatabase;
import a3.quinzical.frontend.switcher.ScreenType;
import a3.quinzical.frontend.switcher.ScreenSwitcher;

// Java dependencies.
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

// JavaFX dependencies.
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ScrollPane;
import javafx.stage.Screen;


/**
 * This class is the controller class for the Practice Module screen.
 * @author Shrey Tailor, Jason Wang
 */
public class PracticeModuleController implements Initializable {

    private GridPane _gridPane;

    @FXML
    BorderPane root;
    @FXML
    Button backButton;
    @FXML
    ScrollPane scrollPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupGrid();
    }

    /**
     * This method is the listener for when a key is pressed. It is used to add shortcuts.
     * @param event the key event from which we can extract the key pressed.
     */
    @FXML
    private void onKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case B:
                backButton.fire();
                break;
        }
    }

    /**
     * This method is the listener for the Back to Menu button.
     */
    @FXML
    private void handleBackButton() {
        ScreenSwitcher screenSwitcher = ScreenSwitcher.getInstance();
        screenSwitcher.setTitle("Main Menu");
        screenSwitcher.setScreen(ScreenType.MAIN_MENU);
    }

    private void handleCategoryButton(int categoryNumber) {
        PracticeDatabase db = PracticeDatabase.getInstance();
        Clue random = db.getCategory(categoryNumber).getRandom();
        PracticeDatabase.getInstance().select(random);

        try {
            ScreenSwitcher.getInstance().addScreen(ScreenType.PRACTICE_CLUE, FXMLLoader.load(getClass().getResource("./../fxml/PracticeClue.fxml")));
        } catch(IOException error) { }
        ScreenSwitcher.getInstance().setScreen(ScreenType.PRACTICE_CLUE);
    }

    /**
     * This is a private method which is used by the initialize() method in this class, in order to
     * setup the GridPane programmatically. Here, we are reading the categories from the Databases
     * backend, and trying to populate the GUI with their respective buttons.
     */
    private void setupGrid() {
        _gridPane = new GridPane();

        // Find the number of categories in the database.
        PracticeDatabase database = PracticeDatabase.getInstance();
        int categories = database.getCateSize();

        // Determine the rows and columns of the GridPane.
        int ROWS = 6;
        int COLS = (categories / ROWS) + 1;

        int tracker = 0;

        // Loop through the columns and rows of the GridPane and add the buttons.
        for (int col = 0; col < COLS; col++) {
            for (int row = 0; row < ROWS; row++) {
                // If we finish all the categories, then finish both the loops.
                if (tracker >= categories) {
                    break;
                }

                // Getting the information of the current category and creating its button.
                String category = database.getCategory(tracker).getName();
                Button button = new Button(category);
                button.setPrefWidth(195);
                button.setPrefHeight(90);
                int finalTracker = tracker;
                button.setOnAction(action -> {
                    handleCategoryButton(finalTracker);
                });
                GridPane.setMargin(button, new Insets(20, 10, 20, 10));

                // Adding the button to the grid.
                _gridPane.add(button, row, col);
                tracker++;
            }
        }

        // After the GridPane is built, we are adding it to the parent ScrollPane.
        scrollPane.setContent(_gridPane);
    }

}