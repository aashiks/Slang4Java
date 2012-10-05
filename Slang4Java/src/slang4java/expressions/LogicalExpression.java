/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java.expressions;

import slang4java.contexts.CompilationContext;
import slang4java.contexts.RuntimeContext;
import slang4java.metainfo.Token;
import slang4java.metainfo.SymbolInfo;
import slang4java.metainfo.TypeInfo;

/**
 *
 * @author aashiks
 */
//    && ( AND ) , || ( OR )
public class LogicalExpression extends AbstractExpression {

    //which operator
    Token m_op;
    TypeInfo _type;
    // Two expressions and associated type information
    private AbstractExpression leftExpr, rightExpr;

    public LogicalExpression(Token op, AbstractExpression left, AbstractExpression right) {
        leftExpr = left;
        rightExpr = right;
        m_op = op;
    }

    @Override
    public SymbolInfo Evaluate(RuntimeContext cont) throws Exception {
        SymbolInfo eval_left = leftExpr.Evaluate(cont);
        SymbolInfo eval_right = rightExpr.Evaluate(cont);

        if (eval_left.Type == TypeInfo.TYPE_BOOL
                && eval_right.Type == TypeInfo.TYPE_BOOL) {
            SymbolInfo ret_val = new SymbolInfo();
            ret_val.Type = TypeInfo.TYPE_BOOL;
            ret_val.SymbolName = "";

            if (m_op == Token.TOK_AND) {
                ret_val.BoolValue = (eval_left.BoolValue && eval_right.BoolValue);
            } else if (m_op == Token.TOK_OR) {
                ret_val.BoolValue = (eval_left.BoolValue || eval_right.BoolValue);
            } else {
                return null;
            }
            return ret_val;
        }
        return null;
    }

    @Override
    public TypeInfo TypeCheck(CompilationContext cont) throws Exception {
        TypeInfo eval_leftTypeInfo = leftExpr.TypeCheck(cont);
        TypeInfo eval_rightTypeInfo = rightExpr.TypeCheck(cont);

        if ((eval_leftTypeInfo == eval_rightTypeInfo) && (eval_leftTypeInfo == TypeInfo.TYPE_BOOL)) {
            _type = eval_leftTypeInfo;
            return _type;
        } else {
            throw new Exception("Wrong Type in expression");

        }
    }

    @Override
    public TypeInfo GetType() {
        return _type;
    }
}
