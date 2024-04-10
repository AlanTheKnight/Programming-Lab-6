package alantheknight.lab6.common.commands;

/**
 * Base class for client and server commands.
 */
public abstract class BaseCommand {
    /**
     * The type of the command.
     */
    private final CommandType commandType;

    public BaseCommand(CommandType commandType) {
        this.commandType = commandType;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass())
            return false;
        BaseCommand other = (BaseCommand) obj;
        return getCommandType() == other.getCommandType();
    }

    @Override
    public int hashCode() {
        return getCommandType().hashCode();
    }
}
