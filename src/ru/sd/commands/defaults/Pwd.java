package ru.sd.commands.defaults;

import ru.sd.interpretator.Enviroment;

public class Pwd extends Command {
    protected void go(String args[], Enviroment env) {
        jout.println(System.getProperty("user.dir"));
    }
    public Command clone() {
        return new Pwd();
    }
}
