package edu.kit.command;

/**
 * This enum represents the different types of commands.
 *
 * @author Programmieren-Team
 */
public enum CommandType {

    /**
     * Represents the command to load documents from a file.
     */
    LOAD(1),

    /**
     * Represents the command to turn a set of documents into a tree and print it.
     */
    RUN(1),

    /**
     * Represents the command to change the number of uses of a document.
     */
    CHANGE(3),

    /**
     * Represents the command to quit the program.
     */
    QUIT(0);

    private final int requiredArgumentsCount;

    /**
     * Constructs a new CommandType with the given number of required arguments.
     *
     * @param requiredArgumentsCount the number of required arguments
     */
    CommandType(int requiredArgumentsCount) {
        this.requiredArgumentsCount = requiredArgumentsCount;
    }

    /**
     * Returns the CommandType that corresponds to the given string.
     *
     * @param command the string to convert
     * @return the CommandType that corresponds to the given string
     */
    public static CommandType fromString(String command) {
        for (CommandType type : CommandType.values()) {
            if (type.name().equalsIgnoreCase(command)) {
                return type;
            }
        }
        return null;
    }

    /**
     * Returns the number of required arguments for this command.
     *
     * @return the number of required arguments for this command
     */
    public int getRequiredArgumentsCount() {
        return requiredArgumentsCount;
    }
}
