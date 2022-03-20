package ru.sd.commands;

import java.util.HashMap;
import ru.sd.commands.defaults.*;

/**
 * Collects all available commands
 */
public class CommandManager {
    
    private HashMap<String ,Command> storage;
    public CommandManager() {
        storage = new HashMap<String ,Command>();
        storage.put("echo", new Echo());
        storage.put("exit", new Exit());
        storage.put("pwd", new Pwd());
        storage.put("add", new Add());
    }
    /**
     * Returns command by name.
     * @param name Command name
     * @return Command
     */
    public Command get(String name) {
        var cmnd = storage.get(name);
        if(cmnd == null) {
            cmnd = new External();
        }
        return cmnd.clone();
    }
}
