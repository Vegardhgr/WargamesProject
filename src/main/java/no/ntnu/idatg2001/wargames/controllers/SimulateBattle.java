package no.ntnu.idatg2001.wargames.controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import no.ntnu.idatg2001.wargames.SingletonClass;

import java.io.IOException;

public class SimulateBattle {

    @FXML
    private void backToMainScreen(MouseEvent event) throws IOException {
        SingletonClass.getInstance().getScene().loadMainScreen(event);
    }

}
