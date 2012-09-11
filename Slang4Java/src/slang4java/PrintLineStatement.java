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
    public SymbolInfo Execute(RuntimeContext cont) throws Exception {
    
        SymbolInfo val = _ex.Evaluate(cont);
        
        if (val.Type == TypeInfo.TYPE_NUMERIC){
            System.out.println(val.DoubleValue);
        }
        else if(val.Type == TypeInfo.TYPE_STRING){
            System.out.println(val.StringValue);
        }
        else {
            System.out.println(val.BoolValue);
        }
        return null;
    }
}
