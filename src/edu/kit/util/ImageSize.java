package edu.kit.util;

/**
 * This enum represents the different sizes of images.
 *
 * @author uqfdp
 */
public enum ImageSize {

    /**
     * Represents a small icon.
     */
    ICON(10000),

    /**
     * Represents a small image.
     */
    SMALL(40000),

    /**
     * Represents a medium image.
     */
    MEDIUM(800000),

    /**
     * Represents a large image.
     */
    LARGE(Integer.MAX_VALUE);
    private final int maxSize;

    /**
     * Constructs a new ImageSize with the given maximum size.
     *
     * @param maxSize the maximum size of the image
     */
    ImageSize(int maxSize) {
        this.maxSize = maxSize;
    }

    /**
     * Returns the maximum size of the image.
     *
     * @return the maximum size of the image
     */
    public int getMaxSize() {
        return maxSize;
    }
}
