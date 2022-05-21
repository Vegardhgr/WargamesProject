package no.ntnu.idatg2001.wargames.controllers.createArmy;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import no.ntnu.idatg2001.wargames.utilities.CSVFileHandler;
import no.ntnu.idatg2001.wargames.utilities.Dialogs;
import no.ntnu.idatg2001.wargames.utilities.SingletonClass;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller class for FetchArmy2.fxml. This class is responsible for
 * handling which army the user wants to add units to.
 *
 * @author Vegard Grøder
 */
public class FetchArmy2Controller implements Initializable {
    //The path to the path where the army is stored.
    private static final String PATH_TO_ARMY_2 = "src/pathToArmy2.csv";
    //The text field where the path to the selected file is stored.
    @FXML
    private TextField pathField;
    //The border pane id
    @FXML
    private BorderPane borderPaneId;

    /**
     * Sets the text field where the path is stored to be not editable.
     * Also sets the text field to store the path to the army.
     *
     * @param url, the url
     * @param resourceBundle, the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pathField.setEditable(false);
        try {
            pathField.setText(CSVFileHandler.readCSVArmyPath(PATH_TO_ARMY_2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Goes back to the create army screen.
     * @param event, a mouse event
     */
    @FXML
    private void backToCreateArmyScreen(MouseEvent event) {
        SingletonClass.getInstance().getScene().loadCreateArmyScene(event);
    }

    /**
     * Goes back to the main screen.
     * @param event, a mouse event
     */
    @FXML
    private void backToMainScreen(MouseEvent event) {
        SingletonClass.getInstance().getScene().loadMainScreen(event);
    }

    /**
     * Opens the file chooser and sets the path to the selected file in
     * the text field.
     */
    @FXML
    private void openDirectory() {
        FileChooser dirChooser = SingletonClass.getInstance().getFileChooser();
        Stage stage = (Stage) borderPaneId.getScene().getWindow();
        File file = dirChooser.showOpenDialog(stage);

        if (file != null)
            pathField.setText(file.getAbsolutePath());
    }

    /**
     * Saves the path to the selected file in the text field.
     */
    @FXML
    private void savePathArmy2() {
        try {
            String path = pathField.getText();
            CSVFileHandler.writeCSVArmyPath(PATH_TO_ARMY_2, path);
        } catch (IOException e) {
            Dialogs.getInstance().somethingWrongWithTheFile();
        }
    }

    /**
     * Clears the text field.
     */
    @FXML
    private void clearTextField() {
        pathField.clear();
    }

    /**
     * Path to the file where army 2 is stored.
     * @return String, the path.
     * @throws IOException, if the file does not exist.
     */
    public static String getArmy2Path() throws IOException {
        return CSVFileHandler.readCSVArmyPath(PATH_TO_ARMY_2);
    }
}