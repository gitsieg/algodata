package kaos;

import kaos.conways.ConwayTab;
import kaos.cellautomaton.CelAutTab;
import kaos.binarytree.BinaryTreeTab;
import javafx.scene.control.TabPane;
import kaos.bifurcation.BifurcationTab;
import kaos.mandelbrot.MandelbrotTab;

/**
 *
 * @author Rockass
 */
public class KaosTabPane extends TabPane {

    private CelAutTab caTab;
    private BinaryTreeTab btt;
    private ConwayTab ct;
    private BifurcationTab bft;
    private MandelbrotTab mbt;
    
    public KaosTabPane() {
        caTab = new CelAutTab();
        btt = new BinaryTreeTab();
        bft = new BifurcationTab();
        mbt = new MandelbrotTab();
        
        // Legger elementene i TabPanen
        this.getTabs().addAll(caTab, btt, bft, mbt);
    }
}
