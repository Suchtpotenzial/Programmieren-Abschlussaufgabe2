package edu.kit.document.implementations;

import edu.kit.document.Document;
import edu.kit.document.DocumentType;
import edu.kit.document.Tag;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a program document.
 *
 * @author uqfdp
 */
public class ProgramDocument extends Document {
    private static final String EXECUTABLE_TAG_IDENTIFIER = "executable";

    /**
     * Constructs a new ProgramDocument with the given path, tags and number of uses.
     *
     * @param path the path of the program document
     * @param tags the tags of the program document
     * @param uses the number of uses of the program document
     */
    public ProgramDocument(String path, Set<Tag> tags, int uses) {
        super(DocumentType.PROGRAM, path, uses);
        setTags(createProgramTags(tags));
    }

    /**
     * Creates the tags for the program document.
     *
     * @param tags the tags of the program document
     * @return the tags for the program document
     */
    private Set<Tag> createProgramTags(Set<Tag> tags) {
        Set<Tag> videoTags = new HashSet<>(tags);
        videoTags.add(new Tag(EXECUTABLE_TAG_IDENTIFIER));
        return videoTags;
    }
}
