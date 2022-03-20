package ru.sd.commands.defaults;

import ru.sd.interpretator.Enviroment;

/**
 * Echo command
 */
public class Echo extends Command {
    protected void go(String args[], Enviroment env) {
        String res = "";
        for(var a : args) {
            res += a + " ";
        }
        jout.println(res.trim());
    }
    public Command clone() {
        return new Echo();
    }
}
