package alantheknight.lab6.server;

import alantheknight.lab6.common.utils.ConfigReader;
import alantheknight.lab6.server.managers.CollectionManager;
import alantheknight.lab6.server.managers.DumpManager;

public class Main {
    public static final ServerCommandManager commandManager = new ServerCommandManager();

    private static final String dataFilepath = ConfigReader.getConfig().dataPath();

    private static final DumpManager dumpManager = new DumpManager(dataFilepath);

    public static final CollectionManager collectionManager = new CollectionManager(dumpManager);

    public static void main(String[] args) {
        try {
            collectionManager.loadCollection();
        } catch (DumpManager.DocumentReadException e) {
            throw new RuntimeException(e);
        }

        commandManager.initCommands(collectionManager);

        UDPServer server = new UDPServer();
        server.start();
    }
}