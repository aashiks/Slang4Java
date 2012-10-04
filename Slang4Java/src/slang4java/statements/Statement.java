/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java.statements;

import slang4java.contexts.RuntimeContext;
import slang4java.metainfo.SymbolInfo;

/**
 *
 * @author aashiks
 * 
 * A statement is what you execute for it's effect
 */
public abstract class Statement {
    public abstract SymbolInfo Execute(RuntimeContext cont) throws Exception;
}
