/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java;

/**
 *
 * @author aashiks
 * prints the value of an expression on to screen, 
 * moves cursor to next line
 */
public class PrintLineStatement extends Statement{

    private Expression _ex;

    public PrintLineStatement(Expression ex) {
        _ex = ex;
    }
    
    @Override
    public boolean Execute(RuntimeContext con) {
        double a = _ex.Evaluate(con);
        System.out.println(a);
        return true;
    }
}
