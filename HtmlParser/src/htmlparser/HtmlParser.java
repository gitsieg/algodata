package htmlparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * Program for å gjennomløpe en html fil og skille ut kommentarer, tagger og
 * tekstlig innhold. Programmet utfører også sjekk på parantesbalanse mellom
 * tagger. Svakheter er: - Attributter i html tagger fører til at balansesjekk
 * feiler, og blir rapportert som en feil til brukeren - Tolker ikke
 * spesialkarakterer i html
 *
 * Tilstandene som programmet kan være i, og så den utfører handlinger på,
 * ligger i slutten av fila
 *
 * @author Atle Amundsen, Kristian Robertsen, Nikolai Fosså
 */


/**
 * Tilstandene programmet kan være i. Utnyttes i tilstandssjekker med switch
 *
 * @author Nikolai
 */
enum State {
    CODE, // Basestate
    LT, // <
    LTEXC, // <!
    LTEXCDASH, // <!-
    COMMENT, // <!-- 
    COMMENTDASH, // <!-- *** -
    COMMENTDASHDASH, // <!-- *** --
    ELEMENTDECL, // <*
    ELEMENTCLOSE, // </
    AMPERSAND;          // &
}

public class HtmlParser extends Application {

    // GUI, der brukeren kan foreta seg noe.
    private GUI ui = new GUI();

    // Kapasistet til stringbuilderen. Denne må settes basert på hvor store de forskjellige elementene i html filen er. (innhold, kommentarer, tagger)
    private final int STRINGBUILDER_CAPACITY = 100;
    private int errors = 0;

    // Hashset som inneholder de void tags (tagger som ikke trenger en slutt tag)
    private Set<String> voidSet = new HashSet<>(Arrays.asList("area", "base", "br", "col", "command", "embed", "hr", "img", "input", "keygen", "link", "meta", "param", "source", "track", "wbr"));

    // Datastrukturer for å mellomlagre meningsfullt innhold i html filen
    private ArrayList<String> contentList = new ArrayList<>();
    private ArrayList<String> commentList = new ArrayList<>();
    private ArrayList<Tag> tagList = new ArrayList<>();

    // Stack som brukes for å sjekke tag balansen i en html fil.
    private Stack<OpeningTag> openingStack = new Stack();

    // Representerer tilstandene programmet er i. Endres flere ganger ila programgkjøring
    private State state = State.CODE;

    // Variabler for å prosessere en hmtl fil.
    private StringBuilder variableString = new StringBuilder(STRINGBUILDER_CAPACITY);
    private BufferedReader bfreader = null;
    private FileChooser fileChooser;
    private File file = null;
    private char input = ' ';

