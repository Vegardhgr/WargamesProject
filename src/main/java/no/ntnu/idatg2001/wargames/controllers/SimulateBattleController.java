package no.ntnu.idatg2001.wargames.controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import no.ntnu.idatg2001.wargames.utilities.Army;
import no.ntnu.idatg2001.wargames.utilities.Battle;
import no.ntnu.idatg2001.wargames.utilities.CSVFileHandler;
import no.ntnu.idatg2001.wargames.utilities.SingletonClass;
import no.ntnu.idatg2001.wargames.units.Unit;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * The controller class for Simulate Battle. This class contains code for
 * the simulation.
 */
public class SimulateBattleController implements Initializable {
    private Timeline timeline;
    //isArmyOnesTurn is true if it is army1's turn. If false, it is army2's turn
    private boolean isArmyOnesTurn;
    //A list of rectangles representing the units in army1
    private List<Rectangle> rectangleArmy1List;
    //A list of rectangles representing the units in army2
    private List<Rectangle> rectangleArmy2List;
    //A path to the path where army1 is stored
    private static final String PATH_TO_ARMY_1 = "src/pathToArmy1.csv";
    //A path to the path where army2 is stored
    private static final String PATH_TO_ARMY_2 = "src/pathToArmy2.csv";
    private float columnSize;
    private float rowSize;
    //Height of the grid pane
    private static final float GRID_PANE_HEIGHT = 400;
    //Width of the grid pane
    private static final float GRID_PANE_WIDTH = 600;
    private Army army1Stored;
    private Army army2Stored;
    private Army army1;
    private Army army2;
    private Battle battle;
    //The column index of the attacking unit
    private int rectangleColumn;
    //The row index of the attacking unit
    private int rectangleRow;

    @FXML
    GridPane gridPane;

    @FXML
    ComboBox<Battle.Terrain> terrainComboBox;

    @FXML
    Button startSimulationBtn;

    @FXML
    Button resetArmyBtn;

    /**
     * Goes back to main screen
     * @param event, a mouse event
     * @throws IOException, if the path to the main screen does not exist
     */
    @FXML
    private void backToMainScreen(MouseEvent event) throws IOException {
        SingletonClass.getInstance().getScene().loadMainScreen(event);
    }

    /**
     * Creates the grid and fills it with units.
     * @param url, the url
     * @param resourceBundle, the resource bundle
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        resetArmyBtn.setDisable(true);
        Army greatestArmy;
        this.rectangleArmy1List = new ArrayList<>();
        this.rectangleArmy2List = new ArrayList<>();
        this.isArmyOnesTurn = true;
        this.timeline = new Timeline();
        timeline();

        try {
            loadArmies();
        } catch (IOException e) {
            e.getMessage();
        }

        greatestArmy = checkGreatestArmy();
        int numberOfRows = generateNumberOfRows(greatestArmy);

        //Dividing the grid pane width into 15 columns
        this.columnSize = GRID_PANE_WIDTH / 15;
        //Dividing the grid pane height in rows
        this.rowSize = GRID_PANE_HEIGHT / numberOfRows;

        gridPaneHandler(numberOfRows);
        fillTerrainComboBox();
        makeArmy1Rectangles();
        makeArmy2Rectangles();
    }

    /**
     * Checks which army is greater
     *
     * @return Army, the greatest army
     */
    public Army checkGreatestArmy() {
        if (army1.getUnitList().size() > army2.getUnitList().size())
            return army1;
        return army2;
    }

    /**
     * Generates the number of rows. If the greatest army contains less than
     * 25 units, there will be 5 rows. Else the number of rows will be generated based on
     * the number of units in the greatest army divided by 5.
     *
     * @param greatestArmy, the greatest army
     * @return int, number of rows.
     */
    public int generateNumberOfRows(Army greatestArmy) {
        if (greatestArmy.getUnitList().size() < 25)
            return 5;
        return greatestArmy.getUnitList().size() / 5;
    }

