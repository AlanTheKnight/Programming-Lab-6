package alantheknight.lab6.client.commands;

import alantheknight.lab6.common.commands.CommandType;

public class Update extends Insert {
    public Update() {
        super(CommandType.UPDATE, "Обновить значение элемента коллекции по ID",
                "update <id>");
    }
}
