/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java.contexts;

import slang4java.metainfo.SymbolTable;

/**
 *
 * @author aashiks
 */
public class CompilationContext {

    private SymbolTable symbolTable;

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

   

    public CompilationContext() {
        symbolTable = new SymbolTable();
    }
    
}
