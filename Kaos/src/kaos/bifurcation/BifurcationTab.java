package kaos.bifurcation;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Nikolai
 */
public class BifurcationTab extends Tab {
    
    private BorderPane content;
    private Canvas canvas;
    private BifurcationDiagram bifurcationDiagram;

    public BifurcationTab() {
        this.setText("Bifurcation Diagram");
        bifurcationDiagram = new BifurcationDiagram();
        canvas = bifurcationDiagram.getCanvas();
        content = new BorderPane();
        content.setCenter(canvas);
        this.setContent(content);
    }
}
