package alantheknight.lab6.common.utils;

/**
 * Exception thrown when a model can't be created from XML element.
 */
public class ElementConversionException extends Exception {
    /**
     * Constructor for the exception.
     *
     * @param message message
     */
    public ElementConversionException(String message) {
        super(message);
    }
}
