package alantheknight.lab6.common.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Object that can be converted to XML element.
 */
public interface Convertible {
    /**
     * Converts the object to XML element.
     *
     * @param document the document
     * @return the XML element
     */
    Element getElement(Document document);
}
