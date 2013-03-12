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
 * @author aashiks one can store number inside the class
 */
public class NumericConstant extends AbstractExpression {

    private SymbolInfo info;
    /*
     *  Construction does not do much , just keeps the 
     *  value assigned to the private variable
     */

    public NumericConstant(double value) {
        info = new SymbolInfo();
        info.DoubleValue = value;
        info.SymbolName = null;
        info.Type = TypeInfo.TYPE_NUMERIC;
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
        return g.NumericConstant(info);
    }
}
