/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java.support;

/**
 *
 * @author aashiks
 */
public class CParserException extends Exception {

    

    private int ErrorCode;
    private String ErrorString;
    private int Lexical_Offset;

    public CParserException(int pErrorCode,
            String pErrorString,
            int pLexical_Offset) {
        ErrorCode = pErrorCode;
        ErrorString = pErrorString;
        Lexical_Offset = pLexical_Offset;
    }
    public int getErrorCode() {
        return ErrorCode;
    }

   

    public String getErrorString() {
        return ErrorString;
    }

    public void setErrorString(String ErrorString) {
        this.ErrorString = ErrorString;
    }

    public int getLexical_Offset() {
        return Lexical_Offset;
    }

    public void setLexical_Offset(int Lexical_Offset) {
        this.Lexical_Offset = Lexical_Offset;
    }
    
}
