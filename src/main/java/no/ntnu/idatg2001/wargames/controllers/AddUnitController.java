package no.ntnu.idatg2001.wargames.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import no.ntnu.idatg2001.wargames.SingletonClass;
import no.ntnu.idatg2001.wargames.units.UnitFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AddUnitController implements Initializable {
    @FXML
    ComboBox<UnitFactory.UnitType> unitTypeComboBox;
    @FXML
    TextField nameField;
    @FXML
    TextField healthField;
    @FXML
    TextField attackField;
    @FXML
    TextField armorField;

    @Override
    public void initialize(URL var1, ResourceBundle var2) {
        List<UnitFactory.UnitType> unitTypes = new ArrayList<>(Arrays.asList(UnitFactory.UnitType.values()));
        ObservableList<UnitFactory.UnitType> observableListUnitTypes= FXCollections.observableList(unitTypes);
        unitTypeComboBox.setItems(observableListUnitTypes);
    }

    @FXML
    private void backToMainScreen(MouseEvent event) throws IOException {
        SingletonClass.getInstance().getScene().loadMainScreen(event);
    }

    @FXML
    private void addUnit(MouseEvent event) {
        UnitFactory.UnitType unitType = unitTypeComboBox.getValue();
        String name = nameField.getText();
        int health = Integer.parseInt(healthField.getText());
        UnitFactory.getInstance().createOneUnit(unitType, name, health);
    }
}
