/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java;

/**
 *
 * @author aashiks
 */
public class ExpressionBuilder extends AbstractBuilder {

    public String _expr_string;

    public ExpressionBuilder(String expr) {
        _expr_string = expr;


    }

    public Expression GetExpression() {
        try {
            RDParser p = new RDParser(_expr_string);
            return p.CallExpression();
        } catch (Exception e) {
            return null;
        }
    }
}
