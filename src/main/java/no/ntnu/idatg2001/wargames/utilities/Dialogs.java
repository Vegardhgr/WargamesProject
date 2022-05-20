package no.ntnu.idatg2001.wargames.utilities;

import javafx.scene.control.Alert;

public class Dialogs {
    private static volatile Dialogs instance;
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

    public void cannotLoadScene() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Path error");
        alert.setContentText("Cannot load the scene");
        alert.showAndWait();
    }
}