    @Override
    public void start(Stage primaryStage) throws IOException {
        Scene scene = new Scene(ui, 800, 600);

        primaryStage.setTitle("Parser");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        ui.urlView.btnReadFile.setOnAction(e -> {
            fileChooser = new FileChooser();
            fileChooser.setTitle("Open .html file");
            fileChooser.getExtensionFilters().add(new ExtensionFilter("Html files", "*.html"));
            file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                try {
                    processInput();
                } catch (Exception ex) {
                    System.out.println("Oida");
                }
            }
            sortAndPrint();
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Handles what should happen in state CODE, which is the base state
     *
     * @param input
     */
    private void stateCODE() {
        switch (input) {
            case '<':
                createContent();
                state = State.LT;
                break;
            case '&':
                state = State.AMPERSAND;
                // Do something else
                break;
            default:
                state = State.CODE;
                break;
        }
    }

    /**
     * Handles what should happen in state element declaration. The method
     * checks the input and changes states based on the input.
     *
     * @param input
     */
    private void stateELEMENTDECL() {
        switch (input) {
            case '>':
                createElement();
                // createOpeningTag();
                state = State.CODE;
                break;
            case '<':
                state = State.LT;
                break;
            default:
                state = State.ELEMENTDECL;
                break;
        }
    }

    /**
     * Handles what should happen in state element close.
     *
     * @param input
     */
    private void stateELEMENTCLOSE() {

        // Needs more work.
        switch (input) {
            case '>':
                closeElement();
                // createClosingTag();
                state = State.CODE;
                break;
            case '<':
                state = State.LT;
                break;
            default:
                state = State.ELEMENTCLOSE;
                break;
        }
    }

    /**
     * &
     * Handles what should happen in state ampersand
     *
     * @param input
     */
    private void stateAMPERSAND() {
        if (input == ';') {
            state = State.CODE;
        } else {
            state = State.AMPERSAND;
        }
    }

    /**
     * Handles what should happen in lt.
     *
     * @param input
     */
    private void stateLT() {
        switch (input) {
            case '!':
                state = State.LTEXC;
                // Do this
                break;
            case '/':
                state = State.ELEMENTCLOSE;
                // Do something else
                break;
            default:
                state = State.ELEMENTDECL;
                // Do something different
                break;
        }
    }

    /**
     * Handles what should happen in state ltexc.
     *
     * @param input
     */
    private void stateLTEXC() {
        if (input == '-') {
            state = State.LTEXCDASH;
        } else {
            state = State.CODE;
        }
    }

    /**
     * Handles what should happen in state ltexcdash.
     *
     * @param input
     */
    private void stateLTEXCDASH() {
        if (input == '-') {
            state = State.COMMENT;
        } else {
            state = State.CODE;
        }
    }

    /**
     * Handles what should happen in state comment.
     *
     * @param input
     */
    private void stateCOMMENT() {
        if (input == '-') {
            state = State.COMMENTDASH;
        } else {
            state = State.COMMENT;
        }
    }

    /**
     * Handles what should happen in commentdash
     *
     * @param input
     */
    private void stateCOMMENTDASH() {
        if (input == '-') {
            state = State.COMMENTDASHDASH;
        } else {
            state = State.COMMENT;
        }
    }

    /**
     * Handles what should happen in state commentdashdash
     *
     * @param input
     */
    private void stateCOMMENTDASHDASH() {
        if (input == '>') {
            String comment = variableString.toString().substring(4, variableString.length() - 3);
            commentList.add(comment);
            state = State.CODE;
        } else {
            state = State.COMMENT;
        }
    }

    /**
     * Metoden setter i gang operasjoner basert på hvilken tilstand programmet
     * er i.
     *
     * @param input
     */
    private void checkState() {
        switch (state) {
            case CODE:
                stateCODE();
                break;
            case LT: ;
                stateLT();
                break;
            case LTEXC:
                stateLTEXC();
                break;
            case LTEXCDASH:
                stateLTEXCDASH();
                break;
            case COMMENT:
                stateCOMMENT();
                break;
            case COMMENTDASH:
                stateCOMMENTDASH();
                break;
            case COMMENTDASHDASH:
                stateCOMMENTDASHDASH();
                break;
            case AMPERSAND:
                stateAMPERSAND();
                break;
            case ELEMENTDECL:
                stateELEMENTDECL();
                break;
            case ELEMENTCLOSE:
                stateELEMENTCLOSE();
                break;
        }
    }

    /**
     * Metoden oppretter en ny åpningstagg og legger den på stakken dersom den
     * krever en lukketag, legges rett i tagList dersom den ikke gjør det. Dette
     * bestemmes av å sjekke om et hashset inneholder den taggen.
     */
    private void createElement() {
        String type = variableString.toString().substring(1, variableString.length() - 1);
        if (voidSet.contains(type)) {                   // Check if tag is a legal voidtag
            VoidTag vtag = new VoidTag(type);           // Create a new tag
            tagList.add(vtag);                          // Add to tagList
        } else {
            OpeningTag otag = new OpeningTag(type);     // Opening tag
            openingStack.push(otag);                    // push to stack
        }
        variableString = new StringBuilder(STRINGBUILDER_CAPACITY);
    }

    /**
     * Metoden henter ut en lukketag fra en String og oppretter et tag-objekt.
     * Det vil utføres forskjellige operasjoner basert på: - Detektert lukke tag
     * - Tag som ligger på toppen av stack. - Om det er EOF - Om det er innhold
     * i stacken
     */
    private void closeElement() {
        String type = variableString.toString().substring(2, variableString.length() - 1);
        ClosingTag ctag = new ClosingTag(type);
        try {
            if (!bfreader.ready() && !openingStack.empty()) {
                while (!openingStack.empty()) {
                    if (openingStack.peek().getContent().equals(ctag.getContent())) {
                        openingStack.peek().setLinked();
                        ctag.setLinked();
                    } else {
                        errors++;
                    }
                    tagList.add(openingStack.pop());
                }
                tagList.add(ctag);
            }
        } catch (IOException e) {
            System.out.println("Something happened");
        }
        if (!openingStack.empty()) {
            if (openingStack.peek().getContent().equals(ctag.getContent())) {
                openingStack.peek().setLinked();
                ctag.setLinked();
                tagList.add(openingStack.pop());
                tagList.add(ctag);
            } else if (!openingStack.peek().equals(ctag.getContent())) {
                tagList.add(ctag);
                errors++;
            }
        }
        variableString = new StringBuilder(STRINGBUILDER_CAPACITY);
    }

    /**
     * Metoden oppretter et nytt String-objekt som representerer tekstlig
     * innhold i dokumentet og legger den til en ArrayList.
     */
    private void createContent() {
        if (variableString.length() > 1) {
            contentList.add(variableString.toString());
            variableString = new StringBuilder(STRINGBUILDER_CAPACITY);
            variableString.append('<');
        }
    }

    /**
     * Oppretter en BufferedReader på gitt html fil, og henter ut en og en char
     * som sendes videre til behandling av tilstandsmaskinen.
     *
     * @throws Exception
     */
    private void processInput() throws Exception {
        bfreader = new BufferedReader(new FileReader(file));
        while (bfreader.ready()) {
            input = (char) bfreader.read();
            if (input != '\n') {
                variableString.append(input);
            }
            checkState();
        }
        bfreader.close();
    }

    /**
     * Sorterer innholdet i tagList, slik at utskrift skjer i den rekkefølgen
     * taggene ble oppdaga i html dokumentet Legger tagger, kommentarer og
     * innhold i 3 forskjellige tekstområder i GUI
     */
    private void sortAndPrint() {
        Collections.sort(tagList);
        ui.urlView.lblErrors.setText("Errors: " + errors);

        // Writing results to gui.
        tagList.stream().forEach((t) -> {
            Text txt = new Text(t.toString() + "\n");
            if (t.isLinked()) {
                txt.setFill(Color.GREEN);
            } else {
                txt.setFill(Color.RED);
            }
            ui.tagView.tflowTags.getChildren().add(txt);

        });
        contentList.stream().map((s) -> s.replace("  ", "")).filter((s) -> !(s.charAt(0) == '<')).map((s) -> s.substring(0, s.length() - 1)).forEach((s) -> {
            ui.contView.taContent.appendText(s + "\n");
        });

        commentList.stream().forEach((s) -> {
            ui.commentView.taComments.appendText(s + "\n");
        });
    }
}
