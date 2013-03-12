/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java.statements;

import slang4java.contexts.RuntimeContext;
import slang4java.expressions.AbstractExpression;
import slang4java.expressions.Variable;
import slang4java.generators.IGenerator;
import slang4java.metainfo.SymbolInfo;

/**
 *
 * @author aashiks
 */
public class AssignmentStatement extends AbstractStatement {

    private Variable variable;
    private AbstractExpression expr;

    public AssignmentStatement(Variable var, AbstractExpression e) {
        variable = var;
        expr = e;

    }

    public AssignmentStatement(SymbolInfo var, AbstractExpression e) {
        variable = new Variable(var);
        expr = e;

    }

    @Override
    public SymbolInfo Execute(RuntimeContext cont) throws Exception {
        SymbolInfo val = expr.Evaluate(cont);
        
        
        cont.getSymbolTable().Assign(variable, val);
        return null;
    }

    @Override
    public String Generate(IGenerator g) {
        return g.AssignmentStatement(variable, expr);
    }
}
