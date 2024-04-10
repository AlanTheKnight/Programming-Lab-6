package alantheknight.lab6.common.commands;

public enum CommandType {
    INSERT(), EXECUTE_SCRIPT(), SHOW(), HELP(), SAVE(), CLEAR(),
    EXIT(), FILTER_BY_END_DATE(), HISTORY(), INFO(), REMOVE_ANY_BY_END_DATE(),
    REMOVE_GREATER(), REMOVE_GREATER_KEY(), REMOVE_KEY(), UPDATE(), PRINT_ASCENDING;

    private final String commandName;

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
