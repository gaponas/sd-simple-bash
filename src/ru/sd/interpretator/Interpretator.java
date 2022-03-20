package ru.sd.interpretator;

import java.io.InputStream;
import java.io.OutputStream;

import ru.sd.commands.CommandManager;
import ru.sd.parser.Parser;
import ru.sd.parser.expression.BadExpression;

/**
 * Runs command from String
 */
public class Interpretator {
    private CommandManager manager;
    private Enviroment env;

    /**
     * Creates Interpretator from command set
     * @param mngr Command set
     */
    public Interpretator(CommandManager mngr) {
        manager = mngr;
        env = new Enviroment(manager);
    }

    /**
     * Handles string with commands
     * @param cmnd String with commands
     */
    public void go(String cmnd) {
        go_inner(cmnd, System.in, System.out);
    }

    /**
     * Handles string with commands
     * @param cmnd String with command
     * @param is InputStream for commands
     * @param os OutputStream for commands
     */
    public void go(String cmnd, InputStream is, OutputStream os) {
        go_inner(cmnd, is, os);
    }

    private void go_inner(String cmnd, InputStream is, OutputStream os) {
        Parser p = new Parser(cmnd);
        var ast = p.parse();
        if(ast instanceof BadExpression) {
            return;
        }
        ast.run(is, os, env);
    }

}
