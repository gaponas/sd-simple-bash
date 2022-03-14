package ru.sd.commands.defaults;

import ru.sd.interpretator.Enviroment;

public class Pwd implements Command {
    public String run(String args[], String run, Enviroment env) {
        return System.getProperty("user.dir") + '\n';
    }
}
