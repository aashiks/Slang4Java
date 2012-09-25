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
    //  
    private ArrayList protos = null;
    
    public TModuleBuilder() {
        procs = new ArrayList();
        protos = new ArrayList();
    }
    
    public boolean IsFunction(String name) {
        for (Object o : protos) {
            FunctionInfo fpinfo = (FunctionInfo) o;
            if (fpinfo._name.equals(name)) {
                return true;
            }
        }
        return false;
        
    }
    
    public void AddFunctionProtoType(String name, TypeInfo ret_type,
            ArrayList type_infos) {
        FunctionInfo info = new FunctionInfo(name, ret_type, type_infos);
        protos.add(info);
    }
    
    public boolean CheckFunctionProtoType(String name, TypeInfo ret_type, ArrayList type_infos) {
        for (Object o : protos) {
            FunctionInfo fpinfo = (FunctionInfo) o;
            if (fpinfo._name.equals(name)) {
                if (fpinfo._ret_value == ret_type) {
                    if (type_infos.size() == fpinfo._typeinfo.size()) {
                        int i;
                        for (i = 0; i < type_infos.size(); ++i) {
                            TypeInfo a = (TypeInfo) type_infos.get(i);
                            TypeInfo b = (TypeInfo) type_infos.get(i);
                            
                            if (a != b) {
                                return false;
                            }
                        }
                        return true;                        
                    }
                }                
            }
        }
        return false;
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
