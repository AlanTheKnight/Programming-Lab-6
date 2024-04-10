package alantheknight.lab6.common.fields.handlers;

import alantheknight.lab6.common.fields.Field;
import alantheknight.lab6.common.fields.Model;
import alantheknight.lab6.common.utils.Console;

import java.lang.reflect.Modifier;

public class InputHandler {
    public static <T extends Model> void input(Console console, T model, Class<? extends Model> modelClass) throws InputException {
        for (Model.FieldInfo field : Model.getFieldClasses(modelClass)) {
            field.field().setAccessible(true);

            assert field.field().getType().isAssignableFrom(Field.class);
            assert Modifier.isPublic(field.field().getModifiers());

            try {
                var fieldValue = (Field<?>) field.field().get(model);
                if (!fieldValue.isAskForValue()) return; // Skip fields that don't require input
                fieldValue.input(console);
            } catch (IllegalAccessException | InputException e) {
                throw new InputException(e.getMessage());
            }
        }
    }

    /**
     * Exception for input handler, thrown when input is unsuccessful.
     */
    public static class InputException extends Exception {
        InputException(String message) {
            super(message);
        }
    }
}
