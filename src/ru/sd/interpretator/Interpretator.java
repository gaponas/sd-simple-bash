package ru.sd.interpretator;

import ru.sd.commands.CommandManager;
import ru.sd.parser.Parser;
import ru.sd.parser.expression.BadExpression;

public class Interpretator {
    private CommandManager manager;
    private Enviroment env;

    public Interpretator(CommandManager mngr) {
        manager = mngr;
        env = new Enviroment(manager);
    }

    public void go(String cmnd) {
        Parser p = new Parser(cmnd);
        var ast = p.parse();
        if(ast instanceof BadExpression) {
            return;
        }
        ast.run(System.in, System.out, env);
    }
}
