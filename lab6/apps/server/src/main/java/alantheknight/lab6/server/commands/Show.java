package alantheknight.lab6.server.commands;

import alantheknight.lab6.common.commands.CommandType;
import alantheknight.lab6.common.managers.CollectionManager;
import alantheknight.lab6.common.models.Worker;
import alantheknight.lab6.common.network.Request;
import alantheknight.lab6.common.network.Response;

import java.util.List;

/**
 * Command for adding a new element with a given key.
 *
 * @author AlanTheKnight
 */
public class Show extends ServerCommand {
    private final CollectionManager collectionManager;

    /**
     * Constructor for the command.
     *
     * @param collectionManager collection manager
     */
    public Show(CollectionManager collectionManager) {
        super(CommandType.SHOW);
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean validateRequest(Request request) {
        return super.validateRequest(request) && !request.hasPayload();
    }

    @Override
    public Response<List<Worker>> apply(Request request) {
        List<Worker> workers = collectionManager.getCollection().stream().toList();
        return new Response<>(getCommandType(), "Список элементов коллекции:", workers);
    }
}
