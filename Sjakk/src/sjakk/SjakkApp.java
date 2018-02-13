/**
 * Denne klassen lager et GUI til bruker som ønsker å spille sjakk med en annen person på samme datamaskin.
 * Klassen SjakkApp benytter modell-klassene Taarn.java, Offiser.java, Brikke.java, Bonde.Java
 * Brett.java er en kontrollklasse for modellklassene.
 */
package sjakk;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Nikolai Fosså, Atle Amundsen, Kristian Robertsen
 */
public class SjakkApp extends Application {

    private final static int BRETTSTORRELSE = 8;
    private final static int RUTESTORRELSE = 70;
    private final int BREDDE = 599;
    private final int HOYDE = 664;
    private static int spillNr = 1;
    private Label lblFra = new Label("Fra rute"), lblTil = new Label("Til rute");
    private TextField txtFra = new TextField(), txtTil = new TextField();
    private String fra = "  ", til = "  ";
    private Button btnFlytt = new Button("Flytt brikke"), btnNyttSpill = new Button("Nytt spill"), btnLagre = new Button("Lagre spill");
    private Text txtStatus = new Text("Status:  ");
    private GridPane rootBrett = new GridPane();
    private GridPane horisontalIndexTop = new GridPane();
    private GridPane horisontalIndexBot = new GridPane();
    private GridPane vertikalIndexLeft = new GridPane();
    private GridPane vertikalIndexRight = new GridPane();
    private Brett brett = new Brett(1);
    private StackPane[][] panes = new StackPane[BRETTSTORRELSE][BRETTSTORRELSE];
    private Rectangle[][] rektangler = new Rectangle[BRETTSTORRELSE][BRETTSTORRELSE];
    private Color sort = Color.web("#B49B81");
    private Color hvit = Color.web("#FFFFFF");
    private HBox topHBox, botHBox;
    private BorderPane root = new BorderPane();
    private BorderPane bottomBorder = new BorderPane();
    private BorderPane topBorder = new BorderPane();
    private ImageView[] tokensIv = new ImageView[12];

    @Override
    public void start(Stage primaryStage) {
        // Tegn brettet til konsoll
        tegnCharBrett();

        // Oppretter spillebrettet
        createBoard();

        // Stiler textfields
        txtFra.setPrefColumnCount(2);
        txtTil.setPrefColumnCount(2);

        // Metode for å stilere HBoxer
        // Stiling i topHBox
        stilerHBoxer();
        topBorder.setTop(topHBox);
        horisontalIndexTop.setAlignment(Pos.CENTER);
        topBorder.setBottom(horisontalIndexTop);

        // Noder i bottomBorder
        // Styling og posisjon botHBox
        bottomBorder.setBottom(botHBox);
        horisontalIndexBot.setAlignment(Pos.CENTER);
        bottomBorder.setTop(horisontalIndexBot);

        // Stiler gridpanes
        stilerGrids();
        // Legger til noder i root
        plasserIRoot();

        Scene scene = new Scene(root, BREDDE, HOYDE);

        primaryStage.setTitle("This is Sjakk");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false); // Sett false når ferdig
        primaryStage.show();

        // EventHandling
        btnFlytt.setOnAction(event -> {
            // Hardkoder inn litt feilhåndtering ved å legge til mellomrom, slik at det alltid blir passert 2 karakterer til metoder
            // Metoder tilgjengelig i brett osv henter kun på første 2 karakterer.
            gjorTrekk();
        });

        txtFra.setOnAction(event -> {
            gjorTrekk();
        });

        txtTil.setOnAction(event -> {
            gjorTrekk();
        });

