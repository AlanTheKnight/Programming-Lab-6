package alantheknight.lab6.common.network;

import alantheknight.lab6.common.commands.CommandType;

import java.io.Serializable;

/**
 * The Response class represents a response from the server.
 */
public class Response<T> implements Serializable {
    /**
     * The success flag.
     */
    private final boolean success;

    /**
     * The message.
     */
    private final String message;

    /**
     * The command type.
     */
    private final CommandType commandType;

    /**
     * The payload.
     */
    private final T payload;

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

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getPayload() {
        return payload;
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
