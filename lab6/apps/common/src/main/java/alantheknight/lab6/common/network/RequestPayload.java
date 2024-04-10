package alantheknight.lab6.common.network;

import alantheknight.lab6.common.models.Worker;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * The RequestPayload class represents a payload of a request.
 */
public record RequestPayload(Worker element, Integer key, LocalDate endDate) implements Serializable {
    public RequestPayload(LocalDate endDate) {
        this(null, null, endDate);
    }

    public RequestPayload(Worker element, Integer key) {
        this(element, key, null);
    }

    public RequestPayload(Integer key) {
        this(null, key, null);
    }

    /**
     * Checks if the payload is empty.
     *
     * @return true if the payload is empty, false otherwise
     */
    public boolean isEmpty() {
        return element() == null && key() == null && endDate() == null;
    }
}
