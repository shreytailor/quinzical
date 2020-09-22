package a3.quinzical;

import a3.quinzical.frontend.controllers.ScreenController;

// JavaFX dependencies.
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.application.Application;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setResizable(false);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("./frontend/resources/logo.png")));
        stage.setOnCloseRequest(event -> {
            /*
            --------------------------------------------------------
            THIS IS WHERE THE PROGRESS SAVING CODE WILL BE PLACED.
            --------------------------------------------------------
             */
            System.out.println("Window has been closed.");
            Platform.exit();
        });

        ScreenController screenController = ScreenController.initialize(stage);
        screenController.addScreen("PRACTICE_MODULE", FXMLLoader.load(getClass().getResource("./frontend/fxml/PracticeModule.fxml")));

        stage.show();
    }

}