/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java;

/**
 *
 * @author aashiks
 *  Expression is what you evaluate for it's Value
 */
public abstract class Expression {
    public abstract SymbolInfo Evaluate(RuntimeContext cont) throws Exception;
    public abstract TypeInfo TypeCheck(CompilationContext cont) throws Exception;
    public abstract TypeInfo GetType();
}
