package no.ntnu.idatg2001.wargames.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * This class starts the application
 */
public class WargamesApplication extends Application {

    /**
     * Starts the program by opening the main screen
     * @param stage, the stage to be shown
     * @throws IOException, exception that will be thrown if the path does not exist
     */
    @Override
    public void start(Stage stage) throws IOException {
        String path = "/fxmlFiles/createArmy/MainScreen.fxml";
        URL url = getClass().getResource(path);
        Parent root = FXMLLoader.load(Objects.requireNonNull(url));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
