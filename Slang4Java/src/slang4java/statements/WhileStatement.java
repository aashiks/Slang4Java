/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java.statements;

import java.util.ArrayList;
import slang4java.contexts.RuntimeContext;
import slang4java.expressions.AbstractExpression;
import slang4java.generators.IGenerator;
import slang4java.metainfo.SymbolInfo;
import slang4java.metainfo.TypeInfo;

/**
 *
 * @author aashiks
 */
public class WhileStatement extends AbstractStatement {
//    conditional expression
    //    the type ought to be boolean

    private AbstractExpression cond;
    //    List of statements to be 
    //    executed if conditoon is true
    private ArrayList _statementsList;

    public WhileStatement(AbstractExpression e, ArrayList statementList) {
        _statementsList = statementList;
        cond = e;
    }

    @Override
    public SymbolInfo Execute(RuntimeContext cont) throws Exception {

        while (true) {
            SymbolInfo m_cond = cond.Evaluate(cont);

            if (m_cond == null || m_cond.Type != TypeInfo.TYPE_BOOL) {
                return null;
            }

            if (m_cond.BoolValue != true) {
                return null;
            }

            SymbolInfo tsp = null;
            for (Object o : _statementsList) {
                AbstractStatement rst = (AbstractStatement) o;
                tsp = rst.Execute(cont);
                if (tsp != null) {
                    return tsp;
                }
            }
        }

    }

    @Override
    public String Generate(IGenerator g) {
        return g.WhileStatement(cond, _statementsList);
    }
}
