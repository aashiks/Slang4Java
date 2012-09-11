/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java;

/**
 *
 * @author aashiks
 */
public class BooleanConstant extends Expression {

    private SymbolInfo info;

    public BooleanConstant(boolean pvalue) {
        info = new SymbolInfo();
        info.BoolValue = pvalue;
        info.Type = TypeInfo.TYPE_BOOL;
        info.SymbolName = null;

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
