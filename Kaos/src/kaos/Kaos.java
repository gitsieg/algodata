package kaos;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Kaos extends Application {

    private KaosTabPane ktp;
    private Scene scene;
    @Override
    public void start(Stage primaryStage) {
        ktp = new KaosTabPane();
        
        scene = new Scene(ktp, 1300, 900);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
}
