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

    private String _exp;
    private int _index;
    private int _length_string;
    private double _curr_num;
    private ValueTable[] _val = null;
    private String last_str;

    public Lexer(String expression) {
        _exp = expression;
        _length_string = expression.length();
        _index = 0;

        // populate the value table. 
        //Is this how we add new supported statements ?
        // TODO: Ask Pai
        _val = new ValueTable[2];
        _val[0] = new ValueTable(Token.TOK_PRINT, "PRINT");
        _val[1] = new ValueTable(Token.TOK_PRINTLN, "PRINTLINE");
    }

    public double Number() {
        return _curr_num;
    }

    public double GetNumber() {
        return _curr_num;
    }

    /*public Token GetToken() throws Exception {
        Token tok = Token.TOK_CNTRLCHAR;
        while(tok==Token.TOK_CNTRLCHAR)
        {
            tok=GetNToken();
        }
        return tok;
    }*/
    // Big Ass Custom Tokenizer. Now checks for entries in _val ( the ValueTable)
    // as well

    public Token GetToken() throws Exception {

        Token tok = Token.ILLEGAL_TOKEN;

        //// Skipping white spaces and control chars
        while ((_index < _length_string)
                && (_exp.toCharArray()[_index] == ' ' || 
                    _exp.toCharArray()[_index] == '\t' ||
                    _exp.toCharArray()[_index] == '\r' ||
                    _exp.toCharArray()[_index] == '\n' 
                    )) {
            _index++;
        }

        /// End Of Expression
        if (_index == _length_string) {
            return Token.TOK_NULL;
        }

        switch (_exp.toCharArray()[_index]) {
           
            case '+':
                tok = Token.TOK_PLUS;
                _index++;
                break;
            case '-':
                tok = Token.TOK_SUB;
                _index++;
                break;
            case '/':
                tok = Token.TOK_DIV;
                _index++;
                break;
            case '*':
                tok = Token.TOK_MUL;
                _index++;
                break;
            case '(':
                tok = Token.TOK_OPAREN;
                _index++;
                break;
            case ')':
                tok = Token.TOK_CPAREN;
                _index++;
                break;
            case ';':
                tok = Token.TOK_SEMI;
                _index++;
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
                while ((_index < _length_string)
                        && (_exp.toCharArray()[_index] == '0'
                        || _exp.toCharArray()[_index] == '1'
                        || _exp.toCharArray()[_index] == '2'
                        || _exp.toCharArray()[_index] == '3'
                        || _exp.toCharArray()[_index] == '4'
                        || _exp.toCharArray()[_index] == '5'
                        || _exp.toCharArray()[_index] == '6'
                        || _exp.toCharArray()[_index] == '7'
                        || _exp.toCharArray()[_index] == '8'
                        || _exp.toCharArray()[_index] == '9')) {
                    str += (_exp.toCharArray()[_index]);
                    _index++;
                }
                _curr_num = Double.parseDouble(str);
                tok = Token.TOK_DOUBLE;
            }
            break;
            default: //deal with statements
            {
                if (Character.isLetter(_exp.toCharArray()[_index])) {

                    String tem = String.valueOf(_exp.toCharArray()[_index]);
                    _index++;
                    while (_index < _length_string
                            && (Character.isLetterOrDigit(_exp.toCharArray()[_index])
                            || _exp.toCharArray()[_index] == '_')) {
                        tem += _exp.toCharArray()[_index];
                        _index++;
                    }

                    tem = tem.toUpperCase();

                    for (int i = 0; i < this._val.length; ++i) {
                        if (_val[i].Value.equals(tem)) {
                            return _val[i].tok;
                        }

                    }

                    this.last_str = tem;

                    return Token.TOK_UNQUOTED_STRING;
                } else {
                    System.out.println("Error tokenizing input");
                    throw new Exception("Error tokenizing input");
                }
            }
        }

       /* System.out.println("Error tokenizing _exp = " + _exp + "\n"
                + "after " + _exp.substring(17) + "\n"
                + " at _index = " + _index + "\n"
                + "length of input = " + _length_string + "\n"
                + "\n returning ILLEGAL_TOKEN");*/
        return tok;
    }
}
