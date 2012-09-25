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
public class Procedure extends AbstractProcedure {

    //   Procedure name ..which defaults to Main 
    //   in the type MainClass
    public String m_name;
    //   Formal parameters...
    public ArrayList m_formals = null;
    //    List of statements which comprises the Procedure
    public ArrayList m_statements = null;
    //    Local variables
    public SymbolTable m_locals = null;
    //       return_value.... a hard coded zero at this
    //       point of time..
    public SymbolInfo return_value = null;
    //      TypeInfo => TYPE_NUMERIC
    public TypeInfo _type = TypeInfo.TYPE_ILLEGAL;

    //
    public Procedure(String name,
            ArrayList stats,
            SymbolTable locals,
            TypeInfo type) {
        m_name = name;
        m_formals = null;
        m_statements = stats;
        m_locals = locals;
        _type = type;
    }

    public String getM_name() {
        return m_name;
    }

    public ArrayList getM_formals() {
        return m_formals;
    }

    public TypeInfo getType() {
        return _type;
    }

    
    public SymbolInfo ReturnValue() {
        return return_value;
    }

    public TypeInfo TypeCheck(CompilationContext cont) {

        return TypeInfo.TYPE_NUMERIC;
    }

    @Override
    public SymbolInfo Execute(RuntimeContext cont) throws Exception {

        for (Object o : m_statements) {
            Statement stmt = (Statement) o;
            stmt.Execute(cont);
        }
        return null;

    }
}
