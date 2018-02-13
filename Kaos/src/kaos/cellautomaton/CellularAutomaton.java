package kaos.cellautomaton;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Nikolai
 */
class CellularAutomaton {

    private Canvas canvas;
    private GraphicsContext gc;
    private final int ARR_SIZE = 600;
    private boolean[] values;
    private boolean[] last = new boolean[ARR_SIZE];
    private boolean[] next = new boolean[ARR_SIZE];

    // Brukes for å kalkulere størrelse på rektangler. X endrer verdi for hvert rektangel som blir tegnet, y endres for hvert nivå.
    private int y1;
    private int x1;
    private double cubeDimension = 2;      // Størrelse på kuben som blir tegnet.

    public CellularAutomaton(boolean[] values) {
        last[ARR_SIZE / 2] = true;
        this.values = values;               // Representerer reglene for hva som skal fargelegges eller ikke.
        canvas = new Canvas(1200, 800);
        gc = canvas.getGraphicsContext2D();

        drawFirst();
        for (int i = 0; i < 399; i++) {
            writeLine();
        }
    }
    /**
     * Gjennomløper tabell for forrige linje og genererer en ny tabell basert på predefinerte reglene en bruker har satt.
     */
    private void writeLine() {
        for (int i = 0; i < last.length - 3; i++) {
            for (int j = i; j < i + 3 && j < last.length - 2; j++) {
                if (last[j] && last[j + 1] && last[j + 2]) {                             //XXX
                    next[j + 1] = values[0];
                } else if (last[j] && last[j + 1] && !last[j + 2]) {                     //XX_
                    next[j + 1] = values[1];
                } else if (last[j] && !last[j + 1] && last[j + 2]) {                     //X_X
                    next[j + 1] = values[2];
                } else if (!last[j] && last[j + 1] && last[j + 2]) {                     //_XX
                    next[j + 1] = values[3];
                } else if (last[j] && !last[j + 1] && !last[j + 2]) {                    //X__
                    next[j + 1] = values[4];
                } else if (!last[j] && last[j + 1] && !last[j + 2]) {                    //_X_
                    next[j + 1] = values[5];
                } else if (!last[j] && !last[j + 1] && last[j + 2]) {                    //__X
                    next[j + 1] = values[6];
                } else if (!last[j] && !last[j + 1] && !last[j + 2]) {                   //___
                    next[j + 1] = values[7];
                }
            }
        }
        last = next;
        drawRects();
        next = new boolean[ARR_SIZE];
    }

    private void drawRects() {
        for (int x = 0; x < next.length; x++) {
            if (next[x]) {
                gc.setFill(Color.CYAN);
            } else {
                gc.setFill(Color.GRAY);
            }
            gc.fillRect(x1, y1, cubeDimension, cubeDimension);
            x1 += cubeDimension;
        }
        x1 = 0;
        y1 += cubeDimension;
    }

    private void drawFirst() {
        for (int i = 0; i < last.length; i++) {
            if (last[i]) {
                gc.setFill(Color.CYAN);
            } else {
                gc.setFill(Color.GRAY);
            }
            gc.fillRect(x1, y1, cubeDimension, cubeDimension);
            x1 += cubeDimension;
        }
        x1 = 0;
        y1 += cubeDimension;
    }

    public Canvas getCanvas() {
        return this.canvas;
    }
    
    class Rekt extends Rectangle implements EventHandler<MouseEvent> {
        this.
        
        
        @Override
        public void handle(MouseEvent event) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    
    
    }
}
