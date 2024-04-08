package alantheknight.lab6.server.commands;

import alantheknight.lab6.common.commands.BaseCommand;
import alantheknight.lab6.common.commands.CommandType;
import alantheknight.lab6.common.managers.CollectionManager;
import alantheknight.lab6.common.managers.CommandManager;
import alantheknight.lab6.common.network.Request;
import alantheknight.lab6.common.network.Response;

import java.util.List;

/**
 * Server command.
 */
public abstract class ServerCommand extends BaseCommand {
    public ServerCommand(CommandType commandType) {
        super(commandType);
    }

    public static void bulkRegister(List<Class<? extends ServerCommand>> commands, CommandManager<ServerCommand> commandManager, CollectionManager collectionManager) {
        for (Class<? extends ServerCommand> command : commands) {
            try {
                commandManager.register(command.getConstructor(CollectionManager.class).newInstance(collectionManager));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Validate the request (check if the command is correct and the payload is valid).
     *
     * @param request request
     * @return true if the request is valid, false otherwise
     */
    public boolean validateRequest(Request request) {
        return request.getCommand() == getCommandType();
    }

    /**
     * Apply the command to the request (command's main logic).
     *
     * @param request request
     * @return response
     */
    public abstract Response<?> apply(Request request);

    /**
     * Execute the command (validate the request and apply the command).
     *
     * @param request request
     * @return response
     */
    public Response<?> execute(Request request) {
        if (!validateRequest(request)) {
            return new Response<>(getCommandType(), "Неверный запрос", false);
        }
        return apply(request);
    }
}
