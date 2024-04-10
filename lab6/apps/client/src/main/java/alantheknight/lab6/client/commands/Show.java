package alantheknight.lab6.client.commands;

import alantheknight.lab6.client.commands.validators.DefaultResponseValidator;
import alantheknight.lab6.common.commands.CommandType;
import alantheknight.lab6.common.models.Worker;
import alantheknight.lab6.common.network.Request;
import alantheknight.lab6.common.network.Response;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.ArrayList;

import static alantheknight.lab6.client.Main.stdConsole;

public class Show extends ClientCommand<ArrayList<Worker>> {
    public Show() {
        super(CommandType.SHOW, "Показать все элементы коллекции",
                "show", new PayloadValidator());
    }

    public Show(CommandType commandType, String description, String commandFormat) {
        super(commandType, description, commandFormat, new PayloadValidator());
    }

    @Override
    public boolean apply(String[] arguments) throws CommandExecutionException {
        Request request = new Request(getCommandType());
        var response = sendRequestAndHandleResponse(request);

        if (response.getPayload().isEmpty()) {
            stdConsole.println("Коллекция пуста");
            return true;
        }

        stdConsole.println("Список элементов коллекции:");
        response.getPayload().forEach(worker -> stdConsole.println(worker.toString()));
        return true;
    }

    public static class PayloadValidator extends DefaultResponseValidator {
        @Override
        public ImmutablePair<Boolean, String> validate(Response<?> response, ClientCommand<?> command) {
            ImmutablePair<Boolean, String> result = super.validate(response, command);
            if (!result.getLeft()) return result;

            if (response.getPayload() instanceof ArrayList)
                return new ImmutablePair<>(true, null);
            return new ImmutablePair<>(false, "Получен неверный тип данных");
        }
    }
}
