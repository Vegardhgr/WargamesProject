package no.ntnu.idatg2001.wargames;

import javafx.stage.FileChooser;

/**
 * This class contains objects where only one instance
 * of that object should be created.
 */
public class SingletonClass {
    // The fields
    private static volatile SingletonClass instance;
    private final LoadScene loadScene;
    private final FileChooser fileChooser;

    /**
     * The constructor. Initializes the fields.
     */
    private SingletonClass() {
        this.loadScene = new LoadScene();
        this.fileChooser = new FileChooser();
    }

    /**
     * Makes sure that the constructor, SingletonClass, is only called once.
     * @return SingletonClass, an instance of this class.
     */
    public static SingletonClass getInstance() {
        if (SingletonClass.instance == null) {
            synchronized (SingletonClass.class) {
                SingletonClass.instance = new SingletonClass();
            }
        }
        return SingletonClass.instance;
    }

    /**
     * Returns a LoadScene object.
     * @return LoadScene, the loadScene field
     */
    public LoadScene getScene() {
        return loadScene;
    }

    /**
     * Returns a FileChooser object.
     * @return FileChooser, the fileChooser field.
     */
    public FileChooser getFileChooser() {
        return fileChooser;
    }
}
