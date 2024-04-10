package alantheknight.lab6.client.commands;

import alantheknight.lab6.client.CommandRunner;
import alantheknight.lab6.common.commands.CommandType;

import static alantheknight.lab6.client.Main.commandRunner;
import static alantheknight.lab6.client.Main.stdConsole;

public class ExecuteScript extends ClientCommand<Void> {
    public ExecuteScript() {
        super(CommandType.EXECUTE_SCRIPT, "Считать и исполнить скрипт из указанного файла", "execute_script <filename>");
    }

    @Override
    public boolean apply(String[] arguments) {
        stdConsole.printSuccess("Выполнение скрипта " + arguments[1] + "...");
        CommandRunner.ExitCode status = commandRunner.executeScript(arguments[1]);
        return status == CommandRunner.ExitCode.OK;
    }
}
