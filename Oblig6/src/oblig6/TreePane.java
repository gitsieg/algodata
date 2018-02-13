package oblig6;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import oblig6.BinarySearchTree.BinaryNode;

public class TreePane extends BorderPane {

    private int margin = 60;
    private Pane pane;
    private BinarySearchTree<Integer> tree;
    private Label lblNy, lblSlett;
    private TextField txtNy, txtSlett, txtMeldinger, txtHeight;
    private Button btnExit;
    private HBox hbIn;
    private int itr;

    public TreePane() {
        tree = new BinarySearchTree<>();
        
        lblNy = new Label("Ny");
        lblSlett = new Label("Slett");
        txtNy = new TextField();
        txtSlett = new TextField();
        txtMeldinger = new TextField();
        txtHeight = new TextField();
        btnExit = new Button("Exit");
        hbIn = new HBox(lblNy, txtNy, lblSlett, txtSlett, txtMeldinger, btnExit);
        hbIn.setSpacing(20);

        pane = new Pane();
        pane.setPrefSize(1900, 1000);
        itr = 0;
        drawBinaryTree(tree);
        this.setTop(txtHeight);
        this.setCenter(pane);
        this.setBottom(hbIn);

        txtNy.setOnAction(e -> {
            txtMeldinger.setText("");
            itr = 0;
            int s = Integer.parseInt(txtNy.getText());
            txtNy.setText("");
            pane = new Pane();
            pane.setPrefSize(1900, 1000);
            this.setCenter(pane);
            try {
                tree.insert(s);
            } catch (IllegalArgumentException ev) {
                txtMeldinger.setText("Duplikat: " + ev.toString());
            }
            txtHeight.setText(tree.getHeight()+"");

            drawBinaryTree(tree);
        });
    }

    public void drawBinaryTree(BinarySearchTree tree) {
        if (tree.size() == 0) {
            return;
        }
        int nivå = 0, pos = 0;
        BinaryNode node = tree.getRoot();
        System.out.println("root:" + node.element);
        tegnTre(node, nivå, pos);
    }

    private int tegnTre(BinaryNode node, int nivå, int pos) {
        int leftNivå = nivå;
        int rightNivå = nivå;
        int childPos = 0;
        if (node.left != null) {
            leftNivå++;
            childPos = tegnTre(node.left, leftNivå, itr);
        }
        pos = ++itr;
        if (node.left != null) {
            tegnStrek(pos, childPos, nivå);
        }
        tegnNode(nivå, pos, node.element.toString());
        System.out.println("nodevalue: " + node.element + " nivå: " + nivå + " pos: " + pos);
        if (node.right != null) {
            rightNivå++;
            childPos = tegnTre(node.right, rightNivå, pos);
        }
        if (node.right != null) {
            tegnStrek(pos, childPos, nivå);
        }
        return pos;
    }

    private void tegnNode(int nivå, int pos, String element) {
        double cX = ((1200 - margin * 2) + margin) / tree.size * pos;
        double cY = (((800 - margin * 2) + margin) / tree.calcLevels()) * nivå;
        Circle c = new Circle(cX, cY + 30, 25);
        pane.getChildren().add(c);
        Text t = new Text(cX - 10, cY + 40, element);
        t.setFont(Font.font("Verdana", 30));
        t.setFill(Color.WHITE);
        pane.getChildren().add(t);
    }

    private void tegnStrek(int pos, int strekPos, int nivå) {
        double cX = ((1200 - margin * 2) + margin) / tree.size * pos;
        double cY = (((800 - margin * 2) + margin) / tree.calcLevels()) * nivå;

        double cX2 = (((1200 - margin * 2) + margin) / tree.size * strekPos);
        double cY2 = ((((800 - margin * 2) + margin) / tree.calcLevels()) * (nivå + 1));

        Line l = new Line(cX, cY + 30, cX2, cY2 + 30);
        pane.getChildren().add(l);
        l.toBack();

    }
}
