package ru.sd.parser.expression;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import ru.sd.interpretator.Enviroment;

public class AssignCommand implements Expression {
    private List<Expression> assignments;

    /**
     * Ast node to do assignment enviroment varibles
     * @param assigns varibles
     */
    public AssignCommand(List<Expression> assigns) {
        assignments = assigns;
    }
    public void run(InputStream in, OutputStream out, Enviroment env) {
        for(var a : assignments) {
            a.run(in, out, env);
        }
    }
    public String print() {
        String str = "";
        for(var a : assignments) {
            str += a.print() + " ";
        }
        return str.trim();
    }
}
