package alantheknight.lab6.common.fields;

import org.apache.commons.lang3.tuple.ImmutablePair;

import java.io.Serializable;

/**
 * Interface for checking field values.
 *
 * @param <T> type of the field value
 */
public interface FieldChecker<T> extends Serializable {
    /**
     * Check if the value is valid.
     *
     * @param value the value to check
     * @return a pair of a boolean indicating if the value is valid and a message
     */
    ImmutablePair<Boolean, String> isValid(T value);
}
