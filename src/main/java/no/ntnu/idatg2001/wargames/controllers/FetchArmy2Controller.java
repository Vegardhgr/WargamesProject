package no.ntnu.idatg2001.wargames.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import no.ntnu.idatg2001.wargames.CSVFileHandler;
import no.ntnu.idatg2001.wargames.SingletonClass;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FetchArmy2Controller implements Initializable {
    @FXML
    private TextField pathField;
    @FXML
    private BorderPane borderPaneId;
    @FXML
    private void backToCreateArmyScreen(MouseEvent event) throws IOException {
        SingletonClass.getInstance().getScene().loadCreateArmyScene(event);
    }

    @FXML
    private void openDirectory(MouseEvent event) {
        FileChooser dirChooser = SingletonClass.getInstance().getFileChooser();
        Stage stage = (Stage) borderPaneId.getScene().getWindow();
        File file = dirChooser.showOpenDialog(stage);

        if (file != null)
            pathField.setText(file.getAbsolutePath());
    }

    @FXML
    private void savePathArmy2(MouseEvent event) throws IOException {
        String path = pathField.getText();
        CSVFileHandler.writeCSVArmyPath("src/pathToArmy2.csv", path, 2);
    }

    @FXML
    private void clearTextField(MouseEvent event) {
        pathField.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pathField.setEditable(false);
        try {
            pathField.setText(CSVFileHandler.readCSVArmyPath("src/pathToArmy2.csv", 1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}