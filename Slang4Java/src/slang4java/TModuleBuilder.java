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
public class TModuleBuilder extends AbstractBuilder {
    //  
    //     Array of Procs 
    //  

    private ArrayList procs;
    //  
    //    Array of Function Prototypes
    //    not much use as of now...
    //  
    private ArrayList protos = null;

    public TModuleBuilder() {
        procs = new ArrayList();
        protos = null;
    }

    //      Add Procedure
    public boolean Add(Procedure p) {
        procs.add(p);
        return true;
    }

    //       Create Program 
    public TModule GetProgram() {
        return new TModule(procs);
    }

    // 
    // 
    // 
    public Procedure GetProc(String name) {
        for (Object p : procs) {
            if (((Procedure) p).getM_name().equals(name)) {
                return ((Procedure) p);
            }

        }
        return null;
    }
}
