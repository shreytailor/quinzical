package quinzical.frontend.controllers;
import quinzical.backend.models.Clue;
import quinzical.backend.models.Category;
import quinzical.frontend.helper.ScreenType;
import quinzical.frontend.helper.ScreenSwitcher;
import quinzical.backend.database.PracticeDatabase;

// Java dependencies.
import java.net.URL;
import java.util.ResourceBundle;

// JavaFX dependencies.
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ScrollPane;

/**
 * This class is the controller class for the Practice Module screen.
 * @author Shrey Tailor, Jason Wang
 */
public class PracticeModuleController implements Initializable {
    @FXML BorderPane root;
    @FXML Button backButton;
    @FXML ScrollPane scrollPane;

    private GridPane gridPane;
    private final ScreenSwitcher switcher = ScreenSwitcher.getInstance();
    private final PracticeDatabase database = PracticeDatabase.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupGrid();
    }

    @FXML
    private void handleBackButton() {
        switcher.switchTo(ScreenType.MAIN_MENU);
        switcher.setTitle("Main Menu");
    }

    /**
     * This is a private method used to create the grid that shows all the category buttons.
     */
    private void setupGrid() {
        gridPane = new GridPane();
        int categories = database.getCateSize();

        // Define the rows and columns of the GridPane.
        int ROWS = 5;
        int COLS = (categories / ROWS) + 1;

        int col;
        int row = 0;
        int tracker = 0;

        // Loop through the columns and rows of the GridPane and add the buttons.
        for (col = 0; col < COLS; col++) {
            for (row = 0; row < ROWS; row++) {
                // If we finish all the categories, then terminate the loops.
                if (tracker >= categories) {
                    break;
                }

                // Getting the information of the current category and creating its button.
                Category category = database.getCategory(tracker);
                String categoryName = category.getName();
                Button button = buttonGenerator(categoryName);

                // Applying different styles to mark the category that needs to be practised.
                Category marked = database.getMarkedCategory();
                if (marked != null && categoryName.equals(marked.getName())) {
                    button.getStyleClass().add("markedButton");
                }

                // Adding the listener to open the clue screen, if pressed.
                int finalTracker = tracker;
                button.setOnAction(action -> {
                    handleCategoryButton(category);
                });

                // Adding the button to the grid.
                gridPane.add(button, row, col);
                tracker++;
            }
        }

        addInternationalCategory(row, col - 1);

        // After the grid is built, we finally add it to the screen.
        scrollPane.setContent(gridPane);
        gridPane.translateXProperty().bind(scrollPane.widthProperty().subtract(gridPane.widthProperty()).divide(2));
    }

    /**
     * This is a private method (handler) for when the a category is selected.
     * @param category the category that the user wants to practice.
     */
    private void handleCategoryButton(Category category) {
        // Generate a random clue from the category, and show it to the user.
        Clue random = category.getRandom();
        database.select(random);
        switcher.switchTo(ScreenType.PRACTICE_CLUE);
    }

    /**
     * This private method is used to generate buttons to reduce the code-duplication.
     * @param categoryName the category which was selected.
     * @return Button a button for the selected category.
     */
    private Button buttonGenerator(String categoryName) {
        Button button = new Button(categoryName);
        button.setWrapText(true);
        button.getStyleClass().add("categoryButton");
        button.getStylesheets().add(getClass().getClassLoader().getResource("quinzical/resources/styles/PracticeModule.css").toExternalForm());
        GridPane.setMargin(button, new Insets(12));
        return button;
    }

    /**
     * This method is used to append an international category to the overall categories.
     * @param row the row placement in the grid.
     * @param col te column placement in the grid.
     */
    private void addInternationalCategory(int row, int col) {
        Category category = database.getInternationalCategory();
        Button button = buttonGenerator(category.getName());
        button.setOnAction(action -> {
            handleCategoryButton(category);
        });

        gridPane.add(button, row, col);
    }
}