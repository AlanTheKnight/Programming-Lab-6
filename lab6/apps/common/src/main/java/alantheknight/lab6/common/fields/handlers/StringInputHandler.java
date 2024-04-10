package alantheknight.lab6.common.fields.handlers;

import alantheknight.lab6.common.fields.Field;
import alantheknight.lab6.common.utils.Console;


/**
 * Input for numeric values.
 */
public final class StringInputHandler {
    public static void input(Console console, Field<String> field)
            throws InputHandler.InputException {
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

            var isValid = field.isValid(input);

            if (isValid.getLeft()) {
                field.setValue(input);
                break;
            } else {
                if (!console.isInteractive()) {
                    throw new InputHandler.InputException(isValid.getRight());
                } else {
                    console.printError(isValid.getRight());
                }
            }
        }
    }
}
