package no.ntnu.idatg2001.wargames.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
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
    private boolean getRandomUnitFromArmy1 = true;
    private List<Rectangle> rectangleArmy1List;
    private List<Rectangle> rectangleArmy2List;
    private static final String PATH_TO_ARMY_1 = "src/pathToArmy1.csv";
    private static final String PATH_TO_ARMY_2 = "src/pathToArmy2.csv";
    private float columnSize = 0;
    private float rowSize = 0;
    private static final float GRID_PANE_HEIGHT = 400;
    private static final float GRID_PANE_WIDTH = 600;
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
        Army greatestArmy = null;
        this.rectangleArmy1List = new ArrayList<>();
        this.rectangleArmy2List = new ArrayList<>();

        try {
            remakeArmies();
        } catch (IOException e) {
            e.getMessage();
        }
        // If largestArmy is true, army1 is bigger than army2
        if (army1.getUnitList().size() > army2.getUnitList().size()) {
            greatestArmy = army1;
        } else {
            greatestArmy = army2;
        }
        int numberOfRows;
        if (greatestArmy.getUnitList().size() < 25){
            numberOfRows = 5;
        }
        else {
            numberOfRows = greatestArmy.getUnitList().size() / 5;

        }
        //Dividing the grid pane width into 15 columns
        this.columnSize = GRID_PANE_WIDTH / 15;
        //Dividing the grid pane height in rows
        this.rowSize = GRID_PANE_HEIGHT / numberOfRows;

        gridPane.getRowConstraints().remove(0);
        gridPane.getColumnConstraints().remove(0);

        for (int i = 0; i < numberOfRows; i++) {
            gridPane.getRowConstraints().add(new RowConstraints(rowSize));
        }

        for (float i = 0; i < GRID_PANE_WIDTH; i += columnSize) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(columnSize));
        }

        gridPane.setGridLinesVisible(false);
        gridPane.setLayoutX(100);
        gridPane.setLayoutY(10);
        gridPane.setBackground(new Background(
                new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        fillTerrainComboBox();

        makeArmy1Rectangles();
        makeArmy2Rectangles();
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
            rectangle.setHeight(rowSize);
            rectangle.setWidth(columnSize);
            rectangle.setStyle("-fx-fill: blue");
            gridPane.add(rectangle, j, i);
            this.rectangleArmy1List.add(rectangle);
            i++;
        }

    }

    public void makeArmy2Rectangles() {
        int j = gridPane.getColumnCount()-1;
        int i = 0;
        for (Unit unit : this.army2.getUnitList()) {
            if ((i+1)%(gridPane.getRowCount()+1) == 0) {
                j--;
                i = 0;
            }
            Rectangle rectangle = new Rectangle();
            rectangle.setHeight(rowSize);
            rectangle.setWidth(columnSize);
            rectangle.setStyle("-fx-fill: red");
            gridPane.add(rectangle, j, i);
            this.rectangleArmy2List.add(rectangle);
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
    public void startSimulation() {
        for (int i = 0; i < 10; i++) {
            oneStepSimulation();
        }
    }

    public void oneStepSimulation() {
        int randomNumberArmy1 = army1.getRandomNumber();
        int randomNumberArmy2 = army2.getRandomNumber();
        Rectangle attackingRectangle;
        Rectangle defendingRectangle;
        if (getRandomUnitFromArmy1) {
            attackingRectangle = rectangleArmy1List.get(randomNumberArmy1);
            defendingRectangle = rectangleArmy2List.get(randomNumberArmy2);
        }
        else {
            attackingRectangle = rectangleArmy2List.get(randomNumberArmy2);
            defendingRectangle = rectangleArmy1List.get(randomNumberArmy1);
        }

        int rectangleColumn = GridPane.getColumnIndex(attackingRectangle);
        int rectangleRow = GridPane.getRowIndex(attackingRectangle);

        if(getRandomUnitFromArmy1) {
            if ((gridPane.getColumnCount()-1 > rectangleColumn) && !getNodeFromGridPane(rectangleColumn + 1, rectangleRow)) {
                gridPane.getChildren().remove(attackingRectangle);
                gridPane.add(attackingRectangle, rectangleColumn + 1, rectangleRow);
            } else if ((gridPane.getRowCount()-1 > rectangleRow) && !getNodeFromGridPane(rectangleColumn, rectangleRow + 1)) {
                gridPane.getChildren().remove(attackingRectangle);
                gridPane.add(attackingRectangle, rectangleColumn, rectangleRow + 1);
            } else if ((rectangleRow > 0) && !getNodeFromGridPane(rectangleColumn, rectangleRow - 1)) {
                gridPane.getChildren().remove(attackingRectangle);
                gridPane.add(attackingRectangle, rectangleColumn, rectangleRow - 1);
            } else if ((rectangleColumn > 0) && !getNodeFromGridPane(rectangleColumn - 1, rectangleRow)) {
                gridPane.getChildren().remove(attackingRectangle);
                gridPane.add(attackingRectangle, rectangleColumn - 1, rectangleRow);
            }
        } else {
            if ((rectangleColumn > 0) && !getNodeFromGridPane(rectangleColumn - 1, rectangleRow)) {
                gridPane.getChildren().remove(attackingRectangle);
                gridPane.add(attackingRectangle, rectangleColumn - 1, rectangleRow);
            } else if ((gridPane.getRowCount()-1 > rectangleRow) && !getNodeFromGridPane(rectangleColumn, rectangleRow + 1)) {
                gridPane.getChildren().remove(attackingRectangle);
                gridPane.add(attackingRectangle, rectangleColumn, rectangleRow + 1);
            } else if ((rectangleRow > 0) && !getNodeFromGridPane(rectangleColumn, rectangleRow - 1)) {
                gridPane.getChildren().remove(attackingRectangle);
                gridPane.add(attackingRectangle, rectangleColumn, rectangleRow - 1);
            } else if ((gridPane.getColumnCount()-1 > rectangleColumn) && !getNodeFromGridPane(rectangleColumn + 1, rectangleRow)) {
                gridPane.getChildren().remove(attackingRectangle);
                gridPane.add(attackingRectangle, rectangleColumn + 1, rectangleRow);
            }
        }
        Unit attacker = null;
        Unit defender = null;
        if (getRandomUnitFromArmy1) {
            attacker = army1.getUnitList().get(rectangleArmy1List.indexOf(attackingRectangle));
            defender = army2.getUnitList().get(rectangleArmy2List.indexOf(defendingRectangle));
            getRandomUnitFromArmy1 = false;
        } else {
            attacker = army2.getUnitList().get(rectangleArmy2List.indexOf(attackingRectangle));
            defender = army1.getUnitList().get(rectangleArmy1List.indexOf(defendingRectangle));
            getRandomUnitFromArmy1 = true;
        }
    }

    private boolean getNodeFromGridPane(int column, int row) {
        ObservableList<Node> children = gridPane.getChildren();
        for (Node node : children) {
            Integer columnIndex = GridPane.getColumnIndex(node);
            Integer rowIndex = GridPane.getRowIndex(node);

            if (columnIndex == null)
                columnIndex = 0;
            if (rowIndex == null)
                rowIndex = 0;

            if (columnIndex == column && rowIndex == row)
                return true;
        }
        return false;
    }

    @FXML
    private void changeBattleColor(ActionEvent event) {
        Battle.Terrain selectedTerrain = terrainComboBox.getSelectionModel().getSelectedItem();
        if (selectedTerrain.equals(Battle.Terrain.FOREST))
            gridPane.setBackground(new Background(
                    new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        else if (selectedTerrain.equals(Battle.Terrain.HILL))
            gridPane.setBackground(new Background(
                    new BackgroundFill(Color.LIGHTGOLDENRODYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        else
            gridPane.setBackground(new Background(
                    new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
    }
}