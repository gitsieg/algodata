package kaos.bifurcation;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

/**
 *
 * @author Nikolai
 */
public class BifurcationDiagram {

    private static Canvas canvas;
    private static GraphicsContext gc;
    private static PixelWriter pw;
    private double høyde, bredde;
    private int xc;
    private double rc = 0;
    private double rmax = 4.0;

    public BifurcationDiagram() {

        canvas = new Canvas(600, 600);
        høyde = canvas.getHeight();
        bredde = canvas.getWidth();
        gc = canvas.getGraphicsContext2D();
        pw = gc.getPixelWriter();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 600, 600);
        tegnDiagram();
    }

    public void tegnDiagram() {
        double x = 0.5;
        for (double r = 0.0; r < 4.0; r += 0.001) {

            xc = (int) (høyde - (x * høyde));
            rc = (4.0 - r) / 4 ;
            pw.setColor((int)rc, xc, Color.BLACK);
            x = r * x * (1 - x);
            r += 0.01;
        }
    }

    public static void main(String[] args) {
        double X = 0.5;
        double r = 2.4;
        for (int i = 0; i < 40 && r < 4; i++) {
            System.out.println("Iterasjon: " + i + " : X : " + X + " : r : " + r + " : " + r * X * (1 - X));
            r += 0.1;
            X = r * X * (1 - X);
        }
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
