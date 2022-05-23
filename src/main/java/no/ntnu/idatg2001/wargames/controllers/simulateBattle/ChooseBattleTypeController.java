package no.ntnu.idatg2001.wargames.controllers.simulateBattle;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import no.ntnu.idatg2001.wargames.core.utilities.CSVFileHandler;
import no.ntnu.idatg2001.wargames.core.utilities.Dialogs;
import no.ntnu.idatg2001.wargames.core.utilities.LoadScene;
import java.io.IOException;

/**
 * The controller class for ChooseBattleType.fxml. This class is responsible for
 * handling which simulation the user selects.
 *
 * @author Vegard Grøder
 */
public class ChooseBattleTypeController {
    private int numberOfUnitsInArmy1;
    private int numberOfUnitsInArmy2;

    /**
     * Gets the number of units in both armies.
     */
    private void getNumberOfUnitsInBothArmies() {
        try {
            numberOfUnitsInArmy1 = CSVFileHandler.readCSVArmy(
                            CSVFileHandler.readCSVArmyPath("pathToArmy1.csv"))
                    .getUnitList().size();
            numberOfUnitsInArmy2 = CSVFileHandler.readCSVArmy(
                            CSVFileHandler.readCSVArmyPath("pathToArmy2.csv"))
                    .getUnitList().size();
        } catch (IOException|IndexOutOfBoundsException e) {
            Dialogs.getInstance().cannotLoadScene();
        } catch (NullPointerException e) {
            Dialogs.getInstance().noFileIsSelected();
        }
    }
    /**
     * Loads the animated battle scene.
     * @param event, a mouse event
     */
    @FXML
    private void animatedBattle(MouseEvent event) {
        getNumberOfUnitsInBothArmies();
        if (numberOfUnitsInArmy1 > 1000 || numberOfUnitsInArmy2 > 1000) {
            Dialogs.getInstance().tooManyUnits();
        } else if (numberOfUnitsInArmy1 == 0 || numberOfUnitsInArmy2 == 0) {
            Dialogs.getInstance().noUnitsInArmy();
        } else {
            LoadScene.getInstance().loadAnimatedBattle(event);
        }
    }

    /**
     * Loads the quick battle scene.
     * @param event, a mouse event
     */
    @FXML
    private void quickBattle(MouseEvent event) {
        getNumberOfUnitsInBothArmies();
        if (numberOfUnitsInArmy1 == 0 || numberOfUnitsInArmy2 == 0) {
            Dialogs.getInstance().noUnitsInArmy();
        } else {
            LoadScene.getInstance().loadQuickBattle(event);
        }
    }

    /**
     * Loads the main screen.
     * @param event, a mouse event
     */
    @FXML
    private void backToMainScreen(MouseEvent event) {
        LoadScene.getInstance().loadMainScreen(event);
    }

}
