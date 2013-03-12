/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java.builders;

import java.util.ArrayList;
import slang4java.contexts.CompilationContext;
import slang4java.expressions.AbstractExpression;
import slang4java.metainfo.SymbolInfo;
import slang4java.metainfo.SymbolTable;
import slang4java.metainfo.TypeInfo;
import slang4java.procedures.Procedure;
import slang4java.statements.AbstractStatement;

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
    ArrayList m_formals = new ArrayList();
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

    public boolean  AddFormals(SymbolInfo info) {
        m_formals.add(info);
        return true;
    }

    public TypeInfo TypeCheck(AbstractExpression e) throws Exception {
        return e.TypeCheck(ctx);
    }

    public void AddStatement(AbstractStatement st) {
        m_stmts.add(st);
    }

    public SymbolInfo GetSymbol(String strname) {

        return ctx.getSymbolTable().Get(strname);

    }

    public boolean CheckProto(String name) {
        return true;

    }

    public TypeInfo getTypeInfo() {
        return inf;
    }

    public void setTypeInfo(TypeInfo info) {
        this.inf = info;
    }

    public SymbolTable getSymbolTable() {
        return ctx.getSymbolTable();
    }

    public CompilationContext getContext() {
        return ctx;
    }

    public String getProcedureName() {
        return proc_name;
    }

    public void setProcedureName(String procName) {
        this.proc_name = procName;
    }

    public Procedure GetProcedure() {
        Procedure ret = new Procedure(proc_name,m_formals,
                m_stmts, ctx.getSymbolTable(), inf);
        return ret;
    }
}
