package alantheknight.lab6.common.fields.handlers;

import alantheknight.lab6.common.fields.Field;
import alantheknight.lab6.common.utils.Console;

import java.time.DateTimeException;
import java.time.LocalDate;


/**
 * Input for numeric values.
 */
public final class LocalDateInputHandler {
    public static void input(Console console, Field<LocalDate> field)
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
                    return;
                }
            }

            if (!input.matches("\\d{4}-\\d{2}-\\d{2}")) {
                if (!console.isInteractive())
                    throw new InputHandler.InputException("Поле endDate должно быть в формате yyyy-mm-dd");
                else
                    console.printError("Поле endDate должно быть в формате yyyy-mm-dd");
                continue;
            }

            LocalDate newValue;

            try {
                newValue = LocalDate.parse(input);
            } catch (DateTimeException e) {
                if (!console.isInteractive())
                    throw new InputHandler.InputException("Поле endDate должно быть в формате yyyy-mm-dd");
                else
                    console.printError("Поле endDate должно быть в формате yyyy-mm-dd");
                continue;
            }

            var isValid = field.isValid(newValue);

            if (isValid.getLeft()) {
                field.setValue(newValue);
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
