/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java.lexer;

/**
 *
 * @author aashiks
 */
public class Lexer {

    //    Items which are static of nature
    String iExpr;
    int length;
    double number;
    protected ValueTable[] keywords;
    //  Items which are dependent on state
    //  index can be changed by GetNext,a Loop or IF statement
    int index;           // index into a character  
    public String last_str; // Token assoicated with  Last grabbed String
    protected Token Current_Token;  // Current Token
    protected Token Last_Token;     // Penultimate token

    //    Get Next Token from the stream and return to the caller
    protected Token GetNext() throws Exception {
        Last_Token = Current_Token;
        Current_Token = GetToken();
        return Current_Token;
    }

    public int SaveIndex() {
        return index;
    }
    ///     Get Line where Error Occured

    public String GetCurrentLine(int pindex) {
        int tindex = pindex;
        if (pindex >= length) {
            tindex = length - 1;
        }
        while (tindex > 0 && iExpr.toCharArray()[tindex] != '\n') {
            tindex--;
        }

        if (iExpr.toCharArray()[tindex] == '\n') {
            tindex++;
        }

        String CurrentLine = "";

        while (tindex < length && (iExpr.toCharArray()[tindex] != '\n')) {
            CurrentLine = CurrentLine + iExpr.toCharArray()[tindex];
            tindex++;
        }

        return CurrentLine + "\n";

    }

    public String GetPreviousLine(int pindex) {

        int tindex = pindex;
        while (tindex > 0 && iExpr.toCharArray()[tindex] != '\n') {
            tindex--;
        }

        if (iExpr.toCharArray()[tindex] == '\n') {
            tindex--;
        } else {
            return "";
        }

        while (tindex > 0 && iExpr.toCharArray()[tindex] != '\n') {
            tindex--;
        }


        if (iExpr.toCharArray()[tindex] == '\n') {
            tindex--;
        }


        String CurrentLine = "";

        while (tindex < length && (iExpr.toCharArray()[tindex] != '\n')) {
            CurrentLine = CurrentLine + iExpr.toCharArray()[tindex];
            tindex++;
        }

        return CurrentLine + "\n";



    }

    //    Restore Index . Only after a GetNext , contents
    //    of the state variables will be reliable
    public void RestoreIndex(int m_index) {
        index = m_index;

    }

    public Lexer(String expression) {
        iExpr = expression;
        length = iExpr.length();
        index = 0;
        ////////////////////////////////////////////////////
        // Fill the Keywords
        //
        //
        keywords = new ValueTable[16];

        keywords[0] = new ValueTable(Token.TOK_BOOL_FALSE, "FALSE");
        keywords[1] = new ValueTable(Token.TOK_BOOL_TRUE, "TRUE");
        keywords[2] = new ValueTable(Token.TOK_VAR_STRING, "STRING");
        keywords[3] = new ValueTable(Token.TOK_VAR_BOOL, "BOOLEAN");
        keywords[4] = new ValueTable(Token.TOK_VAR_NUMBER, "NUMERIC");
        keywords[5] = new ValueTable(Token.TOK_PRINT, "PRINT");
        keywords[6] = new ValueTable(Token.TOK_PRINTLN, "PRINTLINE");

        // -------------- Added in the step 6
        // -------------- To support control structures

        keywords[7] = new ValueTable(Token.TOK_IF, "IF");
        keywords[8] = new ValueTable(Token.TOK_WHILE, "WHILE");
        keywords[9] = new ValueTable(Token.TOK_WEND, "WEND");
        keywords[10] = new ValueTable(Token.TOK_ELSE, "ELSE");
        keywords[11] = new ValueTable(Token.TOK_ENDIF, "ENDIF");
        keywords[12] = new ValueTable(Token.TOK_THEN, "THEN");

        // ----------- Step 7

        keywords[13] = new ValueTable(Token.TOK_END, "END");
        keywords[14] = new ValueTable(Token.TOK_FUNCTION, "FUNCTION");
        keywords[15] = new ValueTable(Token.TOK_RETURN, "RETURN");

    }
//      Extract string from the stream

    private String ExtractString() {
        String ret_value = "";
        while (index < iExpr.length()
                && (Character.isLetterOrDigit(iExpr.toCharArray()[index])
                || iExpr.toCharArray()[index] == '_')) {

            ret_value = ret_value + iExpr.toCharArray()[index];
            index++;
        }
        return ret_value;
    }
//    Skip to the End of Line

    public void SkipToEoln() {
        while (index < length && (iExpr.toCharArray()[index] != '\n')) {
            index++;
        }

        if (index == length) {
            return;
        }
    }

