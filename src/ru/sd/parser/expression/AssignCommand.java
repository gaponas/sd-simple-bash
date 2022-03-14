package ru.sd.parser.expression;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import ru.sd.interpretator.Enviroment;

public class AssignCommand implements Expression {
    private List<Expression> assignments;

    public AssignCommand(List<Expression> assigns) {
        assignments = assigns;
    }
    public void run(InputStream in, OutputStream out, Enviroment env) {
        for(var a : assignments) {
            a.run(in, out, env);
        }
    }
    public void print() {
        System.out.println("Inner command");
    }
}
