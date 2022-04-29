package no.ntnu.idatg2001.wargames.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import no.ntnu.idatg2001.wargames.Army;
import no.ntnu.idatg2001.wargames.Battle;
import no.ntnu.idatg2001.wargames.CSVFileHandler;
import no.ntnu.idatg2001.wargames.SingletonClass;
import no.ntnu.idatg2001.wargames.units.Unit;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SimulateBattleController implements Initializable {
    private static final String PATH_TO_ARMY_1 = "src/pathToArmy1.csv";
    private static final String PATH_TO_ARMY_2 = "src/pathToArmy2.csv";
    private Army army1;
    private Army army2;
    private Battle battle;

    @FXML
    GridPane gridPane;

    @FXML
    ComboBox<Battle.Terrain> terrainComboBox;

    @FXML
    private void backToMainScreen(MouseEvent event) throws IOException {
        SingletonClass.getInstance().getScene().loadMainScreen(event);
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        gridPane.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.setGridLinesVisible(true);
        fillTerrainComboBox();
        try {
            remakeArmies();
            makeArmy1Rectangles();
            makeArmy2Rectangles();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void makeArmy1Rectangles() {
        int j = 0;
        int i = 0;
        for (Unit unit : this.army1.getUnitList()) {
            if ((i+1)%(gridPane.getRowCount()+1) == 0) {
                j++;
                i = 0;
            }
            Rectangle rectangle = new Rectangle();
            rectangle.setHeight(20);
            rectangle.setWidth(20);
            rectangle.setStyle("-fx-fill: blue");
            gridPane.add(rectangle, j, i);
            i++;
        }

    }

    public void makeArmy2Rectangles() {
        int j = gridPane.getColumnCount();
        int i = 0;
        for (Unit unit : this.army2.getUnitList()) {
            if ((i+1)%(gridPane.getRowCount()+1) == 0) {
                j--;
                i = 0;
            }
            Rectangle rectangle = new Rectangle();
            rectangle.setHeight(20);
            rectangle.setWidth(20);
            rectangle.setStyle("-fx-fill: red");
            gridPane.add(rectangle, j, i);
            i++;
        }
    }

    public void fillTerrainComboBox() {
        List<Battle.Terrain> terrainList = new ArrayList<>(List.of(Battle.Terrain.values()));
        ObservableList<Battle.Terrain> terrainObservableList = FXCollections.observableList(terrainList);
        terrainComboBox.setItems(terrainObservableList);
    }

    public void remakeArmies() throws IOException {
        this.army1 = CSVFileHandler.readCSVArmy(CSVFileHandler.readCSVArmyPath(PATH_TO_ARMY_1));
        this.army2 = CSVFileHandler.readCSVArmy(CSVFileHandler.readCSVArmyPath(PATH_TO_ARMY_2));
        this.battle = new Battle(army1, army2);
    }

    @FXML
    public void startSimulation() throws IOException {
        remakeArmies();
        Battle.setTerrain(terrainComboBox.getValue());
        System.out.println(battle.simulate().getName());
    }
}
