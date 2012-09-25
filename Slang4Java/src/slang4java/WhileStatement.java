/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java;

import java.util.ArrayList;

/**
 *
 * @author aashiks
 */
public class WhileStatement extends Statement {
//    conditional expression
    //    the type ought to be boolean

    private Expression cond;
    //    List of statements to be 
    //    executed if conditoon is true
    private ArrayList _statementsList;

    public WhileStatement(Expression e, ArrayList statementList) {
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
                Statement rst = (Statement) o;
                tsp = rst.Execute(cont);
                if (tsp != null) {
                    return tsp;
                }
            }
        }

    }
}
