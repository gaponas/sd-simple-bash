package ru.sd.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ParserTest {
    
    @Test
    public void CanParseSimpleCommand() {
        String input = "echo 'smth'";
        Parser p = new Parser(input);

        Assertions.assertEquals(3, 3);
        //TODO: finish test
    }

    //TODO: more tests

}
