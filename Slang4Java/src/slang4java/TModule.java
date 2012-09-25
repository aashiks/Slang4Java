/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java;

import java.util.ArrayList;

/**
 *
 * @author aashiks
 */
public class TModule extends CompilationUnit {

    //    A Program is a collection of Procedures...
    //    Now , we support only global function...
    private ArrayList m_procs = null;
    //    List of Compiled Procedures....
    //    At this point of time..only one procedure
    //    will be there....
    private ArrayList compiled_procs = null;

    public TModule(ArrayList procs) {
        m_procs = procs;

    }

    @Override
    public SymbolInfo Execute(RuntimeContext cont) throws Exception {
        Procedure p = Find("Main");

        if (p != null) {

            return p.Execute(cont);
        }

        return null;

    }

    public Procedure Find(String str) {
        for (Object p : m_procs) {
            String pname = ((Procedure) p).getM_name();

            if (pname.toUpperCase().compareTo(str.toUpperCase()) == 0) {
                return ((Procedure) p);
            }

        }

        return null;

    }
}
