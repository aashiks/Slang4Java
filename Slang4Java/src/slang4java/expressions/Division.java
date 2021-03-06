/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java.expressions;

import slang4java.contexts.CompilationContext;
import slang4java.contexts.RuntimeContext;
import slang4java.metainfo.SymbolInfo;
import slang4java.metainfo.TypeInfo;

/**
 *
 * @author aashiks
 */
public class Division extends AbstractExpression {

    // Two expressions and associated type information
    private AbstractExpression leftExpr, rightExpr;
    TypeInfo _type;

    public Division(AbstractExpression left, AbstractExpression right) {
        leftExpr = left;
        rightExpr = right;
    }

    @Override
    public SymbolInfo Evaluate(RuntimeContext cont) throws Exception {

        SymbolInfo eval_left = leftExpr.Evaluate(cont);
        SymbolInfo eval_right = rightExpr.Evaluate(cont);

        // Allow only numeric divison
        if ((eval_left.Type == TypeInfo.TYPE_NUMERIC)
                && (eval_right.Type == TypeInfo.TYPE_NUMERIC)) {
            SymbolInfo retval = new SymbolInfo();
            retval.DoubleValue = eval_left.DoubleValue / eval_right.DoubleValue;
            retval.Type = TypeInfo.TYPE_NUMERIC;
            retval.SymbolName = "";
            return retval;
        } else {
            throw new Exception("Type mismatch");
        }


    }

    //allows only numeric 
    @Override
    public TypeInfo TypeCheck(CompilationContext cont) throws Exception {
        TypeInfo eval_leftTypeInfo = leftExpr.TypeCheck(cont);
        TypeInfo eval_rightTypeInfo = rightExpr.TypeCheck(cont);

        if ((eval_leftTypeInfo == eval_rightTypeInfo) && (eval_leftTypeInfo == TypeInfo.TYPE_NUMERIC)) {
            _type = eval_leftTypeInfo;
            return _type;
        } else {
            throw new Exception("Division -Type mismatch failure");

        }
    }

    @Override
    public TypeInfo GetType() {
        return _type;
    } 
    
    
}
