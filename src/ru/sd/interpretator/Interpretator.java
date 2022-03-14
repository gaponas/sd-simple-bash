package ru.sd.interpretator;

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
        Parser p = new Parser(cmnd);
        var ast = p.parse();
        if(ast instanceof BadExpression) {
            return;
        }
        ast.run(System.in, System.out, env);
    }
}
