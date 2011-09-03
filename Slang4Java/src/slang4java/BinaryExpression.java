/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java;

/**
 *
 * @author aashiks
 * This class supports Binary Operators like + , - , / , *
 */
public class BinaryExpression extends Expression {

    private Expression _ex1, _ex2;
    private OPERATOR _op;

    public BinaryExpression(Expression a, Expression b, OPERATOR op) {
        _ex1 = a;
        _ex2 = b;
        _op = op;
    }

    @Override
    public double Evaluate(RUNTIME_CONTEXT cont) {


        switch (_op) {
            case PLUS:
                return _ex1.Evaluate(cont) + _ex2.Evaluate(cont);
            case MINUS:
                return _ex1.Evaluate(cont) - _ex2.Evaluate(cont);
            case DIV:
                return _ex1.Evaluate(cont) / _ex2.Evaluate(cont);
            case MUL:
                return _ex1.Evaluate(cont) * _ex2.Evaluate(cont);
        }

        return Double.NaN;

    }
}
