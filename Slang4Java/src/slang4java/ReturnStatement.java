/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java;

/**
 *
 * @author aashiks
 */
public class ReturnStatement extends Statement {

    private Expression _exp;
    private SymbolInfo inf = null;

    public ReturnStatement(Expression e) {
        _exp = e;
    }

    @Override
    public SymbolInfo Execute(RuntimeContext cont) throws Exception {
        inf = (_exp == null) ? null : _exp.Evaluate(cont);
        return inf;

    }
}
