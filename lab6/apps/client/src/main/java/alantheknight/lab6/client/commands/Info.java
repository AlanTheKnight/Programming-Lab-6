package alantheknight.lab6.client.commands;

import alantheknight.lab6.common.commands.CommandType;
import alantheknight.lab6.common.network.Request;
import alantheknight.lab6.common.network.Response;

import static alantheknight.lab6.client.Main.stdConsole;

public class Info extends ClientCommand<Void> {
    public Info() {
        super(CommandType.INFO, "Вывести информацию о коллекции", "info");
    }

    @Override
    public boolean apply(String[] arguments) throws CommandExecutionException {
        Request request = new Request(getCommandType());
        Response<Void> response = sendRequestAndHandleResponse(request);
        for (String line : response.getMessage().split("\n")) {
            stdConsole.println(line);
        }
        return true;
    }
}
