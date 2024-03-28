package edu.kit.document;

import edu.kit.DocumentHandler;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Locale;
import java.util.LinkedHashSet;


/**
 * The StructuralTree class represents a structural tree.
 * It provides methods to build a tree and to get the initial documents, tags, tag path and children.
 *
 * @author uqfdp
 */
public class StructuralTree {
    private static final double MINIMUM_INFORMATION_GAIN = 0.001;
    private static final String PATH_FORMAT = "%s/%s%n";
    private static final String TAG_REPRESENTATION_FORMAT = "%s=%.2f";
    private static final String TREE_FORMAT = "%s/\"%s\"%n";
    private final Set<Document> initialDocuments;
    private final DocumentHandler documentHandler;
    private final List<StructuralTree> children;
    private final String tagPath;
    private final List<Tag> tags;

    /**
     * Constructs a new StructuralTree with the given document handler and documents.
     *
     * @param documentHandler the document handler
     * @param documents the documents
     */
    public StructuralTree(DocumentHandler documentHandler, Set<Document> documents) {
        this(documentHandler, documents, "", new LinkedList<>());
    }

    /**
     * Constructs a new StructuralTree with the given document handler, documents, tag path and tags.
     *
     * @param documentHandler the document handler
     * @param documents the documents
     * @param tagPath the tag path
     * @param tags the tags
     */
    private StructuralTree(DocumentHandler documentHandler, Set<Document> documents, String tagPath, List<Tag> tags) {
        this.initialDocuments = documents;
        this.documentHandler = documentHandler;
        this.tagPath = tagPath;
        this.tags = tags;
        this.children = new ArrayList<>();
    }

    /**
     * Builds the tree.
     *
     * @return returns a representation tags with the highest information gain
     */
    public String buildTree() {
        StringBuilder tree = new StringBuilder();

        List<String> sortedIdentifier = getSortedIdentifier(initialDocuments);
        List<String> identifierToRemove = new ArrayList<>();
        sortedIdentifier.forEach(identifier -> {
            double informationGain = documentHandler.getInformationGain(initialDocuments, identifier);
            if (informationGain < MINIMUM_INFORMATION_GAIN) {
                identifierToRemove.add(identifier);
            }
        });

        sortedIdentifier.removeAll(identifierToRemove);
        if (sortedIdentifier.isEmpty()) {
            return tree.toString();
        }

        for (String identifier : sortedIdentifier) {
            tree.append(PATH_FORMAT.formatted(
                    tagPath,
                    String.format(
                            Locale.ROOT,
                            TAG_REPRESENTATION_FORMAT,
                            identifier,
                            documentHandler.getInformationGain(initialDocuments, identifier))));
        }

        String bestIdentifier = sortedIdentifier.get(0);
        Set<String> possibleValues = Tag.getPossibleTagValues(initialDocuments, bestIdentifier);
        for (String value : possibleValues) {
            Tag tag = new Tag(bestIdentifier, value);
            Set<Document> documentsWithValue = documentHandler.getDocumentsWithTag(initialDocuments, tag);
            if (documentsWithValue.isEmpty()) {
                continue;
            }
            List<Tag> copyTags = copyTags();
            copyTags.add(tag);
            StructuralTree subTree = addChild(tagPath + "/" + tag, documentsWithValue, copyTags);
            tree.append(subTree.buildTree());
        }

        return tree.toString();
    }

    /**
     * Adds a child to the tree.
     *
     * @param path the path
     * @param documents the documents
     * @param tags the tags
     * @return the child
     */
    private StructuralTree addChild(String path, Set<Document> documents, List<Tag> tags) {
        StructuralTree child = new StructuralTree(documentHandler, documents, path, tags);
        children.add(child);
        return child;
    }

    /**
     * Creates a copy of the tags.
     *
     * @return the copied tags
     */
    private List<Tag> copyTags() {
        List<Tag> copy = new LinkedList<>();
        tags.forEach(tag -> copy.add(new Tag(tag.getIdentifier(), tag.getValue())));
        return copy;
    }

    /**
     * Returns a list of sorted identifiers.
     * @param documents the documents
     * @return the sorted identifiers
     */
    private List<String> getSortedIdentifier(Set<Document> documents) {
        Set<String> identifier = new LinkedHashSet<>();
        documents.forEach(document -> document.getTags().forEach(tag -> identifier.add(tag.getIdentifier())));


        List<String> sortedTagList = new ArrayList<>(identifier);
        sortedTagList.sort((id1, id2) -> {
            double informationGain1 = documentHandler.getInformationGain(documents, id1);
            double informationGain2 = documentHandler.getInformationGain(documents, id2);

            if (informationGain1 == informationGain2) {
                return id1.compareTo(id2);
            }
            return Double.compare(informationGain2, informationGain1);
        });
        return sortedTagList;
    }

    /**
     * Sorts the children.
     */
    private void sortChildren() {
        children.sort((child1, child2) -> {
            double probability1 = documentHandler.getProbabilityOfTag(child1.getTags().get(child1.getTags().size() - 1), initialDocuments);
            double probability2 = documentHandler.getProbabilityOfTag(child2.getTags().get(child2.getTags().size() - 1), initialDocuments);

            if (probability1 == probability2) {
                return child1.getTagPath().compareTo(child2.getTagPath());
            }
            return Double.compare(probability2, probability1);
        });
    }

    /**
     * Returns a string representation of the tree.
     * @return the string representation of the tree
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        sortChildren();
        for (StructuralTree child : children) {
            builder.append(child.toString());
        }
        if (children.isEmpty()) {
            List<Document> sortedDocuments = getSortedDocuments(initialDocuments);
            for (Document document : sortedDocuments) {
                builder.append(TREE_FORMAT.formatted(tagPath, document.getPath()));
            }
        }
        return builder.toString();
    }

    /**
     * Returns a list of sorted documents.
     * @param documents the documents to sort
     * @return the sorted documents
     */
    private List<Document> getSortedDocuments(Set<Document> documents) {
        List<Document> sortedDocuments = new ArrayList<>(documents);
        sortedDocuments.sort((doc1, doc2) -> {
            double probability1 = documentHandler.getProbabilityOfDocument(doc1, documents);
            double probability2 = documentHandler.getProbabilityOfDocument(doc2, documents);

            if (probability1 == probability2) {
                return doc1.getPath().compareTo(doc2.getPath());
            }
            return Double.compare(probability2, probability1);
        });
        return sortedDocuments;
    }

    /**
     * Returns the tags.
     * @return the tags
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     * Returns the tag path.
     * @return the tag path
     */
    public String getTagPath() {
        return tagPath;
    }
}
