package no.ntnu.idatg2001.wargames.controllers.mainScreen;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import no.ntnu.idatg2001.wargames.core.utilities.LoadScene;

/**
 * The controller class for MainScreen.fxml. This class is the main screen
 * for the application.
 *
 * @author Vegard Grøder
 */
public class MainScreenController {
    /**
     * Goes to the chooseBattleType scene.
     * @param event, a mouse event
     */
    @FXML
    private void simulateBattle(MouseEvent event) {
        LoadScene.getInstance().loadChooseBattleType(event);
    }

    /**
     * Goes to the editArmy scene
     * @param event, a mouse event
     */
    @FXML
    private void editArmy(MouseEvent event) {
        LoadScene.getInstance().loadEditArmy(event);
    }

    /**
     * Goes to the createArmy scene
     * @param event, a mouse event
     */
    @FXML
    private void loadCreateArmyScene(MouseEvent event) {
        LoadScene.getInstance().loadCreateArmyScene(event);
    }
}
