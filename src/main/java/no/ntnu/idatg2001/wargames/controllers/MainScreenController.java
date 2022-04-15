package no.ntnu.idatg2001.wargames.controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import no.ntnu.idatg2001.wargames.SingletonClass;

import java.io.IOException;

public class MainScreenController {
    @FXML
    private void loadCreateArmyScene(MouseEvent event) throws IOException {
        SingletonClass.getInstance().getScene().loadCreateArmyScene(event);
    }

    @FXML
    private void viewArmy1Details(MouseEvent event) throws IOException {
        SingletonClass.getInstance().getScene().loadViewArmy1Details(event);
    }
}
