package alantheknight.lab6.common.fields;

import alantheknight.lab6.common.utils.Convertible;
import alantheknight.lab6.common.utils.Validatable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public abstract class Model implements Convertible, Validatable, Serializable {
    protected final String elementName;

    public Model() {
        this.elementName = this.getClass().getSimpleName().toLowerCase();
    }

    public static <T extends Model> List<FieldInfo> getFieldClasses(Class<T> modelClass) {
        return getReflectFields(modelClass).stream()
                .map(field -> new FieldInfo(
                        field.getName(),
                        ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0],
                        field))
                .toList();
    }

    private static <T extends Model> List<java.lang.reflect.Field> getReflectFields(Class<T> modelClass) {
        return Arrays.stream(modelClass.getDeclaredFields())
                .filter(field -> Modifier.isPublic(field.getModifiers()) && field.getType().equals(Field.class) && field.getGenericType() instanceof ParameterizedType)
                .toList();
    }

    @Override
    public Element getElement(Document document) {
        Element currentElement = document.createElement(elementName);
        getFields().forEach(field -> {
            Element fieldElement = field.getElement(document);
            currentElement.appendChild(fieldElement);
        });
        return currentElement;
    }

    public List<? extends Field<?>> getFields() {
        return getReflectFields(this.getClass()).stream().map(field -> {
            try {
                return (Field<?>) field.get(this);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }

    @Override
    public boolean validate() {
        return getFields().stream().allMatch(Field::validate);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName()).append(" {");
        getFields().forEach(field -> sb.append(field.getName()).append("=").append(field.getValue()).append(", "));
        sb.delete(sb.length() - 2, sb.length());
        sb.append("}");
        return sb.toString();
    }

    public record FieldInfo(String name, Type type, java.lang.reflect.Field field) {
    }
}
