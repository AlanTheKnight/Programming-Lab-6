package alantheknight.lab6.common.commands;

public enum CommandType {
    INSERT(),
    EXECUTE_SCRIPT(), SHOW(), HELP();

    private final String commandName;

    CommandType(String commandName) {
        this.commandName = commandName;
    }

    CommandType() {
        this.commandName = this.name().toLowerCase();
    }

    /**
     * Get the command type by its name.
     *
     * @param name the name of the command
     * @return the command type
     * @throws IllegalArgumentException if the command type is not found
     */
    public static CommandType fromString(String name) {
        for (CommandType type : CommandType.values()) {
            if (type.getCommandName().equalsIgnoreCase(name))
                return type;
        }
        throw new IllegalArgumentException("No such command type: " + name);
    }

    public String getCommandName() {
        return commandName;
    }
}
