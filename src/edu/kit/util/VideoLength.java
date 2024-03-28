package edu.kit.util;

/**
 * This enum represents the different lengths of videos.
 *
 * @author uqfdp
 */
public enum VideoLength {

    /**
     * Represents a video clip.
     */
    CLIP(300),

    /**
     * Represents a short video.
     */
    SHORT(3600),

    /**
     * Represents a movie.
     */
    MOVIE(7200),

    /**
     * Represents a long movie.
     */
    LONG(Integer.MAX_VALUE);

    private final int maxLength;

    /**
     * Constructs a new VideoLength with the given maximum length.
     *
     * @param maxLength the maximum length of the video
     */
    VideoLength(int maxLength) {
        this.maxLength = maxLength;
    }

    /**
     * Returns the maximum length of the video.
     *
     * @return the maximum length of the video
     */
    public int getMaxLength() {
        return maxLength;
    }
}
