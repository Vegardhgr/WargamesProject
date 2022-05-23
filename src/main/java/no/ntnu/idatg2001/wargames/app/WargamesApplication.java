package no.ntnu.idatg2001.wargames.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import no.ntnu.idatg2001.wargames.core.Army;
import no.ntnu.idatg2001.wargames.core.utilities.CSVFileHandler;
import no.ntnu.idatg2001.wargames.core.utilities.Dialogs;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Objects;

/**
 * This class starts the application
 */
public class WargamesApplication extends Application {

    /**
     * Starts the program by opening the main screen
     * @param stage, the stage to be shown
     * @throws IOException, exception that will be thrown if the path does not exist
     */
    @Override
    public void start(Stage stage) throws IOException {
        String filename1 = "pathToArmy1.csv";
        String filename2 = "pathToArmy2.csv";
        String savePath1 = Path.of(filename1).toAbsolutePath().toString();
        String savePath2 = Path.of(filename2).toAbsolutePath().toString();

        if (!fileExists(savePath1)) {
            try {
                CSVFileHandler.writeCSVArmy(new Army("", Collections.emptyList()), filename1);
            } catch (IOException e) {
                Dialogs.getInstance().somethingWrongWithTheFile();
            }
        }
        if (!fileExists(savePath2)) {
            try {
                CSVFileHandler.writeCSVArmy(new Army("", Collections.emptyList()), filename2);
            } catch (IOException e) {
                Dialogs.getInstance().somethingWrongWithTheFile();
            }
        }
        String path = "/fxmlFiles/mainScreen/MainScreen.fxml";
        URL url = getClass().getResource(path);
        Parent root = FXMLLoader.load(Objects.requireNonNull(url));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private boolean fileExists(String filePath) {
        File file = new File(filePath);
        return (file.exists() && !file.isDirectory());
    }

    /**
     * Launces the application
     * @param args, the arguments
     */
    public static void main(String[] args) {
        launch();
    }
}
