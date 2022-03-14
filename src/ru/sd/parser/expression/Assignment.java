package ru.sd.parser.expression;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import ru.sd.interpretator.Enviroment;

public class Assignment implements Expression {
    private String name;
    private Expression val;
    public Assignment(String _var, Expression _val) {
        name = _var;
        val = _val;
    }
    public void run(InputStream in, OutputStream out, Enviroment env) {
        out = new ByteArrayOutputStream();
        val.run(in, out, env);
        env.add(name, out.toString());
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
