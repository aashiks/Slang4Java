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
 */
public class StringLiteral extends AbstractExpression{
    private SymbolInfo info;
    /*
     *  Construction does not do much , just keeps the 
     *  value assigned to the private variable
     */

    public StringLiteral(String value) {
        info = new SymbolInfo();
        info.StringValue = value;
        info.SymbolName = null;
        info.Type = TypeInfo.TYPE_STRING;
    }

    @Override
    public SymbolInfo Evaluate(RuntimeContext cont) {
        return info;
    }

    @Override
    public TypeInfo TypeCheck(CompilationContext cont) {
        return info.Type;
    }

    @Override
    public TypeInfo GetType() {
        return info.Type;
    }
}
