package alantheknight.lab6.client.commands;

import alantheknight.lab6.common.commands.CommandType;
import alantheknight.lab6.common.fields.handlers.InputHandler;
import alantheknight.lab6.common.models.Worker;
import alantheknight.lab6.common.network.Request;
import alantheknight.lab6.common.network.RequestPayload;

import static alantheknight.lab6.client.Main.stdConsole;

public class Insert extends ClientCommand<Void> {
    public Insert() {
        super(CommandType.INSERT, "Добавить новый элемент с заданным ключом",
                "insert <id>");
    }

    public Insert(CommandType commandType, String description, String commandFormat) {
        super(commandType, description, commandFormat, new Show.PayloadValidator());
    }

    @Override
    public boolean apply(String[] arguments) throws CommandExecutionException, InputHandler.InputException {
        Integer id = readIdArg(arguments[1]);
        if (id == null) {
            return false;
        }

        Worker w = new Worker();
        InputHandler.input(stdConsole, w, Worker.class);
        w.id.setValue(id);

        Request request = new Request(getCommandType(), new RequestPayload(w, id));
        var response = sendRequestAndHandleResponse(request);
        stdConsole.printSuccess(response.getMessage());
        return true;
    }
}
