/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java.statements;

import slang4java.contexts.RuntimeContext;
import slang4java.expressions.Variable;
import slang4java.generators.IGenerator;
import slang4java.metainfo.SymbolInfo;

/**
 *
 * @author aashiks
 */
public class VariableDeclStatement extends AbstractStatement {

    SymbolInfo m_inf = null;
    Variable var = null;

    public VariableDeclStatement(SymbolInfo inf) {
        m_inf = inf;
    }

    @Override
    public SymbolInfo Execute(RuntimeContext cont) throws Exception {
        cont.getSymbolTable().Add(m_inf);
        var = new Variable(m_inf);
        return null;
    }

    @Override
    public String Generate(IGenerator g) {
        return g.VariableDeclStatement(m_inf);
    }
}
