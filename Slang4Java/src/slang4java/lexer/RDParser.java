/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java.lexer;

import java.util.ArrayList;
import slang4java.builders.*;
import slang4java.compilationunits.TModule;
import slang4java.contexts.CompilationContext;
import slang4java.expressions.*;
import slang4java.metainfo.RelationalOperators;
import slang4java.metainfo.SymbolInfo;
import slang4java.metainfo.Token;
import slang4java.metainfo.TypeInfo;
import slang4java.procedures.*;
import slang4java.statements.*;
import slang4java.support.*;

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

    private RelationalOperators GetRelationalOperator(Token tok) {
        if (tok == Token.TOK_EQ) {
            return RelationalOperators.TOK_EQ;
        } else if (tok == Token.TOK_NEQ) {
            return RelationalOperators.TOK_NEQ;
        } else if (tok == Token.TOK_GT) {
            return RelationalOperators.TOK_GT;
        } else if (tok == Token.TOK_GTE) {
            return RelationalOperators.TOK_GTE;
        } else if (tok == Token.TOK_LT) {
            return RelationalOperators.TOK_LT;
        } else {
            return RelationalOperators.TOK_LTE;
        }


    }

    public AbstractExpression LExpr(ProcedureBuilder pb) throws Exception {
        Token l_token;
        AbstractExpression RetValue = Expression(pb);
        while (Current_Token == Token.TOK_GT
                || Current_Token == Token.TOK_LT
                || Current_Token == Token.TOK_GTE
                || Current_Token == Token.TOK_LTE
                || Current_Token == Token.TOK_NEQ
                || Current_Token == Token.TOK_EQ) {
            l_token = Current_Token;
            Current_Token = GetNext();
            AbstractExpression e2 = Expression(pb);
            RelationalOperators relop = GetRelationalOperator(l_token);
            RetValue = new RelationalExpression(relop, RetValue, e2);
        }
        return RetValue;

    }

    public AbstractExpression BinaryExpression(ProcedureBuilder pb) throws Exception {
        Token l_token;
        AbstractExpression RetValue = LExpr(pb);
        while (Current_Token == Token.TOK_AND || Current_Token == Token.TOK_OR) {
            l_token = Current_Token;
            Current_Token = GetNext();
            AbstractExpression e2 = LExpr(pb);
            RetValue = new LogicalExpression(l_token, RetValue, e2);

        }
        return RetValue;

    }

    public AbstractExpression Expression(ProcedureBuilder pb) throws Exception {
        Token l_token;
        AbstractExpression RetValue = Term(pb);
        while (Current_Token == Token.TOK_PLUS || Current_Token == Token.TOK_SUB) {
            l_token = Current_Token;
            Current_Token = GetToken();
            AbstractExpression e1 = Expression(pb);

            if (l_token == Token.TOK_PLUS) {
                RetValue = new BinaryPlus(RetValue, e1);
            } else {
                RetValue = new BinaryMinus(RetValue, e1);
            }
        }

        return RetValue;
    }

    public AbstractExpression Term(ProcedureBuilder pb) throws Exception {
        Token l_token;
        AbstractExpression RetValue = Factor(pb);

        while (Current_Token == Token.TOK_MUL || Current_Token == Token.TOK_DIV) {
            l_token = Current_Token;
            Current_Token = GetToken();


            AbstractExpression e1 = Term(pb);
            if (l_token == Token.TOK_MUL) {
                RetValue = new Multiply(RetValue, e1);
            } else {
                RetValue = new Division(RetValue, e1);
            }

        }

        return RetValue;
    }

    public AbstractExpression Factor(ProcedureBuilder pb) throws Exception {
        Token l_token;
        AbstractExpression RetValue = null;



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

            RetValue = BinaryExpression(pb);  // Recurse

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

            if (!prog.IsFunction(str)) {
                //
                // if it is not a function..it ought to 
                // be a variable...
                SymbolInfo inf = pb.GetSymbol(str);

                if (inf == null) {
                    throw new Exception("Undefined symbol");
                }

                GetNext();
                return new Variable(inf);
            }

            //
            // P can be null , if we are parsing a
            // recursive function call
            //
            Procedure p = prog.GetProc(str);
            // It is a Function Call
            // Parse the function invocation
            //
            AbstractExpression ptr = ParseCallProc(pb, p);
            GetNext();
            return ptr;
        } else {

            System.out.println("Illegal Token");
            throw new Exception();
        }


        return RetValue;

    }

    public AbstractExpression ParseCallProc(ProcedureBuilder pb, Procedure p) throws Exception {
        GetNext();

        if (Current_Token != Token.TOK_OPAREN) {
            throw new Exception("Opening Parenthesis expected");
        }

        GetNext();

        ArrayList actualparams = new ArrayList();

        while (true) {
            // Evaluate Each Expression in the 
            // parameter list and populate actualparams
            // list
            AbstractExpression exp = BinaryExpression(pb);
            // do type analysis
            exp.TypeCheck(pb.getContext());
            // if , there are more parameters
            if (Current_Token == Token.TOK_COMMA) {
                actualparams.add(exp);
                GetNext();
                continue;
            }


            if (Current_Token != Token.TOK_CPAREN) {
                throw new Exception("Expected paranthesis");
            } else {
                // Add the last parameters
                actualparams.add(exp);
                break;

            }
        }

        // if p is null , that means it is a 
        // recursive call. Being a one pass 
        // compiler , we need to wait till 
        // the parse process to be over to
        // resolve the Procedure.
        //
        //
        if (p != null) {
            return new CallExpression(p, actualparams);
        } else {
            return new CallExpression(pb.getProcedureName(),
                    true, // recurse !
                    actualparams,
                    pb.GetProcedure()._type);
        }



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
//            ProcedureBuilder p = new ProcedureBuilder("MAIN", new CompilationContext());
//            ArrayList stmts = Parse(p);
//            
//            for (Object s : stmts) {
//                p.AddStatement((Statement) s);
//            }
//            
//            Procedure pc = p.GetProcedure();
//            
//            prog.Add(pc);
//            return prog.GetProgram();
            GetNext();   // Get The First Valid Token
            return ParseFunctions();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }

    }

    public TModule ParseFunctions() throws Exception {

        while (Current_Token == Token.TOK_FUNCTION) {
            ProcedureBuilder b = ParseFunction();
            Procedure s = b.GetProcedure();

            if (s == null) {
                System.out.println("Error While Parsing Functions");
                return null;
            }

            prog.Add(s);
            GetNext();
        }

        //
        //  Convert the builder into a program
        //
        return prog.GetProgram();
    }

    ProcedureBuilder ParseFunction() throws Exception {
        //
        // Create a Procedure builder Object
        //
        ProcedureBuilder p = new ProcedureBuilder("", new CompilationContext());
        if (Current_Token != Token.TOK_FUNCTION) {
            return null;
        }


        GetNext();
        // return type of the Procedure ought to be 
        // Boolean , Numeric or String 
        if (!(Current_Token == Token.TOK_VAR_BOOL
                || Current_Token == Token.TOK_VAR_NUMBER
                || Current_Token == Token.TOK_VAR_STRING)) {

            return null;

        }

        //-------- Assign the return type
        p.setTypeInfo((Current_Token == Token.TOK_VAR_BOOL)
                ? TypeInfo.TYPE_BOOL : (Current_Token == Token.TOK_VAR_NUMBER)
                ? TypeInfo.TYPE_NUMERIC : TypeInfo.TYPE_STRING);

        // Parse the name of the Function call
        GetNext();
        if (Current_Token != Token.TOK_UNQUOTED_STRING) {
            return null;
        }
        p.setProcedureName(this.last_str); // assign the name

        // ---------- Opening parenthesis for 
        // the start of <paramlist>
        GetNext();
        if (Current_Token != Token.TOK_OPAREN) {
            return null;
        }

        //---- Parse the Formal Parameter list
        FormalParameters(p);



        if (Current_Token != Token.TOK_CPAREN) {
            return null;
        }

        GetNext();

        // --------- Parse the Function code
        ArrayList lst = StatementList(p);

        if (Current_Token != Token.TOK_END) {
            throw new Exception("END expected");
        }

        // Accumulate all statements to 
        // Procedure builder
        //
        for (Object o : lst) {
            Statement s = (Statement) o;
            p.AddStatement(s);
        }
        return p;
    }

    void FormalParameters(ProcedureBuilder pb) throws Exception {

        if (Current_Token != Token.TOK_OPAREN) {
            throw new Exception("Opening Parenthesis expected");
        }
        GetNext();

        ArrayList lst_types = new ArrayList();

        while (Current_Token == Token.TOK_VAR_BOOL
                || Current_Token == Token.TOK_VAR_NUMBER
                || Current_Token == Token.TOK_VAR_STRING) {
            SymbolInfo inf = new SymbolInfo();

            inf.Type = (Current_Token == Token.TOK_VAR_BOOL)
                    ? TypeInfo.TYPE_BOOL : (Current_Token == Token.TOK_VAR_NUMBER)
                    ? TypeInfo.TYPE_NUMERIC : TypeInfo.TYPE_STRING;





            GetNext();
            if (Current_Token != Token.TOK_UNQUOTED_STRING) {
                throw new Exception("Variable Name expected");
            }

            inf.SymbolName = this.last_str;
            lst_types.add(inf.Type);
            pb.AddFormals(inf);
            pb.AddLocal(inf);


            GetNext();

            if (Current_Token != Token.TOK_COMMA) {
                break;
            }
            GetNext();
        }

        prog.AddFunctionProtoType(pb.getProcedureName(), pb.getTypeInfo(), lst_types);
        return;

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
                && (Current_Token != Token.TOK_END)) {
            Statement temp = Statement(pb);
            if (temp != null) {
                arr.add(temp);
            }
        }
        return arr;
    }

    private Statement Statement(ProcedureBuilder pb) throws Exception {
        Statement retval = null;

        switch (this.Current_Token) {
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
            case TOK_RETURN:
                retval = ParseReturnStatement(pb);
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
        AbstractExpression a = BinaryExpression(pb);

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
        AbstractExpression a = Expression(pb);

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
        AbstractExpression exp = BinaryExpression(pb);

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
        ArrayList true_part;
        true_part = null;
        ArrayList false_part;
        false_part = null;
        AbstractExpression exp = BinaryExpression(pb);  // Evaluate Expression


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
            return new IfStatement(exp, true_part, false_part);
        }


        if (Current_Token != Token.TOK_ELSE) {

            throw new Exception("ELSE expected");
        }

        GetNext();
        false_part = StatementList(pb);

        if (Current_Token != Token.TOK_ENDIF) {
            throw new Exception("END IF EXPECTED");

        }

        return new IfStatement(exp, true_part, false_part);

    }

    public Statement ParseWhileStatement(ProcedureBuilder pb) throws Exception {

        GetNext();

        AbstractExpression exp = BinaryExpression(pb);
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

    public Statement ParseReturnStatement(ProcedureBuilder pb) throws Exception {

        GetNext();
        AbstractExpression exp = BinaryExpression(pb);
        if (Current_Token != Token.TOK_SEMI) {
            CSyntaxErrorLog.AddLine("; expected");
            CSyntaxErrorLog.AddLine(GetCurrentLine(SaveIndex()));
            throw new CParserException(-100, " ; expected", -1);

        }
        pb.TypeCheck(exp);
        return new ReturnStatement(exp);

    }
}
