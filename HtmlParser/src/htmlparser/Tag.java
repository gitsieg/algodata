package htmlparser;

/**
 * Abstrakt klasse for å representere html tagger
 * @author Atle Amundsen, Kristian Robertsen, Nikolai Fosså
 */
public abstract class Tag implements Comparable<Tag> {

    protected static int nextID = 0;
    protected int id;
    protected String content;

    public Tag(String content) {
        id = nextID++;          // Bruker for å ta vare på rekkefølgen av tagger i et html dokument
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public int getID() {
        return id;
    }

    @Override
    public int compareTo(Tag o) {
        return this.id - o.getID();
    }

    @Override
    public abstract String toString();

    public abstract void setLinked();
    public abstract boolean isLinked();
}
