package alantheknight.lab6.client.commands;

import alantheknight.lab6.common.commands.CommandType;
import alantheknight.lab6.common.models.Worker;
import alantheknight.lab6.common.network.Request;
import alantheknight.lab6.common.network.Response;
import alantheknight.lab6.common.utils.Console;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.List;

public class Show extends ClientCommand {
    public Show(Console console, ClientCommandManager commandManager) {
        super(CommandType.SHOW, "Показать все элементы коллекции",
                "show", console, commandManager);
    }


    @Override
    public boolean apply(String[] arguments) {
        try {
            Request request = new Request(getCommandType());
            Response<List<Worker>> response = sendRequestAndHandleResponse(request);

            if (response.getPayload().isEmpty()) {
                console.println("Коллекция пуста");
                return true;
            }

            console.println("Список элементов коллекции:");
            response.getPayload().forEach(worker -> console.println(worker.toString()));
            return true;
        } catch (CommandExecutionException e) {
            console.printError("Ошибка выполнения команды: " + e.getMessage());
            return false;
        }
    }

    @Override
    protected ImmutablePair<Boolean, String> localValidateResponse(Response<?> response) {
        if (!response.hasPayload() || !(response.getPayload() instanceof List)) {
            return new ImmutablePair<>(false, "Ответ содержит неверный тип данных");
        }
        return new ImmutablePair<>(true, null);
    }
}
