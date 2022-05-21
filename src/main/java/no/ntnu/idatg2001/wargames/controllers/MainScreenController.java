package no.ntnu.idatg2001.wargames.controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import no.ntnu.idatg2001.wargames.utilities.SingletonClass;

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
        SingletonClass.getInstance().getScene().loadChooseBattleType(event);
    }

    /**
     * Goes to the editArmy scene
     * @param event, a mouse event
     */
    @FXML
    private void editArmy(MouseEvent event) {
        SingletonClass.getInstance().getScene().loadEditArmy(event);
    }

    /**
     * Goes to the createArmy scene
     * @param event, a mouse event
     */
    @FXML
    private void loadCreateArmyScene(MouseEvent event) {
        SingletonClass.getInstance().getScene().loadCreateArmyScene(event);
    }
}
