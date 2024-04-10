package alantheknight.lab6.client.commands;

import alantheknight.lab6.common.commands.CommandType;

public class PrintAscending extends Show {
    public PrintAscending() {
        super(CommandType.PRINT_ASCENDING, "Вывести элементы коллекции в порядке возрастания",
                "print_ascending");
    }
}
