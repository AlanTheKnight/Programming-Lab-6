package alantheknight.lab6.common.network;

import alantheknight.lab6.common.commands.CommandType;

import java.io.Serializable;
import java.util.Optional;

/**
 * The Request class represents a request to the server.
 */
public class Request implements Serializable {
    /**
     * The command type.
     */
    private final CommandType command;
    /**
     * The payload.
     */
    private final RequestPayload payload;

    public Request(CommandType command, RequestPayload payload) {
        this.command = command;
        this.payload = payload;
    }

    public Request(CommandType command) {
        this(command, null);
    }

    public CommandType getCommand() {
        return command;
    }

    public RequestPayload getPayload() {
        return payload;
    }

    /**
     * Checks if the request has a payload.
     *
     * @return true if the request doesn't have a payload, false otherwise
     */
    public boolean doesNotHavePayload() {
        return Optional.ofNullable(payload).isEmpty();
    }

    @Override
    public String toString() {
        return "Request{" +
                "command='" + command + '\'' +
                ", payload=" + payload +
                '}';
    }
}
