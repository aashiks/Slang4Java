/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java.compilationunits;

import java.util.ArrayList;
import slang4java.contexts.RuntimeContext;
import slang4java.metainfo.SymbolInfo;

/**
 *
 * @author aashiks
 */
public abstract class AbstractCompilationUnit {
     public abstract SymbolInfo Execute(RuntimeContext cont, ArrayList actuals) throws Exception;
}
