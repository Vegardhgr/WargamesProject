package no.ntnu.idatg2001.wargames.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import no.ntnu.idatg2001.wargames.LoadScene;

import java.io.IOException;

public class MainScreenController {
    LoadScene sceneLoader = new LoadScene();
    @FXML
    private void loadCreateArmyScene(ActionEvent event) throws IOException {
        sceneLoader.loadCreateArmyScene(event);
    }
}
