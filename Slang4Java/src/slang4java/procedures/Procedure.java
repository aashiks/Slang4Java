/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java.procedures;

import java.util.ArrayList;
import slang4java.contexts.CompilationContext;
import slang4java.contexts.RuntimeContext;
import slang4java.generators.IGenerator;
import slang4java.metainfo.SymbolInfo;
import slang4java.metainfo.SymbolTable;
import slang4java.metainfo.TypeInfo;
import slang4java.statements.AbstractStatement;

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
    public Procedure(String name, ArrayList formals,
            ArrayList stats,
            SymbolTable locals,
            TypeInfo type) {
        m_name = name;
        m_formals = formals;
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

    public TypeInfo GetType() {
        return _type;
    }

    public SymbolInfo ReturnValue() {
        return return_value;
    }

    public TypeInfo TypeCheck(CompilationContext cont) {
        return TypeInfo.TYPE_NUMERIC;
    }

    @Override
    public SymbolInfo Execute(RuntimeContext cont, ArrayList actuals) throws Exception {

        ArrayList vars = new ArrayList();
        int i = 0;
        if (m_formals != null && actuals != null) {
            i = 0;
            for (Object o : m_formals) {
                SymbolInfo b = (SymbolInfo) o;
                SymbolInfo inf = (SymbolInfo) actuals.get(i);
                inf.SymbolName = b.SymbolName;
                cont.getSymbolTable().Add(inf);
                i++;
            }
        }
        for (Object o : m_statements) {
            AbstractStatement stmt = (AbstractStatement) o;

            return_value = stmt.Execute(cont);
            if (return_value != null) {
                return return_value;
            }
        }
        return null;
    }

    @Override
    public String Generate(IGenerator g) {
        return g.Procedure(m_name, m_formals, m_statements);
    }
}
