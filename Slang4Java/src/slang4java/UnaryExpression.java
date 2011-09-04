/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java;

/**
 *
 * @author aashiks
 */
public class UnaryExpression extends Expression {

    private Expression _ex1;
    private Operator _op;

    public UnaryExpression(Expression a, Operator op) {
        _ex1 = a;
        _op = op;
    }

    @Override
    public double Evaluate(RuntimeContext cont) {
        switch (_op) {
            case PLUS:
                return _ex1.Evaluate(cont);
            case MINUS:
                return -_ex1.Evaluate(cont);
        }

        return Double.NaN;
    }
}
