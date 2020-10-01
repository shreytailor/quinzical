package a3.quinzical.frontend.controllers;

import a3.quinzical.backend.database.GameDatabase;
import a3.quinzical.backend.models.Clue;
import a3.quinzical.frontend.helper.ScreenType;
import a3.quinzical.frontend.helper.ScreenSwitcher;
import a3.quinzical.backend.database.PracticeDatabase;

// Java dependencies.
import java.net.URL;
import java.io.IOException;
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

    @FXML BorderPane root;
    @FXML Button backButton;
    @FXML ScrollPane scrollPane;

    private GridPane _gridPane;
    private PracticeDatabase _db = PracticeDatabase.getInstance();
    private ScreenSwitcher _switcher = ScreenSwitcher.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupGrid();
    }

    /**
     * This handler is for the "Back to Menu" button.
     */
    @FXML
    private void handleBackButton() {
        _switcher.setTitle("Main Menu");
        _switcher.setScreen(ScreenType.MAIN_MENU);
    }

    /**
     * This is a private method which is used by the initialize() method in this class, in order to
     * setup the GridPane programmatically. Here, we are reading the categories from the Databases
     * backend, and trying to populate the GUI with their respective buttons.
     */
    private void setupGrid() {
        _gridPane = new GridPane();
        int categories = _db.getCateSize();

        // Determine the rows and columns of the GridPane.
        int ROWS = 5;
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
                String category = _db.getCategory(tracker).getName();
                Button button = new Button(category);
                button.getStyleClass().add("categoryButton");
                button.getStylesheets().add(getClass().getClassLoader().getResource("a3/quinzical/frontend/styles/PracticeModule.css").toExternalForm());

                int finalTracker = tracker;
                button.setOnAction(action -> {
                    handleCategoryButton(finalTracker);
                });

                GridPane.setMargin(button, new Insets(12));

                // Adding the button to the grid.
                _gridPane.add(button, row, col);
                tracker++;
            }
        }

        // After GridPane is built, we're adding it to the parent ScrollPane, and then centering.
        scrollPane.setContent(_gridPane);
        _gridPane.translateXProperty().bind(scrollPane.widthProperty().subtract(_gridPane.widthProperty()).divide(2));
    }

    /**
     * This is a private method, and a handler for when the one of the categories is selected.
     * @param categoryNumber
     */
    private void handleCategoryButton(int categoryNumber) {
        PracticeDatabase db = PracticeDatabase.getInstance();
        Clue random = db.getCategory(categoryNumber).getRandom();
        PracticeDatabase.getInstance().select(random);

        try {
            ScreenSwitcher.getInstance().addScreen(ScreenType.PRACTICE_CLUE, FXMLLoader.load(getClass().getResource("./../fxml/PracticeClue.fxml")));
        } catch (IOException error) {  };

        ScreenSwitcher.getInstance().setScreen(ScreenType.PRACTICE_CLUE);
    }

}