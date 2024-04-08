package alantheknight.lab6.common.commands;

public abstract class BaseCommand {
    private final CommandType commandType;

    public BaseCommand(CommandType commandType) {
        this.commandType = commandType;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public String getName() {
        return commandType.name();
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
