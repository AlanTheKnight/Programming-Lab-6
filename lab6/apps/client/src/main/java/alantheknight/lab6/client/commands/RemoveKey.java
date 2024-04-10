package alantheknight.lab6.client.commands;

import alantheknight.lab6.common.commands.CommandType;
import alantheknight.lab6.common.network.Request;
import alantheknight.lab6.common.network.RequestPayload;

import static alantheknight.lab6.client.Main.stdConsole;

public class RemoveKey extends ClientCommand<Void> {
    public RemoveKey() {
        super(CommandType.REMOVE_KEY, "Удалить элемент из коллекции по его ключу", "remove_key <id>");
    }

    public RemoveKey(CommandType commandType, String description, String commandFormat) {
        super(commandType, description, commandFormat);
    }

    @Override
    public boolean apply(String[] arguments) throws CommandExecutionException {
        Integer id = readIdArg(arguments[1]);
        if (id == null) {
            return false;
        }

        Request request = new Request(getCommandType(), new RequestPayload(id));
        var response = sendRequestAndHandleResponse(request);
        stdConsole.printSuccess(response.getMessage());
        return true;
    }
}
