package ru.sd.parser.expression;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import ru.sd.interpretator.Enviroment;

public class Command implements Expression {

    private Expression command;
    private List<Expression> arguments;
    private List<Expression> assignments;

    /**
     * Comand node, makes call to command set and executes command
     * @param cmnd command name
     * @param args command arguments
     * @param assign local eniroment varibles
     */
    public Command(Expression cmnd, List<Expression> args, List<Expression> assign) {
        command = cmnd;
        arguments = args;
        assignments = assign;
    }
    public void run(InputStream in, OutputStream out, Enviroment env) {
        Enviroment local = env.clone();
        for(var a : assignments) {
            a.run(in, out, local);
        }
        List<String> args = new LinkedList<String>();
        for(var a : arguments) {
            OutputStream os = new ByteArrayOutputStream();
            a.run(in, os, local);
            args.add(os.toString());
        }

        
        OutputStream cos = new ByteArrayOutputStream();
        command.run(in, cos, env);
        String cmnd = cos.toString();

        local.call(cmnd, args.toArray(new String[0]), in, out);
    }
    public void print() {
        System.out.println("=========");
        System.out.println("Command");
        for(var a : assignments) {
            a.print();
        }
        command.print();
        for(var a : arguments) {
            a.print();
        }
        System.out.println("_________");
    }
}
