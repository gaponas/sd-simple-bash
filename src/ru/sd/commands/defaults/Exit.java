package ru.sd.commands.defaults;

import ru.sd.interpretator.Enviroment;

public class Exit extends Command {
    protected void go(String[] args, Enviroment env) {
        System.exit(0);
    }
}
