package alantheknight.lab6.client;

import alantheknight.lab6.client.commands.*;
import alantheknight.lab6.common.utils.ConfigReader;
import alantheknight.lab6.common.utils.Console;
import alantheknight.lab6.common.utils.StandardConsole;

import java.util.List;

public class Main {
    public static final Console stdConsole = new StandardConsole();
    public static final ClientCommandManager commandManager = new ClientCommandManager();

    public static final CommandRunner commandRunner = new CommandRunner();

    public static void main(String[] args) {
        var commands = List.of(
                Insert.class,
                Show.class,
                Help.class,
                PrintAscending.class,
                Update.class,
                History.class,
                RemoveAnyByEndDate.class,
                RemoveKey.class,
                RemoveGreaterKey.class,
                FilterByEndDate.class,
                ExecuteScript.class,
                Exit.class,
                Info.class
        );

        ClientCommand.bulkRegister(commands);

        commandRunner.setMaxRecursionLevel(ConfigReader.getConfig().clientConfig().maxRecursionLevel());
        commandRunner.run();
    }
}
