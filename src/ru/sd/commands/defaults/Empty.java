package ru.sd.commands.defaults;

import ru.sd.interpretator.Enviroment;

/**
 * 404 command
 */
public class Empty extends Command {
    protected void go(String [] args, Enviroment env) {
        jout.println("Command not found....");
    }
    public Command clone() {
        return new Empty();
    }
}
