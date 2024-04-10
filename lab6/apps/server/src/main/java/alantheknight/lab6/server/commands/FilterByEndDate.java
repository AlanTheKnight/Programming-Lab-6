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
 * Command for filtering elements by end date.
 */
public class FilterByEndDate extends ServerCommand {
    public FilterByEndDate() {
        super(CommandType.FILTER_BY_END_DATE);
    }

    @Override
    public boolean validateRequest(Request request) {
        return validateRequest(request) && request.getPayload() != null && request.getPayload().endDate() != null;
    }

    @Override
    public Response<ArrayList<Worker>> apply(Request request) {
        var endDate = request.getPayload().endDate();
        ArrayList<Worker> workers = collectionManager.getWorkers().values().stream()
                .filter(w -> w.getEndDate().equals(endDate))
                .sorted(workerCoordinatesComparator)
                .collect(Collectors.toCollection(ArrayList::new));
        return new Response<>(getCommandType(), "Найдено " + workers.size() + " элементов с датой окончания " + endDate, workers);
    }
}
