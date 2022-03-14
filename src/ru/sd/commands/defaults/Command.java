package ru.sd.commands.defaults;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import ru.sd.interpretator.Enviroment;

public abstract class Command {
    protected Scanner jin;
    protected PrintStream jout;
    
    protected abstract void go(String[] args, Enviroment env);
    public OutputStream run(String[] args, Enviroment env, InputStream in, OutputStream os) {
        jin = new Scanner(in);
        // OutputStream os = new ByteArrayOutputStream();
        jout = new PrintStream(os);
        go(args, env);
        return os;
    }
}
