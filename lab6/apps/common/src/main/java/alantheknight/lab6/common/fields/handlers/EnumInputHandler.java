package alantheknight.lab6.common.fields.handlers;


import alantheknight.lab6.common.fields.Field;
import alantheknight.lab6.common.utils.Console;
import alantheknight.lab6.common.utils.EnumHelper;

/**
 * Input handler for enum values.
 */
public final class EnumInputHandler {
    /**
     * Asks the user for an enum value.
     *
     * @param <T>     enum type
     * @param console console
     * @throws EnumInputException if input was unsuccessful, in file console mode
     */
    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>> void input(Console console, Field<T> field)
            throws EnumInputException {
        var enumClass = field.getValueClass();
        while (true) {
            if (console.isInteractive())
                console.println("Выберите одно из значений: " + EnumHelper.enumToString(enumClass));
            String input = console.readLine(false).trim();
            if (input.isEmpty()) {
                if (field.isRequired()) {
                    console.printError("Поле " + field.getName() + " не может быть пустым");
                    if (!console.isInteractive())
                        throw new EnumInputException("Поле " + field.getName() + " не может быть пустым");
                    continue;
                } else {
                    field.setValue(null);
                    break;
                }
            }
            try {
                T enumValue = T.valueOf((Class<T>) field.getValue().getClass(), input);
                field.setValue(enumValue);
                break;
            } catch (IllegalArgumentException e) {
                String msg = "Значение поля " + field.getName() + " должно быть одним из: " + EnumHelper.enumToString(enumClass) + ". Полученное значение: " + input;
                if (!console.isInteractive())
                    throw new EnumInputException(msg);
                else
                    console.printError(msg);
            }
        }
    }

    /**
     * Exception for enum input handler, thrown when input is unsuccessful, in file
     * console mode.
     */
    public static class EnumInputException extends InputHandler.InputException {
        EnumInputException(String message) {
            super(message);
        }
    }
}
