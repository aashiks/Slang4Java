/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java.expressions;

import slang4java.contexts.CompilationContext;
import slang4java.contexts.RuntimeContext;
import slang4java.generators.IGenerator;
import slang4java.metainfo.SymbolInfo;
import slang4java.metainfo.TypeInfo;

/**
 *
 * @author aashiks
 */
public class Variable extends AbstractExpression {

    private String m_name;  // Var name
    TypeInfo _type;        // Type 

    public String GetName() {
        return m_name;
    }

    public void SetName(String s) {
        m_name = s;
    }
    public Variable(SymbolInfo inf) {
        m_name = inf.SymbolName;

    }

    public Variable(CompilationContext st, String name, double _value) {
        SymbolInfo s = new SymbolInfo();
        s.SymbolName = name;
        s.Type = TypeInfo.TYPE_NUMERIC;
        s.DoubleValue = _value;
        st.getSymbolTable().Add(s);
        m_name = name;
    }

    public Variable(CompilationContext st, String name, String _value) {
        SymbolInfo s = new SymbolInfo();
        s.SymbolName = name;
        s.Type = TypeInfo.TYPE_STRING;
        s.StringValue = _value;
        st.getSymbolTable().Add(s);
        m_name = name;
    }

    public Variable(CompilationContext st, String name, boolean _value) {
        SymbolInfo s = new SymbolInfo();
        s.SymbolName = name;
        s.Type = TypeInfo.TYPE_BOOL;
        s.BoolValue = _value;
        st.getSymbolTable().Add(s);
        m_name = name;
    }

    @Override
    public SymbolInfo Evaluate(RuntimeContext cont) {
        if (cont.getSymbolTable() == null) {
            return null;
        } else {
            SymbolInfo a = cont.getSymbolTable().Get(m_name);
            return a;
        }
    }

    @Override
    public TypeInfo TypeCheck(CompilationContext cont) {
        if (cont.getSymbolTable() == null) {
            return TypeInfo.TYPE_ILLEGAL;
        } else {
            SymbolInfo a = cont.getSymbolTable().Get(m_name);
            if (a != null) {
                _type = a.Type;
                return _type;

            }
            return TypeInfo.TYPE_ILLEGAL;

        }
    }

    
    //     this should only be called after the TypeCheck method
    //     has been invoked on AST 

    @Override
    public TypeInfo GetType() {
        return _type;
    }

    @Override
    public String Generate(IGenerator g) {
        return g.Variable(m_name);
    }
}
