package alantheknight.lab6.server.commands;

import alantheknight.lab6.common.commands.CommandType;
import alantheknight.lab6.common.managers.CollectionManager;
import alantheknight.lab6.common.network.Request;
import alantheknight.lab6.common.network.Response;

/**
 * Command for adding a new element with a given key.
 *
 * @author AlanTheKnight
 */
public class Insert extends ServerCommand {
    private final CollectionManager collectionManager;

    /**
     * Constructor for the command.
     *
     * @param collectionManager collection manager
     */
    public Insert(CollectionManager collectionManager) {
        super(CommandType.INSERT);
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean validateRequest(Request request) {
        var payloadElement = request.getPayload().element();
        return (
                super.validateRequest(request) && payloadElement != null &&
                        payloadElement.validate() && payloadElement.id.getValue() != null);
    }

    @Override
    public Response<Void> apply(Request request) {
        var payload = request.getPayload();
        collectionManager.insertWorker(payload.element());
        return new Response<>(getCommandType(), "Элемент с id " + payload.element().getId() + " успешно добавлен");
    }
}
