/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java;

/**
 *
 * @author aashiks
 */
public class UnaryMinus extends Expression {

    // Two expressions and associated type information
    private Expression expr;
    TypeInfo _type;

    public UnaryMinus(Expression e) {
        expr = e;
    }

    @Override
    public SymbolInfo Evaluate(RuntimeContext cont) throws Exception {

        SymbolInfo eval_expr = expr.Evaluate(cont);

        // Allow only numeric substraction
        if (eval_expr.Type == TypeInfo.TYPE_NUMERIC) {
            SymbolInfo retval = new SymbolInfo();
            retval.DoubleValue = (-1)*eval_expr.DoubleValue;
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
            throw new Exception("Type mismatch failure");

        }
    }

    @Override
    public TypeInfo GetType() {
        return _type;
    }
    
}
