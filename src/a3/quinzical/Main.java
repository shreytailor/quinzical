package a3.quinzical;

import a3.quinzical.backend.IO;
import a3.quinzical.backend.Speaker;
import a3.quinzical.backend.database.GameDatabase;
import a3.quinzical.frontend.helper.ScreenSwitcher;

// JavaFX dependencies.
import javafx.stage.Stage;
import java.io.IOException;
import javafx.application.Platform;
import javafx.application.Application;


public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Checking if the configuration folder exists.
        IO.checkDirectory();

        stage.setResizable(false);
        stage.setOnCloseRequest(event -> {
            try {
                IO.writeGameData(GameDatabase.getInstance());
            } catch (IOException error) {  };
            Speaker.init().kill();
            Platform.exit();
        });

        ScreenSwitcher.initialize(stage);
        stage.show();
    }
}