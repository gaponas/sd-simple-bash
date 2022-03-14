package ru.sd.parser.expression;

import ru.sd.interpretator.Enviroment;

public interface Expression {
    String run(String stdin, Enviroment env);
    void print();
}
