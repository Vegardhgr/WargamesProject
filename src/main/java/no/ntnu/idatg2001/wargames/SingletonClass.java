package no.ntnu.idatg2001.wargames;

public class SingletonClass {
    private static volatile SingletonClass instance;
    private LoadScene loadScene;

    private SingletonClass() {
        this.loadScene = new LoadScene();
    }

    public static SingletonClass getInstance() {
        if (SingletonClass.instance == null) {
            synchronized (SingletonClass.class) {
                SingletonClass.instance = new SingletonClass();
            }
        }
        return SingletonClass.instance;
    }

    public LoadScene getScene() {
        return loadScene;
    }
}
