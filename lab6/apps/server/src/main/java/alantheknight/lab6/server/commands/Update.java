package alantheknight.lab6.server.commands;

import alantheknight.lab6.common.commands.CommandType;
import alantheknight.lab6.common.network.Request;
import alantheknight.lab6.common.network.Response;

import static alantheknight.lab6.server.Main.collectionManager;
import static alantheknight.lab6.server.Main.commandManager;

/**
 * Command for updating the element with the specified id.
 */
public class Update extends ServerCommand {
    public Update() {
        super(CommandType.INSERT);
    }

    @Override
    public boolean validateRequest(Request request) {
        var payloadElement = request.getPayload().element();
        return validateRequest(request) && payloadElement != null && payloadElement.validate() && payloadElement.id.getValue() != null;
    }

    @Override
    public Response<Void> apply(Request request) {
        var payload = request.getPayload();
        collectionManager.insertWorker(payload.element());
        commandManager.save();
        return new Response<>(getCommandType(), "Элемент с id " + payload.element().getId() + " успешно обновлён");
    }
}
