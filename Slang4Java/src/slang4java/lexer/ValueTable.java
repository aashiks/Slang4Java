/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java.lexer;

import slang4java.metainfo.Token;

/**
 *
 * @author aashiks
 */
public class ValueTable {

    public Token tok;          // Token id
    public String Value;       // Token string  

    public ValueTable(Token tok, String Value) {
        this.tok = tok;
        this.Value = Value;

    }
}
