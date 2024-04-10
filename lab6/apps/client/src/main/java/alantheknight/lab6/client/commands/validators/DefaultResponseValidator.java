package alantheknight.lab6.client.commands.validators;

import alantheknight.lab6.client.commands.ClientCommand;
import alantheknight.lab6.common.network.Response;
import org.apache.commons.lang3.tuple.ImmutablePair;

public class DefaultResponseValidator implements ResponseValidator {
    @Override
    public ImmutablePair<Boolean, String> validate(Response<?> response, ClientCommand<?> command) {
        if (response == null)
            return new ImmutablePair<>(false, "Получен пустой ответ от сервера");
        if (response.getCommandType() != command.getCommandType())
            return new ImmutablePair<>(false, "Получен ответ на неверную команду");
        if (!response.isSuccess())
            return new ImmutablePair<>(false, response.getMessage());
        return new ImmutablePair<>(true, null);
    }
}
