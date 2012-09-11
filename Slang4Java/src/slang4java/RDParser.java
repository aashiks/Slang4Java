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

 

    public RDParser(String Expr) {
        super(Expr);
    }

    public Expression Expr(CompilationContext ctx) throws Exception{
        Token l_token;
        Expression RetValue = Term(ctx);
        while ( Current_Token == Token.TOK_PLUS || Current_Token == Token.TOK_SUB) {
            l_token = Current_Token;
            Current_Token = GetToken();
            Expression e1 = Expr(ctx);

            if (l_token == Token.TOK_PLUS) {
                RetValue = new BinaryPlus(RetValue, e1);
            } else {
                RetValue = new BinaryMinus(RetValue, e1);
            }
        }

        return RetValue;
    }

    public Expression Term(CompilationContext ctx) throws Exception {
        Token l_token;
        Expression RetValue = Factor(ctx);

        while (Current_Token == Token.TOK_MUL || Current_Token == Token.TOK_DIV) {
            l_token = Current_Token;
            Current_Token = GetToken();


            Expression e1 = Term(ctx);
            if (l_token == Token.TOK_MUL) {
                RetValue = new Multiply(RetValue, e1);
            } else {
                RetValue = new Division(RetValue, e1);
            }

        }

        return RetValue;
    }

    public Expression Factor(CompilationContext ctx) throws Exception{
        Token l_token;
        Expression RetValue = null;



        if (Current_Token == Token.TOK_NUMERIC) {

            RetValue = new NumericConstant(GetNumber());
            Current_Token = GetToken();

        } else if (Current_Token == Token.TOK_STRING) {
            RetValue = new StringLiteral(last_str);
            Current_Token = GetToken();
        } else if (Current_Token == Token.TOK_BOOL_FALSE
                || Current_Token == Token.TOK_BOOL_TRUE) {
            RetValue = new BooleanConstant(
                    Current_Token == Token.TOK_BOOL_TRUE ? true : false);
            Current_Token = GetToken();
        } else if (Current_Token == Token.TOK_OPAREN) {

            Current_Token = GetToken();

            RetValue = Expr(ctx);  // Recurse

            if (Current_Token != Token.TOK_CPAREN) {
                System.out.println("Missing Closing Parenthesis\n");
                throw new Exception();

            }
            Current_Token = GetToken();
        } else if (Current_Token == Token.TOK_PLUS || Current_Token == Token.TOK_SUB) {
            l_token = Current_Token;
            Current_Token = GetToken();
            RetValue = Factor(ctx);
            if (l_token == Token.TOK_PLUS) {
                RetValue = new UnaryPlus(RetValue);
            } else {
                RetValue = new UnaryMinus(RetValue);
            }

        } else if (Current_Token == Token.TOK_UNQUOTED_STRING) {
            ///
            ///  Variables 
            ///
            String str = super.last_str;
            SymbolInfo inf = ctx.getSymbolTable().Get(str);

            if (inf == null) {
                throw new Exception("Undefined symbol");
            }

            GetNext();
            RetValue = new Variable(inf);
        } else {

            System.out.println("Illegal Token");
            throw new Exception();
        }


        return RetValue;

    }

      /// <returns></returns>
        public ArrayList Parse(CompilationContext ctx) throws Exception
        {
            GetNext();  // Get the Next Token
            //
            // Parse all the statements
            //
            return StatementList(ctx);
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
    private ArrayList StatementList(CompilationContext ctx) throws Exception {
        ArrayList arr = new ArrayList();
            while (Current_Token != Token.TOK_NULL)
            {
                Statement temp = Statement(ctx);
                if (temp != null)
                {
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
    private Statement Statement(CompilationContext ctx) throws Exception {
        Statement retval = null;
            switch (Current_Token)
            {
                case TOK_VAR_STRING:
                case TOK_VAR_NUMBER:
                case TOK_VAR_BOOL:
                    retval = ParseVariableDeclStatement(ctx);
                    GetNext();
                    return retval;
                case TOK_PRINT:
                    retval = ParsePrintStatement(ctx);
                    GetNext();
                    break;
                case TOK_PRINTLN:
                    retval = ParsePrintLNStatement(ctx);
                    GetNext();
                    break;
                case TOK_UNQUOTED_STRING:
                    retval = ParseAssignmentStatement(ctx);
                    GetNext();
                    return retval;
  
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
    private Statement ParsePrintStatement(CompilationContext ctx) throws Exception {
        GetNext();
        Expression a = Expr(ctx);

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
    private Statement ParsePrintLNStatement(CompilationContext ctx) throws Exception {
        GetNext();
        Expression a = Expr(ctx);

        if (Current_Token != Token.TOK_SEMI) {
            throw new Exception("; is expected");
        }
        return new PrintLineStatement(a);
    }
    
    
    public Statement ParseVariableDeclStatement(CompilationContext ctx) throws Exception
        {
            
            //--- Save the Data type 
            Token tok = Current_Token;

            // --- Skip to the next token , the token ought 
            // to be a Variable name ( UnQouted String )
            GetNext();

            if (Current_Token == Token.TOK_UNQUOTED_STRING)
            {
                SymbolInfo symb = new SymbolInfo();
                symb.SymbolName = super.last_str;
                symb.Type = (tok == Token.TOK_VAR_BOOL) ?
                TypeInfo.TYPE_BOOL : (tok == Token.TOK_VAR_NUMBER) ?
                TypeInfo.TYPE_NUMERIC : TypeInfo.TYPE_STRING;

                //---------- Skip to Expect the SemiColon
                
                GetNext();



                if (Current_Token == Token.TOK_SEMI)
                {
                    // ----------- Add to the Symbol Table
                    // for type analysis 
                    ctx.getSymbolTable().Add(symb); 

                    // --------- return the Object of type
                    // --------- VariableDeclStatement
                    // This will just store the Variable name
                    // to be looked up in the above table
                    return new VariableDeclStatement(symb);
                }
                else
                {
                    CSyntaxErrorLog.AddLine("; expected");
                    CSyntaxErrorLog.AddLine(GetCurrentLine(SaveIndex()));
                    throw new CParserException(-100, ", or ; expected", SaveIndex());
                }
            }
            else
            {

                CSyntaxErrorLog.AddLine("invalid variable declaration");
                CSyntaxErrorLog.AddLine(GetCurrentLine(SaveIndex()));
                throw new CParserException(-100, ", or ; expected", SaveIndex());
            }

        }
    
    public Statement ParseAssignmentStatement(CompilationContext ctx) throws Exception
        {

            //
            // Retrieve the variable and look it up in 
            // the symbol table ..if not found throw exception
            //
            String variable = super.last_str;
            SymbolInfo s = ctx.getSymbolTable().Get(variable);
            if (s == null)
            {
                CSyntaxErrorLog.AddLine("Variable not found  " + last_str);
                CSyntaxErrorLog.AddLine(GetCurrentLine(SaveIndex()));
                throw new CParserException(-100, "Variable not found", SaveIndex());

            }

            //------------ The next token ought to be an assignment
            // expression....

            GetNext();

            if (Current_Token != Token.TOK_ASSIGN)
            {

                CSyntaxErrorLog.AddLine("= expected");
                CSyntaxErrorLog.AddLine(GetCurrentLine(SaveIndex()));
                throw new CParserException(-100, "= expected", SaveIndex());

            }

            //-------- Skip the token to start the expression
            // parsing on the RHS
            GetNext();
            Expression exp = Expr(ctx);

            //------------ Do the type analysis ...

            if (exp.TypeCheck(ctx) != s.Type)
            {
                throw new Exception("Type mismatch in assignment");

            }

            // -------------- End of statement ( ; ) is expected
            if (Current_Token != Token.TOK_SEMI)
            {
                CSyntaxErrorLog.AddLine("; expected");
                CSyntaxErrorLog.AddLine(GetCurrentLine(SaveIndex()));
                throw new CParserException(-100, " ; expected", -1);

            }
            // return an instance of AssignmentStatement node..
            //   s => Symbol info associated with variable
            //   exp => to evaluated and assigned to symbol_info
            return new AssignmentStatement(s, exp);

        }


    
}
