package a3.quinzical.frontend.controllers;

import a3.quinzical.backend.models.Clue;
import a3.quinzical.backend.database.GameDatabase;

// Java dependencies.
import java.net.URL;
import java.util.ResourceBundle;

// JavaFX dependencies.
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;


/**
 * This class is the controller class for the Game Question screen.
 * @author Shrey Tailor, Jason Wang
 */
public class GameClueController implements Initializable {

    @FXML
    Label categoryLabel;
    @FXML
    Label prizeLabel;
    @FXML
    Label prefixLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Getting the currently selected question.
        Clue clue = GameDatabase.getInstance().getCurrentClue();

        // Updating the information on the screen to reflect the chosen question above.
        categoryLabel.setText(clue.getCategory().getName());
        prizeLabel.setText("$" + clue.getPrize());
        prefixLabel.setText(clue.getPrefix());
    }

}
