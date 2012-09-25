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
public class FunctionInfo {

    public TypeInfo _ret_value;
    public String _name;
    public ArrayList _typeinfo;

    public FunctionInfo(String name, TypeInfo ret_value, ArrayList formals) {

        _ret_value = ret_value;
        _typeinfo = formals;
        _name = name;
    }
}
