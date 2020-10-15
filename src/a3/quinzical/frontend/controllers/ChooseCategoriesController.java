package a3.quinzical.frontend.controllers;
import a3.quinzical.backend.models.Category;
import a3.quinzical.frontend.helper.ScreenType;
import a3.quinzical.frontend.helper.ScreenSwitcher;
import a3.quinzical.backend.database.PracticeDatabase;

// Java dependencies.
import java.net.URL;
import java.util.ResourceBundle;

// JavaFX dependencies.
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ScrollPane;
import javafx.collections.FXCollections;
import javafx.scene.control.ToggleButton;
import javafx.collections.ObservableList;

public class ChooseCategoriesController implements Initializable {

    @FXML
    ScrollPane scrollPane;

    PracticeDatabase _database;
    ObservableList<Category> _selectedCategories;
    ScreenSwitcher _switcher = ScreenSwitcher.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initializing the list which contains the selected items.
        _database = PracticeDatabase.getInstance();
        _selectedCategories = FXCollections.observableArrayList();

        // Delegating to another method to populate the grid with categories.
        setupGrid();
    }

    @FXML
    private void handleBackButton() {
        _switcher.setTitle("Main Menu");
        _switcher.switchTo(ScreenType.MAIN_MENU);
    }

    /**
     * This method is used to populate the grid with categories that are available for the user
     * to choose. It's a long method, because we are populating the grid for each item.
     */
    private void setupGrid() {
        GridPane gridPane = new GridPane();

        // Determining the size of the grid, to use in the for-loop.
        int ROWS = 5;
        int COLS = (_database.getCateSize() / ROWS) + 1;

        int counter = 0;
        for (int col = 0; col < COLS; col++) {
            for (int row = 0; row < ROWS; row++) {
                // If we run out of categories, then finish the process.
                if (counter >= _database.getCateSize()) {
                    break;
                }

                Category category = _database.getCategory(counter);

                // Creating the toggle button, and styling it as per needed.
                ToggleButton button = new ToggleButton(category.getName());
                button.getStyleClass().add("toggleButton");
                button.getStylesheets().add(getClass().getClassLoader().getResource(
                        "a3/quinzical/frontend/styles/ToggleButtons.css").toExternalForm()
                );

                // Adding listeners to each button, which are triggered when checked/unchecked.
                button.selectedProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue == true) {
                        addItem(category, button);
                    } if (newValue == false) {
                        removeItem(category);
                    }
                });

                GridPane.setMargin(button, new Insets(12));
                gridPane.add(button, row, col);
                counter++;
            }
        }

        // Add the created grid to the final scroll pane.
        scrollPane.setContent(gridPane);
    }

    @FXML
    private void handleRandomButton() {
        _selectedCategories.clear();

    }

    /**
     * This method is used to add an item to the list of selected categories.
     */
    private void addItem(Category category, ToggleButton button) {
        if (_selectedCategories.size() < 5) {
            _selectedCategories.add(category);
        } else {
            button.setSelected(false);
        }
    }

    /**
     * This method is used to delete an item from the list of selected categories.
     */
    private void removeItem(Category category) {
        _selectedCategories.remove(category);
    }

}
