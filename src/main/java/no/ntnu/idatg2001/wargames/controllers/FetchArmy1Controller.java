package no.ntnu.idatg2001.wargames.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import no.ntnu.idatg2001.wargames.SingletonClass;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FetchArmy1Controller implements Initializable{
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
            pathField.setText("Path: " + file.getAbsolutePath());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pathField.setEditable(false);
    }
}
