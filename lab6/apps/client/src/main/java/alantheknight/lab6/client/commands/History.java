package alantheknight.lab6.client.commands;

import alantheknight.lab6.common.commands.CommandType;
import alantheknight.lab6.common.utils.ConsoleColors;

import java.util.ArrayList;

import static alantheknight.lab6.client.Main.commandManager;
import static alantheknight.lab6.client.Main.stdConsole;

public class History extends ClientCommand<Void> {
    public History() {
        super(CommandType.HISTORY, "Вывести историю команд", "history");
    }

    @Override
    public boolean apply(String[] arguments) {
        ArrayList<String> history = commandManager.getHistory();
        stdConsole.printInColor(ConsoleColors.BLUE, "История команд:");

        for (int i = history.size() - 1; i >= Math.max(0, history.size() - 13); i--) {
            stdConsole.println(history.get(i));
        }

        return true;
    }
}
