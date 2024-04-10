package alantheknight.lab6.client.commands;

import alantheknight.lab6.common.commands.CommandType;
import alantheknight.lab6.common.network.Request;
import alantheknight.lab6.common.network.RequestPayload;

import java.time.LocalDate;

import static alantheknight.lab6.client.Main.stdConsole;

public class RemoveAnyByEndDate extends ClientCommand<Void> {
    public RemoveAnyByEndDate() {
        super(CommandType.REMOVE_ANY_BY_END_DATE, "Удалить из коллекции один элемент, значение поля endDate которого эквивалентно заданному",
                "remove_any_by_end_date yyyy-mm-dd");
    }

    @Override
    public boolean apply(String[] arguments) throws CommandExecutionException {
        String endDate = arguments[1];
        if (!endDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            stdConsole.printError("Дата должна быть в формате yyyy-mm-dd");
            return false;
        }
        LocalDate date = LocalDate.parse(endDate);
        Request request = new Request(getCommandType(), new RequestPayload(date));

        var response = sendRequestAndHandleResponse(request);
        stdConsole.printSuccess(response.getMessage());
        return true;
    }
}
