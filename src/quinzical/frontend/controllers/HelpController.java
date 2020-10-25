package quinzical.frontend.controllers;
import quinzical.frontend.helper.ScreenType;
import quinzical.frontend.helper.ScreenSwitcher;

// Java dependencies.
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

// JavaFX dependencies.
import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;

public class HelpController implements Initializable {

    @FXML WebView webView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Retrieving the URL to the User Manual file.
        File userManual = new File("UserManual/UserManual.html").getAbsoluteFile();

        // Setting up the WebView such that it displays the User Manual.
        WebEngine engine = webView.getEngine();
        engine.load(userManual.toURI().toString());
    }

    @FXML
    private void handleBackButton() {
        ScreenSwitcher.getInstance().switchTo(ScreenType.MAIN_MENU);
    }

}