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
public class IfStatement extends AbstractStatement {

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
        SymbolInfo ret = null;
        if (m_cond.BoolValue == true) {
            for (Object s : _ifStatementsList) {
                AbstractStatement statement = (AbstractStatement) s;
                ret = statement.Execute(cont);
                if (ret != null) {
                    return ret;
                }
            }
        }

        if ((m_cond.BoolValue == false) && (_elseStatementsList != null)) {
            for (Object s : _elseStatementsList) {
                AbstractStatement statement = (AbstractStatement) s;
                ret = statement.Execute(cont);
                if (ret != null) {
                    return ret;
                }
            }
        }
        return null;
    }

    @Override
    public String Generate(IGenerator g) {
        return g.IfStatement(cond, _ifStatementsList, _elseStatementsList);
    }
}
