package ru.sd.parser.expression;

import java.io.InputStream;
import java.io.OutputStream;

import ru.sd.interpretator.Enviroment;

public interface Expression {
    void run(InputStream in, OutputStream out, Enviroment env);
    void print();
}
