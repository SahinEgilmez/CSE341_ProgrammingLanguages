package coffee.project;

import coffee.IdentifierList;
import coffee.TokenList;
import coffee.datatypes.*;

import java.util.ArrayList;

/**
 * Created by ftektas on 12/12/16.
 * Modify by Şahin Eğilmez
 */
public class Parser {
    // Parses the lexer result and prints *ONLY* the parsing result.
    ArrayList ParsedList = new ArrayList();
    ArrayList<String> parsing = new ArrayList<String>();
    ArrayList<String> resultParser = new ArrayList<String>();

    public void parse() {
        IdentifierList identifierList = IdentifierList.getInstance();
        TokenList tokenList = TokenList.getInstance();
        ArrayList tokens = (ArrayList) tokenList.getAllTokens();

        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i) instanceof Operator) {
                ParsedList.add(((Operator) tokens.get(i)).getOperator());
                parsing.add(((Operator) tokens.get(i)).getOperator());
            } else if (tokens.get(i) instanceof Identifier) {
                ParsedList.add(((Identifier) tokens.get(i)).getName());
                parsing.add("ID");
            } else if (tokens.get(i) instanceof Keyword) {
                ParsedList.add(((Keyword) tokens.get(i)).getKeyword());
                parsing.add(((Keyword) tokens.get(i)).getKeyword());
            } else if (tokens.get(i) instanceof ValueInt) {
                ParsedList.add(((ValueInt) tokens.get(i)).getValue());
                parsing.add("IntVal");
            } else if (tokens.get(i) instanceof ValueBinary) {
                ParsedList.add(((ValueBinary) tokens.get(i)).getValue());
                parsing.add(((ValueBinary) tokens.get(i)).getValue().toString());
            }
        }
        ArrayList<String> last = new ArrayList<String>();
        last.addAll(parsing);

        int unClosedParanthesis = 0, maxP = 0;
        int pNum = 1;
        boolean change = false;
        int k = 0;
        while (!checkResult()) {
            int i = 0;
            for (i = 0; i < parsing.size(); i++) {
                if (parsing.get(i).equals("(")) {
                    unClosedParanthesis++;
                    if (unClosedParanthesis > maxP)
                        maxP = unClosedParanthesis;
                } else if (parsing.get(i).equals(")")) {
                    unClosedParanthesis--;
                }

                if (unClosedParanthesis == pNum) {
                    if (convert(i)) {
                        unClosedParanthesis--;
                        change = true;
                        break;
                    }

                }
            }
            unClosedParanthesis = 0;
            if (change == true)
                change = false;
            else
                pNum++;
            writeResult();
            k++;
        }
        System.out.println(maxP);
        finalEdit(maxP);

        print();
        System.out.print("    ->");
        for (String s : last)
            System.out.print(s + " ");
        System.out.println(" ");
    }

    private boolean convert(int index) {
        if (parsing.get(index).equals("ID")) {
            parsing.set(index, "EXPI");
            return true;
        } else if (parsing.get(index).equals("IntVal")) {
            parsing.set(index, "EXPI");
            return true;
        }

        return false;
    }

    private void finalEdit(int max) {
        int pNum = 0;
        ArrayList<String> res = new ArrayList<String>();
        boolean change = false;
        while (max > 0) {
            for (String s : parsing) {
                if (s.equals("("))
                    pNum++;
                else if (s.equals(")"))
                    pNum--;
                if (pNum == max) {
                    change = true;

                    continue;
                } else {
                    if (change == true) {
                        change = false;
                        res.add("EXPI");
                        max--;
                    } else
                        res.add(s);
                }
            }

            parsing.clear();
            parsing.addAll(res);
            res.clear();
            //System.out.println(parsing);
            writeResult();


            pNum = 0;
        }
    }

    private boolean checkResult() {
        for (String s : parsing) {
            if (s.equals("EXPI") || Utils.isOperator(s) || Utils.isKeyword(s) || Utils.isBinary(s)) {
                continue;
            } else
                return false;
        }

        return true;
    }

    private void writeResult() {
        String str = "";
        for (String s : parsing)
            str += s + " ";
        resultParser.add(str);
    }

    private void print() {

        System.out.println("START -> INPUT");

        for (int i = resultParser.size() - 1; i >= 0; i--) {
            System.out.println("    ->  " + resultParser.get(i));
        }
    }
}
