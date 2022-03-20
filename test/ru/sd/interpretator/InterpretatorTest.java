package ru.sd.interpretator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ru.sd.commands.CommandManager;

public class InterpretatorTest {

    private class Pair {
        public String key;
        public String val;
        public Pair(String k, String v) {
            key = k;
            val = v;
        }
    }
    
    @Test
    public void canRunSimpleCommand() {
        Assertions.assertEquals("a\n", interpret("echo 'a'"));
    }

    @Test
    public void canRunPipeline() {
        Assertions.assertEquals("b\n", interpret("echo 'a' | echo 'b'"));
    }

    @Test
    public void canRunWithEnvVaribles() {
        Pair[] cmnds = new Pair[] {
            new Pair("A=15", ""),
            new Pair("echo \"A is $A\"", "")
        };
        Assertions.assertEquals("A is 15\n", interpret(cmnds));
    }

    //TODO: more tests

    private String interpret(String cmnd) {
        Interpretator i = new Interpretator(new CommandManager());
        OutputStream mem = new ByteArrayOutputStream();
        i.go(cmnd, System.in, mem);
        return mem.toString();
    }

    private String interpret(String cmnd, String stdin) {
        Interpretator i = new Interpretator(new CommandManager());
        OutputStream mem = new ByteArrayOutputStream();
        InputStream in = new ByteArrayInputStream(stdin.getBytes());
        i.go(cmnd, in, mem);
        return mem.toString();
    }

    private String interpret(Pair[] cmnds) {
        Interpretator i = new Interpretator(new CommandManager());
        String res = "";
        for(var p : cmnds) {
            OutputStream mem = new ByteArrayOutputStream();
            InputStream in = new ByteArrayInputStream(p.val.getBytes());
            i.go(p.key, in, mem);
            res += mem.toString();
        }
        return res;
    }

}
