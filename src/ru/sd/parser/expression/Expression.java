package ru.sd.parser.expression;

import java.io.InputStream;
import java.io.OutputStream;

import ru.sd.interpretator.Enviroment;

public interface Expression {
    /**
     * Unified method for exec node
     * @param in Input stream
     * @param out Output stream
     * @param env Enviroment
     */
    void run(InputStream in, OutputStream out, Enviroment env);
    String print();
}
