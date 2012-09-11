/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java;

/**
 *
 * @author aashiks
 */
public class AssignmentStatement extends Statement {

    private Variable variable;
    private Expression expr;

    public AssignmentStatement(Variable var, Expression e) {
        variable = var;
        expr = e;

    }

    public AssignmentStatement(SymbolInfo var, Expression e) {
        variable = new Variable(var);
        expr = e;

    }

    @Override
    public SymbolInfo Execute(RuntimeContext cont) throws Exception {
        SymbolInfo val = expr.Evaluate(cont);
        cont.getSymbolTable().Assign(variable, val);
        return null;
    }
}
