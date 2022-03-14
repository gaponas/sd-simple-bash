package ru.sd.parser.expression;

import java.io.InputStream;

import ru.sd.interpretator.Enviroment;

public interface Expression {
    String run(InputStream stdin, Enviroment env);
    void print();
}
