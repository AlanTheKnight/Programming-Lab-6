package alantheknight.lab6.server.commands;

import alantheknight.lab6.common.commands.CommandType;
import alantheknight.lab6.common.network.Request;
import alantheknight.lab6.common.network.Response;

import static alantheknight.lab6.server.Main.collectionManager;
import static alantheknight.lab6.server.Main.commandManager;

/**
 * Command for removing all elements greater than the given one.
 */
public class RemoveGreater extends ServerCommand {
    public RemoveGreater() {
        super(CommandType.REMOVE_GREATER);
    }

    @Override
    public boolean validateRequest(Request request) {
        var payloadElement = request.getPayload().element();
        return validateRequest(request) && payloadElement != null && payloadElement.validate() && payloadElement.id.getValue() != null;
    }

    @Override
    public Response<Void> apply(Request request) {
        var key = request.getPayload().key();
        int removed = collectionManager.removeGreaterKey(key);
        if (removed > 0) commandManager.save();
        return new Response<>(getCommandType(), "Удалено " + removed + " элементов с ключом больше " + key);
    }
}
