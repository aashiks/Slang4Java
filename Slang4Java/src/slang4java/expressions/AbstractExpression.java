/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java.expressions;

import slang4java.contexts.CompilationContext;
import slang4java.contexts.RuntimeContext;
import slang4java.metainfo.SymbolInfo;
import slang4java.metainfo.TypeInfo;

/**
 *
 * @author aashiks
 *  Expression is what you evaluate for it's Value
 */
public abstract class AbstractExpression {
    public abstract SymbolInfo Evaluate(RuntimeContext cont) throws Exception;
    public abstract TypeInfo TypeCheck(CompilationContext cont) throws Exception;
    public abstract TypeInfo GetType();
}
