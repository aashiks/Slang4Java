/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java.expressions;

import slang4java.contexts.CompilationContext;
import slang4java.contexts.RuntimeContext;
import slang4java.generators.IGenerator;
import slang4java.metainfo.SymbolInfo;
import slang4java.metainfo.TypeInfo;

/**
 *
 * @author aashiks
 */
public class BooleanConstant extends AbstractExpression {

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

    @Override
    public String Generate(IGenerator g) {
        return g.BooleanConstant(info);
    }
}
