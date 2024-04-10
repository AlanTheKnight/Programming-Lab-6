package alantheknight.lab6.server.commands;

import alantheknight.lab6.common.commands.CommandType;
import alantheknight.lab6.common.models.Worker;
import alantheknight.lab6.common.network.Request;
import alantheknight.lab6.common.network.Response;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static alantheknight.lab6.server.Main.collectionManager;
import static alantheknight.lab6.server.managers.CollectionManager.workerCoordinatesComparator;

/**
 * Command for adding a new element with a given key.
 */
public class Show extends ServerCommand {
    public Show() {
        super(CommandType.SHOW);
    }

    @Override
    public boolean validateRequest(Request request) {
        return request.doesNotHavePayload();
    }

    @Override
    public Response<ArrayList<Worker>> apply(Request request) {
        ArrayList<Worker> workers = collectionManager.getCollection().stream().sorted(workerCoordinatesComparator).collect(Collectors.toCollection(ArrayList::new));
        return new Response<>(getCommandType(), "Список элементов коллекции:", workers);
    }
}
