package ru.sd.parser;
import java.util.LinkedList;
import java.util.List;

import ru.sd.parser.expression.*;

/**
 * Parser creates AST from String
 */
public class Parser {

    private class LexRecord {

        public List<LexRecord> inner;
        public String val;
        public String type;

        public LexRecord(String _type, String _val, List<LexRecord> _inner) {
            val = _val;
            inner = _inner;
            type = _type;
        }
    }
    
    /* private fields */
    private char[] input;
    private int i = 0;

    private boolean isWord(char c) {
        return (c >= 'a' && c <= 'z')
                || (c >= 'A' && c <= 'Z')
                || (c == '_')
                || (c == '-')
                || (c == '?'); 
    }

    private boolean isNumber(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isWordOrNumber(char c) {
        return isWord(c) || isNumber(c);
    }

    private char get() {
        if(i == input.length) return 0;
        return input[i];
    }
    
    private char mv() {
        if(i == input.length) return 0;
        return input[i++];
    }

    private LexRecord lex(boolean endWithBracket) {
        List<LexRecord> res = new LinkedList<LexRecord>();
        String buffer = "";
        int mode = 1;
        String type = "";
        List<LexRecord> inner = null;
        String varBuffer = "";
        while(i < input.length) {
            if(mode == 0) {
                char c = get();
                if(endWithBracket && c == ')') {
                    mv();
                    res.add(new LexRecord(type, buffer, inner));
                    break;
                }
                if(c == ' ') {
                    res.add(new LexRecord(type, buffer, inner));
                    buffer = "";
                    type = "";
                    inner = null;
                    mode = 1;
                    continue;
                }
                if(c == '\'') {
                    buffer += mv();
                    type = "sstring";
                    mode = 2;
                    continue;
                }
                if(c == '"') {
                    buffer += mv();
                    mode = 3;
                    type = "wstring";
                    continue;
                }
                if(c == '$') {
                    mv();
                    varBuffer = "";
                    mode = 4;
                    continue;
                }
                buffer += mv();
            } 
            else if(mode == 1) {
                char c = get();
                if(c == '|') {
                    List<LexRecord> tmp = res;
                    res = new LinkedList<LexRecord>();
                    res.add(new LexRecord("separator", "|", tmp));
                    mv();
                    continue;
                }
                if(c == ' ') {
                    mv();
                    continue;
                }
                type = "chunk";
                mode = 0;
            }
            else if(mode == 2) {
                char c = mv();
                buffer += c;
                if(c == '\'') {
                    mode = 0;
                }
            }
            else if(mode == 3) {
                char c = mv();
                if(c == '$') {
                    varBuffer = "";
                    mode = 5;
                    continue;
                }
                buffer += c;
                if(c == '\"') {
                    mode = 0;
                    continue;
                }
            }
            else if(mode == 4 || mode == 5) {
                char c = get();
                if(c == '(') {
                    mv();
                    if(inner == null) {
                        inner = new LinkedList<LexRecord>();
                    }
                    inner.add(lex(true));
                    buffer += "%s";
                    mode = mode == 4 ? 0 : 3;
                    continue;
                }
                if(isWordOrNumber(c)) {
                    varBuffer += mv();
                } else {
                    if(inner == null) {
                        inner = new LinkedList<LexRecord>();
                    }
                    inner.add(new LexRecord("varible", varBuffer, null));
                    buffer += "%s";
                    mode = mode == 4 ? 0 : 3;
                    continue;
                }
            }
        }
        return new LexRecord("wrapper", "", res);
    }

    /**
     * Test propose print
     * @param exp Root expression 
     */
    public String print() {
        var ast = parse();
        return ast.print();
    }

    private String[] makeSplitString(String text) {
        String buffer = "";
        int j = 0;
        if(text.length() == 0) return new String[] {"", text};
        if(text.charAt(0) == '=' || isNumber(text.charAt(0))) return new String[] {"", text}; 
        while(j < text.length()) {
            if(text.charAt(j) == '\'') {
                return new String[] {"", text};
            }
            if(text.charAt(j) == '"') {
                return new String[] {"", text};
            }
            if(text.charAt(j) == '=') {
                return new String[] {buffer, text.substring(j+1)};
            }
            buffer += text.charAt(j);
            ++j;
        }

        return new String[] {"", text};
    }

    private Expression parseLocalLex(LexRecord lex) {
        if(lex.type == "wrapper") {
            return parseLocal(lex.inner);
        }
        var tmp = new LinkedList<LexRecord>();
        tmp.add(lex);
        return parseLocal(tmp);
    }

    private Expression parseLocal(List<LexRecord> lst) {
        if(lst == null) return null;
        int j = 0;
        Expression exp = null;
        Expression cmnd = null;
        List<Expression> args = new LinkedList<Expression>();
        List<Expression> assigns = new LinkedList<Expression>();

        if(lst.size() == 0) return new BadExpression();
        if(lst.get(j).type == "separator") {
            exp = parseLocal(lst.get(j).inner);
            ++j;
        }
        if(lst.get(j).type == "varible") {
            return new Varible(lst.get(j).val);
        }
        boolean isAssignments = true;
        for( ; j < lst.size(); ++j) {
            var storage = isAssignments ? assigns : args;
            var current = lst.get(j);
            var text = current.val;
            String[] splitted = makeSplitString(text);
            if(splitted[0] == "") {
                isAssignments = false;
                storage = args;
            }
            List<Expression> mapped = new LinkedList<Expression>();
            if(current.inner != null) {
                for(var ex : current.inner) {
                    mapped.add(parseLocalLex(ex));
                }
            }
            var left = new WString(splitted[1], current.type, mapped);
            storage.add(isAssignments ? new Assignment(splitted[0], left) : left);
        }

        if(args.size() == 0) {
            return new AssignCommand(assigns);
        }    
        cmnd = args.remove(0);
        var resCmnd = new Command(cmnd, args, assigns);
        return exp == null ? resCmnd : new Pipe(exp, resCmnd);
    }
    
    /**
     * Parses the input and returns AST root
     * @return AST root
     */
    public Expression parse() {
        var tree = lex(false);
        var res = parseLocalLex(tree);
        return res;
    }

    /**
    * Init parser
    * @param _input String to parse
    */
    public Parser(String _input) {
        _input = _input + ' ';
        input = _input.toCharArray();
    };
}
