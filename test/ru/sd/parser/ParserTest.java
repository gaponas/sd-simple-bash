package ru.sd.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ParserTest {
    
    @Test
    public void canParseSimpleCommand() {
        String input = "echo 'smth'";
        Parser p = new Parser(input);

        Assertions.assertEquals("Command(String[chunk](echo), String[sstring]('smth'))", p.print());
    }

    @Test
    public void canParseAssignments() {
        
        Parser p;
        String expected;

        p = new Parser("A=10");
        expected = "Assignment(A, String[chunk](10))";
        Assertions.assertEquals(expected, p.print());

        p = new Parser("A=10 B=20");
        expected = "Assignment(A, String[chunk](10)) Assignment(B, String[chunk](20))";
        Assertions.assertEquals(expected, p.print());

        p = new Parser("A='smth'");
        expected = "Assignment(A, String[sstring]('smth'))";
        Assertions.assertEquals(expected, p.print());

        p = new Parser("A=sm'th' B=smt\"h else\"");
        expected = "Assignment(A, String[sstring](sm'th')) Assignment(B, String[wstring](smt\"h else\"))";
        Assertions.assertEquals(expected, p.print());
    }

    @Test
    public void canParseVarible() {
        
        Parser p;
        String expected;

        p = new Parser("$A");
        expected = "Command(String[chunk](%s, Var(A)))";
        Assertions.assertEquals(expected, p.print());

        p = new Parser("\"$A abc\"");
        expected = "Command(String[wstring](\"%s abc\", Var(A)))";
        Assertions.assertEquals(expected, p.print());

        p = new Parser("'$A abc'");
        expected = "Command(String[sstring]('$A abc'))";
        Assertions.assertEquals(expected, p.print());

    }

    @Test
    public void canParsePipe() {
        
        Parser p;
        String expected;

        p = new Parser("A='smth' cmnd -s -d 'str' | B='smth else' cmnd -s -p");
        expected = "Pipe(Assignment(A, String[sstring]('smth')) Command(String[chunk](cmnd), String[chunk](-s), String[chunk](-d), String[sstring]('str')), Assignment(B, String[sstring]('smth else')) Command(String[chunk](cmnd), String[chunk](-s), String[chunk](-p)))";
        Assertions.assertEquals(expected, p.print());

        p = new Parser("echo \"smth\" | make -d -s -e | d");
        expected = "Pipe(Pipe(Command(String[chunk](echo), String[wstring](\"smth\")), Command(String[chunk](make), String[chunk](-d), String[chunk](-s), String[chunk](-e))), Command(String[chunk](d)))";
        Assertions.assertEquals(expected, p.print());
        
    }

    @Test
    public void canParseWeekQs() {
        
        Parser p;
        String expected;

        p = new Parser("echo \"$A $(pwd | grep home)\"");
        expected = "Command(String[chunk](echo), String[wstring](\"%s %s\", Var(A), Pipe(Command(String[chunk](pwd)), Command(String[chunk](grep), String[chunk](home)))))";
        Assertions.assertEquals(expected, p.print());
        
    }

}
