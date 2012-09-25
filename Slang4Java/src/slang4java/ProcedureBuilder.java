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
public class ProcedureBuilder extends AbstractBuilder {

    ///    Procedure name ..now it is hard coded
    ///    to MAIN
    private String proc_name = "";
    ///    Compilation context for type analysis
    CompilationContext ctx = null;
    ///    Procedure does not take any argument..
    ArrayList m_formals = null;
    ///    Array of Statements
    ArrayList m_stmts = new ArrayList();
    ///    Return Type of the procedure
    TypeInfo inf = TypeInfo.TYPE_ILLEGAL;

    public ProcedureBuilder(String name, CompilationContext _ctx) {
        ctx = _ctx;
        proc_name = name;
    }

    public boolean AddLocal(SymbolInfo info) {
        ctx.getSymbolTable().Add(info);
        return true;
    }

    public TypeInfo TypeCheck(Expression e) throws Exception {
        return e.TypeCheck(ctx);
    }

    public void AddStatement(Statement st) {
        m_stmts.add(st);
    }

    public SymbolInfo GetSymbol(String strname) {

        return ctx.getSymbolTable().Get(strname);

    }

    public boolean CheckProto(String name) {
        return true;

    }

    public TypeInfo getInf() {
        return inf;
    }

    public void setInf(TypeInfo inf) {
        this.inf = inf;
    }

    public SymbolTable getSymbolTable() {
        return ctx.getSymbolTable();
    }

    public CompilationContext getContext() {
        return ctx;
    }

    public String getProc_name() {
        return proc_name;
    }

    public void setProc_name(String proc_name) {
        this.proc_name = proc_name;
    }

    public Procedure GetProcedure() {
        Procedure ret = new Procedure(proc_name,
                m_stmts, ctx.getSymbolTable(), inf);
        return ret;
    }
}
