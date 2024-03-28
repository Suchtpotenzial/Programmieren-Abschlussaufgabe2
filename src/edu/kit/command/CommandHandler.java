package edu.kit.command;

import edu.kit.DocumentHandler;
import edu.kit.command.implementation.ChangeCommand;
import edu.kit.command.implementation.LoadCommand;
import edu.kit.command.implementation.RunCommand;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * This class handles the commands that are entered by the user.
 *
 * @author Programmieren-Team
 * @author uqfdp
 **/
public final class CommandHandler {
    private static final String COMMAND_SEPARATOR_REGEX = " +";
    private static final String COMMAND_NOT_FOUND_FORMAT = "Command '%s' not found";
    private static final String ERROR_PREFIX = "ERROR: ";
    private static final String INVALID_ARGUMENT_COUNT_MESSAGE_FORMAT = "Invalid number of arguments. Expected: %d.";
    private final DocumentHandler documentHandler;
    private final Scanner scanner;
    private final Map<CommandType, InteractionCommand> commands;
    private boolean running = false;

    /**
     * Creates a new CommandHandler instance.
     *
     * @param documentHandler the game to handle the commands for
     */
    public CommandHandler(DocumentHandler documentHandler) {
        this.documentHandler = documentHandler;
        this.commands = new HashMap<>();
        this.scanner = new Scanner(System.in);

        initCommands();
    }

    /**
     * Starts the interaction with the user.
     */
    public void handleUserInput() {
        if (running) {
            return;
        }
        this.running = true;
        while (running && scanner.hasNextLine()) {
            executeCommand(scanner.nextLine());
        }
    }

    /**
     * Quits the interaction with the user.
     */
    private void quit() {
        this.running = false;
    }

    /**
     * Executes a command.
     *
     * @param commandWithArguments the command with arguments
     */
    private void executeCommand(String commandWithArguments) {
        String[] splittedCommand = commandWithArguments.trim().split(COMMAND_SEPARATOR_REGEX);
        String commandName = splittedCommand[0];
        String[] commandArguments = Arrays.copyOfRange(splittedCommand, 1, splittedCommand.length);

        executeCommand(commandName, commandArguments);
    }

    /**
     * Executes a command.
     *
     * @param commandName      the name of the command
     * @param commandArguments the arguments of the command
     */
    private void executeCommand(String commandName, String[] commandArguments) {
        CommandType commandType = CommandType.fromString(commandName);
        if (commandType == null) {
            System.out.println(ERROR_PREFIX + COMMAND_NOT_FOUND_FORMAT.formatted(commandName));
            return;
        }

        if (commandArguments.length != commandType.getRequiredArgumentsCount()) {
            System.out.println(ERROR_PREFIX + INVALID_ARGUMENT_COUNT_MESSAGE_FORMAT.formatted(commandType.getRequiredArgumentsCount()));
            return;
        }

        if (commandType == CommandType.QUIT) {
            quit();
            return;
        }

        InteractionCommand commandImplementation = commands.get(commandType);
        CommandResult result = commandImplementation.execute(documentHandler, commandArguments);
        String output = switch (result.getType()) {
            case SUCCESS -> result.getMessage();
            case FAILURE -> ERROR_PREFIX + result.getMessage();
        };
        if (output != null) {
            System.out.println(output);
        }
    }

    /**
     * Initializes the commands.
     */
    private void initCommands() {
        addCommand(CommandType.LOAD, new LoadCommand());
        addCommand(CommandType.RUN, new RunCommand());
        addCommand(CommandType.CHANGE, new ChangeCommand());
    }

    /**
     * Adds a command to the command handler.
     *
     * @param command the command to be added
     * @param commandImplementation the implementation of the command
     */
    private void addCommand(CommandType command, InteractionCommand commandImplementation) {
        this.commands.put(command, commandImplementation);
    }
}
