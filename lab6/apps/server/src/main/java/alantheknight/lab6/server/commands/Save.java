package alantheknight.lab6.server.commands;

import alantheknight.lab6.common.commands.CommandType;
import alantheknight.lab6.common.network.Request;
import alantheknight.lab6.common.network.Response;
import alantheknight.lab6.server.managers.DumpManager;
import org.apache.logging.log4j.Level;

import static alantheknight.lab6.server.Main.collectionManager;
import static alantheknight.lab6.server.Main.logger;

/**
 * Command for clearing the collection.
 */
public class Save extends ServerCommand {
    public Save() {
        super(CommandType.SAVE);
    }

    @Override
    public boolean validateRequest(Request request) {
        return true;
    }

    @Override
    public Response<Void> apply(Request request) {
        try {
            collectionManager.saveCollection();
            logger.log(Level.INFO, "Collection saved to file");
        } catch (DumpManager.DocumentWriteException e) {
            logger.log(Level.ERROR, "Failed to write collection to file: " + e.getMessage());
            throw new RuntimeException("Ошибка при записи в файл: " + e.getMessage());
        }
        return null;
    }
}
