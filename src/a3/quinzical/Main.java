package a3.quinzical;

import a3.quinzical.backend.IO;
import a3.quinzical.backend.Speaker;
import a3.quinzical.frontend.helper.ScreenType;
import a3.quinzical.backend.database.GameDatabase;
import a3.quinzical.frontend.helper.ScreenSwitcher;

// JavaFX dependencies.
import javafx.stage.Stage;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.application.Platform;
import javafx.application.Application;


public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setResizable(false);
        stage.setOnCloseRequest(event -> {
            try {
                IO.writeGameData(GameDatabase.getInstance());
            } catch (IOException error) {  };
            Speaker.init().kill();
            Platform.exit();
        });

        ScreenSwitcher screenSwitcher = ScreenSwitcher.initialize(stage);
        screenSwitcher.addScreen(ScreenType.PRACTICE_MODULE, FXMLLoader.load(getClass().getResource("./frontend/fxml/PracticeModule.fxml")));
        screenSwitcher.addScreen(ScreenType.GAME_MODULE, FXMLLoader.load(getClass().getResource("./frontend/fxml/GameModule.fxml")));
        stage.show();
    }
}