    /////////////////////////////////////////////////////
    // Grab the next token from the stream
    //
    //    
    //
    //
    public Token GetToken() {

        Token tok = Token.ILLEGAL_TOKEN;

        boolean restartloop = true;
        while (restartloop) {
            restartloop = false;
            tok = Token.ILLEGAL_TOKEN;

            ////////////////////////////////////////////////////////////
            //
            // Skip  the white space 
            //  

            while (index < length
                    && (iExpr.toCharArray()[index] == ' ' || iExpr.toCharArray()[index] == '\t')) {
                index++;
            }
            //////////////////////////////////////////////
            //
            //   End of string ? return NULL;
            //

            if (index == length) {
                return Token.TOK_NULL;
            }
            switch (iExpr.toCharArray()[index]) {


                case '\n':
                    index++;

                    restartloop = true;
                    break;
                case '\r':
                    index++;
                    restartloop = true;
                    break;

                case '+':
                    tok = Token.TOK_PLUS;
                    index++;
                    break;
                case '-':
                    tok = Token.TOK_SUB;
                    index++;
                    break;
                case '*':
                    tok = Token.TOK_MUL;
                    index++;
                    break;
                case ',':
                    // -------- Addition in step 7
                    tok = Token.TOK_COMMA;
                    index++;
                    break;
                case '(':
                    tok = Token.TOK_OPAREN;
                    index++;
                    break;
                case ';':
                    tok = Token.TOK_SEMI;
                    index++;
                    break;
                case ')':
                    tok = Token.TOK_CPAREN;
                    index++;
                    break;
                case '!':
                    tok = Token.TOK_NOT;
                    index++;
                    break;
                case '>':
                    if (iExpr.toCharArray()[index + 1] == '=') {
                        tok = Token.TOK_GTE;
                        index += 2;
                    } else {
                        tok = Token.TOK_GT;
                        index++;
                    }
                    break;
                case '<':
                    if (iExpr.toCharArray()[index + 1] == '=') {
                        tok = Token.TOK_LTE;
                        index += 2;
                    } else if (iExpr.toCharArray()[index + 1] == '>') {
                        tok = Token.TOK_NEQ;
                        index += 2;
                    } else {
                        tok = Token.TOK_LT;
                        index++;
                    }
                    break;
                case '=':
                        if (iExpr.toCharArray()[index + 1] == '=') {
                        tok = Token.TOK_EQ;
                        index += 2;
                    } else {
                        tok = Token.TOK_ASSIGN;
                        index++;
                    }
                    break;
                case '&':
                    if (iExpr.toCharArray()[index + 1] == '&') {
                        tok = Token.TOK_AND;
                        index += 2;
                    } else {
                        tok = Token.ILLEGAL_TOKEN;
                        index++;
                    }
                    break;
                case '|':
                    if (iExpr.toCharArray()[index + 1] == '|') {
                        tok = Token.TOK_OR;
                        index += 2;
                    } else {
                        tok = Token.ILLEGAL_TOKEN;
                        index++;
                    }
                    break;
                case '/':

                    if (iExpr.toCharArray()[index + 1] == '/') {
                        SkipToEoln();
                        restartloop = true;
                        break;

                    } else {
                        tok = Token.TOK_DIV;
                        index++;
                    }
                    break;
                case '"':
                    String x = "";
                    index++;
                    while (index < length && iExpr.toCharArray()[index] != '"') {
                        x = x + iExpr.toCharArray()[index];
                        index++;
                    }

                    if (index == length) {
                        tok = Token.ILLEGAL_TOKEN;
                        return tok;
                    } else {
                        index++;
                        last_str = x;
                        tok = Token.TOK_STRING;
                        return tok;
                    }
                default:
                    if (Character.isDigit(iExpr.toCharArray()[index])) {

                        String str = "";
                        while (index < length && (iExpr.toCharArray()[index] == '0'
                                || iExpr.toCharArray()[index] == '1'
                                || iExpr.toCharArray()[index] == '2'
                                || iExpr.toCharArray()[index] == '3'
                                || iExpr.toCharArray()[index] == '4'
                                || iExpr.toCharArray()[index] == '5'
                                || iExpr.toCharArray()[index] == '6'
                                || iExpr.toCharArray()[index] == '7'
                                || iExpr.toCharArray()[index] == '8'
                                || iExpr.toCharArray()[index] == '9')) {
                            str += (iExpr.toCharArray()[index]);
                            index++;
                        }

                        if (iExpr.toCharArray()[index] == '.') {
                            str = str + ".";
                            index++;
                            while (index < length && (iExpr.toCharArray()[index] == '0'
                                    || iExpr.toCharArray()[index] == '1'
                                    || iExpr.toCharArray()[index] == '2'
                                    || iExpr.toCharArray()[index] == '3'
                                    || iExpr.toCharArray()[index] == '4'
                                    || iExpr.toCharArray()[index] == '5'
                                    || iExpr.toCharArray()[index] == '6'
                                    || iExpr.toCharArray()[index] == '7'
                                    || iExpr.toCharArray()[index] == '8'
                                    || iExpr.toCharArray()[index] == '9')) {
                                str += (iExpr.toCharArray()[index]);
                                index++;
                            }
                        }
                        number = Double.parseDouble(str);
                        tok = Token.TOK_NUMERIC;
                    } else if (Character.isLetter(iExpr.toCharArray()[index])) {


                        String tem = String.valueOf((iExpr.toCharArray()[index]));
                        index++;
                        while (index < length && (Character.isLetterOrDigit(iExpr.toCharArray()[index])
                                || (iExpr.toCharArray()[index] == '_'))) {
                            tem += iExpr.toCharArray()[index];
                            index++;
                        }

                        tem = tem.toUpperCase();

                        for (int i = 0; i < keywords.length; ++i) {
                            if (keywords[i].Value.compareTo(tem) == 0) {
                                return keywords[i].tok;
                            }

                        }
                        this.last_str = tem;
                        return Token.TOK_UNQUOTED_STRING;
                    } else {
                        return Token.ILLEGAL_TOKEN;
                    }
                    break;
            }

        }
        return tok;
    }

    public double GetNumber() {
        return number;
    }
}
