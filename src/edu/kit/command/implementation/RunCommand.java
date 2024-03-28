package edu.kit.command.implementation;

import edu.kit.DocumentHandler;
import edu.kit.command.CommandResult;
import edu.kit.command.CommandResultType;
import edu.kit.command.InteractionCommand;
import edu.kit.document.Document;
import edu.kit.document.StructuralTree;

import java.util.Set;

/**
 * Represents the command to turn a set of documents into a tree and print it.
 *
 * @author uqfdp
 */
public class RunCommand implements InteractionCommand {
    private static final String INVALID_ARGUMENTS_ERROR_MESSAGE = "Invalid argument.";
    private static final String DOCUMENT_NOT_FOUND_ERROR_MESSAGE = "Document not found.";
    private static final String TREE_FORMAT = "%s%n---%n%s";

    @Override
    public CommandResult execute(DocumentHandler documentHandler, String[] commandArguments) {
        int id;
        try {
            id = Integer.parseInt(commandArguments[0]);
        } catch (NumberFormatException e) {
            return new CommandResult(CommandResultType.FAILURE, INVALID_ARGUMENTS_ERROR_MESSAGE);
        }

        Set<Document> documents = documentHandler.getDocumentSet(id);
        if (documents == null) {
            return new CommandResult(CommandResultType.FAILURE, DOCUMENT_NOT_FOUND_ERROR_MESSAGE);
        }
        StructuralTree tree = new StructuralTree(documentHandler, documents);
        String treeValues = tree.buildTree();
        String treeString = tree.toString();
        return new CommandResult(CommandResultType.SUCCESS,
                TREE_FORMAT.formatted(
                        treeValues.substring(0, treeValues.length() - 1),
                        treeString.substring(0, treeString.length() - 1)));
    }
}
