package kaos.mandelbrot;

import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 *
 * @author Nikolai
 */
public class MandelbrotTab extends Tab {

    private StackPane manzoomStack;
    private BorderPane container;
    private Canvas mandelbrotCanvas, zoomCanvas;
    private MandelbrotPlotter mandelbrot;
    private GraphicsContext zoomgc, mangc;
    private double høyde, bredde;

    public MandelbrotTab() {
        this.setText("Mandelbrot");
        manzoomStack = new StackPane();
        container = new BorderPane();
        mandelbrot = new MandelbrotPlotter();
        mandelbrotCanvas = mandelbrot.getCanvas();

        // For displaying zoom-rectangle
        bredde = mandelbrot.getBredde();
        høyde = mandelbrot.getHøyde();
        zoomCanvas = new Canvas(bredde, høyde);
        zoomgc = zoomCanvas.getGraphicsContext2D();
        manzoomStack.getChildren().addAll(mandelbrotCanvas, zoomCanvas);
        container.setCenter(manzoomStack);
        this.setContent(container);
        container.setCursor(Cursor.CROSSHAIR);

        // Displaying zoom, responds to onMouseMoved
        zoomCanvas.setOnMouseMoved(e -> {
            zoomgc.clearRect(0, 0, bredde, høyde);
            zoomgc.setStroke(Color.RED);
            zoomgc.strokeRect(e.getX() - bredde / 8 / 2, e.getY() - høyde / 8 / 2, bredde / 8, høyde / 8);

        });

        zoomCanvas.setOnMouseClicked(e -> {
            mangc = mandelbrotCanvas.getGraphicsContext2D();
            mangc.clearRect(0, 0, bredde, høyde);
            if (e.getButton() == MouseButton.PRIMARY) {
                mandelbrot.zoomIn(e.getX() - bredde / 8 / 2, e.getX() - høyde / 8 / 2);
            }
            if (e.getButton() == MouseButton.SECONDARY) {
                mandelbrot.zoomOut(e.getX() - bredde / 8 / 2, e.getY() - høyde / 8 / 2);
            }
        });
    }
}
