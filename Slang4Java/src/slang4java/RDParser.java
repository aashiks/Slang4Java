/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java;

/**
 *
 * @author aashiks
 */
public class RDParser extends Lexer {

    Token Current_Token;

    public RDParser(String Expr) {
        super(Expr);
    }

    public Expression CallExpression() throws Exception {
        Current_Token = GetToken();
        return Expr();
    }

    public Expression Expr() throws Exception {
        Token l_token;
        Expression RetValue = Term();
        while (Current_Token == Token.TOK_PLUS || Current_Token == Token.TOK_SUB) {
            l_token = Current_Token;
            Current_Token = GetToken();
            Expression e1 = Expr(); // bwahaha Recurse
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
                throw new Exception("Missing Closing Parenthesis\n");

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
            throw new Exception("Illegal Token");
        }

        return RetValue;

    }
}
