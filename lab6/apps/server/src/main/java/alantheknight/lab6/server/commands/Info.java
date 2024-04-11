package alantheknight.lab6.server.commands;

import alantheknight.lab6.common.commands.CommandType;
import alantheknight.lab6.common.network.Request;
import alantheknight.lab6.common.network.Response;
import alantheknight.lab6.common.utils.ConsoleColors;

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
                ConsoleColors.colorize("Тип: ", ConsoleColors.BLUE) + collectionManager.getWorkers().getClass().getName() + "\n" +
                ConsoleColors.colorize("Количество элементов: ", ConsoleColors.BLUE) + collectionManager.getCollection().size() + "\n" +
                ConsoleColors.colorize("Дата инициализации: ", ConsoleColors.BLUE) + collectionManager.getLastInitTime().toLocalDate().toString() + " " +
                collectionManager.getLastInitTime().toLocalTime().toString() + "\n";
        return new Response<>(getCommandType(), info);
    }
}
