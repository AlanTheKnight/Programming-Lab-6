package alantheknight.lab6.client.commands;

import alantheknight.lab6.common.commands.CommandType;

import static alantheknight.lab6.client.Main.stdConsole;

public class Exit extends ClientCommand<Void> {
    public Exit() {
        super(CommandType.EXIT, "Завершить работу клиента", "exit");
    }

    @Override
    public boolean apply(String[] arguments) {
        stdConsole.printSuccess("Завершение работы клиента...");
        System.exit(0);
        return true;
    }
}
