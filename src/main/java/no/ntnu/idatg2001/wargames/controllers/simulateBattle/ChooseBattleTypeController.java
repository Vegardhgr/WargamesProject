package no.ntnu.idatg2001.wargames.controllers.simulateBattle;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import no.ntnu.idatg2001.wargames.utilities.CSVFileHandler;
import no.ntnu.idatg2001.wargames.utilities.Dialogs;
import no.ntnu.idatg2001.wargames.utilities.SingletonClass;
import java.io.IOException;

/**
 * The controller class for ChooseBattleType.fxml. This class is responsible for
 * handling which simulation the user selects.
 *
 * @author Vegard Grøder
 */
public class ChooseBattleTypeController {
    /**
     * Loads the animated battle scene.
     * @param event, a mouse event
     */
    @FXML
    private void animatedBattle(MouseEvent event) {
        try {
            int numberOfUnitsInArmy1 = CSVFileHandler.readCSVArmy(
                            CSVFileHandler.readCSVArmyPath("src/pathToArmy1.csv"))
                    .getUnitList().size();
            int numberOfUnitsInArmy2 = CSVFileHandler.readCSVArmy(
                            CSVFileHandler.readCSVArmyPath("src/pathToArmy2.csv"))
                    .getUnitList().size();
            if (numberOfUnitsInArmy1 > 1000 || numberOfUnitsInArmy2 > 1000) {
                Dialogs.getInstance().tooManyUnits();
            } else {
                SingletonClass.getInstance().getScene().loadAnimatedBattle(event);
            }
        } catch (IOException|IndexOutOfBoundsException e) {
            Dialogs.getInstance().cannotLoadScene();
        } catch (NullPointerException e) {
            Dialogs.getInstance().noFileIsSelected();
        }
    }

    /**
     * Loads the quick battle scene.
     * @param event, a mouse event
     */
    @FXML
    private void quickBattle(MouseEvent event) {
        try {
            SingletonClass.getInstance().getScene().loadQuickBattle(event);
        } catch (IndexOutOfBoundsException e) {
            Dialogs.getInstance().cannotLoadScene();
        } catch (NullPointerException e) {
            Dialogs.getInstance().noFileIsSelected();
        }
    }

    /**
     * Loads the main screen.
     * @param event, a mouse event
     */
    @FXML
    private void backToMainScreen(MouseEvent event) {
        SingletonClass.getInstance().getScene().loadMainScreen(event);
    }

}
