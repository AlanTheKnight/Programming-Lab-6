package alantheknight.lab6.server;

import alantheknight.lab6.common.utils.ConfigReader;
import alantheknight.lab6.server.managers.CollectionManager;
import alantheknight.lab6.server.managers.DumpManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    public static final ServerCommandManager commandManager = new ServerCommandManager();
    public static final Logger logger = LogManager.getLogger(Main.class);
    private static final String dataFilepath = ConfigReader.getConfig().dataPath();
    private static final DumpManager dumpManager = new DumpManager(dataFilepath);
    public static final CollectionManager collectionManager = new CollectionManager(dumpManager);

    public static void main(String[] args) {
        try {
            collectionManager.loadCollection();
            logger.log(Level.INFO, "Collection has been loaded");
        } catch (DumpManager.DocumentReadException e) {
            logger.log(Level.ERROR, "Failed to read collection from file: " + e.getMessage());
            throw new RuntimeException(e);
        }

        commandManager.initCommands(collectionManager);
        logger.log(Level.INFO, "Commands have been initialized");

        UDPServer server = new UDPServer();
        server.start();
    }
}