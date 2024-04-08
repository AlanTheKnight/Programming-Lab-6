package alantheknight.lab6.common.fields;

import org.apache.commons.lang3.tuple.ImmutablePair;

import java.io.Serializable;


public interface FieldChecker<T> extends Serializable {
    ImmutablePair<Boolean, String> isValid(T value);
}
