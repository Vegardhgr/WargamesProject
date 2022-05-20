package no.ntnu.idatg2001.wargames.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import no.ntnu.idatg2001.wargames.utilities.Army;
import no.ntnu.idatg2001.wargames.utilities.CSVFileHandler;
import no.ntnu.idatg2001.wargames.utilities.Dialogs;
import no.ntnu.idatg2001.wargames.utilities.SingletonClass;
import no.ntnu.idatg2001.wargames.units.Unit;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditArmyController implements Initializable {
    private static final String PATH_TO_ARMY_1 = "src/pathToArmy1.csv";
    private static final String PATH_TO_ARMY_2 = "src/pathToArmy2.csv";
    Army army1;
    Army army2;
    @FXML
    TextField army1Name;
    @FXML
    TextField amountOfUnits;
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
    TextField army2Name;
    @FXML
    TextField amountOfUnits2;
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

        fillTableViewArmy1();
        fillTableViewArmy2();
        army1Name.setEditable(false);
        army2Name.setEditable(false);
        amountOfUnits.setEditable(false);
        amountOfUnits2.setEditable(false);
    }

    private void fillTableViewArmy1() {
        try {
            this.army1 = readArmy(PATH_TO_ARMY_1);
            army1Name.setText(army1.getName());
            amountOfUnits.setText("Total units: " + army1.getUnitList().size());
            unitTypeColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue()
                    .getClass()
                    .getSimpleName()));
            tableViewArmy1.setItems(FXCollections.observableList(army1.getUnitList()));
        } catch (NullPointerException e) {
            //TODO:Maybe add a dialog here
        }
    }

    private void fillTableViewArmy2() {
        try {
            this.army2 = readArmy(PATH_TO_ARMY_2);
            army2Name.setText(army2.getName());
            amountOfUnits2.setText("Total units: " + army2.getUnitList().size());
            unitTypeColumn2.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue()
                    .getClass()
                    .getSimpleName()));
            tableViewArmy2.setItems(FXCollections.observableList(army2.getUnitList()));
        } catch (NullPointerException e) {
            //TODO:Maybe add a dialog here
        }
    }

    private Army readArmy(String path) {
        try {
            String pathToArmy = CSVFileHandler.readCSVArmyPath(path);
            if (pathToArmy != null) {
                return CSVFileHandler.readCSVArmy(pathToArmy);
            }
        } catch (IOException e) {
            Dialogs.getInstance().fileNotFound();
        }
        return null;
    }

    @FXML
    private void backToMainScreen(MouseEvent event) {
        SingletonClass.getInstance().getScene().loadMainScreen(event);
    }

    @FXML
    private void addUnit(MouseEvent event) {
        SingletonClass.getInstance().getScene().loadAddUnit(event);
    }

    @FXML
    private void removeUnit() throws IOException {
        if (tableViewArmy1.getSelectionModel().getSelectedItem() != null) {

            removeUnitHandler(tableViewArmy1, PATH_TO_ARMY_1);
            amountOfUnits.setText("Total units: " + this.army1.getUnitList().size());
        } else {
            removeUnitHandler(tableViewArmy2, PATH_TO_ARMY_2);
            amountOfUnits2.setText("Total units: " + this.army2.getUnitList().size());

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
    private void tableViewArmy1Clicked() {
        tableViewArmy2.getSelectionModel().clearSelection();
    }
    @FXML
    private void tableViewArmy2Clicked() {
        tableViewArmy1.getSelectionModel().clearSelection();
    }
}
