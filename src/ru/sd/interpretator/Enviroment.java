package ru.sd.interpretator;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import ru.sd.commands.CommandManager;
import ru.sd.commands.defaults.External;

/**
 * Enviroment wrapper
 */
public class Enviroment {
    private HashMap<String, String> env;
    private CommandManager manager;

    /**
     * Init enviroment with no command set
     */
    public Enviroment() { 
        env = new HashMap<String, String>();
        manager = null; 
    }

    /**
     * Uses command set
     * @param mngr Command set
     */
    public Enviroment(CommandManager mngr) { 
        env = new HashMap<String, String>();
        manager = mngr; 
    }

    /**
     * Calls command by name 
     * @param name Command name
     * @param args Command arguments
     * @param is Input stream
     * @param os Output stream
     * @return Output stream
     */
    public OutputStream call(String name, String args[], InputStream is, OutputStream os) {
        var proc = manager.get(name);
        if(proc instanceof External)
            return proc.run(new String[] { name + " " + String.join(" ", args) }, this, is, os);
        return proc.run(args, this, is, os);
    }

    /**
     * Creates new enviroment varible
     * @param key varible name
     * @param value varible value
     */
    public void add(String key, String value) {
        env.put(key, value);
    }

    /**
     * Gets enviroment varible
     * @param key Varible name
     * @return Varible value
     */
    public String get(String key) {
        return env.get(key);
    }

    /**
     * Creates enviroment copy
     */
    public Enviroment clone() {
        var tmp = new Enviroment(manager);
        tmp.env = new HashMap<String, String>(env);
        return tmp;
    }
}
