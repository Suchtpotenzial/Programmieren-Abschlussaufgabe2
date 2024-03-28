package edu.kit;

import edu.kit.document.Document;
import edu.kit.document.DocumentType;
import edu.kit.document.implementations.AudioDocument;
import edu.kit.document.implementations.ImageDocument;
import edu.kit.document.implementations.ProgramDocument;
import edu.kit.document.implementations.TextDocument;
import edu.kit.document.implementations.VideoDocument;
import edu.kit.document.Tag;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The DocumentHandler class is responsible for handling documents.
 * It provides methods to calculate information gain, expected remaining uncertainty, probability of a document and a tag,
 * uncertainty of documents and accumulated uses.
 *
 * @author uqfdp
 */
public final class DocumentHandler {

    /**
     * The instance of the DocumentHandler class.
     */
    public static DocumentHandler instance;
    private final List<Set<Document>> loadedDocuments;

    /**
     * The constructor of the DocumentHandler class.
     */
    public DocumentHandler() {
        instance = this;
        this.loadedDocuments = new ArrayList<>();
    }

    /**
     * Returns all documents with a given tag.
     * @param documents the documents to get the documents with the tag from
     * @param tag the tag to get the documents with
     * @return the documents with the tag
     */
    public Set<Document> getDocumentsWithTag(Set<Document> documents, Tag tag) {
        Set<Document> documentsWithTag = new LinkedHashSet<>();

        if (tag.getValue().equalsIgnoreCase(Tag.UNDEFINED_VALUE_REPRESENTATION)) {
            for (Document document : documents) {
                if (document.getTags().stream().noneMatch(t -> t.getIdentifier().equals(tag.getIdentifier()))) {
                    documentsWithTag.add(document);
                }
            }
            return documentsWithTag;
        }
        return documents.stream()
            .filter(document -> document.getTags().contains(tag))
            .collect(Collectors.toSet());
    }

    /**
     * Returns the information gain of a set of documents with a given identifier.
     * @param documents the documents to get the information gain from
     * @param identifier the identifier to get the information gain from
     * @return the information gain of the documents
     */
    public double getInformationGain(Set<Document> documents, String identifier) {
        double uncertainty = getUncertaintyOfDocuments(documents);
        double remainingUncertainty = getExpectedRemainingUncertainty(documents, identifier);
        return uncertainty - remainingUncertainty;
    }

    /**
     * Returns the expected remaining uncertainty of a set of documents with a given identifier.
     * @param documents the documents to get the expected remaining uncertainty from
     * @param identifier the identifier to get the expected remaining uncertainty from
     * @return the expected remaining uncertainty of the documents
     */
    public double getExpectedRemainingUncertainty(Set<Document> documents, String identifier) {
        Set<String> possibleValues = Tag.getPossibleTagValues(documents, identifier);
        double sum = 0;
        for (String value : possibleValues) {
            Set<Document> documentsWithValue = getDocumentsWithTag(documents, new Tag(identifier, value));

            double probability = getProbabilityOfTag(new Tag(identifier, value), documents);
            double uncertainty = getUncertaintyOfDocuments(documentsWithValue);
            sum += probability * uncertainty;
        }
        return sum;
    }

    /**
     * Returns the probability of a document in a set of documents.
     * @param document the document to get the probability from
     * @param documents the documents to take the probability from
     * @return the probability of the document
     *
     */
    public double getProbabilityOfDocument(Document document, Set<Document> documents) {
        return document.getUses() / (double) getAccumulatedUses(documents);
    }

    /**
     * Returns the probability of a tag in a set of documents.
     * @param tag the tag to get the probability from
     * @param documents the documents to take the probability from
     * @return the probability of the tag
     */
    public double getProbabilityOfTag(Tag tag, Set<Document> documents) {
        Set<Document> documentsWithTag = getDocumentsWithTag(documents, tag);
        return documentsWithTag.stream()
            .mapToDouble(document -> getProbabilityOfDocument(document, documents))
            .sum();
    }

    /**
     * Returns the uncertainty of a set of documents.
     * @param documents the documents to get the uncertainty from
     * @return the uncertainty of the documents
     */
    public double getUncertaintyOfDocuments(Set<Document> documents) {
        double sum = 0;
        for (Document document : documents) {
            double probability = getProbabilityOfDocument(document, documents);
            sum += probability * (Math.log(probability) / Math.log(2));
        }
        return -sum;
    }

    /**
     * Returns the accumulated uses of given documents.
     * @param documents the documents to get the uses from
     * @return the accumulated uses of the documents
     */
    private int getAccumulatedUses(Set<Document> documents) {
        return documents.stream()
            .mapToInt(Document::getUses)
            .sum();
    }

    /**
     * Creates a document with a given type, path, tags and uses.
     * @param type the type of the document
     * @param path the path of the document
     * @param tags the tags of the document
     * @param uses the amount of uses of the document
     * @return the created document
     */
    public Document createDocument(DocumentType type, String path, Set<Tag> tags, int uses) {
        if (type == null || path == null || tags == null) {
            return null;
        }
        return switch (type) {
            case AUDIO -> new AudioDocument(path, tags, uses);
            case TEXT -> new TextDocument(path, tags, uses);
            case IMAGE -> new ImageDocument(path, tags, uses);
            case VIDEO -> new VideoDocument(path, tags, uses);
            case PROGRAM -> new ProgramDocument(path, tags, uses);
        };
    }

    /**
     * Returns the document set with a given index.
     * @param index the index of the document set
     * @return the document set with the index
     */
    public Set<Document> getDocumentSet(int index) {
        return isValidDocumentSet(index) ? loadedDocuments.get(index) : null;
    }

    /**
     * Returns whether a document set with a given index is valid.
     * @param index the index of the document set to check
     * @return whether the document set is valid
     */
    private boolean isValidDocumentSet(int index) {
        return index >= 0 && index < loadedDocuments.size();
    }

    /**
     * Adds a document set to the loaded documents.
     * @param documents the document set to add
     * @return the index of the added document set
     */
    public int addDocumentSet(Set<Document> documents) {
        loadedDocuments.add(documents);
        return loadedDocuments.size() - 1;
    }
}
