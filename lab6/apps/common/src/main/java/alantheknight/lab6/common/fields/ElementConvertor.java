package alantheknight.lab6.common.fields;

import alantheknight.lab6.common.models.Coordinates;
import alantheknight.lab6.common.models.Person;
import alantheknight.lab6.common.utils.ElementConversionException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.time.LocalDate;

public interface ElementConvertor<T> {
    ElementConvertor<Double> doubleConvertor = node -> Double.parseDouble(node.getTextContent());
    ElementConvertor<Float> floatConvertor = node -> Float.parseFloat(node.getTextContent());
    ElementConvertor<Long> longConvertor = node -> Long.parseLong(node.getTextContent());
    ElementConvertor<String> stringConvertor = Node::getTextContent;
    ElementConvertor<Integer> integerConvertor = node -> Integer.parseInt(node.getTextContent());

    ElementConvertor<Coordinates> coordinatesConvertor = node -> {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            return Coordinates.fromElement(element);
        }
        throw new ElementConversionException("Invalid node type");
    };

    ElementConvertor<Person> personConvertor = node -> {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            return Person.fromElement(element);
        }
        throw new ElementConversionException("Invalid node type");
    };

    ElementConvertor<LocalDate> dateConvertor = node -> LocalDate.parse(node.getTextContent());

    static <E extends Enum<E>> ElementConvertor<E> enumConvertor(Class<E> enumClass) {
        return node -> {
            String value = node.getTextContent();
            return value.equals("null") ? null : Enum.valueOf(enumClass, value);
        };
    }

    T fromElement(Node node) throws ElementConversionException;
}
