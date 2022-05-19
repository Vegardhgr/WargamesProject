package no.ntnu.idatg2001.wargames.controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import no.ntnu.idatg2001.wargames.utilities.SingletonClass;

import java.io.IOException;

public class MainScreenController {
    @FXML
    private void simulateBattle(MouseEvent event) throws IOException {
        SingletonClass.getInstance().getScene().loadSimulateBattle(event);
    }

    @FXML
    private void editArmy(MouseEvent event) throws IOException {
        SingletonClass.getInstance().getScene().loadEditArmy(event);
    }

    @FXML
    private void loadCreateArmyScene(MouseEvent event) throws IOException {
        SingletonClass.getInstance().getScene().loadCreateArmyScene(event);
    }
}