    /**
     * Handles the grid pane
     * @param numberOfRows, the number of rows the grid pane should contain
     */
    public void gridPaneHandler(int numberOfRows) {
        gridPane.getRowConstraints().remove(0);
        gridPane.getColumnConstraints().remove(0);
        gridPane.setGridLinesVisible(false);
        gridPane.setLayoutX(100);
        gridPane.setLayoutY(10);
        gridPane.setBackground(new Background(
                new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        for (int i = 0; i < numberOfRows; i++) {
            gridPane.getRowConstraints().add(new RowConstraints(rowSize));
        }
        for (float i = 0; i < GRID_PANE_WIDTH; i += columnSize) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(columnSize));
        }
    }

    /**
     * Creates one rectangle for each unit in army1 and adds it to the grid pane
     */
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
            if(army1.getInfantryUnit().contains(unit)) {
                rectangle.setStyle("-fx-fill: blue");
            } else if(army1.getCavalryUnit().contains(unit)) {
                rectangle.setStyle("-fx-fill: #2222ee");
            } else if(army1.getCommanderUnitList().contains(unit)) {
                rectangle.setStyle("-fx-fill: #4444dd");
            } else if(army1.getRangedUnitList().contains(unit)) {
                rectangle.setStyle("-fx-fill: #0044dc");
            }
            gridPane.add(rectangle, j, i);
            this.rectangleArmy1List.add(rectangle);
            i++;
        }
    }

    /**
     * Creates one rectangle for each unit in army2 and adds it to the grid pane
     */
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
            if(army2.getInfantryUnit().contains(unit)) {
                rectangle.setStyle("-fx-fill: red");
            } else if(army2.getCavalryUnit().contains(unit)) {
                rectangle.setStyle("-fx-fill: #ee2222");
            } else if(army2.getCommanderUnitList().contains(unit)) {
                rectangle.setStyle("-fx-fill: #dd4444");
            } else if(army2.getRangedUnitList().contains(unit)) {
                rectangle.setStyle("-fx-fill: #dc4400");
            }
            gridPane.add(rectangle, j, i);
            this.rectangleArmy2List.add(rectangle);
            i++;
        }
    }

    /**
     * Fills the terrain combo box with all the terrains
     */
    public void fillTerrainComboBox() {
        List<Battle.Terrain> terrainList = new ArrayList<>(List.of(Battle.Terrain.values()));
        ObservableList<Battle.Terrain> terrainObservableList = FXCollections.observableList(terrainList);
        terrainComboBox.setItems(terrainObservableList);
    }

    /**
     * Loads the armies with new units
     * @throws IOException, if the files does not exist.
     */
    public void loadArmies() throws IOException {
        this.army1Stored = CSVFileHandler.readCSVArmy(CSVFileHandler.readCSVArmyPath(PATH_TO_ARMY_1));
        this.army2Stored = CSVFileHandler.readCSVArmy(CSVFileHandler.readCSVArmyPath(PATH_TO_ARMY_2));
        refreshArmies();
    }

    /**
     * Refreshes the armies
     */
    public void refreshArmies() {
        this.army1 = new Army(army1Stored);
        this.army2 = new Army(army2Stored);
        this.battle = new Battle(army1, army2);
    }

    /**
     * Starts the timeline
     */
    private void timeline() {
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1), this::oneStepSimulation));
    }

    /**
     * Creates the armies, starts the timeline and also sets the button that was clicked
     * to be disabled.
     */
    @FXML
    public void startSimulation() {
        if (terrainComboBox.getSelectionModel().getSelectedItem() != null) {
            startSimulationBtn.setDisable(true);
            timeline.play();
        } else {
            System.out.println("Select a terrain");
        }
    }

    /**
     * Simulates one step of the battle
     * @param event, an action event
     */
    public void oneStepSimulation(ActionEvent event) {
        //The attacking unit
        Rectangle attackingRectangle;
        if (isArmyOnesTurn) {
            //Generates a number based on army1 size
            int randomNumberArmy1 = army1.getRandomNumber();
            attackingRectangle = rectangleArmy1List.get(randomNumberArmy1);
        }
        else {
            //Generates a number based on army2 size
            int randomNumberArmy2 = army2.getRandomNumber();
            attackingRectangle = rectangleArmy2List.get(randomNumberArmy2);
        }

        this.rectangleColumn = GridPane.getColumnIndex(attackingRectangle);
        this.rectangleRow = GridPane.getRowIndex(attackingRectangle);
        Unit attacker;
        Army defender;
        List<Rectangle> defendingRectangleList;
        if (isArmyOnesTurn) {
            attacker = army1.getUnitList().get(rectangleArmy1List.indexOf(attackingRectangle));
            defender = army2;
            defendingRectangleList = rectangleArmy2List;
        } else {
            attacker = army2.getUnitList().get(rectangleArmy2List.indexOf(attackingRectangle));
            defender = army1;
            defendingRectangleList = rectangleArmy1List;
        }

        Rectangle defendingRectangle = checkForUnitInRange(isArmyOnesTurn);
        if (defendingRectangle != null) {
            //Fetching the unit based on the index of rectangle that represents it
            Unit defendingUnit = defender.getUnitList().get(defendingRectangleList.indexOf(defendingRectangle));
            //removeRectangle is true if the defending unit's health is zero
            boolean removeRectangle = battle.oneStepBattle(attacker, defendingUnit);
            if (removeRectangle) {
                gridPane.getChildren().remove(defendingRectangle);
                defender.getUnitList().remove(defendingUnit);
                defendingRectangleList.remove(defendingRectangle);
            }
        } else
            //Moves a rectangle in the grid
            moveRectangleInGridPane(isArmyOnesTurn, attackingRectangle);
        //Switches which army that is the attacking part
        isArmyOnesTurn = !isArmyOnesTurn;

        if (defender.getUnitList().isEmpty()) {
            timeline.stop();
            resetArmyBtn.setDisable(false);
        }
    }

    /**
     * Moves the rectangles in the grid pane
     * @param isArmyOnesTurn, true if it is army1's turn
     * @param attackingRectangle, the rectangle that represents the unit that attacks
     */
    public void moveRectangleInGridPane(boolean isArmyOnesTurn, Rectangle attackingRectangle) {
        if (isArmyOnesTurn) {
            //This code describes how a unit from army1 should move in the grid pain
            if (((gridPane.getColumnCount()-1)/2 > rectangleColumn) && getNodeFromGridPane(rectangleColumn + 1, rectangleRow) == null) {
                gridPane.getChildren().remove(attackingRectangle);
                gridPane.add(attackingRectangle, rectangleColumn + 1, rectangleRow);
            } else if ((gridPane.getRowCount()-1 > rectangleRow) && getNodeFromGridPane(rectangleColumn, rectangleRow + 1) == null) {
                gridPane.getChildren().remove(attackingRectangle);
                gridPane.add(attackingRectangle, rectangleColumn, rectangleRow + 1);
            } else if ((rectangleRow > 0) && getNodeFromGridPane(rectangleColumn, rectangleRow - 1) == null) {
                gridPane.getChildren().remove(attackingRectangle);
                gridPane.add(attackingRectangle, rectangleColumn, rectangleRow - 1);
            } else if ((rectangleColumn > 0) && getNodeFromGridPane(rectangleColumn - 1, rectangleRow) == null) {
                gridPane.getChildren().remove(attackingRectangle);
                gridPane.add(attackingRectangle, rectangleColumn - 1, rectangleRow);
            }
        } else {
            //This code describes how a unit from army2 should move in the grid pain
            if ((rectangleColumn > gridPane.getColumnCount()/2) && getNodeFromGridPane(rectangleColumn - 1, rectangleRow) == null) {
                gridPane.getChildren().remove(attackingRectangle);
                gridPane.add(attackingRectangle, rectangleColumn - 1, rectangleRow);
            } else if ((gridPane.getRowCount()-1 > rectangleRow) && getNodeFromGridPane(rectangleColumn, rectangleRow + 1) == null) {
                gridPane.getChildren().remove(attackingRectangle);
                gridPane.add(attackingRectangle, rectangleColumn, rectangleRow + 1);
            } else if ((rectangleRow > 0) && getNodeFromGridPane(rectangleColumn, rectangleRow - 1) == null) {
                gridPane.getChildren().remove(attackingRectangle);
                gridPane.add(attackingRectangle, rectangleColumn, rectangleRow - 1);
            } else if ((gridPane.getColumnCount()-1 > rectangleColumn) && getNodeFromGridPane(rectangleColumn + 1, rectangleRow) == null) {
                gridPane.getChildren().remove(attackingRectangle);
                gridPane.add(attackingRectangle, rectangleColumn + 1, rectangleRow);
            }
        }
    }

    /**
     * Returns a node if the parameters column and row matches with a
     * node's column and row index. If there is no match, it returns null
     * @param column, an int
     * @param row, an int
     * @return Node
     */
    private Node getNodeFromGridPane(int column, int row) {
        ObservableList<Node> children = gridPane.getChildren();
        for (Node node : children) {
            Integer columnIndex = GridPane.getColumnIndex(node);
            Integer rowIndex = GridPane.getRowIndex(node);
            if (columnIndex == null)
                columnIndex = 0;
            if (rowIndex == null)
                rowIndex = 0;
            if (columnIndex == column && rowIndex == row)
                return node;
        }
        return null;
    }

    /**
     * Changes the color of the grid pane based on the terrain that is selected
     * @param event, an action event
     */
    @FXML
    private void changeBattleColor(ActionEvent event) {
        Battle.Terrain selectedTerrain = terrainComboBox.getSelectionModel().getSelectedItem();
        if (selectedTerrain.equals(Battle.Terrain.FOREST)) {
            gridPane.setBackground(new Background(
                    new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
            Battle.setTerrain(Battle.Terrain.FOREST);
        } else if (selectedTerrain.equals(Battle.Terrain.HILL)) {
            gridPane.setBackground(new Background(
                    new BackgroundFill(Color.LIGHTGOLDENRODYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
            Battle.setTerrain(Battle.Terrain.HILL);
        } else {
            gridPane.setBackground(new Background(
                    new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
            Battle.setTerrain(Battle.Terrain.PLAINS);
        }
    }

    /**
     * Checks for a unit
     * @param attackingArmy, the attacking army
     * @return Rectangle, the rectangle/unit that is within the attacker's range
     */
    private Rectangle checkForUnitInRange(boolean attackingArmy) {
        List<Rectangle> defendingArmy;
        if (attackingArmy)
            defendingArmy = rectangleArmy2List;
        else
            defendingArmy = rectangleArmy1List;
        for (int column = 1; column >= -1; column--) {
            for (int row = -1; row <= 1; row++) {
                int checkColumn = rectangleColumn + column;
                int checkRow = rectangleRow + row;
                Rectangle rectangle = (Rectangle) getNodeFromGridPane(checkColumn, checkRow);
                if (rectangle!=null && defendingArmy.contains(rectangle)) {
                    return rectangle;
                }
            }
        }
        return null;
    }

    /**
     * Resets the army after a battle
     */
    @FXML
    public void resetArmy() {
        gridPane.getChildren().removeAll(rectangleArmy1List);
        gridPane.getChildren().removeAll(rectangleArmy2List);
        rectangleArmy1List.clear();
        rectangleArmy2List.clear();
        refreshArmies();
        makeArmy1Rectangles();
        makeArmy2Rectangles();
        resetArmyBtn.setDisable(true);
        startSimulationBtn.setDisable(false);
    }
}