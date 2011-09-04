/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java;

/**
 *
 * @author aashiks
 * TODO: figure out how to give explicit int values to tokens ( like in C)
 * use constructors ?
 */
 public enum Token
    {
        ILLEGAL_TOKEN , // Not a Token
        TOK_PLUS , // '+'
        TOK_MUL, // '*'
        TOK_DIV, // '/'
        TOK_SUB, // '-'
        TOK_OPAREN, // '('
        TOK_CPAREN, // ')'
        TOK_DOUBLE, // 'number'
        TOK_NULL, // End of string
        TOK_PRINT, // Print Statement
        TOK_PRINTLN, // PrintLine
        TOK_UNQUOTED_STRING,
        TOK_SEMI // ; 
    }
