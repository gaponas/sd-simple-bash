package ru.sd.commands.defaults;

import ru.sd.interpretator.Enviroment;

public class Echo extends Command {
    protected void go(String args[], Enviroment env) {
        for(var a : args) {
            jout.print(a);
            jout.print(" ");
        }
        jout.println();
    }
}
