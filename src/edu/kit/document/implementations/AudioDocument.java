package edu.kit.document.implementations;

import edu.kit.document.Document;
import edu.kit.document.DocumentType;
import edu.kit.document.Tag;
import edu.kit.util.AudioLength;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents an audio document.
 *
 * @author uqfdp
 */
public class AudioDocument extends Document {
    private static final String GENERIC_LENGTH_TAG_IDENTIFIER = "length";
    private static final String AUDIO_LENGTH_TAG_IDENTIFIER = "audiolength";
    private static final String GENERIC_GENRE_TAG_IDENTIFIER = "genre";
    private static final String AUDIO_GENRE_TAG_IDENTIFIER = "audiogenre";

    /**
     * Constructs a new AudioDocument with the given path, tags and number of uses.
     *
     * @param path the path of the audio document
     * @param tags the tags of the audio document
     * @param uses the number of uses of the audio document
     */
    public AudioDocument(String path, Set<Tag> tags, int uses) {
        super(DocumentType.AUDIO, path, uses);
        super.setTags(createAudioTags(tags));
    }

    /**
     * Creates the tags for the audio document.
     *
     * @param tags the tags of the audio document
     * @return the tags for the audio document
     */
    private Set<Tag> createAudioTags(Set<Tag> tags) {
        Set<Tag> audioTags = new HashSet<>();
        for (Tag tag : tags) {
            switch (tag.getIdentifier()) {
                case GENERIC_GENRE_TAG_IDENTIFIER:
                    audioTags.add(new Tag(AUDIO_GENRE_TAG_IDENTIFIER, tag.getValue()));
                    break;
                case GENERIC_LENGTH_TAG_IDENTIFIER:
                    try {
                        int size = Integer.parseInt(tag.getValue());
                        AudioLength audioLength = getAudioLengthFromLengthTag(size);
                        audioTags.add(new Tag(AUDIO_LENGTH_TAG_IDENTIFIER, audioLength.name().toLowerCase()));
                    } catch (NumberFormatException e) {
                        audioTags.add(new Tag(tag.getIdentifier(), tag.getValue()));
                    }
                    break;
                default:
                    audioTags.add(new Tag(tag.getIdentifier(), tag.getValue()));
            }
        }

        return audioTags;
    }

    /**
     * Returns the audio length from the given length.
     *
     * @param length the length tag
     * @return the audio length from the given length
     */
    private AudioLength getAudioLengthFromLengthTag(int length) {
        if (length < AudioLength.SAMPLE.getMaxLength()) {
            return AudioLength.SAMPLE;
        } else if (length < AudioLength.SHORT.getMaxLength()) {
            return AudioLength.SHORT;
        } else if (length < AudioLength.NORMAL.getMaxLength()) {
            return AudioLength.NORMAL;
        } else {
            return AudioLength.LONG;
        }
    }
}
