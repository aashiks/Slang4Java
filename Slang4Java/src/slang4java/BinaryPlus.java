/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java;

/**
 *
 * @author aashiks
 */
public class BinaryPlus extends Expression {

    // Two expressions and associated type information
    private Expression leftExpr, rightExpr;
    TypeInfo _type;

    public BinaryPlus(Expression left, Expression right) {
        leftExpr = left;
        rightExpr = right;
    }

    @Override
    public SymbolInfo Evaluate(RuntimeContext cont) throws Exception {

        SymbolInfo eval_left = leftExpr.Evaluate(cont);
        SymbolInfo eval_right = rightExpr.Evaluate(cont);

        // Implement String concat logic
        if ((eval_left.Type == TypeInfo.TYPE_STRING)
                && (eval_right.Type == TypeInfo.TYPE_STRING)) {
            SymbolInfo retval = new SymbolInfo();
            retval.StringValue = eval_left.StringValue + eval_right.StringValue;
            retval.Type = TypeInfo.TYPE_STRING;
            retval.SymbolName = "";
            return retval;
        } else if ((eval_left.Type == TypeInfo.TYPE_NUMERIC)
                && (eval_right.Type == TypeInfo.TYPE_NUMERIC)) {
            SymbolInfo retval = new SymbolInfo();
            retval.DoubleValue = eval_left.DoubleValue + eval_right.DoubleValue;
            retval.Type = TypeInfo.TYPE_NUMERIC;
            retval.SymbolName = "";
            return retval;
        } else {
            throw new Exception("Type mismatch");
        }


    }

    //allows only numeric and string type
    @Override
    public TypeInfo TypeCheck(CompilationContext cont) throws Exception {
        TypeInfo eval_leftTypeInfo = leftExpr.TypeCheck(cont);
        TypeInfo eval_rightTypeInfo = rightExpr.TypeCheck(cont);

        if ((eval_leftTypeInfo == eval_rightTypeInfo) && (eval_leftTypeInfo != TypeInfo.TYPE_BOOL)) {
            _type = eval_leftTypeInfo;
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
