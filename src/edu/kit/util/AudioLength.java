package edu.kit.util;

/**
 * This enum represents the different lengths of audio files.
 *
 * @author uqfdp
 */
public enum AudioLength {

    /**
     * Represents a short audio sample.
     */
    SAMPLE(10),

    /**
     * Represents a short audio file.
     */
    SHORT(60),

    /**
     * Represents a normal audio file.
     */
    NORMAL(300),

    /**
     * Represents a long audio file.
     */
    LONG(Integer.MAX_VALUE);

    private final int maxLength;

    /**
     * Constructs a new AudioLength with the given maximum length.
     *
     * @param maxLength the maximum length of the audio file
     */
    AudioLength(int maxLength) {
        this.maxLength = maxLength;
    }

    /**
     * Returns the maximum length of the audio file.
     *
     * @return the maximum length of the audio file
     */
    public int getMaxLength() {
        return maxLength;
    }
}
