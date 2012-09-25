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

    TModuleBuilder prog = null;

    public RDParser(String Expr) {
        super(Expr);
        prog = new TModuleBuilder(); 
    }

    private RelationalOperator GetRelationalOperator(Token tok) {
        if (tok == Token.TOK_EQ) {
            return RelationalOperator.TOK_EQ;
        } else if (tok == Token.TOK_NEQ) {
            return RelationalOperator.TOK_NEQ;
        } else if (tok == Token.TOK_GT) {
            return RelationalOperator.TOK_GT;
        } else if (tok == Token.TOK_GTE) {
            return RelationalOperator.TOK_GTE;
        } else if (tok == Token.TOK_LT) {
            return RelationalOperator.TOK_LT;
        } else {
            return RelationalOperator.TOK_LTE;
        }


    }

    public Expression LExpr(ProcedureBuilder pb) throws Exception {
        Token l_token;
        Expression RetValue = Expr(pb);
        while (Current_Token == Token.TOK_GT
                || Current_Token == Token.TOK_LT
                || Current_Token == Token.TOK_GTE
                || Current_Token == Token.TOK_LTE
                || Current_Token == Token.TOK_NEQ
                || Current_Token == Token.TOK_EQ) {
            l_token = Current_Token;
            Current_Token = GetNext();
            Expression e2 = Expr(pb);
            RelationalOperator relop = GetRelationalOperator(l_token);
            RetValue = new RelationalExpression(relop, RetValue, e2);
        }
        return RetValue;

    }

    public Expression BExpr(ProcedureBuilder pb) throws Exception {
        Token l_token;
        Expression RetValue = LExpr(pb);
        while (Current_Token == Token.TOK_AND || Current_Token == Token.TOK_OR) {
            l_token = Current_Token;
            Current_Token = GetNext();
            Expression e2 = LExpr(pb);
            RetValue = new LogicalExpression(l_token, RetValue, e2);

        }
        return RetValue;

    }

    public Expression Expr(ProcedureBuilder pb) throws Exception {
        Token l_token;
        Expression RetValue = Term(pb);
        while (Current_Token == Token.TOK_PLUS || Current_Token == Token.TOK_SUB) {
            l_token = Current_Token;
            Current_Token = GetToken();
            Expression e1 = Expr(pb);

            if (l_token == Token.TOK_PLUS) {
                RetValue = new BinaryPlus(RetValue, e1);
            } else {
                RetValue = new BinaryMinus(RetValue, e1);
            }
        }

        return RetValue;
    }

    public Expression Term(ProcedureBuilder pb) throws Exception {
        Token l_token;
        Expression RetValue = Factor(pb);

        while (Current_Token == Token.TOK_MUL || Current_Token == Token.TOK_DIV) {
            l_token = Current_Token;
            Current_Token = GetToken();


            Expression e1 = Term(pb);
            if (l_token == Token.TOK_MUL) {
                RetValue = new Multiply(RetValue, e1);
            } else {
                RetValue = new Division(RetValue, e1);
            }

        }

        return RetValue;
    }

    public Expression Factor(ProcedureBuilder pb) throws Exception {
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

            RetValue = BExpr(pb);  // Recurse

            if (Current_Token != Token.TOK_CPAREN) {
                System.out.println("Missing Closing Parenthesis\n");
                throw new Exception();

            }
            Current_Token = GetToken();
        } else if (Current_Token == Token.TOK_PLUS || Current_Token == Token.TOK_SUB) {
            l_token = Current_Token;
            Current_Token = GetToken();
            RetValue = Factor(pb);
            if (l_token == Token.TOK_PLUS) {
                RetValue = new UnaryPlus(RetValue);
            } else {
                RetValue = new UnaryMinus(RetValue);
            }

        } else if (Current_Token == Token.TOK_NOT) {
            l_token = Current_Token;
            Current_Token = GetToken();
            RetValue = Factor(pb);

            RetValue = new LogicalNot(RetValue);
        } else if (Current_Token == Token.TOK_UNQUOTED_STRING) {
            ///
            ///  Variables 
            ///
            String str = super.last_str;
            SymbolInfo inf = pb.getSymbolTable().Get(str);

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
    public ArrayList Parse(ProcedureBuilder pb) throws Exception {
        GetNext();  // Get the Next Token
        //
        // Parse all the statements
        //
        return StatementList(pb);
    }

    public TModule DoParse() {
        try {
            ProcedureBuilder p = new ProcedureBuilder("MAIN", new CompilationContext());
            ArrayList stmts = Parse(p);

            for (Object s : stmts) {
                p.AddStatement((Statement) s);
            }

            Procedure pc = p.GetProcedure();

            prog.Add(pc);
            return prog.GetProgram();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
            return null;
        }

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

    private ArrayList StatementList(ProcedureBuilder pb) throws Exception {
        ArrayList arr = new ArrayList();
        while ((Current_Token != Token.TOK_ELSE)
                && (Current_Token != Token.TOK_ENDIF)
                && (Current_Token != Token.TOK_WEND)
                && (Current_Token != Token.TOK_NULL)) {
            Statement temp = Statement(pb);
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
    private Statement Statement(ProcedureBuilder pb) throws Exception {
        Statement retval = null;
        switch (Current_Token) {
            case TOK_VAR_STRING:
            case TOK_VAR_NUMBER:
            case TOK_VAR_BOOL:
                retval = ParseVariableDeclStatement(pb);
                GetNext();
                return retval;
            case TOK_PRINT:
                retval = ParsePrintStatement(pb);
                GetNext();
                break;
            case TOK_PRINTLN:
                retval = ParsePrintLNStatement(pb);
                GetNext();
                break;
            case TOK_UNQUOTED_STRING:
                retval = ParseAssignmentStatement(pb);
                GetNext();
                return retval;
            case TOK_IF:
                retval = ParseIfStatement(pb);
                GetNext();
                return retval;

            case TOK_WHILE:
                retval = ParseWhileStatement(pb);
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
    private Statement ParsePrintStatement(ProcedureBuilder pb) throws Exception {
        GetNext();
        Expression a = BExpr(pb);

        a.TypeCheck(pb.getContext());

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
    private Statement ParsePrintLNStatement(ProcedureBuilder pb) throws Exception {
        GetNext();
        Expression a = Expr(pb);

        a.TypeCheck(pb.getContext());
        if (Current_Token != Token.TOK_SEMI) {
            throw new Exception("; is expected");
        }
        return new PrintLineStatement(a);
    }

    public Statement ParseVariableDeclStatement(ProcedureBuilder pb) throws Exception {

        //--- Save the Data type 
        Token tok = Current_Token;

        // --- Skip to the next token , the token ought 
        // to be a Variable name ( UnQouted String )
        GetNext();

        if (Current_Token == Token.TOK_UNQUOTED_STRING) {
            SymbolInfo symb = new SymbolInfo();
            symb.SymbolName = super.last_str;
            symb.Type = (tok == Token.TOK_VAR_BOOL)
                    ? TypeInfo.TYPE_BOOL : (tok == Token.TOK_VAR_NUMBER)
                    ? TypeInfo.TYPE_NUMERIC : TypeInfo.TYPE_STRING;

            //---------- Skip to Expect the SemiColon

            GetNext();



            if (Current_Token == Token.TOK_SEMI) {
                // ----------- Add to the Symbol Table
                // for type analysis 
                pb.getSymbolTable().Add(symb);

                // --------- return the Object of type
                // --------- VariableDeclStatement
                // This will just store the Variable name
                // to be looked up in the above table
                return new VariableDeclStatement(symb);
            } else {
                CSyntaxErrorLog.AddLine("; expected");
                CSyntaxErrorLog.AddLine(GetCurrentLine(SaveIndex()));
                throw new CParserException(-100, ", or ; expected", SaveIndex());
            }
        } else {

            CSyntaxErrorLog.AddLine("invalid variable declaration");
            CSyntaxErrorLog.AddLine(GetCurrentLine(SaveIndex()));
            throw new CParserException(-100, ", or ; expected", SaveIndex());
        }

    }

    public Statement ParseAssignmentStatement(ProcedureBuilder pb) throws Exception {

        //
        // Retrieve the variable and look it up in 
        // the symbol table ..if not found throw exception
        //
        String variable = super.last_str;
        SymbolInfo s = pb.getSymbolTable().Get(variable);
        if (s == null) {
            CSyntaxErrorLog.AddLine("Variable not found  " + last_str);
            CSyntaxErrorLog.AddLine(GetCurrentLine(SaveIndex()));
            throw new CParserException(-100, "Variable not found", SaveIndex());

        }

        //------------ The next token ought to be an assignment
        // expression....

        GetNext();

        if (Current_Token != Token.TOK_ASSIGN) {

            CSyntaxErrorLog.AddLine("= expected");
            CSyntaxErrorLog.AddLine(GetCurrentLine(SaveIndex()));
            throw new CParserException(-100, "= expected", SaveIndex());

        }

        //-------- Skip the token to start the expression
        // parsing on the RHS
        GetNext();
        Expression exp = BExpr(pb);

        //------------ Do the type analysis ...

        if (exp.TypeCheck(pb.getContext()) != s.Type) {
            throw new Exception("Type mismatch in assignment");

        }

        // -------------- End of statement ( ; ) is expected
        if (Current_Token != Token.TOK_SEMI) {
            CSyntaxErrorLog.AddLine("; expected");
            CSyntaxErrorLog.AddLine(GetCurrentLine(SaveIndex()));
            throw new CParserException(-100, " ; expected", -1);

        }
        // return an instance of AssignmentStatement node..
        //   s => Symbol info associated with variable
        //   exp => to evaluated and assigned to symbol_info
        return new AssignmentStatement(s, exp);

    }

    public Statement ParseIfStatement(ProcedureBuilder pb) throws Exception {
        GetNext();
        ArrayList true_part = null;
        ArrayList false_part = null;
        Expression exp = BExpr(pb);  // Evaluate Expression


        if (pb.TypeCheck(exp) != TypeInfo.TYPE_BOOL) {
            throw new Exception("Expects a boolean expression");

        }


        if (Current_Token != Token.TOK_THEN) {
            CSyntaxErrorLog.AddLine(" Then Expected");
            CSyntaxErrorLog.AddLine(GetCurrentLine(SaveIndex()));
            throw new CParserException(-100, "Then Expected", SaveIndex());

        }

        GetNext();

        true_part = StatementList(pb);

        if (Current_Token == Token.TOK_ENDIF) {
            return new ifStatement(exp, true_part, false_part);
        }


        if (Current_Token != Token.TOK_ELSE) {

            throw new Exception("ELSE expected");
        }

        GetNext();
        false_part = StatementList(pb);

        if (Current_Token != Token.TOK_ENDIF) {
            throw new Exception("END IF EXPECTED");

        }

        return new ifStatement(exp, true_part, false_part);

    }

    public Statement ParseWhileStatement(ProcedureBuilder pb) throws Exception {

        GetNext();

        Expression exp = BExpr(pb);
        if (pb.TypeCheck(exp) != TypeInfo.TYPE_BOOL) {
            throw new Exception("Expects a boolean expression");

        }

        ArrayList body = StatementList(pb);
        if ((Current_Token != Token.TOK_WEND)) {
            CSyntaxErrorLog.AddLine("Wend Expected");
            CSyntaxErrorLog.AddLine(GetCurrentLine(SaveIndex()));
            throw new CParserException(-100, "Wend Expected", SaveIndex());

        }
        return new WhileStatement(exp, body);

    }
}
