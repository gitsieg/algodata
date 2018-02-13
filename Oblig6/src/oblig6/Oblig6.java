
package oblig6;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Oblig6 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        TreePane root = new TreePane();
        Scene scene = new Scene(root, 1920, 1000);
        primaryStage.setTitle("Tree");
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
