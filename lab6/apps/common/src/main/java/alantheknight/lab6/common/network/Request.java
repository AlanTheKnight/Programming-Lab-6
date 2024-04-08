package alantheknight.lab6.common.network;

import alantheknight.lab6.common.commands.CommandType;

import java.io.Serializable;

public class Request implements Serializable {
    private final CommandType command;
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

    public boolean hasPayload() {
        return payload != null;
    }

    @Override
    public String toString() {
        return "Request{" +
                "command='" + command + '\'' +
                ", payload=" + payload +
                '}';
    }
}
