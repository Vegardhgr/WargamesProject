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
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import no.ntnu.idatg2001.wargames.utilities.*;
import no.ntnu.idatg2001.wargames.units.Unit;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * The controller class for animated battle. This class contains code for
 * the animated simulation.
 *
 * @author Vegard Grøder
 */
public class AnimatedBattleController implements Initializable {
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
    //The column size
    private float columnSize;
    //The row size
    private float rowSize;
    //Height of the grid pane
    private static final float GRID_PANE_HEIGHT = 400;
    //Width of the grid pane
    private static final float GRID_PANE_WIDTH = 600;
    //Stores army1
    private Army army1Stored;
    //Stores army2
    private Army army2Stored;
    //This army is used in battles
    private Army army1;
    //This army is used in battles
    private Army army2;
    private Battle battle;
    //The column index of the attacking unit
    private int rectangleColumn;
    //The row index of the attacking unit
    private int rectangleRow;

    //The grid pane
    @FXML
    GridPane gridPane;

    //The combo box for terrain
    @FXML
    ComboBox<Battle.Terrain> terrainComboBox;

    //Button to start a simulation
    @FXML
    Button startSimulationBtn;

    //Button to reset the armies and make them ready for a new battle
    @FXML
    Button resetArmyBtn;

    //A slider to change the speed of the simulation
    @FXML
    Slider sliderSimulationSpeed;

    /**
     * Goes back to main screen
     * @param event, a mouse event
     */
    @FXML
    private void backToMainScreen(MouseEvent event) {
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
        loadArmies();
        greatestArmy = checkGreatestArmy();
        //The amount of rows needed based on the army size
        int numberOfRows = generateNumberOfRows(greatestArmy);

        //Dividing the grid pane width into 15 columns
        this.columnSize = GRID_PANE_WIDTH / 15;
        //Dividing the grid pane height in rows
        this.rowSize = GRID_PANE_HEIGHT / numberOfRows;

        gridPaneHandler(numberOfRows);
        fillTerrainComboBox();
        //Creates the rectangles for army1 in the grid pane
        makeArmy1Rectangles();
        //Creates the rectangles for army2 in the grid pane
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
     * Generates the number of rows. If the greatest army has less than
     * 25 units, 5 rows is created. Else the number of rows will be generated based on
     * the number of units in the greatest army divided by 5.
     *
     * @param greatestArmy, the greatest army
     * @return int, number of rows.
     */
    public int generateNumberOfRows(Army greatestArmy) {
        //The least amount of rows is 5
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
     * Stores the two armies in two different variables
     */
    public void loadArmies() {
        try {
            this.army1Stored = CSVFileHandler.readCSVArmy(CSVFileHandler.readCSVArmyPath(PATH_TO_ARMY_1));
            this.army2Stored = CSVFileHandler.readCSVArmy(CSVFileHandler.readCSVArmyPath(PATH_TO_ARMY_2));
            refreshArmies();
        } catch (IOException e) {
            Dialogs.getInstance().cannotReadArmies();
        }
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
     * The speed of the simulation
     */
    @FXML
    private void simulationSpeed() {
        sliderSimulationSpeed.valueProperty().addListener((observableValue, number, t1) ->
                timeline.setRate(sliderSimulationSpeed.getValue()));
    }

    /**
     * Starts the timeline
     */
    private void timeline() {
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100), this::oneStepSimulation));
    }

    /**
     * Sets the button that was clicked to be disabled and then the timeline starts
     */
    @FXML
    public void startSimulation() {
        if (terrainComboBox.getSelectionModel().getSelectedItem() == null)
            Dialogs.getInstance().selectTerrain();
        else {
            startSimulationBtn.setDisable(true);
            timeline.play();
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
            //Sets up who is the attacker and who is the defender
            attacker = army1.getUnitList().get(rectangleArmy1List.indexOf(attackingRectangle));
            defender = army2;
            defendingRectangleList = rectangleArmy2List;
        } else {
            //Sets up who is the attacker and who is the defender
            attacker = army2.getUnitList().get(rectangleArmy2List.indexOf(attackingRectangle));
            defender = army1;
            defendingRectangleList = rectangleArmy1List;
        }

        /*The defending unit. If defendingRectangle is null, then there are no units
          nearby the attacker.*/
        Rectangle defendingRectangle = checkForUnitInRange(isArmyOnesTurn);
        if (defendingRectangle != null) {
            //Fetching the unit based on the index of rectangle that represents it
            Unit defendingUnit = defender.getUnitList().get(defendingRectangleList.indexOf(defendingRectangle));
            //removeRectangle is true if the defending unit is dead
            boolean removeRectangle = battle.oneStepBattle(attacker, defendingUnit);
            if (removeRectangle) {
                //Removes the rectangle from the grid pane
                gridPane.getChildren().remove(defendingRectangle);
                defender.getUnitList().remove(defendingUnit);
                defendingRectangleList.remove(defendingRectangle);
            }
        } else
            //Moves a rectangle in the grid
            moveRectangleInGridPane(attackingRectangle);
        //Switches which army that is the attacking part
        isArmyOnesTurn = !isArmyOnesTurn;

        if (defender.getUnitList().isEmpty()) {
            //Stops the timeline because there is a winner
            timeline.stop();
            resetArmyBtn.setDisable(false);
        }
    }

    /**
     * Moves the rectangles in the grid pane
     * @param attackingRectangle, the rectangle that represents the unit that attacks
     */
    public void moveRectangleInGridPane(Rectangle attackingRectangle) {
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
            } else {
                //If the unit cannot move, it is the same army's turn next time
                this.isArmyOnesTurn = !isArmyOnesTurn;
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
            } else {
                //If the unit cannot move, it is the same army's turn next time
                this.isArmyOnesTurn = !isArmyOnesTurn;
            }
        }
    }

    /**
     * Returns a node if the parameters, column and row, matches with a
     * node's column and row index. If there is no match, it returns null
     * @param column, an int
     * @param row, an int
     * @return Node, the node that matches the parameters
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
     */
    @FXML
    private void changeBattleColor() {
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
     * Checks for a unit in range
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