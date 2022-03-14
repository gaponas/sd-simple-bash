package ru.sd.commands.defaults;

import ru.sd.interpretator.Enviroment;

public class Echo implements Command {
    public String run(String args[], String run, Enviroment env) {
        String res = "";
        for(var a : args) {
            res += a + " ";
        }

        return res + '\n';
    }
}
