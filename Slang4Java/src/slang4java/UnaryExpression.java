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
    private OPERATOR _op;

    public UnaryExpression(Expression a, OPERATOR op) {
        _ex1 = a;
        _op = op;
    }

    @Override
    public double Evaluate(RUNTIME_CONTEXT cont) {
        switch (_op) {
            case PLUS:
                return _ex1.Evaluate(cont);
            case MINUS:
                return -_ex1.Evaluate(cont);
        }

        return Double.NaN;
    }
}
