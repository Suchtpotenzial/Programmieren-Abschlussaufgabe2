package edu.kit.command.implementation;

import edu.kit.DocumentHandler;
import edu.kit.command.CommandResult;
import edu.kit.command.CommandResultType;
import edu.kit.command.InteractionCommand;
import edu.kit.document.Document;
import edu.kit.document.DocumentLoader;

import java.io.FileNotFoundException;
import java.util.Set;

/**
 * Represents the command to load a document from a file.
 *
 * @author uqfdp
 */
public class LoadCommand implements InteractionCommand {
    private static final String FILE_NOT_FOUND_ERROR_MESSAGE = "File not found.";
    private static final String LOADING_ERROR_MESSAGE = "Failed to load document";
    private static final String SUCCESS_MESSAGE_FORMAT = "Loaded %s with id: %d%n%s";
    private static final String EMPTY_FILE_ERROR_MESSAGE_FORMAT = "File %s is empty!";

    @Override
    public CommandResult execute(DocumentHandler documentHandler, String[] commandArguments) {
        String path = commandArguments[0];

        Set<Document> documents;
        try {
            documents = DocumentLoader.loadDocuments(path);
        } catch (FileNotFoundException e) {
            return new CommandResult(CommandResultType.FAILURE, FILE_NOT_FOUND_ERROR_MESSAGE);
        }
        if (documents == null) {
            return new CommandResult(CommandResultType.FAILURE, LOADING_ERROR_MESSAGE);
        }

        int id = documentHandler.addDocumentSet(documents);

        String fileRepresentation = DocumentLoader.getFileAsString(path);
        if (fileRepresentation == null) {
            return new CommandResult(CommandResultType.FAILURE,
                    EMPTY_FILE_ERROR_MESSAGE_FORMAT.formatted(path));
        }

        return new CommandResult(CommandResultType.SUCCESS,
                SUCCESS_MESSAGE_FORMAT.formatted(path, id, fileRepresentation));
    }
}
