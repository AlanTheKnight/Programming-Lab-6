package alantheknight.lab6.server.commands;

import alantheknight.lab6.common.commands.CommandType;
import alantheknight.lab6.common.models.Worker;
import alantheknight.lab6.common.network.Request;
import alantheknight.lab6.common.network.Response;

import static alantheknight.lab6.server.Main.collectionManager;
import static alantheknight.lab6.server.Main.commandManager;

/**
 * Command for removing all elements greater than the given one.
 */
public class RemoveAnyByEndDate extends ServerCommand {
    public RemoveAnyByEndDate() {
        super(CommandType.REMOVE_ANY_BY_END_DATE);
    }

    @Override
    public boolean validateRequest(Request request) {
        return validateRequest(request) && request.getPayload() != null && request.getPayload().endDate() != null;
    }

    @Override
    public Response<Void> apply(Request request) {
        var endDate = request.getPayload().endDate();
        Worker w = collectionManager.removeWorkerByEndDate(endDate);
        if (w == null)
            return new Response<>(getCommandType(), "Элемент с датой окончания " + endDate + " не найден", false);
        commandManager.save();
        return new Response<>(getCommandType(), "Элемент с датой окончания " + endDate + " успешно удалён");
    }
}
