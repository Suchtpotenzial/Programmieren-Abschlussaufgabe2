package edu.kit.document.implementations;

import edu.kit.document.Document;
import edu.kit.document.DocumentType;
import edu.kit.document.Tag;
import edu.kit.util.VideoLength;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a video document.
 *
 * @author uqfdp
 **/
public class VideoDocument extends Document {
    private static final String GENERIC_LENGTH_TAG_IDENTIFIER = "length";
    private static final String VIDEO_LENGTH_TAG_IDENTIFIER = "videolength";
    private static final String GENERIC_GENRE_TAG_IDENTIFIER = "genre";
    private static final String VIDEO_GENRE_TAG_IDENTIFIER = "videogenre";

    /**
     * Constructs a new VideoDocument with the given path, tags and number of uses.
     *
     * @param path the path of the video document
     * @param tags the tags of the video document
     * @param uses the number of uses of the video document
     */
    public VideoDocument(String path, Set<Tag> tags, int uses) {
        super(DocumentType.VIDEO, path, uses);
        setTags(createVideoTags(tags));
    }


    /**
     * Creates the tags for the video document.
     *
     * @param tags the tags of the video document
     * @return the tags for the video document
     */
    private Set<Tag> createVideoTags(Set<Tag> tags) {
        Set<Tag> videoTags = new HashSet<>();
        for (Tag tag : tags) {
            switch (tag.getIdentifier()) {
                case GENERIC_GENRE_TAG_IDENTIFIER:
                    videoTags.add(new Tag(VIDEO_GENRE_TAG_IDENTIFIER, tag.getValue()));
                    break;
                case GENERIC_LENGTH_TAG_IDENTIFIER:
                    try {
                        int length = Integer.parseInt(tag.getValue());
                        VideoLength videoLength = getVideoLengthFromLengthTag(length);
                        videoTags.add(new Tag(VIDEO_LENGTH_TAG_IDENTIFIER, videoLength.name().toLowerCase()));
                    } catch (NumberFormatException e) {
                        videoTags.add(new Tag(tag.getIdentifier(), tag.getValue()));
                    }
                    break;
                default:
                    videoTags.add(new Tag(tag.getIdentifier(), tag.getValue()));
            }
        }

        return videoTags;
    }

    /**
     * Returns the video length based on the given length.
     *
     * @param length the length of the video
     * @return the video length based on the given length
     */
    private VideoLength getVideoLengthFromLengthTag(int length) {
        if (length < VideoLength.CLIP.getMaxLength()) {
            return VideoLength.CLIP;
        } else if (length < VideoLength.SHORT.getMaxLength()) {
            return VideoLength.SHORT;
        } else if (length < VideoLength.MOVIE.getMaxLength()) {
            return VideoLength.MOVIE;
        } else {
            return VideoLength.LONG;
        }
    }
}
