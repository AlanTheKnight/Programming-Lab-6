package alantheknight.lab6.common.utils;

import java.util.Scanner;

/**
 * Console interface.
 */
public interface Console {
    /**
     * Print the object to the console.
     *
     * @param obj the object to print
     */
    void print(Object obj);

    /**
     * Print the object to the console and move to the next line.
     *
     * @param obj the object to print
     */
    void println(Object obj);

    /**
     * Read the next line from the file or standard input.
     *
     * @return the next line from the file or standard input
     */
    String readLine();

    /**
     * Read the next line from the file or standard input.
     *
     * @param printPrompt true if the prompt should be printed
     * @return the next line from the file or standard input
     */
    String readLine(boolean printPrompt);

    /**
     * Check if there is another line to read from the file or standard input.
     *
     * @return true if there is another line to read
     */
    boolean canReadLine();

    /**
     * Select the file scanner.
     *
     * @param obj the file scanner
     */
    void selectFileScanner(Scanner obj);

    /**
     * Print two columns.
     *
     * @param left      the left column
     * @param right     the right column
     * @param separator the separator
     * @param leftWidth the width of the left column
     */
    void printTwoColumns(Object left, Object right, String separator, int leftWidth);

    /**
     * Print an error message.
     *
     * @param obj the error message
     */
    void printError(Object obj);

    /**
     * Print a success message.
     *
     * @param s the success message
     */
    void printSuccess(String s);

    /**
     * Print in color.
     *
     * @param color the color
     * @param obj   the object
     */
    void printInColor(ConsoleColors color, Object obj);

    /**
     * Check if the console is interactive.
     *
     * @return true if the console is interactive
     */
    boolean isInteractive();
}
