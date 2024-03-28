package edu.kit.command.implementation;

import edu.kit.DocumentHandler;
import edu.kit.command.CommandResult;
import edu.kit.command.CommandResultType;
import edu.kit.command.InteractionCommand;
import edu.kit.document.Document;

import java.util.Set;

/**
 * Represents the command to change the number of uses of a document.
 *
 * @author uqfdp
 */
public class ChangeCommand implements InteractionCommand {
    private static final String INVALID_ARGUMENTS_ERROR_MESSAGE = "Invalid argument.";
    private static final String DOCUMENT_NOT_FOUND_ERROR_MESSAGE = "Document not found.";
    private static final String INVALID_PATH_ERROR_MESSAGE_FORMAT = "No document with path %s found.";
    private static final String SUCCESS_MESSAGE_FORMAT = "Change %d to %d for %s";


    @Override
    public CommandResult execute(DocumentHandler documentHandler, String[] commandArguments) {
        int id;
        int number;
        try {
            id = Integer.parseInt(commandArguments[0]);
            number = Integer.parseInt(commandArguments[2]);
        } catch (NumberFormatException e) {
            return new CommandResult(CommandResultType.FAILURE, INVALID_ARGUMENTS_ERROR_MESSAGE);
        }

        String path = commandArguments[1];

        Set<Document> documents = documentHandler.getDocumentSet(id);
        if (documents == null) {
            return new CommandResult(CommandResultType.FAILURE, DOCUMENT_NOT_FOUND_ERROR_MESSAGE);
        }

        Document document = documents.stream().filter(d -> d.getPath().equals(path)).findFirst().orElse(null);
        if (document == null) {
            return new CommandResult(CommandResultType.FAILURE, INVALID_PATH_ERROR_MESSAGE_FORMAT.formatted(path));
        }
        int oldUses = document.getUses();
        document.setUses(number);
        return new CommandResult(CommandResultType.SUCCESS, SUCCESS_MESSAGE_FORMAT.formatted(oldUses, number, path));
    }
}
