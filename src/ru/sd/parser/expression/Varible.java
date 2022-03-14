package ru.sd.parser.expression;

import java.io.InputStream;

import ru.sd.interpretator.Enviroment;

public class Varible implements Expression {
    private String name;
    public Varible(String _name) {
        name = _name;
    }
    public String run(InputStream stdin, Enviroment env) {
        String val = env.get(name);
        if(val == null) val = "";
        return val;
    }
    public void print() {
        System.out.println("=========");
        System.out.println("Varible");
        System.out.println(name);
        System.out.println("_________");
    }
}
