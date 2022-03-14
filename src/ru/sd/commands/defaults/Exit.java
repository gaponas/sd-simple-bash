package ru.sd.commands.defaults;

import ru.sd.interpretator.Enviroment;

public class Exit implements Command {
    public String run(String[] args, String stdin, Enviroment env) {
        System.exit(0);
        return "";
    }
}
