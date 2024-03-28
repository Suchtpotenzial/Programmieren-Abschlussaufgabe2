package edu.kit;

import edu.kit.command.CommandHandler;

/**
 * This is the main class of the FlawlessFileFacilitator application.
 *
 * @author uqfdp
 */
public final class FlawlessFileFacilitator {

    /**
     * This class is a utility class and should not be instantiated.
     */
    private FlawlessFileFacilitator() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * The main method of the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DocumentHandler documentHandler = new DocumentHandler();
        new CommandHandler(documentHandler).handleUserInput();
    }


}
