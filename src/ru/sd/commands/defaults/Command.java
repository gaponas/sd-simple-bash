package ru.sd.commands.defaults;

import ru.sd.interpretator.Enviroment;

public interface Command {
    String run(String[] args, String stdin, Enviroment env);
}
