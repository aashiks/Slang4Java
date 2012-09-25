/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java;

/**
 *
 * @author aashiks
 */
public class RelationalExpression extends Expression {

    //which operator
    RelationalOperator m_op;
    TypeInfo _type;
    //Operand type
    TypeInfo _opType;
    // Two expressions and associated type information
    private Expression leftExpr, rightExpr;

    public RelationalExpression(RelationalOperator op, Expression left, Expression right) {
        leftExpr = left;
        rightExpr = right;
        m_op = op;
    }

    @Override
    public SymbolInfo Evaluate(RuntimeContext cont) throws Exception {
        SymbolInfo eval_left = leftExpr.Evaluate(cont);
        SymbolInfo eval_right = rightExpr.Evaluate(cont);

        SymbolInfo retval = new SymbolInfo();

        if (eval_left.Type == TypeInfo.TYPE_NUMERIC
                && eval_right.Type == TypeInfo.TYPE_NUMERIC) {

            retval.Type = TypeInfo.TYPE_BOOL;
            retval.SymbolName = "";

            if (m_op == RelationalOperator.TOK_EQ) {
                retval.BoolValue = eval_left.DoubleValue == eval_right.DoubleValue;
            } else if (m_op == RelationalOperator.TOK_NEQ) {
                retval.BoolValue = eval_left.DoubleValue != eval_right.DoubleValue;
            } else if (m_op == RelationalOperator.TOK_GT) {
                retval.BoolValue = eval_left.DoubleValue > eval_right.DoubleValue;
            } else if (m_op == RelationalOperator.TOK_GTE) {
                retval.BoolValue = eval_left.DoubleValue >= eval_right.DoubleValue;
            } else if (m_op == RelationalOperator.TOK_LTE) {
                retval.BoolValue = eval_left.DoubleValue <= eval_right.DoubleValue;
            } else if (m_op == RelationalOperator.TOK_LT) {
                retval.BoolValue = eval_left.DoubleValue < eval_right.DoubleValue;
            }
            return retval;

        } else if (eval_left.Type == TypeInfo.TYPE_STRING
                && eval_right.Type == TypeInfo.TYPE_STRING) {

            retval.Type = TypeInfo.TYPE_BOOL;
            retval.SymbolName = "";

            if (m_op == RelationalOperator.TOK_EQ) {
                retval.BoolValue = (eval_left.StringValue.compareTo(eval_right.StringValue) == 0) ? true : false;

            } else if (m_op == RelationalOperator.TOK_NEQ) {
                retval.BoolValue = eval_left.StringValue.compareTo(eval_right.StringValue) != 0;

            } else {
                retval.BoolValue = false;

            }


            return retval;

        }
        if (eval_left.Type == TypeInfo.TYPE_BOOL
                && eval_right.Type == TypeInfo.TYPE_BOOL) {

            retval.Type = TypeInfo.TYPE_BOOL;
            retval.SymbolName = "";

            if (m_op == RelationalOperator.TOK_EQ) {
                retval.BoolValue = eval_left.BoolValue == eval_right.BoolValue;
            } else if (m_op == RelationalOperator.TOK_NEQ) {
                retval.BoolValue = eval_left.BoolValue != eval_right.BoolValue;
            } else {
                retval.BoolValue = false;

            }
            return retval;

        }
        return null;
    }

    @Override
    public TypeInfo TypeCheck(CompilationContext cont) throws Exception {
        TypeInfo eval_leftTypeInfo = leftExpr.TypeCheck(cont);
        TypeInfo eval_rightTypeInfo = rightExpr.TypeCheck(cont);

        if (eval_leftTypeInfo != eval_rightTypeInfo) {
            throw new Exception("Wrong Type in expression");
        }

        if (eval_leftTypeInfo == TypeInfo.TYPE_STRING
                && (!(m_op == RelationalOperator.TOK_EQ
                || m_op == RelationalOperator.TOK_NEQ))) {
            throw new Exception("Only == and != supported for string type ");
        }

        if (eval_leftTypeInfo == TypeInfo.TYPE_BOOL
                && (!(m_op == RelationalOperator.TOK_EQ
                || m_op == RelationalOperator.TOK_NEQ))) {
            throw new Exception("Only == and != supported for boolean type ");
        }
        // store the operand type as well
        _opType = eval_leftTypeInfo;
        _type = TypeInfo.TYPE_BOOL;
        return _type;
    }

    @Override
    public TypeInfo GetType() {
        return _type;
    }
}
