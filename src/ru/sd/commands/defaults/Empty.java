package ru.sd.commands.defaults;

import ru.sd.interpretator.Enviroment;

public class Empty implements Command {
    public String run(String [] args, String stdin, Enviroment env) {
        return "Command not found\n";
    }
}
