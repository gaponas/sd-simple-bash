package ru.sd.parser.expression;

import java.io.InputStream;

import ru.sd.interpretator.Enviroment;

public class Assignment implements Expression {
    private String name;
    private Expression val;
    public Assignment(String _var, Expression _val) {
        name = _var;
        val = _val;
    }
    public String run(InputStream stdin, Enviroment env) {
        env.add(name, val.run(stdin, env));
        return "";
    }
    public void print() {
        System.out.println("=========");
        System.out.println("Assignment");
        System.out.println(name);
        System.out.println("<-");
        val.print();
        System.out.println("_________");
    }
}
