package kaos.cellautomaton;

import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class CelAutTab extends Tab {

    // GUIelementer
    private BorderPane cont;
    private HBox checkCont;
    private CheckBox BBBChk, BBWChk, BWBChk, WBBChk, BWWChk, WBWChk, WWBChk, WWWChk;
    private Button btnTegn;

    private Canvas canvas;
    private boolean[] verdier;
    
    // bredde/høyde
    private int bredde, høyde;
    private CellularAutomaton ca;

    public CelAutTab() {
        
        this.setText("Cellular Automaton");
        
        bredde = 1200;
        høyde = 800;
        
        verdier = new boolean[8];
        cont = new BorderPane();
        checkCont = new HBox();

        //Checkboxes
        BBBChk = new CheckBox("<-BBB");
        BBWChk = new CheckBox("<-BBW");
        BWBChk = new CheckBox("<-BWB");
        WBBChk = new CheckBox("<-WBB");
        BWWChk = new CheckBox("<-BWW");
        WBWChk = new CheckBox("<-WBW");
        WWBChk = new CheckBox("<-WWB");
        WWWChk = new CheckBox("<-WWW");
        btnTegn = new Button("Tegn");

        // Legger checkboxene til i hbox
        checkCont.getChildren().addAll(BBBChk, BBWChk, BWBChk, WBBChk, BWWChk, WBWChk, WWBChk, WWWChk, btnTegn);
        checkCont.setPadding(new Insets(10, 20, 10, 20));
        checkCont.setSpacing(20);
        cont.setTop(checkCont);
        cont.setCenter(canvas);
        this.setContent(cont);

        // Actions
        btnTegn.setOnAction(e -> {
            setValues();
            createCelAut();
            canvas = ca.getCanvas();
            cont.setCenter(canvas);
        });
    }

    private void setValues() {
        verdier[0] = BBBChk.isSelected();
        verdier[1] = BBWChk.isSelected();
        verdier[2] = BWBChk.isSelected();
        verdier[3] = WBBChk.isSelected();
        verdier[4] = BWWChk.isSelected();
        verdier[5] = WBWChk.isSelected();
        verdier[6] = WWBChk.isSelected();
        verdier[7] = WWWChk.isSelected();
    }
    
    private void createCelAut() {
        canvas = new Canvas(bredde, høyde);
        ca = new CellularAutomaton(verdier);
    }
    
}
