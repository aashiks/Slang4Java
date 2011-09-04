/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java;

/**
 *
 * @author aashiks
 */
public class Lexer {

    String IExpression; // Expression string
    int index; // index into a character
    int length; // Length of the string
    double number; // Last grabbed number from the stream

    public Lexer(String Expression) {
        IExpression = Expression;
        length = IExpression.length();
        index = 0;
    }

    /*
     * Grab the next token from the stream
     */
    public Token GetToken() throws Exception {
        Token tok = Token.ILLEGAL_TOKEN;
        ////////////////////////////////////////////////////////////
        //
        // Skip the white space
        //
        while (index < length
                && (IExpression.toCharArray()[index] == ' ' || IExpression.toCharArray()[index] == '\t')) {
            index++;
        }
        //////////////////////////////////////////////
        //
        // End of string ? return NULL;
        //
        if (index == length) {
            return Token.TOK_NULL;
        }
        /////////////////////////////////////////////////
        //
        //
        //
        switch (IExpression.toCharArray()[index]) {
            case '+':
                tok = Token.TOK_PLUS;
                index++;
                break;
            case '-':
                tok = Token.TOK_SUB;
                index++;
                break;
            case '/':
                tok = Token.TOK_DIV;
                index++;
                break;
            case '*':
                tok = Token.TOK_MUL;
                index++;
                break;
            case '(':
                tok = Token.TOK_OPAREN;
                index++;
                break;
            case ')':
                tok = Token.TOK_CPAREN;
                index++;
                break;
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9': {
                String str = "";
                while (index < length
                        && (IExpression.toCharArray()[index] == '0'
                        || IExpression.toCharArray()[index] == '1'
                        || IExpression.toCharArray()[index] == '2'
                        || IExpression.toCharArray()[index] == '3'
                        || IExpression.toCharArray()[index] == '4'
                        || IExpression.toCharArray()[index] == '5'
                        || IExpression.toCharArray()[index] == '6'
                        || IExpression.toCharArray()[index] == '7'
                        || IExpression.toCharArray()[index] == '8'
                        || IExpression.toCharArray()[index] == '9')) {
                    str += (IExpression.toCharArray()[index]);
                    index++;
                }

                number = Double.parseDouble(str);
                tok = Token.TOK_DOUBLE;
            }
            break;
            default:
                System.out.println("Error While Analyzing Tokens");
                throw new Exception("Error While Analyzing Tokens");
        }
        return tok;
    }

    public double GetNumber() {
        return number;
    }
}
