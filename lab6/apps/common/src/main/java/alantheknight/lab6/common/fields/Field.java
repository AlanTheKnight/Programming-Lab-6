package alantheknight.lab6.common.fields;

import alantheknight.lab6.common.fields.handlers.*;
import alantheknight.lab6.common.models.Coordinates;
import alantheknight.lab6.common.models.Person;
import alantheknight.lab6.common.utils.Console;
import alantheknight.lab6.common.utils.Convertible;
import alantheknight.lab6.common.utils.Validatable;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.Serializable;
import java.time.LocalDate;

public class Field<T> implements Convertible, Validatable, Serializable {
    String name;
    String verboseName;
    boolean askForValue = true;
    private T value;
    private FieldChecker<T> checker = new DefaultFieldChecker<>();

    private boolean isRequired = true;

    public Field(String name, String verboseName) {
        this.name = name;
        this.verboseName = verboseName;
    }


    public Field(String name, String verboseName, T value) {
        this(name, verboseName);
        this.value = value;
    }

    public Field(String name, String verboseName, T value, FieldChecker<T> checker) {
        this(name, verboseName, value);
        this.checker = checker;
    }

    public Field(String name, String verboseName, T value, boolean isRequired, FieldChecker<T> checker) {
        this(name, verboseName, value, checker);
        this.isRequired = isRequired;
    }

    public Field(String name, String verboseName, T value, boolean isRequired) {
        this(name, verboseName, value);
        this.isRequired = isRequired;
    }

    public Field(String name, String verboseName, T value, boolean isRequired, boolean askForValue) {
        this(name, verboseName, value, isRequired);
        this.askForValue = askForValue;
    }

    @SuppressWarnings("unchecked")
    public Class<T> getValueClass() {
        return (Class<T>) this.value.getClass();
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public ImmutablePair<Boolean, String> isValid() {
        if (isRequired && value == null) {
            return new ImmutablePair<>(false, "Поле " + name + " не может быть пустым");
        }
        return checker.isValid(value);
    }

    public ImmutablePair<Boolean, String> isValid(T value) {
        return checker.isValid(value);
    }

    public Element getElement(Document document) {
        Element element = document.createElement(name);
        element.setTextContent(value == null ? "null" : value.toString());
        return element;
    }

    @Override
    public String toString() {
        return "Field{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Field<?> field = (Field<?>) obj;
        return name.equals(field.name) && value.equals(field.value);
    }

    @Override
    public boolean validate() {
        return isValid().getLeft();
    }

    public boolean isRequired() {
        return isRequired;
    }

    public String getName() {
        return name;
    }

    public boolean isAskForValue() {
        return askForValue;
    }

    public void setAskForValue(boolean askForValue) {
        this.askForValue = askForValue;
    }

    public String getVerboseName() {
        return verboseName;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void input(Console console) throws InputHandler.InputException {
        if (value instanceof Enum) {
            EnumInputHandler.input(console, (Field<? extends Enum>) this);
            return;
        }
        if (value instanceof Number) {
            NumberInputHandler.input(console, (Field<? extends Number>) this);
            return;
        }
        if (value instanceof String) {
            StringInputHandler.input(console, (Field<String>) this);
            return;
        }
        if (value instanceof LocalDate) {
            LocalDateInputHandler.input(console, (Field<LocalDate>) this);
            return;
        }
        if (value instanceof Model) {
            if (value instanceof Coordinates) {
                InputHandler.input(console, (Coordinates) value, Coordinates.class);
                return;
            }

            if (value instanceof Person) {
                InputHandler.input(console, (Model) value, Person.class);
                return;
            }
        }

        throw new UnsupportedOperationException("Input for " + value.getClass() + " is not supported");
    }

    private static class DefaultFieldChecker<T> implements FieldChecker<T> {

        @Override
        public ImmutablePair<Boolean, String> isValid(T value) {
            return new ImmutablePair<>(true, null);
        }
    }
}
