/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java.contexts;

import slang4java.compilationunits.TModule;
import slang4java.metainfo.SymbolTable;

/**
 *
 * @author aashiks
 */
/**
 * One can store the stack frame inside this class
 */
public class RuntimeContext {

    private SymbolTable symbolTable;
    private TModule _prog = null;

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public TModule GetProgram() {
        return _prog;

    }
    

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    public RuntimeContext(TModule program) {
        symbolTable = new SymbolTable();
        _prog = program;
    }
}
