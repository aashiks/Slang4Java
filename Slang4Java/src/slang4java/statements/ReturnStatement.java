/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java.statements;

import slang4java.contexts.RuntimeContext;
import slang4java.expressions.AbstractExpression;
import slang4java.generators.IGenerator;
import slang4java.metainfo.SymbolInfo;

/**
 *
 * @author aashiks
 */
public class ReturnStatement extends AbstractStatement {

    private AbstractExpression _exp;
    private SymbolInfo inf = null;

    public ReturnStatement(AbstractExpression e) {
        _exp = e;
    }

    @Override
    public SymbolInfo Execute(RuntimeContext cont) throws Exception {
        inf = (_exp == null) ? null : _exp.Evaluate(cont);
        return inf;

    }

    @Override
    public String Generate(IGenerator g) {
        return g.ReturnStatement(_exp);
    }
}
