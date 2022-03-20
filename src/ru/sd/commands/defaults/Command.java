package ru.sd.commands.defaults;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import ru.sd.interpretator.Enviroment;

/**
 * Abstruct class for bash commands
 */
public abstract class Command {
    protected Scanner jin;
    protected PrintStream jout;
    InputStream inputStream;
    
    public abstract Command clone();
    /**
     * Command action, main buisness logic
     * @param args command arguments
     * @param env enviroment
     */
    protected abstract void go(String[] args, Enviroment env);
    public OutputStream run(String[] args, Enviroment env, InputStream in, OutputStream os) {
        //TODO: stderr ...
        inputStream = in;
        jin = new Scanner(in);
        jout = new PrintStream(os);
        go(args, env);
        return jout;
    }
}
