package alantheknight.lab6.common.fields;

import alantheknight.lab6.common.models.Coordinates;
import alantheknight.lab6.common.models.Person;
import alantheknight.lab6.common.utils.ElementConversionException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.time.LocalDate;

import static alantheknight.lab6.common.utils.NumberConvertor.convertNumber;

/**
 * Element convertor interface.
 *
 * @param <T> type of the element
 */
public interface ElementConvertor<T> {
    /**
     * Convertor for String.
     */
    ElementConvertor<String> stringConvertor = Node::getTextContent;
    /**
     * Convertor for {@link Coordinates} model.
     */
    ElementConvertor<Coordinates> coordinatesConvertor = node -> {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            return Coordinates.fromElement(element);
        }
        throw new ElementConversionException("Invalid node type");
    };
    /**
     * Convertor for {@link Person} model.
     */
    ElementConvertor<Person> personConvertor = node -> {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            return Person.fromElement(element);
        }
        throw new ElementConversionException("Invalid node type");
    };
    /**
     * Convertor for LocalDate.
     */
    ElementConvertor<LocalDate> dateConvertor = node -> LocalDate.parse(node.getTextContent());

    /**
     * Convertor for numbers.
     *
     * @param numberClass number class
     * @param <T>         number type
     * @return converted value
     */
    static <T extends Number> ElementConvertor<T> numberConvertor(Class<T> numberClass) {
        return node -> {
            String value = node.getTextContent();
            return convertNumber(numberClass, value);
        };
    }

    /**
     * Convertor for enums.
     *
     * @param enumClass enum class
     * @param <E>       enum type
     * @return converted value
     */
    static <E extends Enum<E>> ElementConvertor<E> enumConvertor(Class<E> enumClass) {
        return node -> {
            String value = node.getTextContent();
            return value.equals("null") ? null : Enum.valueOf(enumClass, value);
        };
    }

    /**
     * Convert XML element to an object.
     *
     * @param node XML element
     * @return the object
     * @throws ElementConversionException if conversion fails
     */
    T fromElement(Node node) throws ElementConversionException;
}
