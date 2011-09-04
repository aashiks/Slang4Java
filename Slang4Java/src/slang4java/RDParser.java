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
public class RDParser extends Lexer {

    Token Current_Token;
    Token Last_Token;

    public RDParser(String Expr) {
        super(Expr);
    }

   
    public Expression CallExpression() throws Exception {
        Token tok = GetToken();
        Current_Token = GetToken();
        return Expr();
    }

  
    ///    Here we remember the last token ..before we 
    ///    move on to the next token..
       protected Token GetNext() throws Exception {
        Last_Token = Current_Token;
        Current_Token = GetToken();
        return Current_Token;
    }

    public Expression Expr() throws Exception {
        Token l_token;
        Expression RetValue = Term();
        while (Current_Token == Token.TOK_PLUS || Current_Token == Token.TOK_SUB) {
            l_token = Current_Token;
            Current_Token = GetToken();
            Expression e1 = Expr();
            RetValue = new BinaryExpression(RetValue, e1,
                    l_token == Token.TOK_PLUS ? Operator.PLUS : Operator.MINUS);

        }

        return RetValue;

    }
   

    public Expression Term() throws Exception {
        Token l_token;
        Expression RetValue = Factor();

        while (Current_Token == Token.TOK_MUL || Current_Token == Token.TOK_DIV) {
            l_token = Current_Token;
            Current_Token = GetToken();
            Expression e1 = Term();
            RetValue = new BinaryExpression(RetValue, e1,
                    l_token == Token.TOK_MUL ? Operator.MUL : Operator.DIV);

        }

        return RetValue;
    }

    /// <summary>
    ///    
    /// </summary>
    public Expression Factor() throws Exception {
        Token l_token;
        Expression RetValue = null;
        if (Current_Token == Token.TOK_DOUBLE) {

            RetValue = new NumericConstant(GetNumber());
            Current_Token = GetToken();

        } else if (Current_Token == Token.TOK_OPAREN) {

            Current_Token = GetToken();

            RetValue = Expr();  // Recurse

            if (Current_Token != Token.TOK_CPAREN) {
                System.out.println("Missing Closing Parenthesis\n");
                throw new Exception();

            }
            Current_Token = GetToken();
        } else if (Current_Token == Token.TOK_PLUS || Current_Token == Token.TOK_SUB) {
            l_token = Current_Token;
            Current_Token = GetToken();
            RetValue = Factor();

            RetValue = new UnaryExpression(RetValue,
                    l_token == Token.TOK_PLUS ? Operator.PLUS : Operator.MINUS);
        } else {

            System.out.println("Illegal Token");
            throw new Exception();
        }


        return RetValue;

    }

  
    ///   The new Parser entry point
   
    public ArrayList Parse() throws Exception {
        GetNext();  // Get the Next Token
        //
        // Parse all the statements
        //
        return StatementList();
    }   
    
    ///  The Grammar is 
    ///  
    ///  <stmtlist> :=  { <statement> }+
    ///
    ///  {<statement> :=  <printstmt> | <printlinestmt>
    ///  <printstmt> :=  print   <expr >;
    ///
    /// <printlinestmt>:= printline <expr>;
    ///    
    /// <Expr>  ::=  <Term> | <Term> { + | - } <Expr>
    /// <Term> ::=  <Factor> | <Factor>  {*|/} <Term>
    /// <Factor>::=  <number> | ( <expr> ) | {+|-} <factor>
    ///       
    private ArrayList StatementList() throws Exception {
        ArrayList arr = new ArrayList();
        while (Current_Token != Token.TOK_NULL) {
            Statement temp = Statement();
            if (temp != null) {
                arr.add(temp);
            }
        }
        return arr;
    }


    ///    This Routine Queries Statement Type 
    ///    to take the appropriate Branch...
    ///    Currently , only Print and PrintLine statement
    ///    are supported..
    ///    if a line does not start with Print or PrintLine ..
    ///    an exception is thrown
   
    private Statement Statement() throws Exception {
        Statement retval = null;
        switch (Current_Token) {
            case TOK_PRINT:
                retval = ParsePrintStatement();
                GetNext();
                break;
            case TOK_PRINTLN:
                retval = ParsePrintLNStatement();
                GetNext();
                break;
            default:
                throw new Exception("Invalid statement");
               
        }
        return retval;
    }
   
    ///    Parse the Print Staement .. The grammar is 
    ///    PRINT <expr> ;
    ///    Once you are in this subroutine , we are expecting 
    ///    a valid expression ( which will be compiled ) and a
    ///    semi collon to terminate the line..
    ///    Once Parse Process is successful , we create a PrintStatement
    ///    Object..
   

    private Statement ParsePrintStatement() throws Exception {
        GetNext();
        Expression a = Expr();

        if (Current_Token != Token.TOK_SEMI) {
            throw new Exception("; is expected");
        }
        return new PrintStatement(a);
    }
    
    ///    Parse the PrintLine Staement .. The grammar is 
    ///    PRINTLINE <expr> ;
    ///    Once you are in this subroutine , we are expecting 
    ///    a valid expression ( which will be compiled ) and a
    ///    semi collon to terminate the line..
    ///    Once Parse Process is successful , we create a PrintLineStatement
    ///    Object..
   

    private Statement ParsePrintLNStatement() throws Exception {
        GetNext();
        Expression a = Expr();

        if (Current_Token != Token.TOK_SEMI) {
            throw new Exception("; is expected");
        }
        return new PrintLineStatement(a);
    }
}
