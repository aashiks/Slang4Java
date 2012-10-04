/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java.expressions;

import slang4java.contexts.CompilationContext;
import slang4java.contexts.RuntimeContext;
import slang4java.metainfo.SymbolInfo;
import slang4java.metainfo.TypeInfo;

/**
 *
 * @author aashiks
 */
//Logical !
public class LogicalNot extends AbstractExpression {

    private AbstractExpression _ex;
    TypeInfo _type;

    public LogicalNot(AbstractExpression e) {
        _ex = e;
    }

    @Override
    public SymbolInfo Evaluate(RuntimeContext cont) throws Exception {
        SymbolInfo evalExp = _ex.Evaluate(cont);
        if (evalExp.Type == TypeInfo.TYPE_BOOL) {
            SymbolInfo retval = new SymbolInfo();
            retval.Type = TypeInfo.TYPE_BOOL;
            retval.SymbolName = "";
            retval.BoolValue = (!evalExp.BoolValue);
            return retval;
        }
        return null;
    }

    @Override
    public TypeInfo TypeCheck(CompilationContext cont) throws Exception {
        TypeInfo eval_left = _ex.TypeCheck(cont);

        if (eval_left == TypeInfo.TYPE_BOOL) {
            _type = TypeInfo.TYPE_BOOL;
            return _type;
        } else {
            throw new Exception("Wrong Type in expression");
        }
    }

    @Override
    public TypeInfo GetType() {
        return _type;
    }
}
