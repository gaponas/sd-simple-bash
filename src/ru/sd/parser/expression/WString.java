package ru.sd.parser.expression;

import java.io.InputStream;
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

    public WString(String txt, String _type, List<Expression> _params) {
        text = txt;
        type = _type;
        params = _params;
    }
    public String run(InputStream stdin, Enviroment env) {
        String locString = text;
        if(type == "sstring") {
            locString = removeQs(locString, '\'');
        }
        if(type == "wstring") {
            locString = removeQs(locString, '"');
        }
        List<String> strs = new LinkedList<String>();
        for(var p : params) {
            strs.add(p.run(stdin, env));
        }

        var res = String.format(locString, strs.toArray());
        return res;
    }
    public void print() {
        System.out.println("=========");
        System.out.println("String");
        System.out.println(type);
        System.out.println(text);
        for(var p : params) {
            p.print();
        }
        System.out.println("_________");
    }
}
