package alantheknight.lab6.client.commands;

import alantheknight.lab6.client.UDPClient;
import alantheknight.lab6.common.managers.CommandManager;

public class ClientCommandManager extends CommandManager<ClientCommand> {
    private final UDPClient client;

    public ClientCommandManager() {
        super();
        client = new UDPClient();
    }

    public UDPClient getClient() {
        return client;
    }
}
