package ru.sd.commands.defaults;

import ru.sd.interpretator.Enviroment;

/**
 * Current working directory command
 */
public class Pwd extends Command {
    protected void go(String args[], Enviroment env) {
        jout.println(env.getCurrDirPath());
    }
    public Command clone() {
        return new Pwd();
    }
}
