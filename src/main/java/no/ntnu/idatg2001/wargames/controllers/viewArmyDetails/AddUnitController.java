package no.ntnu.idatg2001.wargames.controllers.viewArmyDetails;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import no.ntnu.idatg2001.wargames.controllers.createArmy.FetchArmy1Controller;
import no.ntnu.idatg2001.wargames.controllers.createArmy.FetchArmy2Controller;
import no.ntnu.idatg2001.wargames.utilities.Army;
import no.ntnu.idatg2001.wargames.utilities.CSVFileHandler;
import no.ntnu.idatg2001.wargames.utilities.Dialogs;
import no.ntnu.idatg2001.wargames.units.Unit;
import no.ntnu.idatg2001.wargames.units.UnitFactory;
import no.ntnu.idatg2001.wargames.utilities.LoadScene;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * The controller for the AddUnit.fxml file.
 *
 * @author Vegard Groder
 */
public class AddUnitController implements Initializable {
    //The max number of units that can be added to the army
    private static final int MAX_NUMBER_OF_UNITS = 10000;
    //The number of units in the army
    private int numberOfUnitsInSelectedArmy;
    //A combo box that contains the unit types
    @FXML
    ComboBox<UnitFactory.UnitType> unitTypeComboBox;
    //The name field for a unit
    @FXML
    TextField nameField;
    //Health field for a unit
    @FXML
    TextField healthField;
    //Army combo box. The selected army is where the units will be added to
    @FXML
    ComboBox<String> armyComboBox;
    //The number of units that should be made
    @FXML
    TextField quantityField;

    /**
     * Fills the combo boxes with values.
     * @param var1, the url
     * @param var2, the resource bundle
     */
    @Override
    public void initialize(URL var1, ResourceBundle var2) {
        List<UnitFactory.UnitType> unitTypes = new ArrayList<>(Arrays.asList(UnitFactory.UnitType.values()));
        ObservableList<UnitFactory.UnitType> observableListUnitTypes = FXCollections.observableList(unitTypes);
        unitTypeComboBox.setItems(observableListUnitTypes);
        List<String> armies = numberOfArmies();
        armyComboBox.setItems(FXCollections.observableList(armies));
    }

