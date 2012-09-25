/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java;

/**
 *
 * @author aashiks TODO: figure out how to give explicit int values to tokens (
 * like in C) use constructors ?
 */
public enum Token {

    ILLEGAL_TOKEN, // Not a Token
    TOK_PLUS, // '+'
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
    TOK_SEMI, // ; 

    //---------- Addition in Step 4
    TOK_VAR_NUMBER, // NUMBER data type
    TOK_VAR_STRING, // STRING data type
    TOK_VAR_BOOL, // Bool data type
    TOK_NUMERIC, // [0-9]+
    TOK_COMMENT, // Comment Token ( presently not used )
    TOK_BOOL_TRUE, // Boolean TRUE
    TOK_BOOL_FALSE, // Boolean FALSE
    TOK_STRING, // String Literal
    TOK_ASSIGN, // Assignment Symbol =
    
    // ----------- Added in Step 6 
    // for relational operator support

    TOK_EQ,                // '=='
    TOK_NEQ,               // '<>'
    TOK_GT,                // '>'
    TOK_GTE,               // '>='
    TOK_LT,                // '<'
    TOK_LTE,               // '<='
    TOK_AND,               // '&&'
    TOK_OR,                // '||'
    TOK_NOT,               // '!'

    //------------ Added in Step 6 for 
    // Control structures support

    TOK_IF,                // IF 
    TOK_THEN,              // Then  
    TOK_ELSE,              // Else Statement
    TOK_ENDIF,             // Endif Statement
    TOK_WHILE,             // WHILE
    TOK_WEND              // Wend Statement
}
