package ru.sd.parser.expression;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import ru.sd.interpretator.Enviroment;

public class Assignment implements Expression {
    private String name;
    private Expression val;
    /**
     * Single assignment
     * @param _var varible name
     * @param _val varible value in terms of expressions
     */
    public Assignment(String _var, Expression _val) {
        name = _var;
        val = _val;
    }
    public void run(InputStream in, OutputStream out, Enviroment env) {
        out = new ByteArrayOutputStream();
        val.run(in, out, env);
        env.add(name, out.toString());
    }
    public String print() {
        return "Assignment(" + name + ", " + val.print() + ")";
    }
}
