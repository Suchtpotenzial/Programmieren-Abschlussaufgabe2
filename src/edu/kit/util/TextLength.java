package edu.kit.util;

/**
 * This enum represents the different lengths of text.
 *
 * @author uqfdp
 */
public enum TextLength {

    /**
     * Represents a short text.
     */
    SHORT(100),

    /**
     * Represents a medium text.
     */
    MEDIUM(1000),

    /**
     * Represents a long text.
     */
    LONG(Integer.MAX_VALUE);

    private final int maxWords;

    /**
     * Constructs a new TextLength with the given maximum length.
     *
     * @param maxWords the maximum length of the text
     */
    TextLength(int maxWords) {
        this.maxWords = maxWords;
    }

    /**
     * Returns the maximum length of the text.
     *
     * @return the maximum length of the text
     */
    public int getMaxWords() {
        return maxWords;
    }
}
