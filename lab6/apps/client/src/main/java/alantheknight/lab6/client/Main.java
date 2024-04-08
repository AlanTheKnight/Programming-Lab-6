package alantheknight.lab6.client;

import alantheknight.lab6.client.commands.*;
import alantheknight.lab6.common.utils.ConfigReader;
import alantheknight.lab6.common.utils.Console;
import alantheknight.lab6.common.utils.StandardConsole;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Console console = new StandardConsole();

        ClientCommandManager commandManager = new ClientCommandManager();

        var commands = List.of(
                Insert.class,
                Show.class,
                Help.class
        );

        ClientCommand.bulkRegister(commands, commandManager, console);

        CommandRunner commandRunner = new CommandRunner(console, commandManager);
        commandRunner.setMaxRecursionLevel(ConfigReader.getConfig().clientConfig().maxRecursionLevel());
        commandRunner.run();
    }
}