    /**
     * Returns a list of army strings.
     * @return List<String>, n armies
     */
    public List<String> numberOfArmies() {
        List<String> armies = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            armies.add("Army " + i);
        }
        return armies;
    }

    /**
     * Loads the edit army window
     * @param event, mouse event
     */
    @FXML
    private void backToEditArmy(MouseEvent event) {
        LoadScene.getInstance().loadEditArmy(event);
    }

    /**
     * Checker for the health input field. Only numbers can be written in the field.
     * Numbers starting with zero is not allowed.
     */
    @FXML
    private void healthFieldInputChecker() {
        healthField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!(newValue.matches("\\d*"))) {
                healthField.setText(newValue.replaceAll("[^\\d]", ""));
            } else if (newValue.equals("")) {
                healthField.setText("");
            } else if (Long.parseLong(newValue) >= 2147483647) {
                healthField.setText("2147483647");
            } else if (newValue.indexOf("0") == 0) {
                Platform.runLater(() ->
                        healthField.setText("1")
                );
            }
        });
    }

    /**
     * Based on which army that is selected, the number of allowed units in the
     * quantity field changes.
     */
    @FXML
    private void armyComboBoxAction() {
        String army = armyComboBox.getSelectionModel().getSelectedItem();
        if (!quantityField.getText().equals("")) {
            try {
                if (army.equals("Army 1")) {
                    numberOfUnitsInSelectedArmy =
                            CSVFileHandler.readCSVArmy(
                                    FetchArmy1Controller.getArmy1Path()).getUnitList().size();
                    if (Integer.parseInt(quantityField.getText()) > MAX_NUMBER_OF_UNITS - numberOfUnitsInSelectedArmy)
                        quantityField.setText(MAX_NUMBER_OF_UNITS - numberOfUnitsInSelectedArmy + "");
                } else {
                    numberOfUnitsInSelectedArmy =
                            CSVFileHandler.readCSVArmy(
                                    FetchArmy2Controller.getArmy2Path()).getUnitList().size();
                    if (Integer.parseInt(quantityField.getText()) > MAX_NUMBER_OF_UNITS - numberOfUnitsInSelectedArmy)
                        quantityField.setText(MAX_NUMBER_OF_UNITS - numberOfUnitsInSelectedArmy + "");
                }
            } catch (IOException e) {
                Dialogs.getInstance().closeFile();
            }
        }
    }

    /**
     * Input checker for the quantity field. Only numbers can be written in the field.
     * Numbers starting with zero is not allowed.
     */
    @FXML
    private void quantityFieldInputChecker() {
        quantityField.textProperty().addListener((observable, oldValue, newValue) -> {
            String army = armyComboBox.getSelectionModel().getSelectedItem();
            if (!(newValue.matches("\\d*")) && quantityField.getText().equals("")) {
                quantityField.setText(newValue.replaceAll("[^\\d]", ""));
            } else {
                try {
                    if (army != null) {
                        if (army.equals("Army 1")) {
                            numberOfUnitsInSelectedArmy =
                                    CSVFileHandler.readCSVArmy(
                                            FetchArmy1Controller.getArmy1Path()).getUnitList().size();
                        } else {
                            numberOfUnitsInSelectedArmy =
                                    CSVFileHandler.readCSVArmy(
                                            FetchArmy2Controller.getArmy2Path()).getUnitList().size();
                        }
                    } else {
                        numberOfUnitsInSelectedArmy = 0;
                    }
                } catch (IOException e) {
                    Dialogs.getInstance().closeFile();
                }
                int numberOfUnitsInField = MAX_NUMBER_OF_UNITS - numberOfUnitsInSelectedArmy;
                if (!(newValue.matches("\\d*"))) {
                    quantityField.setText(newValue.replaceAll("[^\\d]", ""));
                } else if (!quantityField.getText().equals("") && Integer.parseInt(quantityField.getText()) > 10000) {
                    quantityField.setText("10000");
                } else if (newValue.equals("")) {
                    quantityField.setText("");
                } else if (Integer.parseInt(newValue) >= (numberOfUnitsInField)) {
                    quantityField.setText(numberOfUnitsInField + "");
                } else if (newValue.indexOf("0") == 0) {
                    Platform.runLater(() ->
                            quantityField.setText("1")
                    );
                }
            }
        });
    }

    /**
     * Adds a unit to the table view if all the input fields that
     * are required to create a unit is filled in. Then it writes those units to file.
     */
    @FXML
    private void addUnit() {
        UnitFactory.UnitType unitType = unitTypeComboBox.getValue();
        String name;
        if (unitTypeComboBox.getSelectionModel().getSelectedItem() == null ||
            armyComboBox.getSelectionModel().getSelectedItem() == null ||
            nameField.getText().isBlank() ||
            healthField.getText().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid input");
            alert.setTitle("Error");
            alert.setContentText("One or more required fields are empty");
            alert.showAndWait();
            return;
        }
        if (nameField.getText().contains(",")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid input");
            alert.setTitle("Error");
            alert.setContentText("Cannot contain \",\"");
            alert.showAndWait();
            return;
        } else {
            name = nameField.getText();
        }
        int health = Integer.parseInt(healthField.getText());
        String armySelected = armyComboBox.getValue();
        int quantity;
        if (!quantityField.getText().isEmpty())
            quantity = Integer.parseInt(quantityField.getText());
        else
            quantity = 1;

        List<Unit> units = UnitFactory.getInstance().createMultipleUnits(unitType, name, health, quantity);
        String path;
        try {
            if (armySelected.equalsIgnoreCase("Army 1"))
                path = FetchArmy1Controller.getArmy1Path();
            else
                path = FetchArmy2Controller.getArmy2Path();
            Army army = CSVFileHandler.readCSVArmy(path);
            army.getUnitList().addAll(units);
            CSVFileHandler.writeCSVArmy(army, path);
            clearInput();
        } catch (IOException e) {
            Dialogs.getInstance().closeFile();
        }
    }

    /**
     * Goes back to the main screen window
     * @param event, a mouse event
     */
    @FXML
    private void backToMainScreen(MouseEvent event) {
        LoadScene.getInstance().loadMainScreen(event);
    }

    /**
     * Clears the input fields.
     */
    private void clearInput() {
        nameField.clear();
        healthField.clear();
        quantityField.clear();
    }
}
