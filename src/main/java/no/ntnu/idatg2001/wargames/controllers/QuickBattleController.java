package no.ntnu.idatg2001.wargames.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import no.ntnu.idatg2001.wargames.utilities.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * The controller for the QuickBattle.fxml file. This file contains code
 * for the quick battle simulation.
 *
 * @author Vegard Groder
 */
public class QuickBattleController implements Initializable {
    //A path to the path where army1 is stored
    private static final String PATH_TO_ARMY_1 = "src/pathToArmy1.csv";
    //A path to the path where army2 is stored
    private static final String PATH_TO_ARMY_2 = "src/pathToArmy2.csv";
    private Army army1Stored;
    private Army army2Stored;
    private Army army1;
    private Army army2;
    Battle battle;
    private static final int MAX_NUMBER_OF_BATTLES = 5000;

    @FXML
    ComboBox<Battle.Terrain> terrainComboBox;

    @FXML
    TextField numberOfBattles;

    @FXML
    TextArea textArea;

    @FXML
    Button startBattleBtn;

    @FXML
    TextField army1Name;

    @FXML
    TextField army2Name;

    @FXML
    TextField army1Wins;

    @FXML
    TextField army2Wins;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillTerrainComboBox();
        textArea.setEditable(false);
        army1Name.setEditable(false);
        army2Name.setEditable(false);
        army1Wins.setEditable(false);
        army2Wins.setEditable(false);
        Platform.runLater(() -> {
            loadArmies();
            army1Name.setText(army1Stored.getName());
            army1Name.setAlignment(Pos.CENTER);
            army2Name.setText(army2Stored.getName());
            army2Name.setAlignment(Pos.CENTER);
        });
    }

    /**
     * Fills the terrain combo box with all the terrains
     */
    public void fillTerrainComboBox() {
        List<Battle.Terrain> terrainList = new ArrayList<>(List.of(Battle.Terrain.values()));
        ObservableList<Battle.Terrain> terrainObservableList = FXCollections.observableList(terrainList);
        terrainComboBox.setItems(terrainObservableList);
    }


    final void textUpdated() {
        textArea.setScrollTop(0);
        textArea.setScrollLeft(0);
    }

    @FXML
    private void startBattle() {
        if (!numberOfBattles.getText().isBlank()) {
            int army1NumberOfWins = 0;
            int army2NumberOfWins = 0;
            textArea.clear();
            int battles = Integer.parseInt(numberOfBattles.getText());
            if (terrainComboBox.getSelectionModel().getSelectedItem() == null)
                Dialogs.getInstance().selectTerrain();
            else {
                Battle.setTerrain(terrainComboBox.getSelectionModel().getSelectedItem());
                for (int i = 1; i <= battles; i++) {
                    refreshArmies();
                    this.battle = new Battle(army1, army2);
                    Army winner = battle.simulate();
                    if (winner == army1) {
                        army1NumberOfWins++;
                    } else {
                        army2NumberOfWins++;
                    }
                    textArea.appendText(i + ". " + winner.getName() + "\n");
                }
                //Scrolls to the top of the text area
                Platform.runLater(this::textUpdated);
            }
            army1Wins.setText("Wins: " + army1NumberOfWins);
            army2Wins.setText("Wins: " + army2NumberOfWins);
        } else {
            Dialogs.getInstance().selectNumberOfBattles();
        }
    }

    @FXML
    private void numberOfBattlesHandler() {
        numberOfBattles.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!(newValue.matches("\\d*"))) {
                numberOfBattles.setText(newValue.replaceAll("[^\\d]", ""));
            } else if (newValue.equals("")) {
                    numberOfBattles.setText("");
            } else if (Integer.parseInt(newValue) > MAX_NUMBER_OF_BATTLES) {
                numberOfBattles.setText(String.valueOf(MAX_NUMBER_OF_BATTLES));
            } else if (newValue.indexOf("0") == 0) {
                Platform.runLater(() ->
                        numberOfBattles.setText("1")
                );
            }
        });
    }

    /**
     * Loads the armies with new units
     */
    public void loadArmies() {
        try {
            this.army1Stored = CSVFileHandler.readCSVArmy(CSVFileHandler.readCSVArmyPath(PATH_TO_ARMY_1));
            this.army2Stored = CSVFileHandler.readCSVArmy(CSVFileHandler.readCSVArmyPath(PATH_TO_ARMY_2));
        }catch (IOException e) {
            Dialogs.getInstance().cannotLoadScene();
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
     * Goes back to the main screen window
     * @param event, a mouse event
     */
    @FXML
    private void backToMainScreen(MouseEvent event) {
        SingletonClass.getInstance().getScene().loadMainScreen(event);
    }
}
