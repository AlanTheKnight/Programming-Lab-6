package alantheknight.lab6.server;

import alantheknight.lab6.common.managers.CollectionManager;
import alantheknight.lab6.common.managers.CommandManager;
import alantheknight.lab6.common.managers.DumpManager;
import alantheknight.lab6.common.utils.ConfigReader;
import alantheknight.lab6.server.commands.Insert;
import alantheknight.lab6.server.commands.ServerCommand;
import alantheknight.lab6.server.commands.Show;

import java.util.List;

public class Main {
    private static final String dataFilepath = ConfigReader.getConfig().dataPath();

    public static void main(String[] args) {
        DumpManager dumpManager = new DumpManager(dataFilepath);

        CollectionManager collectionManager = new CollectionManager(dumpManager);
        try {
            collectionManager.loadCollection();
        } catch (DumpManager.DocumentReadException e) {
            throw new RuntimeException(e);
        }

        CommandManager<ServerCommand> commandManager = new CommandManager<>();

        var commands = List.of(
                Insert.class,
                Show.class
        );

        ServerCommand.bulkRegister(commands, commandManager, collectionManager);

        UDPServer server = new UDPServer(commandManager);
        server.start();
    }
}