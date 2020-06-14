package common_stage;

import javafx.stage.Stage;

public class CommonStageSingleton {

    private static Stage mainWindow;

    private static  CommonStageSingleton instance;

    private CommonStageSingleton(){
        mainWindow = new Stage();
    }

    public static CommonStageSingleton getInstance(){
        if(instance == null){
            instance = new CommonStageSingleton();
        }
        return instance;
    }
    public Stage getMainWindow(){
        return  mainWindow;
    }

}
