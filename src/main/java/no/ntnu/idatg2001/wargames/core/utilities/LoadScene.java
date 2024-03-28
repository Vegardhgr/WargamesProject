package no.ntnu.idatg2001.wargames.core.utilities;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import no.ntnu.idatg2001.wargames.controllers.createArmy.FetchArmy1Controller;
import no.ntnu.idatg2001.wargames.controllers.createArmy.FetchArmy2Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * This class loads the different scenes
 *
 * @author Vegard Grøder
 * @version 1.0.0
 */
public class LoadScene {
    // Instance object of the class
    private static volatile LoadScene instance;

    /**
     * Loads the scenes
     * @param event, a mouse event
     * @param path, the path to the new scene
     */
    private void loadScene(MouseEvent event, String path) {
        try {
            URL url = getClass().getResource(path);
            Parent root = FXMLLoader.load(Objects.requireNonNull(url));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException|RuntimeException e) {
            Dialogs.getInstance().cannotLoadScene();
        }
    }

    /**
     * If a load scene object is not created, it creates one. If it is created it returns
     * the object.
     * @return LoadScene, a object of the class
     */
    public static LoadScene getInstance() {
        if (LoadScene.instance == null) {
            synchronized (LoadScene.class) {
                LoadScene.instance = new LoadScene();
            }
        }
        return LoadScene.instance;
    }

    /**
     * Sends the main screen path to the loadScene method
     * @param event, a mouse event
     */
    public void loadMainScreen(MouseEvent event) {
        String path = "/fxmlFiles/mainScreen/MainScreen.fxml";
        loadScene(event, path);
    }

    /**
     * Sends the chooseBattleType path to the loadScene method
     * @param event, a mouse event
     */
    public void loadChooseBattleType(MouseEvent event) {
        String path = "/fxmlFiles/simulateBattle/ChooseBattleType.fxml";
        loadScene(event, path);
    }

    /**
     * Sends the loadAnimatedBattle path to the loadScene method if the armies are valid
     * @param event, a mouse event
     */
    public void loadAnimatedBattle(MouseEvent event) {
        try {
            //Checks if the armies are valid
            CSVFileHandler.readCSVArmy(CSVFileHandler.readCSVArmyPath(FetchArmy1Controller.PATH_TO_ARMY_1));
            CSVFileHandler.readCSVArmy(CSVFileHandler.readCSVArmyPath(FetchArmy2Controller.PATH_TO_ARMY_2));
            String path = "/fxmlFiles/simulateBattle/AnimatedBattle.fxml";
            loadScene(event, path);
        } catch (IOException e) {
            Dialogs.getInstance().cannotLoadScene();
        }
    }

    /**
     * Sends the quickBattle path to the loadScene method if the armies are valid
     * @param event, a mouse event
     */
    public void loadQuickBattle(MouseEvent event) {
        try {
            //Checks if the armies are valid
            CSVFileHandler.readCSVArmy(CSVFileHandler.readCSVArmyPath(FetchArmy1Controller.PATH_TO_ARMY_1));
            CSVFileHandler.readCSVArmy(CSVFileHandler.readCSVArmyPath(FetchArmy2Controller.PATH_TO_ARMY_2));
            String path = "/fxmlFiles/simulateBattle/QuickBattle.fxml";
            loadScene(event, path);
        } catch (IOException e) {
            Dialogs.getInstance().cannotLoadScene();
        }
    }


    /**
     * Sends the loadEditArmy path to the loadScene method if the armies are valid
     * @param event, a mouse event
     */
    public void loadEditArmy(MouseEvent event) {
        try {
            //Checks if the armies are valid
            CSVFileHandler.readCSVArmy(CSVFileHandler.readCSVArmyPath(FetchArmy1Controller.PATH_TO_ARMY_1));
            CSVFileHandler.readCSVArmy(CSVFileHandler.readCSVArmyPath(FetchArmy2Controller.PATH_TO_ARMY_2));
            String path = "/fxmlFiles/viewArmyDetails/EditArmy.fxml";
            loadScene(event, path);
        } catch (NullPointerException e) {
            Dialogs.getInstance().noFileIsSelected();
        } catch (IOException|ArrayIndexOutOfBoundsException e) {
            Dialogs.getInstance().cannotLoadScene();
        }
    }

    /**
     * Sends the addUnit path to the loadScene method
     * @param event, a mouse event
     */
    public void loadAddUnit(MouseEvent event) {
        String path = "/fxmlFiles/viewArmyDetails/AddUnit.fxml";
        loadScene(event, path);
    }

    /**
     * Sends the loadCreateArmyScene path to the loadScene method
     * @param event, a mouse event
     */
    public void loadCreateArmyScene(MouseEvent event) {
        String path = "/fxmlFiles/createArmy/CreateArmy.fxml";
        loadScene(event, path);
    }

    /**
     * Sends the fetchArmy1Screen path to the loadScene method
     * @param event, a mouse event
     */
    public void fetchArmy1Screen(MouseEvent event) {
        String path = "/fxmlFiles/createArmy/FetchArmy1.fxml";
        loadScene(event, path);
    }

    /**
     * Sends the fetchArmy2Screen path to the loadScene method
     * @param event, a mouse event
     */
    public void fetchArmy2Screen(MouseEvent event) {
        String path = "/fxmlFiles/createArmy/FetchArmy2.fxml";
        loadScene(event, path);
    }
}
