package alantheknight.lab6.client.commands;

import alantheknight.lab6.client.commands.validators.DefaultResponseValidator;
import alantheknight.lab6.client.commands.validators.ResponseValidator;
import alantheknight.lab6.common.commands.BaseCommand;
import alantheknight.lab6.common.commands.CommandType;
import alantheknight.lab6.common.fields.handlers.InputHandler;
import alantheknight.lab6.common.network.Request;
import alantheknight.lab6.common.network.Response;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.List;

import static alantheknight.lab6.client.Main.commandManager;
import static alantheknight.lab6.client.Main.stdConsole;

public abstract class ClientCommand<ResponseType> extends BaseCommand {
    private final String description;
    private final String format;
    private final int argsCount;

    private final ResponseValidator responseValidator;

    public ClientCommand(CommandType type, String description, String format, ResponseValidator responseValidator) {
        super(type);
        this.description = description;
        this.format = format;
        this.argsCount = format.split(" ").length;
        this.responseValidator = responseValidator;
    }

    public ClientCommand(CommandType type, String description, String format) {
        super(type);
        this.description = description;
        this.format = format;
        this.argsCount = format.split(" ").length;
        responseValidator = new DefaultResponseValidator();
    }

    public static void bulkRegister(List<Class<? extends ClientCommand<?>>> commands) {
        for (Class<? extends ClientCommand<?>> command : commands) {
            try {
                commandManager.register(command.getConstructor().newInstance());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String getDescription() {
        return description;
    }

    public abstract boolean apply(String[] arguments) throws CommandExecutionException, InputHandler.InputException;

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
            stdConsole.printError("id не может быть отрицательным");
            return null;
        }
        return id;
    }

    public int getArgsCount() {
        return argsCount;
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
        stdConsole.printError("Неверный формат аргументов.");
        stdConsole.println("Использование: " + getFormat());
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
            stdConsole.printError(e.getMessage());
            stdConsole.println("Использование: " + getFormat());
            return false;
        } catch (CommandExecutionException e) {
            stdConsole.printError("Ошибка выполнения команды: " + e.getMessage());
            return false;
        } catch (InputHandler.InputException e) {
            stdConsole.printError("Ошибка ввода: " + e.getMessage());
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
    public Response<ResponseType> sendRequestAndHandleResponse(Request request) throws CommandExecutionException {
        Response<ResponseType> response = commandManager.getClient().sendCommand(request);
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
        return responseValidator.validate(response, this);
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
