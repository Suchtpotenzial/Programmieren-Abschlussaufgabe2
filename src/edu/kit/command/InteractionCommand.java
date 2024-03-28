package edu.kit.command;

import edu.kit.DocumentHandler;

/**
 * This interface represents an executable command.
 *
 * @author Programmieren-Team
 */
public interface InteractionCommand {

    /**
     * Executes the command.
     *
     * @param documentHandler the {@link DocumentHandler documentHandler} to execute the command on
     * @param commandArguments the arguments of the command
     * @return the result of the command
     */
    CommandResult execute(DocumentHandler documentHandler, String[] commandArguments);

}
