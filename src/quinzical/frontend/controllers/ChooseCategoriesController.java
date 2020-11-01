package quinzical.frontend.controllers;
import quinzical.backend.models.Category;
import quinzical.frontend.helper.AlertHelper;
import quinzical.frontend.helper.ScreenType;
import quinzical.backend.database.GameDatabase;
import quinzical.frontend.helper.ScreenSwitcher;
import quinzical.backend.database.PracticeDatabase;

// Java dependencies.
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

// JavaFX dependencies.
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ScrollPane;
import javafx.collections.FXCollections;
import javafx.scene.control.ToggleButton;
import javafx.collections.ObservableList;

public class ChooseCategoriesController implements Initializable {

    @FXML
    ScrollPane scrollPane;

    PracticeDatabase database;
    ObservableList<ToggleButton> buttons;
    ObservableList<Category> selectedCategories;
    ScreenSwitcher switcher = ScreenSwitcher.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initializing the list which contains the selected items.
        database = PracticeDatabase.getInstance();
        buttons = FXCollections.observableArrayList();
        selectedCategories = FXCollections.observableArrayList();

        // Getting the current categories, and creating the grid.
        createButtons();
        createGrid();
    }

    @FXML
    private void handleBackButton() {
        switcher.setTitle("Main Menu");
        switcher.switchTo(ScreenType.MAIN_MENU);
    }

    @FXML
    private void handleRandomButton() {
        deselectAll();
        randomize();
    }

    @FXML
    private void handleStartButton() {
        if (selectedCategories.size() == 5) {
            GameDatabase.getInstance(selectedCategories);
            switcher.switchTo(ScreenType.GAME_MODULE);
        } else {
            AlertHelper.getInstance().showAlert(Alert.AlertType.ERROR, "Please select five categories to proceed.", ButtonType.OK);
        }
    }

    /**
     * This method is used to just create the buttons that are required for each of the categories,
     * and after doing that, they are placed within an Observable List to be accessed later.
     */
    private void createButtons() {
        for (int counter = 0; counter < database.getCateSize(); counter++) {
            Category category = database.getCategory(counter);

            // Creating the toggle button, and styling it as per needed.
            ToggleButton button = new ToggleButton(category.getName());
            button.getStyleClass().add("toggleButton");
            button.getStylesheets().add(getClass().getClassLoader().getResource(
                    "quinzical/resources/styles/ToggleButtons.css").toExternalForm()
            );

            // Adding listeners to each button, which are triggered when checked/unchecked.
            button.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == true) {
                    addItem(category, button);
                } if (newValue == false) {
                    removeItem(category);
                }
            });

            buttons.add(button);
        }
    }

    /**
     * This method is used to setup the grid using the buttons that we created for categories
     * earlier, and then placing the grid within the main view.
     */
    private void createGrid() {
        GridPane gridPane = new GridPane();

        // Determining the size of the grid, to use in the for-loop.
        int ROWS = 5;
        int COLS = (buttons.size() / ROWS) + 1;

        int counter = 0;
        for (int col = 0; col < COLS; col++) {
            for (int row = 0; row < ROWS; row++) {
                if (counter >= buttons.size()) {
                    break;
                }

                // Get the corresponding button, and add it.
                ToggleButton button = buttons.get(counter);
                GridPane.setMargin(button, new Insets(12));
                gridPane.add(button, row, col);
                counter++;
            }
        }

        scrollPane.setContent(gridPane);
    }

    /**
     * This method is used to add an item to the list of selected categories. Note that this is
     * triggered when one of the buttons are selected.
     */
    private void addItem(Category category, ToggleButton button) {
        if (selectedCategories.size() < 5) {
            selectedCategories.add(category);
        } else {
            button.setSelected(false);
        }
    }

    /**
     * This method is used to delete an item from the list of selected categories. Note that this
     * is triggered when one of the buttons are deselected.
     */
    private void removeItem(Category category) {
        selectedCategories.remove(category);
    }

    /**
     * This method is used to deselect all the category buttons.
     */
    private void deselectAll() {
        for (int counter = 0; counter < buttons.size(); counter++) {
            buttons.get(counter).setSelected(false);
        }
    }

    /**
     * This method is used to select a random set of five categories from the list given.
     */
    private void randomize() {
        ObservableList<ToggleButton> buttonCopy = FXCollections.observableArrayList(buttons);
        Collections.shuffle(buttonCopy);

        for (int counter = 0; counter < 5; counter++) {
            buttonCopy.get(counter).setSelected(true);
        }
    }

}
