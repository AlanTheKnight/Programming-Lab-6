package alantheknight.lab6.common.fields.handlers;

import alantheknight.lab6.common.fields.Field;
import alantheknight.lab6.common.utils.Console;

import static alantheknight.lab6.common.utils.NumberConvertor.convertNumber;


/**
 * Input for numeric values.
 */
public final class NumberInputHandler {
    /**
     * Asks the user for a numeric value.
     *
     * @param <T>     number type
     * @param console console
     * @throws InputHandler.InputException if input was unsuccessful, in file console mode
     */
    public static <T extends Number> void input(Console console, Field<T> field)
            throws InputHandler.InputException {
        var numberClass = field.getValueClass();

        while (true) {
            if (console.isInteractive())
                console.print("Введите " + field.getVerboseName() + ": ");

            String input = console.readLine(false).trim();

            if (input.isEmpty()) {
                if (field.isRequired()) {
                    if (!console.isInteractive()) {
                        throw new InputHandler.InputException("Поле " + field.getName() + " не может быть пустым");
                    } else {
                        console.printError("Поле " + field.getName() + " не может быть пустым");
                    }
                    continue;
                } else {
                    field.setValue(null);
                    break;
                }
            }

            try {
                T number = convertNumber(numberClass, input);
                var isValid = field.isValid(number);

                if (isValid.getLeft()) {
                    field.setValue(number);
                    break;
                } else {
                    if (!console.isInteractive()) {
                        throw new InputHandler.InputException(isValid.getRight());
                    } else {
                        console.printError(isValid.getRight());
                    }
                }

            } catch (NumberFormatException e) {
                if (!console.isInteractive()) {
                    throw new InputHandler.InputException("Значение должно быть числом типа " + numberClass.getSimpleName());
                } else {
                    console.printError("Значение должно быть числом типа " + numberClass.getSimpleName());
                }
            }
        }
    }

}
