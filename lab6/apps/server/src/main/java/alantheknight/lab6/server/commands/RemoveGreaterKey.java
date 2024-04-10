package alantheknight.lab6.server.commands;

import alantheknight.lab6.common.commands.CommandType;
import alantheknight.lab6.common.network.Request;
import alantheknight.lab6.common.network.Response;

import static alantheknight.lab6.server.Main.collectionManager;
import static alantheknight.lab6.server.Main.commandManager;

/**
 * Command for removing all elements with keys greater than the specified one.
 */
public class RemoveGreaterKey extends ServerCommand {
    public RemoveGreaterKey() {
        super(CommandType.REMOVE_GREATER_KEY);
    }

    @Override
    public boolean validateRequest(Request request) {
        return validateCommandType(request) && request.getPayload().key() != null;
    }

    @Override
    public Response<Void> apply(Request request) {
        var key = request.getPayload().key();
        int removed = collectionManager.removeGreaterKey(key);
        if (removed > 0) commandManager.save();
        return new Response<>(getCommandType(), "Удалено " + removed + " элементов с ключом больше " + key);
    }
}
