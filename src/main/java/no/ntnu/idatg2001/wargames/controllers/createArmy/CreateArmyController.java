package no.ntnu.idatg2001.wargames.controllers.createArmy;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import no.ntnu.idatg2001.wargames.utilities.LoadScene;

/**
 * The controller class for CreateArmy.fxml. This class is responsible for
 * handling which army the user wants to add units to.
 *
 * @author Vegard Grøder
 */
public class CreateArmyController {
    /**
     * Loads the main screen.
     * @param event, a mouse event
     */
    @FXML
    private void backToMainScreen(MouseEvent event) {
        LoadScene.getInstance().loadMainScreen(event);
    }

    /**
     * Loads the fetchArmy1Controller scene.
     * @param event, a mouse event
     */
    @FXML
    private void fetchArmy1Screen(MouseEvent event) {
        LoadScene.getInstance().fetchArmy1Screen(event);
    }

    /**
     * Loads the fetchArmy2Controller scene.
     * @param event, a mouse event
     */
    @FXML
    private void fetchArmy2Screen(MouseEvent event) {
        LoadScene.getInstance().fetchArmy2Screen(event);
    }
}
