package alantheknight.lab6.common.utils;

public class NumberConvertor {
    public static <T extends Number> T convertNumber(Class<T> numberClass, String input) {
        T number;
        if (numberClass == Integer.class) {
            number = numberClass.cast(Integer.parseInt(input));
        } else if (numberClass == Long.class) {
            number = numberClass.cast(Long.parseLong(input));
        } else if (numberClass == Float.class) {
            number = numberClass.cast(Float.parseFloat(input));
        } else if (numberClass == Double.class) {
            number = numberClass.cast(Double.parseDouble(input));
        } else {
            throw new IllegalArgumentException("Unsupported number class: " + numberClass);
        }
        return number;
    }
}
