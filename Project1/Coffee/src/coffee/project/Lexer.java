package coffee.project;

import coffee.IdentifierList;
import coffee.REPL;
import coffee.TokenList;
import coffee.datatypes.*;

/**
 * Created by ft on 10/14/15.
 * Modified by Şahin Eğilmez on 11/13/16.
 */
public class Lexer implements REPL.LineInputCallback {
    @Override
    public String lineInput(String line) throws Exception {
        TokenList tokenList = TokenList.getInstance();
        IdentifierList identifierList = IdentifierList.getInstance();

        String str = Utils.replaceAll(Utils.replaceAll(line, '(', ' '), ')', ' ').toLowerCase();

        // iterate through pieces that partitioned over whitespace.
        for (String s : Utils.split(str, ' ')) {
            // clear all whitespaces from head and end of string.
            s = s.trim();
            if (!s.equals("")) {
                if (Utils.isIdentifier(s)) {
                    identifierList.addIdentifier(s);
                    tokenList.addToken(new Identifier(s));
                } else if (Utils.isOperator(s)) {
                    tokenList.addToken(new Operator(s));
                } else if (Utils.isKeyword(s)) {
                    tokenList.addToken(new Keyword(s));
                } else if (Utils.isInteger(s)) {
                    tokenList.addToken(new ValueInt(Integer.parseInt(s)));
                } else if (Utils.isBool(s)) {
                    tokenList.addToken(new ValueBinary(Boolean.valueOf(s)));
                } else
                    throw new LexerException("Unknown token: " + s);
            }
        }

        // since we deleted all parenthesis from input in the beginning of analyze,
        // we shall handle this as another task
        for (int i = 0; i < Utils.getCountChar(line, '('); ++i)
            tokenList.addToken(new Operator("("));
        for (int i = 0; i < Utils.getCountChar(line, ')'); ++i)
            tokenList.addToken(new Operator(")"));


        return null;
    }

    public class LexerException extends Exception {
        public LexerException() {
        }

        public LexerException(String message) {
            super(message);
        }
    }
}
