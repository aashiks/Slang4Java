/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java.statements;

import slang4java.contexts.RuntimeContext;
import slang4java.expressions.AbstractExpression;
import slang4java.metainfo.SymbolInfo;
import slang4java.metainfo.TypeInfo;

/**
 *
 * @author aashiks prints the value of an expression on to screen
 */
public class PrintStatement extends Statement {

    private AbstractExpression _ex;

    public PrintStatement(AbstractExpression ex) {
        _ex = ex;
    }

    @Override
    public SymbolInfo Execute(RuntimeContext cont) throws Exception {

        SymbolInfo val = _ex.Evaluate(cont);

        if (val.Type == TypeInfo.TYPE_NUMERIC) {
            System.out.print(val.DoubleValue);
        } else if (val.Type == TypeInfo.TYPE_STRING) {
            System.out.print(val.StringValue);
        } else {
            System.out.print(val.BoolValue);
        }
        return null;
    }
}
