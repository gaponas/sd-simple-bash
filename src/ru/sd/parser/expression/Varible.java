package ru.sd.parser.expression;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import ru.sd.interpretator.Enviroment;

public class Varible implements Expression {
    private String name;
    public Varible(String _name) {
        name = _name;
    }
    public void run(InputStream in, OutputStream out, Enviroment env) {
        String val = env.get(name);
        if(val == null) val = "";
        new PrintStream(out).print(val);
    }
    public void print() {
        System.out.println("=========");
        System.out.println("Varible");
        System.out.println(name);
        System.out.println("_________");
    }
}
