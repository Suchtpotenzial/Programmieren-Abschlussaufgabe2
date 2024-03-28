package edu.kit.document.implementations;

import edu.kit.document.Document;
import edu.kit.document.DocumentType;
import edu.kit.document.Tag;
import edu.kit.util.TextLength;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a text document.
 *
 * @author uqfdp
 */
public class TextDocument extends Document {
    private static final String GENERIC_WORDS_TAG_IDENTIFIER = "words";
    private static final String TEXT_LENGTH_TAG_IDENTIFIER = "textlength";
    private static final String GENERIC_GENRE_TAG_IDENTIFIER = "genre";
    private static final String TEXT_GENRE_TAG_IDENTIFIER = "textgenre";

    /**
     * Constructs a new TextDocument with the given path, tags and number of uses.
     *
     * @param path the path of the text document
     * @param tags the tags of the text document
     * @param uses the number of uses of the text document
     */
    public TextDocument(String path, Set<Tag> tags, int uses) {
        super(DocumentType.TEXT, path, uses);
        super.setTags(createTextTags(tags));
    }

    /**
     * Creates the tags for the text document.
     *
     * @param tags the tags of the text document
     * @return the tags for the text document
     */
    private Set<Tag> createTextTags(Set<Tag> tags) {
        Set<Tag> textTags = new HashSet<>();
        for (Tag tag : tags) {
            switch (tag.getIdentifier()) {
                case GENERIC_GENRE_TAG_IDENTIFIER:
                    textTags.add(new Tag(TEXT_GENRE_TAG_IDENTIFIER, tag.getValue()));
                    break;
                case GENERIC_WORDS_TAG_IDENTIFIER:
                    try {
                        int words = Integer.parseInt(tag.getValue());
                        TextLength videoLength = getTextLengthFromWordsTag(words);
                        textTags.add(new Tag(TEXT_LENGTH_TAG_IDENTIFIER, videoLength.name().toLowerCase()));
                    } catch (NumberFormatException e) {
                        textTags.add(new Tag(tag.getIdentifier(), tag.getValue()));
                    }
                    break;
                default:
                    textTags.add(new Tag(tag.getIdentifier(), tag.getValue()));
            }
        }

        return textTags;
    }

    /**
     * Returns the text length from the given words.
     *
     * @param words the words
     * @return the text length from the given words
     */
    private TextLength getTextLengthFromWordsTag(int words) {
        if (words < TextLength.SHORT.getMaxWords()) {
            return TextLength.SHORT;
        } else if (words < TextLength.MEDIUM.getMaxWords()) {
            return TextLength.MEDIUM;
        } else {
            return TextLength.LONG;
        }
    }

}
