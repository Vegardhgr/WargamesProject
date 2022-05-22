package no.ntnu.idatg2001.wargames.controllers.viewArmyDetails;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import no.ntnu.idatg2001.wargames.controllers.createArmy.FetchArmy1Controller;
import no.ntnu.idatg2001.wargames.controllers.createArmy.FetchArmy2Controller;
import no.ntnu.idatg2001.wargames.utilities.Army;
import no.ntnu.idatg2001.wargames.utilities.CSVFileHandler;
import no.ntnu.idatg2001.wargames.utilities.Dialogs;
import no.ntnu.idatg2001.wargames.utilities.SingletonClass;
import no.ntnu.idatg2001.wargames.units.Unit;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller for the EditArmy.fxml file.
 *
 * @author Vegard Grøder
 */
public class EditArmyController implements Initializable {
    //The path to the path where army1 is stored
    private static final String PATH_TO_ARMY_1 = "src/pathToArmy1.csv";
    //The path to the path where army2 is stored
    private static final String PATH_TO_ARMY_2 = "src/pathToArmy2.csv";
    Army army1;
    Army army2;

    //Text field for the name of army1
    @FXML
    TextField army1Name;
    //The text field for the number of units in army1
    @FXML
    TextField amountOfUnits;
    //Table view for the units in army1
    @FXML
    TableView<Unit> tableViewArmy1;
    //Table column for a unit's type in army1
    @FXML
    TableColumn<Unit, String> unitTypeColumn;
    //Table column for a unit's name in army1
    @FXML
    TableColumn<Unit, String> nameColumn;
    //Table column for a unit's health in army1
    @FXML
    TableColumn<Unit, Integer> healthColumn;
    //Table column for a unit's attack in army1
    @FXML
    TableColumn<Unit, Integer> attackColumn;
    //Table column for a unit's armor in army1
    @FXML
    TableColumn<Unit, Integer> armorColumn;

    //Text field for the name of army2
    @FXML
    TextField army2Name;
    //The text field for the number of units in army2
    @FXML
    TextField amountOfUnits2;
    //Table view for the units in army2
    @FXML
    TableView<Unit> tableViewArmy2;
    //Table column for a unit's type in army2
    @FXML
    TableColumn<Unit, String> unitTypeColumn2;
    //Table column for a unit's name in army2
    @FXML
    TableColumn<Unit, String> nameColumn2;
    //Table column for a unit's health in army2
    @FXML
    TableColumn<Unit, Integer> healthColumn2;
    //Table column for a unit's attack in army2
    @FXML
    TableColumn<Unit, Integer> attackColumn2;
    //Table column for a unit's armor in army2
    @FXML
    TableColumn<Unit, Integer> armorColumn2;

    /**
     * Fills the table views with units from each army. Also fills the text fields
     * with the names of the armies and the number of units in each army.
     *
     * @param url, the url
     * @param resourceBundle, the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Sets a cell value factory for each column in the table view
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
        //Fills the table view with units from army1
        fillTableViewArmy1();
        //Fills the table view with units from army2
        fillTableViewArmy2();
        amountOfUnits.setEditable(false);
        amountOfUnits2.setEditable(false);
    }

    /**
     * Fills the table view with units from army1.
     */
    private void fillTableViewArmy1() {
        this.army1 = readArmy(PATH_TO_ARMY_1);
        if (army1 == null ) {
            return;
        }
        army1Name.setText(army1.getName());
        amountOfUnits.setText("Total units: " + army1.getUnitList().size());
        unitTypeColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue()
                .getClass()
                .getSimpleName()));
        tableViewArmy1.setItems(FXCollections.observableList(army1.getUnitList()));
    }

    /**
     * Fills the table view with units from army2.
     */
    private void fillTableViewArmy2() {
        this.army2 = readArmy(PATH_TO_ARMY_2);
        if (army2 == null) {
            return;
        }
        army2Name.setText(army2.getName());
        amountOfUnits2.setText("Total units: " + army2.getUnitList().size());
        unitTypeColumn2.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue()
                .getClass()
                .getSimpleName()));
        tableViewArmy2.setItems(FXCollections.observableList(army2.getUnitList()));
    }

    /**
     * Reads the army from the path specified.
     *
     * @param path, the path to the army.
     * @return Army, the army.
     */
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

    /**
     * Goes back to the main screen.
     *
     * @param event, a mouse event.
     */
    @FXML
    private void backToMainScreen(MouseEvent event) {
        checkForChangeInArmyName();
        SingletonClass.getInstance().getScene().loadMainScreen(event);
    }

    /**
     * Goes to the add unit screen.
     *
     * @param event, a mouse event.
     */
    @FXML
    private void addUnit(MouseEvent event) {
        checkForChangeInArmyName();
        SingletonClass.getInstance().getScene().loadAddUnit(event);
    }

    /**
     * Removes the selected unit.
     * @throws IOException, if there is something wrong with the file.
     */
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

    /**
     * Removes the selected unit.
     * @param tableViewArmy, the table view of the army.
     * @param pathToArmy, the path to the army.
     * @throws IOException, if there is something wrong with the file.
     */
    private void removeUnitHandler(TableView<Unit> tableViewArmy, String pathToArmy) throws IOException {
        Unit unit = tableViewArmy.getSelectionModel().getSelectedItem();
        tableViewArmy.getItems().remove(unit);
        String armyName = CSVFileHandler.readCSVArmy(CSVFileHandler.readCSVArmyPath(pathToArmy)).getName();
        Army army = new Army(armyName, tableViewArmy.getItems().stream().toList());
        CSVFileHandler.writeCSVArmy(army, CSVFileHandler.readCSVArmyPath(pathToArmy));
    }
    private void checkForChangeInArmyName() {
        try {
            if (army1Name.getText() == null) {
                army1Name.setText("");
            }
            if (!army1Name.getText().equals(army1.getName())) {
                Army army1WithNewName = new Army(army1Name.getText(), tableViewArmy1.getItems().stream().toList());
                CSVFileHandler.writeCSVArmy(army1WithNewName, FetchArmy1Controller.getArmy1Path());
            }
            if (army2Name.getText() == null) {
                army2Name.setText("");
            }
            if (!army2Name.getText().equals(army2.getName())) {
                Army army2WithNewName = new Army(army2Name.getText(), tableViewArmy2.getItems().stream().toList());
                CSVFileHandler.writeCSVArmy(army2WithNewName, FetchArmy2Controller.getArmy2Path());
            }
        } catch (IOException e) {
            Dialogs.getInstance().somethingWrongWithTheFile();
        }
    }

    /**
     * Clears the selection of the table view where army2 is stored,
     * since the table view where army1 is stored is clicked.
     */
    @FXML
    private void tableViewArmy1Clicked() {
        tableViewArmy2.getSelectionModel().clearSelection();
    }

    /**
     * Clears the selection of the table view where army1 is stored,
     * since the table view where army2 is stored is clicked.
     */
    @FXML
    private void tableViewArmy2Clicked() {
        tableViewArmy1.getSelectionModel().clearSelection();
    }
}
