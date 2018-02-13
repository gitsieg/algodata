package htmlparser;

/**
 *
 * @author Atle Amundsen, Kristian Robertsen, Nikolai Fosså
 */
public class ClosingTag extends Tag {

    private boolean linked = false;

    public ClosingTag(String content) {
        super(content);
    }

    @Override
    public String toString() {
        return "</" + content + ">";
    }

    @Override
    public void setLinked() {
        linked = true;
    }

    @Override
    public boolean isLinked() {
        return linked;
    }
}
