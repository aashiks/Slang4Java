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
         // Abstract Syntax Tree (AST) for 5*10
            Expression e = new BinaryExpression(new NumericConstant(5),
                                   new NumericConstant(10),
                                   OPERATOR.MUL);

            //
            // Evaluate the Expression
            //
            //
            System.out.println(e.Evaluate(null));

            // AST for  (10 + (30 + 50 ) )

            e = new UnaryExpression(
                         new BinaryExpression(new NumericConstant(10),
                             new BinaryExpression(new NumericConstant(30),
                                           new NumericConstant(50),
                                  OPERATOR.PLUS),
                         OPERATOR.PLUS),
                     OPERATOR.MINUS);

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
