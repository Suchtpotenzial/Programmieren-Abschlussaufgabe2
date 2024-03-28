package edu.kit.document.implementations;

import edu.kit.document.Document;
import edu.kit.document.DocumentType;
import edu.kit.document.Tag;
import edu.kit.util.ImageSize;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents an image document.
 *
 * @author uqfdp
 */
public class ImageDocument extends Document {
    private static final String GENERIC_SIZE_TAG_IDENTIFIER = "size";
    private static final String IMG_SIZE_TAG_IDENTIFIER = "imagesize";

    /**
     * Constructs a new ImageDocument with the given path, tags and number of uses.
     *
     * @param path the path of the image document
     * @param tags the tags of the image document
     * @param uses the number of uses of the image document
     */
    public ImageDocument(String path, Set<Tag> tags, int uses) {
        super(DocumentType.IMAGE, path, uses);
        super.setTags(createImageTags(tags));
    }

    /**
     * Creates the tags for the image document.
     *
     * @param tags the tags of the image document
     * @return the tags for the image document
     */
    private Set<Tag> createImageTags(Set<Tag> tags) {
        Set<Tag> imageTags = new HashSet<>();
        for (Tag tag : tags) {
            if (tag.getIdentifier().equals(GENERIC_SIZE_TAG_IDENTIFIER)) {
                try {
                    int size = Integer.parseInt(tag.getValue());
                    ImageSize imageSize = getImageSizeFromSizeTag(size);
                    imageTags.add(new Tag(IMG_SIZE_TAG_IDENTIFIER, imageSize.name().toLowerCase()));
                } catch (NumberFormatException e) {
                    imageTags.add(new Tag(tag.getIdentifier(), tag.getValue()));
                }
                continue;
            }

            imageTags.add(new Tag(tag.getIdentifier(), tag.getValue()));
        }

        return imageTags;
    }

    /**
     * Returns the image size from the given size tag.
     *
     * @param size the size tag
     * @return the image size from the given size tag
     */
    private ImageSize getImageSizeFromSizeTag(int size) {
        if (size < ImageSize.ICON.getMaxSize()) {
            return ImageSize.ICON;
        } else if (size < ImageSize.SMALL.getMaxSize()) {
            return ImageSize.SMALL;
        } else if (size < ImageSize.MEDIUM.getMaxSize()) {
            return ImageSize.MEDIUM;
        } else {
            return ImageSize.LARGE;
        }
    }
}
