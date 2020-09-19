package a3.quinzical;

import a3.quinzical.frontend.controllers.ScreenController;

// JavaFX dependencies.
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Quinzical");
        stage.setResizable(false);
        stage.setOnCloseRequest(event -> {
            /*
            --------------------------------------------------------
            THIS IS WHERE THE PROGRESS SAVING CODE WILL BE PLACED.
            --------------------------------------------------------
             */
            System.out.println("Window has been closed.");
        });

        ScreenController screenController = ScreenController.getInstance(stage);
        screenController.addScreen("MAIN_MENU", FXMLLoader.load(getClass().getResource("./frontend/fxml/MainMenu.fxml")));
        screenController.setScreen("MAIN_MENU");

        stage.show();
    }

}