package no.ntnu.idatg2001.wargames.controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import no.ntnu.idatg2001.wargames.utilities.SingletonClass;

import java.io.IOException;

public class CreateArmyController {
    @FXML
    private void backToMainScreen(MouseEvent event) throws IOException {
        SingletonClass.getInstance().getScene().loadMainScreen(event);
    }

    @FXML
    private void fetchArmy1Screen(MouseEvent event) throws IOException {
        SingletonClass.getInstance().getScene().fetchArmy1Screen(event);
    }

    @FXML
    private void fetchArmy2Screen(MouseEvent event) throws IOException {
        SingletonClass.getInstance().getScene().fetchArmy2Screen(event);
    }
}
