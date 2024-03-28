package edu.kit.document;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The Document class represents a document with a type, a path, tags and a number of uses.
 *
 * @author uqfdp
 */
public abstract class Document {
    private final DocumentType type;
    private final String path;
    private Set<Tag> tags;
    private int uses;

    /**
     * Constructs a new Document with the given type, path and number of uses.
     *
     * @param type the type of the document
     * @param path the path of the document
     * @param uses the number of uses of the document
     */
    protected Document(DocumentType type, String path, int uses) {
        this.type = type;
        this.path = path;
        this.tags = new LinkedHashSet<>();
        this.uses = uses;
    }

    /**
     * Sets the tags of the document.
     *
     * @param tags the tags of the document
     */
    protected void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    /**
     * Returns the type of the document.
     *
     * @return the type of the document
     */
    public Set<Tag> getTags() {
        return tags;
    }

    /**
     * Returns the path of the document.
     *
     * @return the path of the document
     */
    public String getPath() {
        return path;
    }

    /**
     * Returns the number of uses of the document.
     *
     * @return the number of uses of the document
     */
    public int getUses() {
        return uses;
    }

    /**
     * Sets the number of uses of the document.
     *
     * @param uses the number of uses of the document
     */
    public void setUses(int uses) {
        this.uses = uses;
    }
}
