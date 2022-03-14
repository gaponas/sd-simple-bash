package ru.sd.commands.defaults;

import ru.sd.interpretator.Enviroment;

/**
 * Command adds 2 integers
 */
public class Add extends Command {
    protected void go(String[] args, Enviroment env) {
        jout.println("Enter a");
        int a = jin.nextInt();
        jout.println("Enter b");
        int b = jin.nextInt();
        jout.println("Answer is " + (a + b));
    }
    public Command clone() {
        return new Add();
    }
}
