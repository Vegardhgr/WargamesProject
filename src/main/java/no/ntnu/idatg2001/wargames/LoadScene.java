package no.ntnu.idatg2001.wargames;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * This class loads the different scenes
 */
public class LoadScene {
    /**
     * Loads the scenes
     * @param event, the action event
     * @param path, the path to the new scene
     * @throws IOException, exception to be thrown if the file does not exist
     */
    private void loadScene(MouseEvent event, String path) throws IOException {
        URL url = getClass().getResource(path);
        Parent root = FXMLLoader.load(Objects.requireNonNull(url));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Sends the main screen path to the loadScene method
     * @param event, the action event
     * @throws IOException, exception to be thrown if the file does not exist
     */
    public void loadMainScreen(MouseEvent event) throws IOException {
        String path = "/fxmlFiles/MainScreen.fxml";
        loadScene(event, path);
    }

    /**
     * Sends the loadCreateArmyScene path to the loadScene method
     * @param event, the action event
     * @throws IOException, exception to be thrown if the file does not exist
     */
    public void loadCreateArmyScene(MouseEvent event) throws IOException {
        String path = "/fxmlFiles/CreateArmy.fxml";
        loadScene(event, path);
    }

    /**
     * Sends the fetchArmy1Screen path to the loadScene method
     * @param event, the action event
     * @throws IOException, exception to be thrown if the file does not exist
     */
    public void fetchArmy1Screen(MouseEvent event) throws IOException {
        String path = "/fxmlFiles/FetchArmy1.fxml";
        loadScene(event, path);
    }
}
