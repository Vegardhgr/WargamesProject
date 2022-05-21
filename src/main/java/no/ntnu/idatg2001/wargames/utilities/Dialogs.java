package no.ntnu.idatg2001.wargames.utilities;

import javafx.scene.control.Alert;

/**
 * This class contains dialogs to inform the user.
 */
public class Dialogs {
    //The instance of the class
    private static volatile Dialogs instance;
    //Private constructor to prevent instantiation
    private Dialogs() {}

    /**
     * Makes sure that only one instance of the dialog object is created.
     * @return Dialogs, an instance of this class.
     */
    public static Dialogs getInstance() {
        if (Dialogs.instance == null) {
            synchronized (Dialogs.class) {
                Dialogs.instance = new Dialogs();
            }
        }
        return Dialogs.instance;
    }

    /**
     * Shows an error dialog if the path to the file does
     * not exist.
     */
    public void cannotLoadScene() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Path error");
        alert.setContentText("Cannot load the scene.");
        alert.showAndWait();
    }

    /**
     * Shows an error dialog if the path to the file does
     * not exist.
     */
    public void somethingWrongWithTheFile() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("File error");
        alert.setContentText("Something wrong happened with the file.");
        alert.showAndWait();
    }

    /**
     * Shows an error dialog if the file cannot be read as an army.
     */
    public void cannotReadArmies() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Army error");
        alert.setContentText("Check that the selected file can be read as an army.");
        alert.showAndWait();
    }

    /**
     * Shows an error dialog if the file is not found
     */
    public void fileNotFound() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("File not find error");
        alert.setContentText("Cannot find the file that is selected.");
        alert.showAndWait();
    }

    /**
     * Informs the user to close the file
     */
    public void closeFile() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Close file");
        alert.setContentText("The file you are saving this army to is open.\n" +
                "Please close this file and try again.");
        alert.showAndWait();
    }

    /**
     * Informs the user to select a terrain
     */
    public void selectTerrain() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Select terrain");
        alert.setContentText("Please select a terrain before starting the simulation.");
        alert.showAndWait();
    }

    /**
     * Informs the user that there are too many units in the army
     */
    public void tooManyUnits() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Too many units");
        alert.setContentText("You cannot have more than 1000 units in each army in this simulation.");
        alert.showAndWait();
    }

    /**
     * Informs the user to select a number of battles
     */
    public void selectNumberOfBattles() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Select number of battles");
        alert.setContentText("Selecting a number of battles is required before running the simulation.");
        alert.showAndWait();
    }

    /**
     * Informs the user to select a file
     */
    public void noFileIsSelected() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("No file selected");
        alert.setContentText("Please go to 'Create Army' and select a file.");
        alert.showAndWait();
    }
}
