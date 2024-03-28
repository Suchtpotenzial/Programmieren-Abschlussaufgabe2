package edu.kit.document;

/**
 * This enum represents the different types of documents.
 *
 * @author uqfdp
 */
public enum DocumentType {
    /**
     * Represents an image.
     */
    IMAGE,

    /**
     * Represents an audio file.
     */
    AUDIO,

    /**
     * Represents a video file.
     */
    VIDEO,

    /**
     * Represents a text document.
     */
    TEXT,

    /**
     * Represents a program file.
     */
    PROGRAM;

    /**
     * Returns the DocumentType that corresponds to the given string.
     *
     * @param type the string to convert
     * @return the DocumentType that corresponds to the given string
     */
    public static DocumentType fromString(String type) {
        for (DocumentType documentType : DocumentType.values()) {
            if (documentType.name().equalsIgnoreCase(type)) {
                return documentType;
            }
        }
        return null;
    }
}
