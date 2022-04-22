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
     * @param event, the mouse event
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
     * @param event, the mouse event
     * @throws IOException, exception to be thrown if the file does not exist
     */
    public void loadMainScreen(MouseEvent event) throws IOException {
        String path = "/fxmlFiles/createArmy/MainScreen.fxml";
        loadScene(event, path);
    }

    /**
     * Sends the loadEditArmy path to the loadScene method
     * @param event, the mouse event
     * @throws IOException, exception to be thrown if the file does not exist
     */
    public void loadSimulateBattle(MouseEvent event) throws IOException {
        String path = "/fxmlFiles/simulateBattle/SimulateBattle.fxml";
        loadScene(event, path);
    }


    /**
     * Sends the loadEditArmy path to the loadScene method
     * @param event, the mouse event
     * @throws IOException, exception to be thrown if the file does not exist
     */
    public void loadEditArmy(MouseEvent event) throws IOException {
        String path = "/fxmlFiles/viewArmyDetails/EditArmy.fxml";
        loadScene(event, path);
    }

    /**
     * Sends the loadViewArmy1Details path to the loadScene method
     * @param event, the mouse event
     * @throws IOException, exception to be thrown if the file does not exist
     */
    public void loadViewArmy1Details(MouseEvent event) throws IOException {
        String path = "/fxmlFiles/viewArmyDetails/Army1Details.fxml";
        loadScene(event, path);
    }

    /**
     * Sends the loadViewArmy2Details path to the loadScene method
     * @param event, the mouse event
     * @throws IOException, exception to be thrown if the file does not exist
     */
    public void loadViewArmy2Details(MouseEvent event) throws IOException {
        String path = "/fxmlFiles/viewArmyDetails/Army2Details.fxml";
        loadScene(event, path);
    }

    /**
     * Sends the addUnit path to the loadScene method
     * @param event, the mouse event
     * @throws IOException, exception to be thrown if the file does not exist
     */
    public void loadAddUnit(MouseEvent event) throws IOException {
        String path = "/fxmlFiles/viewArmyDetails/AddUnit.fxml";
        loadScene(event, path);
    }

    /**
     * Sends the loadCreateArmyScene path to the loadScene method
     * @param event, the mouse event
     * @throws IOException, exception to be thrown if the file does not exist
     */
    public void loadCreateArmyScene(MouseEvent event) throws IOException {
        String path = "/fxmlFiles/createArmy/CreateArmy.fxml";
        loadScene(event, path);
    }

    /**
     * Sends the fetchArmy1Screen path to the loadScene method
     * @param event, the mouse event
     * @throws IOException, exception to be thrown if the file does not exist
     */
    public void fetchArmy1Screen(MouseEvent event) throws IOException {
        String path = "/fxmlFiles/createArmy/FetchArmy1.fxml";
        loadScene(event, path);
    }

    /**
     * Sends the fetchArmy2Screen path to the loadScene method
     * @param event, the mouse event
     * @throws IOException, exception to be thrown if the file does not exist
     */
    public void fetchArmy2Screen(MouseEvent event) throws IOException {
        String path = "/fxmlFiles/createArmy/FetchArmy2.fxml";
        loadScene(event, path);
    }
}
