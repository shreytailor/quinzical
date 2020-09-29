package a3.quinzical.frontend.controllers;

import a3.quinzical.backend.Speaker;
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

    private Speaker _speaker = Speaker.init();
    private Clue _clue = GameDatabase.getInstance().getCurrentClue();

    @FXML
    Label categoryLabel;
    @FXML
    Label prizeLabel;
    @FXML
    Label prefixLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Updating the information on the screen to reflect the chosen question above.
        categoryLabel.setText(_clue.getCategory().getName());
        prizeLabel.setText("$" + _clue.getPrize());
        prefixLabel.setText(_clue.getPrefix());

        // Starting the process of speaking the question to the user.
        _speaker.setSpeech(_clue.getQuestion());
        _speaker.speak();
    }


    @FXML
    private void handleRespeakButton() {
        _speaker.speak();
    }

}
