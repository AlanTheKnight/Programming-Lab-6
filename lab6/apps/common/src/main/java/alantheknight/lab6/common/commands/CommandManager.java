package alantheknight.lab6.common.commands;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The CommandManager class is responsible for managing the commands.
 */
public class CommandManager<T extends BaseCommand> {
    /**
     * The commands map.
     */
    private final Map<CommandType, T> commands = new LinkedHashMap<>();
    /**
     * The history of commands.
     */
    private final ArrayList<CommandType> history = new ArrayList<>();

    /**
     * Register a command.
     *
     * @param command the command to register
     */
    public void register(T command) {
        commands.put(command.getCommandType(), command);
    }

    /**
     * Get commands map.
     *
     * @return the commands map
     */
    public Map<CommandType, T> getCommands() {
        return commands;
    }

    /**
     * Get commands history.
     *
     * @return the history as a list of strings
     */
    public ArrayList<String> getHistory() {
        return history.stream().map(Enum::name).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    /**
     * Add command to history.
     *
     * @param commandType the command to add
     */
    public void addToHistory(CommandType commandType) {
        history.add(commandType);
    }

    /**
     * Get command by its name
     *
     * @param command the type of the command
     * @return the command
     */
    public T getCommand(CommandType command) throws CommandNotFoundException {
        var res = commands.get(command);
        if (res == null) {
            throw new CommandNotFoundException("Команда " + command + " не зарегистрирована в менеджере команд");
        }
        return res;
    }

    public static class CommandNotFoundException extends IllegalArgumentException {
        public CommandNotFoundException(String message) {
            super(message);
        }
    }
}
