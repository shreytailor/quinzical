package quinzical.frontend.helper;

// JavaFX dependencies.
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * A singleton class to avoid code duplication when displaying an alert to the user.
 * @author Shrey Tailor, Jason Wang
 */
public class AlertHelper {

    private ButtonType _result;
    private static AlertHelper _alertHelper;

    /**
     * An empty (default) constructor.
     */
    public AlertHelper() {  };

    /**
     * Enforcing the principles of the single pattern.
     * @return
     */
    public static AlertHelper getInstance() {
        if (_alertHelper == null) {
            _alertHelper = new AlertHelper();
        }
        return _alertHelper;
    }

    /**
     * This method is used to show the alert to the user, and then store the result of the button
     * that the user presses. This is so that it can be accessed later.
     * @param alertType the type of alert that we want to show.
     * @param message the message we want to display on the alert.
     * @param buttons the different options that we want to give to our users.
     */
    public void showAlert(Alert.AlertType alertType, String message, ButtonType... buttons) {
        Alert alert = new Alert(alertType, message, buttons);
        alert.setHeaderText(null);
        alert.showAndWait();
        _result = alert.getResult();
    }

    /**
     * This method is used to return the button that the user pressed in the last alert.
     * @return
     */
    public ButtonType getResult() {
        return _result;
    }

}
