package edu.kit.document;

import edu.kit.DocumentHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.Arrays;

/**
 * The DocumentLoader class provides methods to load documents from a file.
 *
 * @author uqfdp
 */
public final class DocumentLoader {

    private static final String DOCUMENT_PART_SEPARATOR = ",";
    private static final int MINIMUM_DOCUMENT_PARTS = 3;

    /**
     * Private constructor to prevent instantiation because this class is a utility class.
     */
    private DocumentLoader() {
    }

    /**
     * Loads documents from the file at the given path.
     *
     * @param path the path of the file
     * @return the set of documents or null if the file does not exist or the documents could not be loaded
     * @throws FileNotFoundException if the file does not exist
     */
    public static Set<Document> loadDocuments(String path) throws FileNotFoundException {
        File documentFile = new File(path);
        if (!documentFile.exists()) {
            return null;
        }

        Scanner scanner = new Scanner(documentFile);
        Set<Document> documents = new LinkedHashSet<>();

        while (scanner.hasNextLine()) {
            String rawDocument = scanner.nextLine();
            String[] documentParts = rawDocument.split(DOCUMENT_PART_SEPARATOR);
            Document document = createDocument(documentParts);
            if (document == null) {
                return null;
            }

            if (containsDocumentPath(documents, document.getPath())) {
                return null;
            }

            documents.add(document);
        }

        scanner.close();
        return documents;
    }

    /**
     * Checks if the set of documents contains a document with the given path.
     *
     * @param documents the set of documents
     * @param path the path of the document
     * @return true if the set of documents contains a document with the given path, false otherwise
     */
    private static boolean containsDocumentPath(Set<Document> documents, String path) {
        for (Document document : documents) {
            if (document.getPath().equals(path)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the content of the file at the given path as a string.
     *
     * @param path the path of the file
     * @return the content of the file as a string or null if the file does not exist or is empty
     */
    public static String getFileAsString(String path) {
        File documentFile = new File(path);
        if (!documentFile.exists()) {
            return null;
        }

        StringBuilder fileContent = new StringBuilder();
        try (Scanner scanner = new Scanner(documentFile)) {
            while (scanner.hasNextLine()) {
                fileContent.append(scanner.nextLine()).append(System.lineSeparator());
            }
        } catch (FileNotFoundException e) {
            return null;
        }

        if (fileContent.isEmpty()) {
            return null;
        }

        return fileContent.substring(0, fileContent.length() - 1);
    }

    /**
     * Creates a document from the given document parts.
     *
     * @param documentParts the document parts
     * @return the document or null if the document parts are invalid
     */
    private static Document createDocument(String[] documentParts) {
        if (documentParts.length < MINIMUM_DOCUMENT_PARTS) {
            return null;
        }

        String path = documentParts[0];
        if (path.contains(" ")) {
            return null;
        }

        DocumentType type = DocumentType.fromString(documentParts[1]);

        int uses;
        try {
            uses = Integer.parseInt(documentParts[2]);
        } catch (NumberFormatException e) {
            return null;
        }
        Set<Tag> tags = Tag.getTagsFromArray(Arrays.copyOfRange(documentParts, MINIMUM_DOCUMENT_PARTS, documentParts.length));
        if (tags == null) {
            return null;
        }

        return DocumentHandler.instance.createDocument(type, path, tags, uses);
    }
}
