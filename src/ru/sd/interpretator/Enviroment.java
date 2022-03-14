package ru.sd.interpretator;

import java.io.InputStream;
import java.io.OutputStream;
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
    public OutputStream call(String name, String args[], InputStream is) {
        return manager.get(name).run(args, is, this);
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
