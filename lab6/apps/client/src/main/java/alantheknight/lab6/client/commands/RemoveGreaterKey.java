package alantheknight.lab6.client.commands;

import alantheknight.lab6.common.commands.CommandType;

public class RemoveGreaterKey extends RemoveKey {
    public RemoveGreaterKey() {
        super(CommandType.REMOVE_GREATER_KEY, "Удалить из коллекции все элементы, ключ которых превышает заданный",
                "remove_greater_key <value>");
    }
}
