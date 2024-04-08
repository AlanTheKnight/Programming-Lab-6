package alantheknight.lab6.client.commands;

import alantheknight.lab6.common.commands.CommandType;
import alantheknight.lab6.common.fields.handlers.InputHandler;
import alantheknight.lab6.common.models.Worker;
import alantheknight.lab6.common.network.Request;
import alantheknight.lab6.common.network.RequestPayload;
import alantheknight.lab6.common.network.Response;
import alantheknight.lab6.common.utils.Console;
import org.apache.commons.lang3.tuple.ImmutablePair;

public class Insert extends ClientCommand {
    public Insert(Console console, ClientCommandManager commandManager) {
        super(CommandType.INSERT, "Добавить новый элемент с заданным ключом",
                "insert <id>", console, commandManager);
    }

    @Override
    public boolean apply(String[] arguments) {
        Integer id = readIdArg(arguments[1]);
        if (id == null) {
            return false;
        }

        try {
            Worker w = new Worker();
            InputHandler.input(console, w, Worker.class);
            w.id.setValue(id);

            Request request = new Request(getCommandType(), new RequestPayload(w, id));
            Response<Void> response = sendRequestAndHandleResponse(request);
            console.printSuccess(response.getMessage());
            return true;
        } catch (InputHandler.InputException e) {
            console.printError("Ошибка ввода: " + e.getMessage());
            return false;
        } catch (CommandExecutionException e) {
            console.printError("Ошибка выполнения команды: " + e.getMessage());
            return false;
        }
    }

    @Override
    protected ImmutablePair<Boolean, String> localValidateResponse(Response<?> response) {
        if (response.hasPayload()) {
            return new ImmutablePair<>(false, "Ответ содержит неверный тип данных");
        }
        return new ImmutablePair<>(true, null);
    }
}
