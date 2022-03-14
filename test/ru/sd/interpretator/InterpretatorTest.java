package ru.sd.interpretator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ru.sd.commands.CommandManager;

public class InterpretatorTest {
    
    @Test
    public void CanParseSimpleCommand() {
        String input = "echo 'smth'";
        Interpretator i = new Interpretator(new CommandManager());
        i.go(input);

        Assertions.assertEquals(3, 3);

        //TODO: check stdout
    }

    //TODO: more tests

}
