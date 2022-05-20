package no.ntnu.idatg2001.wargames.controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import no.ntnu.idatg2001.wargames.utilities.SingletonClass;

public class MainScreenController {
    @FXML
    private void simulateBattle(MouseEvent event) {
        SingletonClass.getInstance().getScene().loadChooseBattleType(event);
    }

    @FXML
    private void editArmy(MouseEvent event) {
        SingletonClass.getInstance().getScene().loadEditArmy(event);
    }

    @FXML
    private void loadCreateArmyScene(MouseEvent event) {
        SingletonClass.getInstance().getScene().loadCreateArmyScene(event);
    }
}
