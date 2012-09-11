/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java;

/**
 *
 * @author aashiks one can store number inside the class
 */
public class NumericConstant extends Expression {

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
}
