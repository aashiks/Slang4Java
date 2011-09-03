/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java;

/**
 *
 * @author aashiks
 * one can store number inside the class
 */
public class NumericConstant extends Expression {

    private double _value;
    /*
     *  Construction does not do much , just keeps the 
     *  value assigned to the private variable
     */

    public NumericConstant(double value) {
        _value = value;
    }
    
    /*
     *  While evaluating a numeric constant , return the _value
     */
    @Override
    public double Evaluate(RUNTIME_CONTEXT cont) {
                    return _value;
    }
}
