/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java;

/**
 *
 * @author aashiks
 * 
 * A statement is what you execute for it's effect
 */
public abstract class Statement {
    public abstract boolean Execute(RuntimeContext con);
}
