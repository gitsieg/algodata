package htmlparser;

/**
 *
 * @author Atle Amundsen, Kristian Robertsen, Nikolai Fosså
 */
public class OpeningTag extends Tag {

    private boolean linked = false;

    public OpeningTag(String content) {
        super(content);
    }

    @Override
    public String toString() {
        return "<" + content + ">";
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
