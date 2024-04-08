package alantheknight.lab6.client.commands;

import alantheknight.lab6.common.commands.CommandType;
import alantheknight.lab6.common.utils.Console;

public class Help extends ClientCommand {
    public Help(Console console, ClientCommandManager commandManager) {
        super(CommandType.HELP, "Показать список доступных команд",
                "help", console, commandManager);
    }


    @Override
    public boolean apply(String[] arguments) {
        console.println("Список доступных команд:");
        assert commandManager != null;
        commandManager.getCommands().values().forEach(command -> {
            console.printTwoColumns(command.getFormat(), command.getDescription(), "-", 40);
        });
        return true;
    }
}
