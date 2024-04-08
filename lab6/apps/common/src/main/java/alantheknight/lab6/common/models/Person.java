package alantheknight.lab6.common.models;

import alantheknight.lab6.common.fields.ElementConvertor;
import alantheknight.lab6.common.fields.Field;
import alantheknight.lab6.common.fields.Model;
import alantheknight.lab6.common.utils.ElementConversionException;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.w3c.dom.Element;

import java.util.Objects;

/**
 * Model for personal data of the worker.
 *
 * @author AlanTheKnight
 */
public class Person extends Model {
    /**
     * Height of the person (not null, greater than 0).
     */
    public final Field<Double> height = new Field<>("height", "Рост", 0.0, true, value -> {
        if (value <= 0) {
            return new ImmutablePair<>(false, "Поле person.height должно быть больше 0");
        }
        return new ImmutablePair<>(true, null);
    });

    /**
     * Weight of the person (not null, greater than 0).
     */
    public final Field<Long> weight = new Field<>("weight", "Вес", 0L, true, value -> {
        if (value <= 0) {
            return new ImmutablePair<>(false, "Поле person.weight должно быть больше 0");
        }
        return new ImmutablePair<>(true, null);
    });

    /**
     * Hair color of the person (can be null).
     */
    public final Field<Color> hairColor = new Field<>("hairColor", "Цвет волос", null, false);

    /**
     * Nationality of the person (can be null).
     */
    public final Field<Country> nationality = new Field<>("nationality", "Национальность", null, false);

    /**
     * Creates a new person.
     *
     * @param height      height
     * @param weight      weight
     * @param hairColor   hair color
     * @param nationality nationality
     */
    public Person(double height, long weight, Color hairColor, Country nationality) {
        super();
        this.height.setValue(height);
        this.weight.setValue(weight);
        this.hairColor.setValue(hairColor);
        this.nationality.setValue(nationality);
    }

    /**
     * Creates a new Person object from the XML element.
     *
     * @param element XML element
     * @return new Person object
     * @throws ElementConversionException if the element is invalid
     */
    public static Person fromElement(Element element) throws ElementConversionException {
        try {
            return new Person(
                    ElementConvertor.doubleConvertor.fromElement(element.getElementsByTagName("height").item(0)),
                    ElementConvertor.longConvertor.fromElement(element.getElementsByTagName("weight").item(0)),
                    ElementConvertor.enumConvertor(Color.class).fromElement(element.getElementsByTagName("hairColor").item(0)),
                    ElementConvertor.enumConvertor(Country.class).fromElement(element.getElementsByTagName("nationality").item(0))
            );
        } catch (NullPointerException | IllegalArgumentException e) {
            throw new ElementConversionException(e.getMessage());
        }
    }

    @Override
    public boolean validate() {
        return !(height.getValue() <= 0) && weight.getValue() > 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(height, weight, hairColor, nationality);
    }
}
