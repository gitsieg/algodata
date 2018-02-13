package drawbinarytree;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DrawBinaryTree extends Application {

    private Gui gui = new Gui();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Drawing Operations Test");
        Group root = new Group();

        drawTree(10, 100, 45);
        root.getChildren().add(gui);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void drawTree(int nivå, double rotstørrelse, double vinkel) {
        vinkel = Math.toRadians(vinkel);
        double rotvinkel = Math.toRadians(90);
        drawTree(gui.canvas.getWidth() / 2, gui.canvas.getHeight(), rotvinkel, vinkel, rotstørrelse, nivå);
    }

    private void drawTree(double x1, double y1, double rotvinkel, double vinkel, double rotstørrelse, int nivå) {
        double x2, y2;
        double v1, v2;

        if (nivå == 0) {
            return;
        } else {
            x2 = x1 + rotstørrelse * Math.cos(rotvinkel);
            y2 = y1 - rotstørrelse * Math.sin(rotvinkel);
            gui.gc.strokeLine(x1, y1, x2, y2);
            v1 = rotvinkel + vinkel;
            drawTree(x2, y2, v1, vinkel, rotstørrelse * 0.8, nivå - 1);

            x2 = x1 + rotstørrelse * Math.cos(rotvinkel);
            y2 = y1 + rotstørrelse * Math.sin(-rotvinkel);
            gui.gc.strokeLine(x1, y1, x2, y2);
            v2 = rotvinkel - vinkel;
            drawTree(x2, y2, v2, vinkel, rotstørrelse * 0.8, nivå - 1);
        }
    }

    class Gui extends BorderPane {

        protected GraphicsContext gc;
        protected Canvas canvas;
        protected Slider slNivå, slVinkel, slRand;
        protected Label lbNivå, lbVinkel, lbRand;
        protected HBox hbNivå, hbVinkel, hbRand;
        protected VBox vbNodeContainer;

        public Gui() {
            // Tegning
            canvas = new Canvas(800, 800);
            gc = canvas.getGraphicsContext2D();

            // Sliders
            slNivå = new Slider(1, 15, 7);
            slVinkel = new Slider(10, 90, 45);
            slRand = new Slider(0, 1, 0.5);

            // Labels
            lbNivå = new Label("Nivå");
            lbVinkel = new Label("Vinkel");
            lbRand = new Label("Randomness");

            // Hboxes
            hbNivå = new HBox(lbNivå, slNivå);
            hbVinkel = new HBox(lbVinkel, slVinkel);
            hbRand = new HBox(lbRand, slRand);

            // VBox
            vbNodeContainer = new VBox(hbNivå, hbVinkel, hbRand);
            
            this.setCenter(canvas);
            this.setRight(vbNodeContainer);
        }
    }
}
