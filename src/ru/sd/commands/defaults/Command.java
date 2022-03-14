package ru.sd.commands.defaults;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import ru.sd.interpretator.Enviroment;

public abstract class Command {
    protected Scanner jin;
    protected PrintStream jout;
    
    protected abstract void go(String[] args, Enviroment env);
    public OutputStream run(String[] args, InputStream stdin, Enviroment env) {
        jin = new Scanner(stdin);
        OutputStream os = new ByteArrayOutputStream();
        jout = new PrintStream(os);
        go(args, env);
        return os;
    }
}
