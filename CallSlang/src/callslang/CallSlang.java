/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package callslang;

import java.io.IOException;
import slang4java.*;

/**
 *
 * @author aashiks
 */
public class CallSlang {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        ExpressionBuilder b = new ExpressionBuilder("-2*((6+6)/3)");
        Expression e = b.GetExpression();
        //
        // Evaluate the Expression
        //
        System.out.println(e.Evaluate(null));

        //
        // Pause for a key stroke
        //
        System.in.read();
    }
}
