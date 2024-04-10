package alantheknight.lab6.server.commands;

import alantheknight.lab6.common.commands.CommandType;
import alantheknight.lab6.common.network.Request;
import alantheknight.lab6.common.network.Response;

import static alantheknight.lab6.server.Main.collectionManager;
import static alantheknight.lab6.server.Main.commandManager;

/**
 * Command for updating the element with the specified id.
 */
public class RemoveKey extends ServerCommand {
    public RemoveKey() {
        super(CommandType.REMOVE_KEY);
    }

    @Override
    public boolean validateRequest(Request request) {
        return validateCommandType(request) && request.getPayload().key() != null;
    }

    @Override
    public Response<Void> apply(Request request) {
        var key = request.getPayload().key();
        if (!collectionManager.hasWorkerWithId(key))
            return new Response<>(getCommandType(), "Элемент с id " + key + " не найден", false);
        collectionManager.removeWorker(key);
        commandManager.save();
        return new Response<>(getCommandType(), "Элемент с id " + key + " успешно удалён");
    }
}
