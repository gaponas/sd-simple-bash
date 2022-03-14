package ru.sd.commands.defaults;

import ru.sd.interpretator.Enviroment;

/**
 * Exit from bash command
 */
public class Exit extends Command {
    protected void go(String[] args, Enviroment env) {
        System.exit(0);
    }
    public Command clone() {
        return new Exit();
    }
}
