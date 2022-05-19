package no.ntnu.idatg2001.wargames.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import no.ntnu.idatg2001.wargames.utilities.Army;
import no.ntnu.idatg2001.wargames.utilities.CSVFileHandler;
import no.ntnu.idatg2001.wargames.utilities.SingletonClass;
import no.ntnu.idatg2001.wargames.units.Unit;
import no.ntnu.idatg2001.wargames.units.UnitFactory;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * The controller for the AddUnit.fxml file
 *
 * @author Vegard Groder
 */
public class AddUnitController implements Initializable {
    // All the fields
    @FXML
    ComboBox<UnitFactory.UnitType> unitTypeComboBox;
    @FXML
    TextField nameField;
    @FXML
    TextField healthField;
    @FXML
    ComboBox<String> armyComboBox;
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
     * Loads the main screen window
     * @param event, mouse event
     * @throws IOException, if the file does not exist.
     */
    @FXML
    private void backToEditArmy(MouseEvent event) throws IOException {
        SingletonClass.getInstance().getScene().loadEditArmy(event);
    }

    /**
     * Checker for the health input field
     */
    @FXML
    private void healthFieldInputChecker() {
        healthField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!(newValue.matches("\\d*"))) {
                healthField.setText(newValue.replaceAll("[^\\d]", ""));
            } else {
                if (newValue.equals("")) {
                    healthField.setText("");
                } else if (Long.parseLong(newValue) >= 2147483647) {
                    healthField.setText("2147483647");
                } else if (newValue.indexOf("0") == 0) {
                    Platform.runLater(() ->
                        healthField.setText("1")
                    );
                }
            }

        });
    }

    //TODO: Add a checker for the quantity field

    /**
     * Adds a unit to the table view and writes that unit to file.
     * @param event, mouse event
     * @throws IOException, if the file does not exist.
     */
    @FXML
    private void addUnit(MouseEvent event) throws IOException {
        UnitFactory.UnitType unitType = unitTypeComboBox.getValue();
        String name = null;
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
        if (armySelected.equalsIgnoreCase("Army 1"))
            path = FetchArmy1Controller.getArmy1Path();
        else
            path = FetchArmy2Controller.getArmy2Path();
        Army army = CSVFileHandler.readCSVArmy(path);
        army.getUnitList().addAll(units);
        CSVFileHandler.writeCSVArmy(army, path);
        clearInput();
    }

    /**
     * Goes back to the main screen window
     * @param event, a mouse event
     * @throws IOException, if the file does not exist
     */
    @FXML
    private void backToMainScreen(MouseEvent event) throws IOException {
        SingletonClass.getInstance().getScene().loadMainScreen(event);
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
