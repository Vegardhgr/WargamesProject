package no.ntnu.idatg2001.wargames.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import no.ntnu.idatg2001.wargames.utilities.CSVFileHandler;
import no.ntnu.idatg2001.wargames.utilities.SingletonClass;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FetchArmy1Controller implements Initializable{
    private static final String PATH_TO_ARMY_1 = "src/pathToArmy1.csv";
    @FXML
    private TextField pathField;
    @FXML
    private BorderPane borderPaneId;
    @FXML
    private void backToCreateArmyScreen(MouseEvent event) {
        SingletonClass.getInstance().getScene().loadCreateArmyScene(event);
    }

    @FXML
    private void backToMainScreen(MouseEvent event) {
        SingletonClass.getInstance().getScene().loadMainScreen(event);
    }

    @FXML
    private void openDirectory() {
        FileChooser fileChooser = SingletonClass.getInstance().getFileChooser();
        Stage stage = (Stage) borderPaneId.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null)
            pathField.setText(file.getAbsolutePath());
    }

    @FXML
    private void savePathArmy1() throws IOException {
        String path = pathField.getText();
        CSVFileHandler.writeCSVArmyPath(PATH_TO_ARMY_1, path);
    }

    @FXML
    private void clearTextField() {
        pathField.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pathField.setEditable(false);
        try {
            pathField.setText(CSVFileHandler.readCSVArmyPath(PATH_TO_ARMY_1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Path to the file where army 1 is stored.
     * @return String, the path.
     * @throws IOException, if the file does not exist.
     */
    public static String getArmy1Path() throws IOException {
        return CSVFileHandler.readCSVArmyPath(PATH_TO_ARMY_1);
    }
}
