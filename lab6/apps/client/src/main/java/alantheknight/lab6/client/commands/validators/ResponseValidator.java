package alantheknight.lab6.client.commands.validators;

import alantheknight.lab6.client.commands.ClientCommand;
import alantheknight.lab6.common.network.Response;
import org.apache.commons.lang3.tuple.ImmutablePair;

public interface ResponseValidator {
    ImmutablePair<Boolean, String> validate(Response<?> response, ClientCommand<?> command);
}
