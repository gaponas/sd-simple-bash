package ru.sd.parser.expression;
import java.util.List;

import ru.sd.interpretator.Enviroment;

public class AssignCommand implements Expression {
    private List<Expression> assignments;

    public AssignCommand(List<Expression> assigns) {
        assignments = assigns;
    }
    public String run(String stdin, Enviroment env) {
        for(var a : assignments) {
            a.run(stdin, env);
        }
        return "";
    }
    public void print() {
        System.out.println("Inner command");
    }
}
