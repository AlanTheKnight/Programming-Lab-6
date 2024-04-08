package alantheknight.lab6.client.commands;

import alantheknight.lab6.common.commands.BaseCommand;
import alantheknight.lab6.common.commands.CommandType;
import alantheknight.lab6.common.managers.CommandManager;
import alantheknight.lab6.common.network.Request;
import alantheknight.lab6.common.network.Response;
import alantheknight.lab6.common.utils.Console;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.List;

public abstract class ClientCommand extends BaseCommand {
    protected final ClientCommandManager commandManager;
    protected final Console console;
    private final String description;
    private final String format;
    private final int argsCount;


    public ClientCommand(CommandType type, String description, String format, Console console) {
        super(type);
        this.description = description;
        this.format = format;
        this.argsCount = format.split(" ").length;
        this.console = console;
        this.commandManager = null;
    }

    public ClientCommand(CommandType type, String description, String format, Console console, ClientCommandManager commandManager) {
        super(type);
        this.description = description;
        this.format = format;
        this.argsCount = format.split(" ").length;
        this.commandManager = commandManager;
        this.console = console;
    }

    public static void bulkRegister(List<Class<? extends ClientCommand>> commands, ClientCommandManager commandManager, Console console) {
        for (Class<? extends ClientCommand> command : commands) {
            try {
                commandManager.register(command.getConstructor(Console.class, ClientCommandManager.class).newInstance(console, commandManager));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public abstract boolean apply(String[] arguments);

    /**
     * Read an id argument.
     *
     * @param argument argument
     * @return id or null if the argument is invalid
     */
    public Integer readIdArg(String argument) {
        int id;
        try {
            id = Integer.parseInt(argument);
        } catch (NumberFormatException e) {
            printArgsError();
            return null;
        }
        if (id < 0) {
            console.printError("id не может быть отрицательным");
            return null;
        }
        return id;
    }

    public int getArgsCount() {
        return argsCount;
    }

    public CommandManager<ClientCommand> getCommandManager() {
        return commandManager;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Get the format of the command.
     *
     * @return format of the command
     */
    public String getFormat() {
        return format;
    }

    @Override
    public String toString() {
        return "Command{" +
                "type='" + getCommandType() + '\'' +
                ", description='" + description + '\'' +
                ", commandFormat='" + format + '\'' +
                '}';
    }

    /**
     * Print the arguments error message.
     */
    public void printArgsError() {
        console.printError("Неверный формат аргументов.");
        console.println("Использование: " + getFormat());
    }

    /**
     * Run the command, performing arguments number check and execution.
     *
     * @param arguments arguments of the command
     * @return true if the command was successful, false otherwise
     */
    public boolean execute(String[] arguments) {
        try {
            checkArguments(arguments);
            return apply(arguments);
        } catch (IllegalArgumentsNumber e) {
            console.printError(e.getMessage());
            console.println("Использование: " + getFormat());
            return false;
        }
    }

    /**
     * Send a request and handle the response.
     *
     * @param request request
     * @return response
     * @throws CommandExecutionException if the response is invalid
     */
    public <T> Response<T> sendRequestAndHandleResponse(Request request) throws CommandExecutionException {
        Response<T> response = commandManager.getClient().sendCommand(request);
        var verdict = validateResponse(response);
        if (verdict.getLeft())
            return response;
        throw new CommandExecutionException(verdict.getRight());
    }

    /**
     * Check the number of arguments.
     *
     * @param arguments arguments
     * @throws IllegalArgumentsNumber if the number of arguments is incorrect
     */
    public void checkArguments(String[] arguments) throws IllegalArgumentsNumber {
        if (arguments.length != getArgsCount()) {
            throw new IllegalArgumentsNumber("Неверное количество аргументов. Ожидается аргументов: " + (getArgsCount() - 1));
        }
    }

    public ImmutablePair<Boolean, String> validateResponse(Response<?> response) {
        if (response == null)
            return new ImmutablePair<>(false, "Получен пустой ответ от сервера");
        if (response.getCommandType() != getCommandType())
            return new ImmutablePair<>(false, "Получен ответ на неверную команду");
        if (!response.isSuccess())
            return new ImmutablePair<>(false, response.getMessage());
        return localValidateResponse(response);
    }

    protected ImmutablePair<Boolean, String> localValidateResponse(Response<?> response) {
        return new ImmutablePair<>(true, null);
    }

    /**
     * Exception for illegal number of arguments.
     */
    public static class IllegalArgumentsNumber extends Exception {
        /**
         * Constructor for the exception.
         *
         * @param message message
         */
        public IllegalArgumentsNumber(String message) {
            super(message);
        }
    }

    public static class CommandExecutionException extends Exception {
        public CommandExecutionException(String message) {
            super(message);
        }
    }
}
