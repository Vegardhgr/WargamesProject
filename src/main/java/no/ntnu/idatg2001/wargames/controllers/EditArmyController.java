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

public class EditArmyController implements Initializable {
    private static final String PATH_TO_ARMY_1 = "src/pathToArmy1.csv";
    private static final String PATH_TO_ARMY_2 = "src/pathToArmy2.csv";
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

    @FXML
    Text amountOfUnits2;
    @FXML
    TableView<Unit> tableViewArmy2;
    @FXML
    TableColumn<Unit, String> unitTypeColumn2;
    @FXML
    TableColumn<Unit, String> nameColumn2;
    @FXML
    TableColumn<Unit, Integer> healthColumn2;
    @FXML
    TableColumn<Unit, Integer> attackColumn2;
    @FXML
    TableColumn<Unit, Integer> armorColumn2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        unitTypeColumn.setCellValueFactory(new PropertyValueFactory<>("UnitType"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        healthColumn.setCellValueFactory(new PropertyValueFactory<>("Health"));
        attackColumn.setCellValueFactory(new PropertyValueFactory<>("Attack"));
        armorColumn.setCellValueFactory(new PropertyValueFactory<>("Armor"));
        unitTypeColumn2.setCellValueFactory(new PropertyValueFactory<>("UnitType"));
        nameColumn2.setCellValueFactory(new PropertyValueFactory<>("Name"));
        healthColumn2.setCellValueFactory(new PropertyValueFactory<>("Health"));
        attackColumn2.setCellValueFactory(new PropertyValueFactory<>("Attack"));
        armorColumn2.setCellValueFactory(new PropertyValueFactory<>("Armor"));
        try {
            amountOfUnits.setText("Total units: " + readArmy(PATH_TO_ARMY_1).size());
            unitTypeColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue()
                    .getClass()
                    .getSimpleName()));
            tableViewArmy1.setItems(readArmy(PATH_TO_ARMY_1));
            amountOfUnits2.setText("Total units: " + readArmy(PATH_TO_ARMY_2).size());
            unitTypeColumn2.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue()
                    .getClass()
                    .getSimpleName()));
            tableViewArmy2.setItems(readArmy(PATH_TO_ARMY_2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ObservableList<Unit> readArmy(String path) throws IOException {
        String pathToArmy = CSVFileHandler.readCSVArmyPath(path);
        if (pathToArmy != null) {
            Army army = CSVFileHandler.readCSVArmy(pathToArmy);
            List<Unit> units = army.getUnitList();
            return FXCollections.observableList(units);
        }
        return FXCollections.emptyObservableList();
    }



    @FXML
    private void backToMainScreen(MouseEvent event) throws IOException {
        SingletonClass.getInstance().getScene().loadMainScreen(event);
    }

    @FXML
    private void addUnit(MouseEvent event) throws IOException {
        SingletonClass.getInstance().getScene().loadAddUnit(event);
    }

    @FXML
    private void removeUnit(MouseEvent event) throws IOException {
        if (tableViewArmy1.getSelectionModel().getSelectedItem() != null) {

            removeUnitHandler(tableViewArmy1, PATH_TO_ARMY_1);
            amountOfUnits.setText("Total units: " + readArmy(PATH_TO_ARMY_1).size());
        } else {
            removeUnitHandler(tableViewArmy2, PATH_TO_ARMY_2);
            amountOfUnits2.setText("Total units: " + readArmy(PATH_TO_ARMY_2).size());

        }
    }

    private void removeUnitHandler(TableView<Unit> tableViewArmy, String pathToArmy) throws IOException {
        Unit unit = tableViewArmy.getSelectionModel().getSelectedItem();
        tableViewArmy.getItems().remove(unit);
        String armyName = CSVFileHandler.readCSVArmy(CSVFileHandler.readCSVArmyPath(pathToArmy)).getName();
        Army army = new Army(armyName, tableViewArmy.getItems().stream().toList());
        CSVFileHandler.writeCSVArmy(army, CSVFileHandler.readCSVArmyPath(pathToArmy));
    }

    @FXML
    private void tableViewArmy1Clicked(MouseEvent event) {
        tableViewArmy2.getSelectionModel().clearSelection();
    }
    @FXML
    private void tableViewArmy2Clicked(MouseEvent event) {
        tableViewArmy1.getSelectionModel().clearSelection();
    }
}
