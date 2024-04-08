package alantheknight.lab6.common.network;

import alantheknight.lab6.common.models.Worker;

import java.io.Serializable;
import java.time.LocalDate;

public record RequestPayload(Worker element, Integer key, LocalDate endDate) implements Serializable {
    public RequestPayload(Worker element) {
        this(element, null, null);
    }

    public RequestPayload(Worker element, Integer key) {
        this(element, key, null);
    }

    public boolean isEmpty() {
        return element() == null && key() == null && endDate() == null;
    }
}
