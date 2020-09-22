package a3.quinzical.frontend.controllers;

// Java dependencies.
import java.net.URL;
import java.util.ResourceBundle;

// JavaFX dependencies.
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * This class is the controller class for the Practice Question screen.
 * @author Shrey Tailor, Jason Wang
 */
public class PracticeQuestionController implements Initializable {

    @FXML
    Label clueLabel;
    @FXML
    Label categoryLabel;
    @FXML
    TextField answerTextField;
    @FXML
    Button submitButton;
    @FXML
    Label attemptsLabel;
    @FXML
    Button backButton;
    @FXML
    Button respeakButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
