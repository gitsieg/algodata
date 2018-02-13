/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaos.binarytree;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author Rockass
 */
public class BinaryTreeTab extends Tab {

    private BorderPane binTreePane;
    private Canvas canvas;
    private Slider slNivå, slVinkel, slRand, slLengde;
    private Label lbNivå, lbVinkel, lbRand, lbLengde;
    private HBox hbNivå, hbVinkel, hbRand, hbLengde;
    private VBox vbNodeContainer;
    private int deg, n, lengde;
    private BinaryTreeDrawer btd;

    public BinaryTreeTab() {
        btd = new BinaryTreeDrawer();
        this.setText("Binary Tree");

        // Pane for å holde på elementer i tab
        binTreePane = new BorderPane();

        // Sliders
        slNivå = new Slider(1, 20, 7);
        slVinkel = new Slider(1, 150, 45);
        slRand = new Slider(0, 1, 0.5);
        slLengde = new Slider(10, 150, 75);

        // Labels
        lbNivå = new Label("Nivå");
        lbVinkel = new Label("Vinkel");
        lbRand = new Label("Randomness");
        lbLengde = new Label("Lengde");

        // Hboxes
        hbNivå = new HBox(lbNivå, slNivå);
        hbVinkel = new HBox(lbVinkel, slVinkel);
        hbRand = new HBox(lbRand, slRand);
        hbLengde = new HBox(lbLengde, slLengde);

        // VBox
        vbNodeContainer = new VBox(hbNivå, hbVinkel, hbRand, hbLengde);

        // Legger elementer til pane
        binTreePane.setRight(vbNodeContainer);
        canvas = btd.getCanvas();
        binTreePane.setCenter(canvas);
        this.setContent(binTreePane);

        //
        btd.drawTree(10, 100, 25);

        // Listeners
        n = slNivå.valueProperty().intValue();
        deg = slVinkel.valueProperty().intValue();
        lengde = slLengde.valueProperty().intValue();

        slNivå.valueProperty().addListener(ov -> {
            n = slNivå.valueProperty().intValue();
            btd.drawTree(n, lengde, deg);
        });

        slVinkel.valueProperty().addListener(ov -> {
            deg = slVinkel.valueProperty().intValue();
            btd.drawTree(n, lengde, deg);
        });

        slLengde.valueProperty().addListener(ov -> {
            lengde = slLengde.valueProperty().intValue();
            btd.drawTree(n, lengde, deg);
        });
    }
}
