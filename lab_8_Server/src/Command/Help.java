package Command;

import TCPServer.CollectionManager;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 *вывести справку по доступным командам.
 */
public class Help extends Command {
    private HashMap<String, Command> commands;

    public Help(CollectionManager manager, HashMap<String, Command> commands) {
        super(manager);
        setDescription("вывести справку по доступным командам.");
        this.commands = commands;
    }

    @Override
    public String execute(Object object) {
        return "execute_script: считать и исполнить скрипт из файла file. \n" +
                commands.entrySet().stream()
                        .filter(command -> command.getKey() != "show").map(command -> command.getKey() + ": " + command.getValue().getDescription())
                        .collect(Collectors.joining("\n"));
    }
}
