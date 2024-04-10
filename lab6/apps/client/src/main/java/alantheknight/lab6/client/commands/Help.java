package alantheknight.lab6.client.commands;

import alantheknight.lab6.common.commands.CommandType;

import static alantheknight.lab6.client.Main.commandManager;
import static alantheknight.lab6.client.Main.stdConsole;

public class Help extends ClientCommand<Void> {
    public Help() {
        super(CommandType.HELP, "Показать список доступных команд",
                "help");
    }

    @Override
    public boolean apply(String[] arguments) {
        stdConsole.println("Список доступных команд:");
        commandManager.getCommands().values().forEach(command -> {
            stdConsole.printTwoColumns(command.getFormat(), command.getDescription(), "-", 40);
        });
        return true;
    }
}
