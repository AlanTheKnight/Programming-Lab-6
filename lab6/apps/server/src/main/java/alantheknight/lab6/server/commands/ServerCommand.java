package alantheknight.lab6.server.commands;

import alantheknight.lab6.common.commands.BaseCommand;
import alantheknight.lab6.common.commands.CommandType;
import alantheknight.lab6.common.network.Request;
import alantheknight.lab6.common.network.Response;
import org.apache.logging.log4j.Level;

import java.util.List;

import static alantheknight.lab6.server.Main.commandManager;
import static alantheknight.lab6.server.Main.logger;

/**
 * Server command.
 */
public abstract class ServerCommand extends BaseCommand {
    public ServerCommand(CommandType commandType) {
        super(commandType);
    }

    /**
     * Register multiple commands.
     *
     * @param commands commands to register
     */
    public static void bulkRegister(List<Class<? extends ServerCommand>> commands) {
        for (Class<? extends ServerCommand> command : commands) {
            try {
                commandManager.register(command.getConstructor().newInstance());
            } catch (Exception e) {
                logger.log(Level.ERROR, "Failed to register command: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Validate the command type.
     *
     * @param request request
     * @return true if the request is valid, false otherwise
     */
    public boolean validateCommandType(Request request) {
        return request.getCommand() == getCommandType();
    }

    /**
     * Validate the request.
     *
     * @param request request
     * @return true if the request is valid, false otherwise
     */
    public abstract boolean validateRequest(Request request);

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
        if (!validateCommandType(request)) {
            return new Response<>(getCommandType(), "Неверный запрос", false);
        }
        return apply(request);
    }
}
