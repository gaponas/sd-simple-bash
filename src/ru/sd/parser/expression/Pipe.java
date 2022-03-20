package ru.sd.parser.expression;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import ru.sd.interpretator.Enviroment;

public class Pipe implements Expression {

    private Expression first;
    private Expression second;

    /**
     * Creates two process,that piped one to another 
     * -> first -> second -> 
     * @param fst first command
     * @param snd second command
     */
    public Pipe(Expression fst, Expression snd) {
        first = fst;
        second = snd;
    }

    public void run(InputStream in, OutputStream out, Enviroment env) {
        OutputStream mem = new ByteArrayOutputStream();
        first.run(in, mem, env);
        second.run(new ByteArrayInputStream(mem.toString().getBytes()), out, env);
    }
    public String print() {
        return "Pipe(" + first.print() + ", " + second.print() + ")";
    }

}
