/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java.statements;

import java.util.ArrayList;
import slang4java.contexts.RuntimeContext;
import slang4java.expressions.AbstractExpression;
import slang4java.metainfo.SymbolInfo;
import slang4java.metainfo.TypeInfo;

/**
 *
 * @author aashiks
 */
public class IfStatement extends Statement {

    //    conditional expression
    //    the type ought to be boolean
    private AbstractExpression cond;
    //    List of statements to be 
    //    executed if conditoon is true
    private ArrayList _ifStatementsList;
    //   List of statements to be 
    //   executed if cond is false
    private ArrayList _elseStatementsList;

    public IfStatement(AbstractExpression e, ArrayList ifList, ArrayList elseList) {
        cond = e;
        _ifStatementsList = ifList;
        _elseStatementsList = elseList;
    }

    @Override
    public SymbolInfo Execute(RuntimeContext cont) throws Exception {
        SymbolInfo m_cond = cond.Evaluate(cont);
        if ((m_cond == null) || (m_cond.Type != TypeInfo.TYPE_BOOL)) {
            return null;
        }
        if (m_cond.BoolValue == true) {
            for (Object s : _ifStatementsList) {
                Statement statement = (Statement) s;
                statement.Execute(cont);
            }
        } else if (_elseStatementsList != null) {
            for (Object s : _elseStatementsList) {
                Statement statement = (Statement) s;
                statement.Execute(cont);
            }
        }
        return null;
    }
}
