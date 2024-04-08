package alantheknight.lab6.common.network;

import alantheknight.lab6.common.commands.CommandType;

import java.io.Serial;
import java.io.Serializable;
import java.util.Optional;

public class Response<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final boolean success;
    private final String message;

    private final CommandType commandType;
    private T payload;

    public Response(CommandType commandType, String message, T payload) {
        this.commandType = commandType;
        this.message = message;
        this.success = true;
        this.payload = payload;
    }

    public Response(CommandType commandType, String message, boolean success) {
        this.commandType = commandType;
        this.message = message;
        this.success = success;
        this.payload = null;
    }

    public Response(CommandType commandType, String message) {
        this.commandType = commandType;
        this.message = message;
        this.success = true;
        this.payload = null;
    }

    // Getter and setter methods
    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public boolean hasPayload() {
        return Optional.ofNullable(payload).isPresent();
    }

    @Override
    public String toString() {
        return "Response{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", payload=" + payload +
                '}';
    }

    public CommandType getCommandType() {
        return commandType;
    }
}