        btnNyttSpill.setOnAction(event -> {
            brett = new Brett(spillNr++);
            creatRects();
            createPanes();
            tegnBrett();
            tegnBrikker();
        });
    }

    /**
     * Denne metoden er en samling metoder som til sammen lager spillebrettet og
     * tilhørende elementer.
     */
    private void createBoard() {
        creatRects();
        createPanes();
        createHorisontalIndex();
        createVerticalIndex();
        tegnBrett();
        tegnBrikker();
    }

    private void tegnCharBrett() {
        Brikke[][] br = brett.getBrikkene();
        System.out.print("  a  b  c  d  e  f  g  h");
        for (int i = 0; i < 8; i++) {
            System.out.print("\n" + (i + 1));
            for (int j = 0; j < 8; j++) {
                if (br[i][j] == null) {
                    System.out.print(":--");
                } else {
                    if (br[i][j].getFarge() == Color.WHITE) {
                    System.out.print(":H" + br[i][j].brikkenavn());
                    }
                    else
                        System.out.print(":B" + br[i][j].brikkenavn());
                }
            }
        }
        System.out.println("\n\n\n");
    }

    /**
     * Denne metoden kaller på metode tegnEndring for å tegne brikker på brett.
     * Den legger til siste trekk til statusfelt, eller gir bruker beskjed dersom trekket ikke er gjennomførbart.
     */
    private void gjorTrekk() {
        fra = txtFra.getText() + "  ";
        til = txtTil.getText() + "  ";
        if (brett.flyttBrikke(fra, til)) {
            tegnEndring(fra, til);
            txtStatus = new Text("Status: " + fra + "til " + til);
            topHBox.getChildren().clear();
            topHBox.getChildren().add(txtStatus);
        } else {
            txtStatus = new Text("Status: Trekk ikke gjennomførbart");
            topHBox.getChildren().clear();
            topHBox.getChildren().add(txtStatus);
        }
    }

    /**
     * Denne metoden initierer tabellen rektangler[][] med rektangler
     */
    private void creatRects() {
        for (int i = 0; i < BRETTSTORRELSE; i++) {
            if (i % 2 == 0) {
                for (int j = 0; j < BRETTSTORRELSE; j += 2) {
                    rektangler[i][j] = new Rectangle(RUTESTORRELSE, RUTESTORRELSE, hvit);
                    rektangler[i][j + 1] = new Rectangle(RUTESTORRELSE, RUTESTORRELSE, sort);
                }
            } else {
                for (int j = 0; j < BRETTSTORRELSE; j += 2) {
                    rektangler[i][j] = new Rectangle(RUTESTORRELSE, RUTESTORRELSE, sort);
                    rektangler[i][j + 1] = new Rectangle(RUTESTORRELSE, RUTESTORRELSE, hvit);
                }
            }
        }
    }

    /**
     * Denne metoden initierer tabellen panes[][] med StackPanes
     */
    private void createPanes() {
        for (int i = 0; i < BRETTSTORRELSE; i++) {
            if (i % 2 == 0) {
                for (int j = 0; j < BRETTSTORRELSE; j += 2) {
                    panes[i][j] = new StackPane();
                    panes[i][j].getChildren().add(rektangler[i][j]);
                    panes[i][j + 1] = new StackPane();
                    panes[i][j + 1].getChildren().add(rektangler[i][j + 1]);
                }
            } else {
                for (int j = 0; j < BRETTSTORRELSE; j += 2) {
                    panes[i][j] = new StackPane();
                    panes[i][j].getChildren().add(rektangler[i][j]);
                    panes[i][j + 1] = new StackPane();
                    panes[i][j + 1].getChildren().add(rektangler[i][j + 1]);
                }
            }
        }
    }

    /**
     * Denne metoden legger til brikkenes "tokens" i sitt tilhørende StackPane,
     * angitt av panes[][]
     */
    private void tegnBrikker() {
        int t = 7; // Oversetter til skikkelig sjakkbrett.
        for (int i = 0; i < BRETTSTORRELSE; i++) {
            for (int j = 0; j < BRETTSTORRELSE; j++) {
                String rute = (char) (j + 'a') + "" + (char) (i + '1');
                if (brett.getBrikke(rute) != null) {
                    String brikkenavn = brett.getBrikke(rute).brikkenavn();
                    if (brett.getBrikke(rute).getFarge() == Color.WHITE) {
                        switch (brikkenavn) {
                            case "B":
                                panes[t][j].getChildren().add(new ImageView(brett.getBrikke(rute).getToken()));
                                break;
                            case "T":
                                panes[t][j].getChildren().add(new ImageView(brett.getBrikke(rute).getToken()));
                                break;
                            case "S":
                                panes[t][j].getChildren().add(new ImageView(brett.getBrikke(rute).getToken()));
                                break;
                            case "L":
                                panes[t][j].getChildren().add(new ImageView(brett.getBrikke(rute).getToken()));
                                break;
                            case "K":
                                panes[t][j].getChildren().add(new ImageView(brett.getBrikke(rute).getToken()));
                                break;
                            case "D":
                                panes[t][j].getChildren().add(new ImageView(brett.getBrikke(rute).getToken()));
                                break;
                        }
                    } else {
                        switch (brikkenavn) {
                            case "B":
                                panes[t][j].getChildren().add(new ImageView(brett.getBrikke(rute).getToken()));
                                break;
                            case "T":
                                panes[t][j].getChildren().add(new ImageView(brett.getBrikke(rute).getToken()));
                                break;
                            case "S":
                                panes[t][j].getChildren().add(new ImageView(brett.getBrikke(rute).getToken()));
                                break;
                            case "L":
                                panes[t][j].getChildren().add(new ImageView(brett.getBrikke(rute).getToken()));
                                break;
                            case "K":
                                panes[t][j].getChildren().add(new ImageView(brett.getBrikke(rute).getToken()));
                                break;
                            case "D":
                                panes[t][j].getChildren().add(new ImageView(brett.getBrikke(rute).getToken()));
                                break;
                        }
                    }

                    /*
                    Text txt = new Text(brett.getBrikke(rute).brikkenavn());
                    txt.setFont(new Font("verdana", 40));
                    txt.setFill(brett.getBrikke(rute).getFarge());
                    txt.setStroke(Color.BLACK);
                    panes[t][j].getChildren().add(txt);
                     */
                } else {
                    panes[t][j].getChildren().add(new Text(""));
                }
            }
            t--;
        }
    }

    /**
     * Denne metoden legger til StackPanes i brettets GridPane
     */
    private void tegnBrett() {
        for (int i = 0; i < BRETTSTORRELSE; i++) {
            if (i % 2 == 0) {
                for (int j = 0; j < BRETTSTORRELSE; j += 2) {
                    rootBrett.add(panes[i][j], j, i);
                    rootBrett.add(panes[i][j + 1], j + 1, i);
                }
            } else {
                for (int j = 0; j < BRETTSTORRELSE; j += 2) {
                    rootBrett.add(panes[i][j], j, i);
                    rootBrett.add(panes[i][j + 1], j + 1, i);
                }
            }
        }
    }

    /**
     * Denne metoden legger til horisontal indeksering til GridPanes
     */
    private void createHorisontalIndex() {
        for (int i = 0; i < BRETTSTORRELSE; i++) {
            horisontalIndexTop.add(new StackPane(new Rectangle(70, 20, Color.WHITE), new Label((char) (i + 'a') + "")), i, 0);
            horisontalIndexBot.add(new StackPane(new Rectangle(70, 20, Color.WHITE), new Label((char) (i + 'a') + "")), i, 0);
        }
    }

    /**
     * Denne metoden legger til vertikal indeksering til GridPanes
     */
    private void createVerticalIndex() {
        int t = 8;
        for (int i = 0; i < BRETTSTORRELSE; i++) {
            vertikalIndexLeft.add(new StackPane(new Rectangle(20, 70, Color.WHITE), new Label(t + "")), 0, i);
            vertikalIndexRight.add(new StackPane(new Rectangle(20, 70, Color.WHITE), new Label(t + "")), 0, i);
            t--;
        }
    }

    /**
     * Denne metoden tegner alle utførte trekk til brettet
     */
    private void tegnEndring(String fra, String til) {

        int fr = fra.charAt(1) - '1';
        fr = 7 - fr;
        int fk = fra.charAt(0) - 'a';
        int tr = til.charAt(1) - '1';
        tr = 7 - tr;
        int tk = til.charAt(0) - 'a';

        ImageView ivToken = new ImageView(brett.getBrikke(til).getToken());

        /*
        Text txt = new Text(brett.getBrikke(til).brikkenavn());
        txt.setFont(new Font("verdana", 30));
        txt.setFill(brett.getBrikke(til).getFarge());
        txt.setStroke(Color.BLACK);
         */
        panes[fr][fk].getChildren().clear();
        panes[fr][fk].getChildren().addAll(rektangler[fr][fk]);
        panes[tr][tk].getChildren().clear();
        panes[tr][tk].getChildren().addAll(rektangler[tr][tk], ivToken);
    }

    /**
     * Denne metoden legger til stiler i programmets gridPanes
     */
    private void stilerGrids() {
        rootBrett.setAlignment(Pos.CENTER);
        rootBrett.setStyle(
                "-fx-background-color: black;"
                + "-fx-hgap: 1;"
                + "-fx-vgap: 1;");
        horisontalIndexTop.setStyle(
                "-fx-background-color: #B49B81;"
                + "-fx-hgap: 1;"
                + "-fx-vgap: 1;");
        horisontalIndexBot.setStyle(
                "-fx-background-color: #B49B81;"
                + "-fx-hgap: 1;"
                + "-fx-vgap: 1;");
        vertikalIndexLeft.setStyle(
                "-fx-background-color: #B49B81;"
                + "-fx-hgap: 1;"
                + "-fx-vgap: 1;");
        vertikalIndexRight.setStyle(
                "-fx-background-color: #B49B81;"
                + "-fx-hgap: 1;"
                + "-fx-vgap: 1;");
    }

    /**
     * Denne metode plasserer noder i rot-panelet
     */
    private void plasserIRoot() {
        // Noder i root-panel
        root.setTop(topBorder);
        root.setCenter(rootBrett);
        root.setBottom(bottomBorder);
        root.setLeft(vertikalIndexLeft);
        root.setRight(vertikalIndexRight);
    }

    /**
     * Denne metoden stilerer HBoxer
     */
    private void stilerHBoxer() {
        // Top HBox
        topHBox = new HBox(txtStatus);
        topHBox.setMinHeight(25);
        topHBox.setStyle(
                "-fx-border-width: 0px 0px 1px 0px;"
                + "-fx-border-color: black;"
                + "-fx-background-color: #DDD5CD;"
                + "-fx-font-family: verdana;"
                + "-fx-font-weight: bold;");

        // Bot HBox
        botHBox = new HBox(20, lblFra, txtFra, lblTil, txtTil, btnFlytt, btnNyttSpill);
        botHBox.setAlignment(Pos.CENTER);
        botHBox.setMinHeight(40);
        botHBox.setStyle(
                "-fx-border-width: 1px 0px 0px 0px;"
                + "-fx-border-color: black;"
                + "-fx-background-color: #DDD5CD;"
                + "-fx-font-family: verdana;"
                + "-fx-font-weight: bold;");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
