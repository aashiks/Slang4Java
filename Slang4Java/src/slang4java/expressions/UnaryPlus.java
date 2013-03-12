/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java.expressions;

import slang4java.contexts.CompilationContext;
import slang4java.contexts.RuntimeContext;
import slang4java.generators.IGenerator;
import slang4java.metainfo.SymbolInfo;
import slang4java.metainfo.TypeInfo;

/**
 *
 * @author aashiks
 */
public class UnaryPlus extends AbstractExpression {

    // Two expressions and associated type information
    private AbstractExpression expr;
    TypeInfo _type;

    public UnaryPlus(AbstractExpression e) {
        expr = e;
    }

    @Override
    public SymbolInfo Evaluate(RuntimeContext cont) throws Exception {

        SymbolInfo eval_expr = expr.Evaluate(cont);

        // Allow only numeric 
        if (eval_expr.Type == TypeInfo.TYPE_NUMERIC) {
            SymbolInfo retval = new SymbolInfo();
            retval.DoubleValue = eval_expr.DoubleValue;
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
        TypeInfo eval_exprType = expr.TypeCheck(cont);


        if (eval_exprType == TypeInfo.TYPE_NUMERIC) {
            _type = eval_exprType;
            return _type;
        } else {
            throw new Exception("Unary Plus Type mismatch failure");

        }
    }

    @Override
    public TypeInfo GetType() {
        return _type;
    }

    @Override
    public String Generate(IGenerator g) {
        return g.UnaryPlus(expr);
    }
}
