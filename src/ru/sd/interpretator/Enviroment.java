package ru.sd.interpretator;

import java.util.HashMap;

import ru.sd.commands.CommandManager;

public class Enviroment {
    private HashMap<String, String> env;
    private CommandManager manager;

    public Enviroment() { 
        env = new HashMap<String, String>();
        manager = null; 
    }
    public Enviroment(CommandManager mngr) { 
        env = new HashMap<String, String>();
        manager = mngr; 
    }
    public String call(String name, String args[]) {
        return manager.get(name).run(args, "", this);
    }

    public void add(String key, String value) {
        env.put(key, value);
    }
    public String get(String key) {
        return env.get(key);
    }
    public Enviroment clone() {
        var tmp = new Enviroment(manager);
        tmp.env = new HashMap<String, String>(env);
        return tmp;
    }
}
