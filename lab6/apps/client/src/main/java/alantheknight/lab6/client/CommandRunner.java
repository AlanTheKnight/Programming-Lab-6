package alantheknight.lab6.client;

import alantheknight.lab6.common.commands.CommandManager;
import alantheknight.lab6.common.commands.CommandType;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import static alantheknight.lab6.client.Main.commandManager;
import static alantheknight.lab6.client.Main.stdConsole;

/**
 * The CommandRunner class is responsible for running the commands.
 */
public class CommandRunner {
    private int MAX_RECURSION_LEVEL = 10;
    private int recursionLevel = 0;

    /**
     * Creates a new CommandRunner.
     */
    public CommandRunner() {
    }

    /**
     * Set the maximum recursion level.
     *
     * @param maxRecursionLevel the maximum recursion level
     */
    public void setMaxRecursionLevel(int maxRecursionLevel) {
        MAX_RECURSION_LEVEL = maxRecursionLevel;
    }

    /**
     * Process the prompt.
     *
     * @param prompt the prompt to process
     * @return the exit code of the command
     */
    public ExitCode processPrompt(String prompt) {
        var readCommand = prompt.trim().split("\\s+");
        if (readCommand[0].isEmpty())
            return ExitCode.OK;

        if (readCommand[0].startsWith("//") || readCommand[0].startsWith("#"))
            return ExitCode.OK;

        try {
            var commandType = CommandType.fromString(readCommand[0]);
            var command = commandManager.getCommand(commandType);
            commandManager.addToHistory(commandType);
            boolean commandStatus = command.execute(readCommand);
            return commandStatus ? ExitCode.OK : ExitCode.ERROR;
        } catch (CommandManager.CommandNotFoundException e) {
            stdConsole.printError(e.getMessage());
            return ExitCode.ERROR;
        } catch (IllegalArgumentException e) {
            stdConsole.printError("Команда не найдена. Используйте команду help для получения справки.");
            return ExitCode.ERROR;
        }
    }

    /**
     * Run the CommandRunner in interactive mode.
     */
    public void run() {
        try {
            ExitCode commandStatus;
            do {
                commandStatus = processPrompt(stdConsole.readLine());
            } while (commandStatus != ExitCode.EXIT);
        } catch (Exception e) {
            stdConsole.printError(e.getMessage());
        }
    }

    /**
     * Execute a script.
     *
     * @param filename the filename of the script
     * @return the exit code of the command
     */
    public ExitCode executeScript(String filename) {
        recursionLevel++;

        if (recursionLevel > MAX_RECURSION_LEVEL) {
            stdConsole.printError("Превышен максимальный уровень рекурсии: " + MAX_RECURSION_LEVEL);
            return ExitCode.ERROR;
        }

        if (!new File(filename).exists()) {
            stdConsole.printError("Файл скрипта " + filename + " не найден");
            return ExitCode.ERROR;
        }

        if (!Files.isReadable(Paths.get(filename))) {
            stdConsole.printError("Файл скрипта " + filename + " не доступен для чтения");
            return ExitCode.ERROR;
        }

        try (Scanner scanner = new Scanner(new File(filename))) {
            stdConsole.selectFileScanner(scanner);
            var commandStatus = ExitCode.OK;
            do {
                commandStatus = processPrompt(scanner.nextLine());
                if (commandStatus == ExitCode.ERROR) {
                    stdConsole.printError("Ошибка при выполнении скрипта " + filename);
                    stdConsole.selectFileScanner(null);
                    return ExitCode.ERROR;
                }
            } while (commandStatus == ExitCode.OK && scanner.hasNextLine());
        } catch (Exception e) {
            stdConsole.printError(e.getMessage());
            stdConsole.printError("Ошибка при выполнении скрипта " + filename);
            return ExitCode.ERROR;
        }

        stdConsole.selectFileScanner(null);
        recursionLevel--;
        stdConsole.printSuccess("Выполнение скрипта " + filename + " завершено");

        return ExitCode.OK;
    }

    /**
     * The exit code of the command.
     */
    public enum ExitCode {
        /**
         * The command was executed successfully.
         */
        OK,

        /**
         * There was an error while executing the command.
         */
        ERROR,

        /**
         * The command was executed successfully and the program should exit.
         */
        EXIT
    }
}