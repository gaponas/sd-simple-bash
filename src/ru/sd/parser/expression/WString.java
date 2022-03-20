package ru.sd.parser.expression;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

import ru.sd.interpretator.Enviroment;

public class WString implements Expression {
    
    private String text;
    private String type;
    private List<Expression> params;

    private String removeQs(String s, char d) {
        String r = "";
        for(var c : s.toCharArray()) {
            if(c == d)
                continue;
            r += c;
        }
        return r;
    }

    /**
     * String 
     * @param txt String pattern 
     * @param _type String type, StrongQuouted, WeakQuouted, NotQuouted 
     * @param _params Expression, inside string
     */
    public WString(String txt, String _type, List<Expression> _params) {
        text = txt;
        type = _type;
        params = _params;
    }
    public void run(InputStream in, OutputStream out, Enviroment env) {
        String locString = text;
        if(type == "sstring") {
            locString = removeQs(locString, '\'');
        }
        if(type == "wstring") {
            locString = removeQs(locString, '"');
        }
        List<String> strs = new LinkedList<String>();
        for(var p : params) {
            OutputStream os = new ByteArrayOutputStream();
            p.run(in, os, env);
            strs.add(os.toString());
        }

        var res = String.format(locString, strs.toArray());
        new PrintStream(out).print(res);
    }
    public String print() {
        String res = "";
        res += "String[" + type + "](" + text;
        for(var p : params) {
            res += ", " + p.print();
        }
        res += ")";
        return res;
    }
}
