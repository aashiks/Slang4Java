/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java;

/**
 *
 * @author aashiks
 */
public abstract class AbstractProcedure {
    public abstract SymbolInfo Execute(RuntimeContext cont) throws Exception;
}
