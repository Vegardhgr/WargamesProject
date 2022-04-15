package no.ntnu.idatg2001.wargames.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import no.ntnu.idatg2001.wargames.Army;
import no.ntnu.idatg2001.wargames.CSVFileHandler;
import no.ntnu.idatg2001.wargames.SingletonClass;
import no.ntnu.idatg2001.wargames.units.Unit;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Army1DetailsController implements Initializable {
    @FXML
    Text amountOfUnits;
    @FXML
    TableView<Unit> tableViewArmy1;
    @FXML
    TableColumn<Unit, String> unitTypeColumn;
    @FXML
    TableColumn<Unit, String> nameColumn;
    @FXML
    TableColumn<Unit, Integer> healthColumn;
    @FXML
    TableColumn<Unit, Integer> attackColumn;
    @FXML
    TableColumn<Unit, Integer> armorColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        unitTypeColumn.setCellValueFactory(new PropertyValueFactory<>("UnitType"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        healthColumn.setCellValueFactory(new PropertyValueFactory<>("Health"));
        attackColumn.setCellValueFactory(new PropertyValueFactory<>("Attack"));
        armorColumn.setCellValueFactory(new PropertyValueFactory<>("Armor"));
        try {
            amountOfUnits.setText("Total units: " + readArmy1().size());
            unitTypeColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue()
                    .getClass()
                    .getSimpleName()));
            tableViewArmy1.setItems(readArmy1());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ObservableList<Unit> readArmy1() throws IOException {
        String pathToArmy1 = CSVFileHandler.readCSVArmyPath("src/pathToArmy1.csv");
        if (pathToArmy1 != null) {
            Army army = CSVFileHandler.readCSVArmy(pathToArmy1);
            List<Unit> units = army.getUnitList();
            return FXCollections.observableList(units);
        }
        return FXCollections.emptyObservableList();
    }

    @FXML
    private void backToMainScreen(MouseEvent event) throws IOException {
        SingletonClass.getInstance().getScene().loadMainScreen(event);
    }
}


