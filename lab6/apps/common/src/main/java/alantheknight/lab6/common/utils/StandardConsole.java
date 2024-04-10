package alantheknight.lab6.common.utils;

import java.util.Scanner;

/**
 * Standard console.
 */
public class StandardConsole implements Console {
    /**
     * The prompt entry
     */
    private static final String promptEntry = "$ ";
    /**
     * The standard input scanner
     */
    private static final Scanner stdInScanner = new Scanner(System.in);
    /**
     * The file scanner
     */
    private static Scanner fileScanner = null;

    @Override
    public void print(Object obj) {
        System.out.print(obj);
    }

    @Override
    public void println(Object obj) {
        System.out.println(obj);
    }

    @Override
    public void printError(Object obj) {
        System.out.println(ConsoleColors.colorize("Error: " + obj, ConsoleColors.RED));
    }

    @Override
    public String readLine() {
        return readLine(true);
    }

    @Override
    public String readLine(boolean printPrompt) {
        if (printPrompt)
            print(promptEntry);
        return (fileScanner != null ? fileScanner : stdInScanner).nextLine();
    }

    @Override
    public boolean canReadLine() {
        return (fileScanner != null ? fileScanner : stdInScanner).hasNextLine();
    }

    @Override
    public void printTwoColumns(Object left, Object right, String separator, int leftWidth) {
        System.out.printf("%-" + leftWidth + "s %s %s%n", left, separator, right);
    }

    @Override
    public void selectFileScanner(Scanner obj) {
        fileScanner = obj;
    }

    @Override
    public void printSuccess(String s) {
        System.out.println(ConsoleColors.GREEN + s + ConsoleColors.RESET);
    }

    @Override
    public void printInColor(ConsoleColors color, Object obj) {
        println(ConsoleColors.colorize(obj.toString(), color));
    }

    @Override
    public boolean isInteractive() {
        return fileScanner == null;
    }
}
