package edu.kit.document;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * The Tag class represents a tag with an identifier and a value.
 *
 * @author uqfdp
 */
public class Tag {

    /**
     * The representation of an undefined value.
     */
    public static final String UNDEFINED_VALUE_REPRESENTATION = "undefined";
    protected static final String TAG_REPRESENTATION_FORMAT = "%s=%s";
    protected static final String DEFINED_VALUE_REPRESENTATION = "defined";
    private final String identifier;
    private final String value;

    /**
     * Constructs a new Tag with the given identifier and value.
     *
     * @param identifier the identifier of the tag
     * @param value the value of the tag
     */
    public Tag(String identifier, String value) {
        this.identifier = identifier;
        this.value = value.isEmpty() ? UNDEFINED_VALUE_REPRESENTATION : value;
    }

    /**
     * Constructs a new Tag with the given identifier and the default value.
     *
     * @param identifier the identifier of the tag
     */
    public Tag(String identifier) {
        this(identifier, DEFINED_VALUE_REPRESENTATION);
    }

    /**
     * Returns the identifier of the tag.
     *
     * @return the identifier of the tag
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Returns the value of the tag.
     *
     * @return the value of the tag
     */
    public String getValue() {
        return value;
    }

    /**
     * This method takes a raw tag array and returns a set of tags.
     *
     * @param rawTags the raw tag array
     * @return the set of tags
     */
    public static Set<Tag> getTagsFromArray(String[] rawTags) {
        HashMap<String, String> tagMap = new HashMap<>();
        for (String rawTag : rawTags) {
            String[] tag = rawTag.split("=");
            if (tag.length == 2) {
                tagMap.put(tag[0].toLowerCase(), tag[1]);
                continue;
            }

            if (tagMap.containsKey(tag[0].toLowerCase())) {
                return null;
            }
            tagMap.put(tag[0].toLowerCase(), DEFINED_VALUE_REPRESENTATION);
        }
        return tagMap.entrySet().stream()
                .map(entry -> new Tag(entry.getKey(), entry.getValue()))
                .collect(Collectors.toSet());
    }

    /**
     * This method returns a set of possible tag values for a given identifier.
     *
     * @param documents the documents to get the possible tag values from
     * @param identifier the identifier to get the possible tag values from
     * @return the possible tag values
     */
    public static Set<String> getPossibleTagValues(Set<Document> documents, String identifier) {
        Set<String> possibleValues = new LinkedHashSet<>();
        for (Document document : documents) {
            for (Tag tag : document.getTags()) {
                if (tag.getIdentifier().equals(identifier)) {
                    possibleValues.add(tag.getValue());
                    continue;
                }
                possibleValues.add(UNDEFINED_VALUE_REPRESENTATION);
            }
        }
        return possibleValues;
    }

    /**
     * This method compares this tag to another object.
     * @param obj the object to compare to
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj.getClass() == getClass())) {
            return false;
        }
        return hashCode() == obj.hashCode();
    }

    /**
     * This method returns the hash code of the tag.
     * @return the hash code of the tag
     */
    @Override
    public int hashCode() {
        return identifier.hashCode() + value.hashCode();
    }

    /**
     * This method returns a string representation of the tag.
     * @return a string representation of the tag
     */
    @Override
    public String toString() {
        return TAG_REPRESENTATION_FORMAT.formatted(identifier, value);
    }
}
