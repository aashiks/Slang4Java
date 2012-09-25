/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java;

import java.util.HashMap;

/**
 *
 * @author aashiks
 */
public class SymbolTable {

    HashMap<String, SymbolInfo> dt = new HashMap<String, SymbolInfo>();

    public boolean Add(SymbolInfo s) {
        dt.put(s.SymbolName, s);
        return true;

    }

    public SymbolInfo Get(String symbolname) {
        return dt.get(symbolname);
    }

    public void Assign(Variable var, SymbolInfo value) {
        value.SymbolName = var.GetName();
        dt.put(var.GetName(), value);

    }

    public void Assign(String var, SymbolInfo value) {
        dt.put(var, value);
    }
}
