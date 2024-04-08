package alantheknight.lab6.common.models;

import alantheknight.lab6.common.fields.Field;
import alantheknight.lab6.common.fields.Model;
import alantheknight.lab6.common.utils.ElementConversionException;
import alantheknight.lab6.common.utils.Validatable;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.w3c.dom.Element;


/**
 * Worker's coordinates model.
 */
public class Coordinates extends Model {
    /**
     * X coordinate (not null).
     */
    public final Field<Integer> x = new Field<>("x", "Координата x", 0, true);

    /**
     * Y coordinate (not null, greater than -154).
     */
    public final Field<Float> y = new Field<>("y", "Координата y", 0F, true, value -> {
        if (value <= -154) {
            return new ImmutablePair<>(false, "Поле coordinates.y должно быть больше -154");
        }
        return new ImmutablePair<>(true, null);
    });

    /**
     * Creates a new Coordinates object.
     *
     * @param x x
     * @param y y
     */
    public Coordinates(int x, Float y) {
        super();
        this.x.setValue(x);
        this.y.setValue(y);
    }

    /**
     * Creates a new Coordinates object from the given XML element.
     *
     * @param element XML element
     * @return new Coordinates object
     * @throws ElementConversionException if the element is invalid
     */
    public static Coordinates fromElement(Element element) throws ElementConversionException {
        try {
            return new Coordinates(
                    Integer.parseInt(element.getElementsByTagName("x").item(0).getTextContent()),
                    Float.parseFloat(element.getElementsByTagName("y").item(0).getTextContent())
            );
        } catch (NullPointerException e) {
            throw new ElementConversionException(e.getMessage());
        }
    }

    @Override
    public boolean validate() {
        return y.getValue() != null && y.getValue() > -154;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Coordinates that = (Coordinates) obj;
        return x.equals(that.x) && y.equals(that.y);
    }

    @Override
    public int hashCode() {
        return x.getValue().hashCode() + y.getValue().hashCode();
    }
}
