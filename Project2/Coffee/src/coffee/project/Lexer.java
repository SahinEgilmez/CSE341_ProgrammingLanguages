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
    public String lineInput(String line) throws LexerException {
        TokenList tokenList = TokenList.getInstance();
        IdentifierList identifierList = IdentifierList.getInstance();

        //String str = Utils.replaceAll(Utils.replaceAll(line, '(', ' '), ')', ' ').toLowerCase();
        String str = Utils.replaceParanthesis(line).toLowerCase();
        System.out.println(str);
        // iterate through pieces that partitioned over whitespace.
        for (String s : Utils.split(str, ' ')) {
            // clear all whitespaces from head and end of string.
            s = s.trim();
            if (!s.equals("")) {
                if (Utils.isKeyword(s)) {
                    tokenList.addToken(new Keyword(s));
                } else if (Utils.isOperator(s)) {
                    tokenList.addToken(new Operator(s));
                } else if (Utils.isInteger(s)) {
                    tokenList.addToken(new ValueInt(Integer.parseInt(s)));
                } else if (Utils.isBinary(s)) {
                    tokenList.addToken(new ValueBinary(Boolean.valueOf(s)));
                } else if (Utils.isIdentifier(s)) {
                    identifierList.addIdentifier(s);
                    tokenList.addToken(new Identifier(s));
                } else
                    throw new LexerException("Unknown token: " + s);

            }
        }


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
