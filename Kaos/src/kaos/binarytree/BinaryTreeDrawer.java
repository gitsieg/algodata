/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaos.binarytree;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author Rockass
 */
class BinaryTreeDrawer {

    private GraphicsContext gc;
    private Canvas canvas;

    public BinaryTreeDrawer() {
        canvas = new Canvas(800, 800);
        gc = canvas.getGraphicsContext2D();
    }

    public void drawTree(int nivå, double rotstørrelse, double vinkel) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        vinkel = Math.toRadians(vinkel);
        double rotvinkel = Math.toRadians(90);
        drawTree(canvas.getWidth() / 2, canvas.getHeight(), rotvinkel, vinkel, rotstørrelse, nivå);
    }

    private void drawTree(double x1, double y1, double rotvinkel, double vinkel, double rotstørrelse, int nivå) {
        double x2, y2;
        double v1, v2;

        if (nivå == 0) {
            return;
        } else {
            x2 = x1 + rotstørrelse * Math.cos(rotvinkel);
            y2 = y1 - rotstørrelse * Math.sin(rotvinkel);
            gc.strokeLine(x1, y1, x2, y2);
            v1 = rotvinkel + vinkel;
            drawTree(x2, y2, v1, vinkel, rotstørrelse * 0.8, nivå - 1);

            x2 = x1 + rotstørrelse * Math.cos(rotvinkel);
            y2 = y1 + rotstørrelse * Math.sin(-rotvinkel);
            gc.strokeLine(x1, y1, x2, y2);
            v2 = rotvinkel - vinkel;
            drawTree(x2, y2, v2, vinkel, rotstørrelse * 0.8, nivå - 1);
        }
    }
    
    public Canvas getCanvas() {
        return canvas;
    }
}
