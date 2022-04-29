package no.ntnu.idatg2001.wargames.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import no.ntnu.idatg2001.wargames.CSVFileHandler;
import no.ntnu.idatg2001.wargames.SingletonClass;
import no.ntnu.idatg2001.wargames.units.Unit;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SimulateBattleController implements Initializable {
    private static final String PATH_TO_ARMY_1 = "src/pathToArmy1.csv";
    private static final String PATH_TO_ARMY_2 = "src/pathToArmy2.csv";
    List<Unit> unitList1;
    List<Unit> unitList2;


    @FXML
    GridPane gridPane;

    @FXML
    private void backToMainScreen(MouseEvent event) throws IOException {
        SingletonClass.getInstance().getScene().loadMainScreen(event);
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        gridPane.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.setGridLinesVisible(true);
        try {
            this.unitList1 = CSVFileHandler.readCSVArmy(CSVFileHandler.readCSVArmyPath(PATH_TO_ARMY_1)).getUnitList();
            this.unitList2 = CSVFileHandler.readCSVArmy(CSVFileHandler.readCSVArmyPath(PATH_TO_ARMY_2)).getUnitList();
            makeArmy1Rectangles();
            makeArmy2Rectangles();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void makeArmy1Rectangles() {
        int j = 0;
        int i = 0;
        for (Unit unit : this.unitList1) {
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

        for (Unit unit : this.unitList2) {
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


}
