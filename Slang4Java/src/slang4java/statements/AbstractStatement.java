/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java.statements;

import slang4java.contexts.RuntimeContext;
import slang4java.generators.IGenerator;
import slang4java.metainfo.SymbolInfo;

/**
 *
 * @author aashiks
 * 
 * A statement is what you execute for it's effect
 */
public abstract class AbstractStatement {
    public abstract SymbolInfo Execute(RuntimeContext cont) throws Exception;
    public abstract String Generate(IGenerator g);
}
