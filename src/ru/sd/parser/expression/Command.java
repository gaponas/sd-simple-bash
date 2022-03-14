package ru.sd.parser.expression;
import java.util.LinkedList;
import java.util.List;

import ru.sd.interpretator.Enviroment;

public class Command implements Expression {

    private Expression command;
    private List<Expression> arguments;
    private List<Expression> assignments;

    public Command(Expression cmnd, List<Expression> args, List<Expression> assign) {
        command = cmnd;
        arguments = args;
        assignments = assign;
    }
    public String run(String stdin, Enviroment env) {
        Enviroment local = env.clone();
        for(var a : assignments) {
            a.run(stdin, local);
        }
        List<String> args = new LinkedList<String>();
        for(var a : arguments) {
            args.add(a.run(stdin, local));
        }
        return local.call(command.run(stdin, local), args.toArray(new String[0]));
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
