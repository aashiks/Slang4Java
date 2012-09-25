/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java;

import java.util.ArrayList;

/**
 *
 * @author aashiks
 */
public abstract class CompilationUnit {
     public abstract SymbolInfo Execute(RuntimeContext cont, ArrayList actuals) throws Exception;
}
