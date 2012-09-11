/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java;

/**
 *
 * @author aashiks
 */
public class VariableDeclStatement extends Statement {

    SymbolInfo m_inf = null;
    Variable var = null;

    public VariableDeclStatement(SymbolInfo inf){
        m_inf = inf;
    }
    @Override
    public SymbolInfo Execute(RuntimeContext cont) throws Exception {
       cont.getSymbolTable().Add(m_inf); 
       var = new Variable(m_inf);
       return null;       
    }
}
