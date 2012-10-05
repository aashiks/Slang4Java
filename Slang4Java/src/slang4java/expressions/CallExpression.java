/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java.expressions;

import java.util.ArrayList;
import slang4java.contexts.CompilationContext;
import slang4java.contexts.RuntimeContext;
import slang4java.metainfo.SymbolInfo;
import slang4java.metainfo.TypeInfo;
import slang4java.procedures.Procedure;

/**
 *
 * @author aashiks
 */
public class CallExpression extends AbstractExpression {

    //    Procedure Object
    Procedure m_proc;
    //   ArrayList of Actuals
    ArrayList m_actuals;
    //    procedure name ...
    String _procname;
    //    Is it  a Recursive Call ?
    boolean _isrecurse;
    //    Return type of the Function
    TypeInfo _type;

    //    Ctor to be called when we make a ordinary
    //    subroutine call
    public CallExpression(Procedure proc, ArrayList actuals) {
        m_proc = proc;
        m_actuals = actuals;
    }
    //    Ctor to implement Recursive sub routine

    public CallExpression(String name, boolean recurse, ArrayList actuals,TypeInfo t) {
        _procname = name;
        if (recurse) {
            _isrecurse = true;
        }
        _type=t;
        m_actuals = actuals;
        //
        // For a recursive call Procedure Address will be null
        // During the interpretation time we will resolve the 
        // call by look up...
        //    m_proc = cont.GetProgram().Find(_procname);
        // This is a hack for implementing one pass compiler
        m_proc = null;
    }

    @Override
    public SymbolInfo Evaluate(RuntimeContext cont) throws Exception {
        if (m_proc != null) {
            //
            // This is a Ordinary Function Call
            //
            //
            RuntimeContext ctx = new RuntimeContext(cont.GetProgram());

            ArrayList lst = new ArrayList();

            for (Object o : m_actuals) {
                AbstractExpression ex = (AbstractExpression) o;
                lst.add(ex.Evaluate(cont));
            }

            return m_proc.Execute(ctx, lst);

        } else {
            // Recursive function call...by the time we 
            // reach here..whole program has already been 
            // parsed. Lookup the Function name table and 
            // resolve the Address
            //
            //
            m_proc = cont.GetProgram().Find(_procname);
            RuntimeContext ctx = new RuntimeContext(cont.GetProgram());
            ArrayList lst = new ArrayList();


            for (Object o : m_actuals) {
                AbstractExpression ex = (AbstractExpression) o;
                lst.add(ex.Evaluate(cont));
            }

            return m_proc.Execute(ctx, lst);


        }
    }

    @Override
    public TypeInfo TypeCheck(CompilationContext cont) {
        if (m_proc != null) {
            _type = m_proc.TypeCheck(cont);

        }   
            
        return _type;

    }

    @Override
    public TypeInfo GetType() {
        return _type;
    }
}
