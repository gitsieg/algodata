package htmlparser;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

/**
 *
 * @author Atle Amundsen, Kristian Robertsen, Nikolai Fosså
 */
public class GUI extends BorderPane {

    protected URLView urlView = new URLView(); // Input
    protected ContentView contView = new ContentView();
    protected TagView tagView = new TagView();
    protected CommentView commentView = new CommentView();
    protected BorderPane outputSection = new BorderPane(); // Grouping output views

    public GUI() {
        this.setTop(urlView);
        outputSection.setTop(contView);
        outputSection.setCenter(tagView);
        outputSection.setBottom(commentView);
        this.setCenter(outputSection);
    }

}

// View for the contents of the html file. 
class ContentView extends VBox {

    protected Label lblContent;
    protected TextArea taContent;

    public ContentView() {
        lblContent = new Label("Content:");
        taContent = new TextArea();
        this.getChildren().addAll(lblContent, taContent);
    }
}

// View for the tags in the html file.
class TagView extends VBox {

    protected Label lblTags;
    protected TextFlow tflowTags;
    protected ScrollPane scp;

    public TagView() {
        lblTags = new Label("Tags:");
        tflowTags = new TextFlow();
        scp = new ScrollPane(tflowTags);

        this.getChildren().addAll(lblTags, scp);

    }
}

// View for the comments in the html file.
class CommentView extends VBox {

    protected Label lblComments;
    protected TextArea taComments;

    public CommentView() {
        lblComments = new Label("Comments:");
        taComments = new TextArea();
        this.getChildren().addAll(lblComments, taComments);
    }
}

// View for URL and errors
class URLView extends HBox {

    protected Button btnReadFile;
    protected Label lblErrors;

    public URLView() {
        btnReadFile = new Button("Choose html file to check");
        lblErrors = new Label("Errors: ");
        this.getChildren().addAll(btnReadFile, lblErrors);
    }
}
