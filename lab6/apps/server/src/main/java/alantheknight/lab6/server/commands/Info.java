package alantheknight.lab6.server.commands;

import alantheknight.lab6.common.commands.CommandType;
import alantheknight.lab6.common.network.Request;
import alantheknight.lab6.common.network.Response;

import static alantheknight.lab6.server.Main.collectionManager;

/**
 * Command for showing information about the collection.
 */
public class Info extends ServerCommand {
    public Info() {
        super(CommandType.INFO);
    }

    @Override
    public boolean validateRequest(Request request) {
        return validateCommandType(request) && request.doesNotHavePayload();
    }

    @Override
    public Response<Void> apply(Request request) {
        String info = "Сведения о коллекции: \n" +
                "Тип: " + collectionManager.getWorkers().getClass().getName() + "\n" +
                "Количество элементов: " + collectionManager.getCollection().size() + "\n" +
                "Дата инициализации: " + collectionManager.getLastInitTime().toLocalDate().toString() + " " +
                collectionManager.getLastInitTime().toLocalTime().toString() + "\n";
        return new Response<>(getCommandType(), info);
    }
}
