package alantheknight.lab6.server.commands;

import alantheknight.lab6.common.commands.CommandType;
import alantheknight.lab6.common.network.Request;
import alantheknight.lab6.common.network.Response;
import alantheknight.lab6.server.managers.DumpManager;

import static alantheknight.lab6.server.Main.collectionManager;

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
        } catch (DumpManager.DocumentWriteException e) {
            throw new RuntimeException("Ошибка при записи в файл: " + e.getMessage());
        }
        return null;
    }
}
