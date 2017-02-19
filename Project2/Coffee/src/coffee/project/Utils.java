
package coffee.project;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Şahin Eğilmez on 11/13/16
 *
 * This class have general utils for Coffee Language's Lexer.
 */
public class Utils {

    /**
     * Checks whether given string is null or not.
     *
     * @param str
     */
    private static void isNull(String str) {
        if (str == null)
            throw new IllegalArgumentException("null input not allowed");
    }

    /**
     * Counts a character in a string.
     *
     * @param str
     * @param target
     * @return
     */
    public static int getCountChar(String str, char target) {
        isNull(str);

        int count = 0;
        for (char ch : str.toCharArray())
            if (ch == target)
                ++count;

        return count;
    }

    /**
     * Searches a character on a given string.
     *
     * @param str
     * @param target
     * @return -1, if character is not on string; index value, otherwise.
     */
    public static int findChar(String str, char target) {
        isNull(str);

        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; ++i) {
            if (chars[i] == target)
                return i;
        }

        return -1;
    }

    /**
     * Replaces all instances of a character with another from string.
     *
     * @param str
     * @param target
     * @param replacement
     * @return
     */
    public static String replaceAll(String str, char target, char replacement) {
        isNull(str);

        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; ++i)
            if (chars[i] == target)
                chars[i] = replacement;

        return new String(chars);
    }

    public static String replaceParanthesis(String str) {
        isNull(str);
        String newStr = "";
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; ++i) {

            if (chars[i] == '(') {
                newStr += "( ";
            }else if( chars[i] == ')')
                newStr += " )";
            else
                newStr += chars[i];
        }
        return newStr;
    }

    /**
     * Splits a string around the given character recursively.
     *
     * @param str
     * @param regex
     * @return List of tokens.
     */
    public static List<String> split(String str, char regex) {
        List<String> strList = new ArrayList<String>();

        int i;
        if ((i = getCountChar(str, regex)) > 0) {
            int pos = Utils.findChar(str, regex);
            strList.add(str.substring(0, pos));

            if (i != 1)
                // If string has more than 1 of this char, that means there will be more than 2 tokens after partition.
                // So call recursive to make problem smaller.
                strList.addAll(split(str.substring(pos + 1), regex));
            else
                // If string has only 1 of this char, that means there will be 2 tokens after partition.
                // One of these token handled above, so add the other one.
                strList.add(str.substring(pos + 1));
        }

        return strList;
    }



    public static boolean isParanthesis(String str){
        return (str.charAt(0) =='(' ||  str.charAt(0) == ')');
    }

    /**
     * check whether given string is identifier or not
     * @param str
     * @return
     */
    public static boolean isIdentifier(String str) {
        return str.matches("[a-zA-Z]+");
    }

    /**
     * check whether given string is operator or not
     * @param str
     * @return
     */
    public static boolean isOperator(String str) {
        return str.matches("[-(+*/)]");
    }

    /**
     * check whether given string is keyword or not
     * @param str
     * @return
     */
    public static boolean isKeyword(String str) {
        return str.equals("for") ||
                str.equals("while") ||
                str.equals("if") ||
                str.equals("then") ||
                str.equals("else") ||
                str.equals("and") ||
                str.equals("or") ||
                str.equals("not") ||
                str.equals("equal") ||
                str.equals("append") ||
                str.equals("concat") ||
                str.equals("set") ||
                str.equals("deffun") ||
                str.equals("sumup");
    }

    /**
     * check whether given string is integer or not
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     * check whether given string is boolean or not
     * @param str
     * @return
     */
    public static boolean isBinary(String str) { return str.equals("false") || str.equals("true"); }


}

