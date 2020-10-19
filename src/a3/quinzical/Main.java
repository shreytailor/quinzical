package a3.quinzical;
import a3.quinzical.backend.IO;
import a3.quinzical.frontend.helper.Speaker;
import a3.quinzical.backend.database.GameDatabase;
import a3.quinzical.frontend.helper.ScreenSwitcher;

// Java dependencies.
import java.io.IOException;
import javafx.application.Platform;
import javafx.application.Application;

// JavaFX dependencies.
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // Checking if the configuration folder exists.
        IO.checkDirectory();

        if (GameDatabase.gameFileExist()) {
            GameDatabase.getInstance();
        }

        stage.setResizable(false);
        stage.setOnCloseRequest(event -> {
            // Save the progress file, only if there is progress.
            if (GameDatabase.singletonExist()) {
                try {
                    IO.writeGameData(GameDatabase.getInstance());
                } catch (IOException error) {  };
            }

            Speaker.init().kill();
            System.exit(0);
            Platform.exit();
        });

        ScreenSwitcher.initialize(stage);
        stage.show();
    }
}