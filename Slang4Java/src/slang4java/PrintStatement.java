/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java;

/**
 *
 * @author aashiks
 * prints the value of an expression on to screen
 */
public class PrintStatement extends Statement {

    private Expression _ex;

    public PrintStatement(Expression ex) {
        _ex = ex;
    }
    @Override
    public boolean Execute(RuntimeContext con) {
        double a = _ex.Evaluate(con);
        System.out.print(a);
        return true;
    }
    
    
}
