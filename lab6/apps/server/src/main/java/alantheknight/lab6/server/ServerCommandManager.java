package alantheknight.lab6.server;

import alantheknight.lab6.common.commands.CommandManager;
import alantheknight.lab6.common.commands.CommandType;
import alantheknight.lab6.server.commands.*;
import alantheknight.lab6.server.managers.CollectionManager;

import java.util.List;

public class ServerCommandManager extends CommandManager<ServerCommand> {
    public ServerCommandManager() {
        super();
    }

    public void save() {
        var saveCmd = getCommand(CommandType.SAVE);
        if (saveCmd != null) {
            saveCmd.apply(null);
        }
    }

    public void initCommands(CollectionManager collectionManager) {
        var commands = List.of(
                Clear.class,
                FilterByEndDate.class,
                Info.class,
                Insert.class,
                RemoveAnyByEndDate.class,
                RemoveGreater.class,
                RemoveGreaterKey.class,
                RemoveKey.class,
                Save.class,
                Show.class,
                Update.class
        );

        ServerCommand.bulkRegister(commands);
    }
}
