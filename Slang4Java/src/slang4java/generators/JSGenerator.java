/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java.generators;

import java.util.ArrayList;
import slang4java.expressions.AbstractExpression;
import slang4java.metainfo.RelationalOperators;
import slang4java.metainfo.SymbolInfo;
import slang4java.metainfo.Token;
import slang4java.statements.AbstractStatement;

/**
 *
 * @author aashiks
 */
public class JSGenerator implements IGenerator {

    @Override
    public String NumericConstant(SymbolInfo s) {
        return String.valueOf(s.DoubleValue);
    }

    @Override
    public String StringLiteral(SymbolInfo s) {
        return "'" + s.StringValue + "'";
    }

    @Override
    public String BinaryMinus(AbstractExpression left, AbstractExpression right) {
        return left.Generate(this) + " - " + right.Generate(this);
    }

    @Override
    public String BinaryPlus(AbstractExpression left, AbstractExpression right) {
        return left.Generate(this) + " + " + right.Generate(this);
    }

    @Override
    public String BooleanConstant(SymbolInfo s) {
        String val = (s.BoolValue == false) ? "false" : "true";
        return val;
    }

    @Override
    public String Division(AbstractExpression left, AbstractExpression right) {
        return left.Generate(this) + "/" + right.Generate(this);
    }

    @Override
    public String Multiply(AbstractExpression left, AbstractExpression right) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String CallExpression(ArrayList actuals, String procname) {
        String expression = " " + procname + "(";
        for (Object o : actuals) {
            AbstractExpression ex = (AbstractExpression) o;
            expression += ex.Generate(this);
            expression += ",";
        }
        expression = expression.substring(0, expression.length() - 1);
        expression += ")";
        return expression;
    }

    @Override
    public String LogicalExpression(Token op, AbstractExpression left, AbstractExpression right) {

        String val = left.Generate(this);
        if (op == Token.TOK_AND) {
            val += " && ";
        } else if (op == Token.TOK_OR) {
            val += " || ";
        }
        val += right.Generate(this);

        return val;
    }

    @Override
    public String LogicalNot(AbstractExpression e) {
        return "!" + e.Generate(this);
    }

    @Override
    public String RelationalExpression(RelationalOperators op, AbstractExpression left, AbstractExpression right) {
        String val = left.Generate(this);
        if (op == RelationalOperators.TOK_EQ) {
            val += " == ";
        } else if (op == RelationalOperators.TOK_NEQ) {
            val += " != ";
        } else if (op == RelationalOperators.TOK_GT) {
            val += " > ";
        } else if (op == RelationalOperators.TOK_GTE) {
            val += " >= ";
        } else if (op == RelationalOperators.TOK_LT) {
            val += " < ";
        } else if (op == RelationalOperators.TOK_LTE) {
            val += " <= ";
        }
        val += right.Generate(this);
        return val;
    }

    @Override
    public String UnaryMinus(AbstractExpression e) {
        return "-" + e.Generate(this);
    }

    @Override
    public String UnaryPlus(AbstractExpression e) {
        return "+" + e.Generate(this);
    }

    @Override
    public String Variable(String variableName) {
        return variableName;
    }

    @Override
    public String AssignmentStatement(slang4java.expressions.Variable var, AbstractExpression e) {

        String val;
        val = var.Generate(this);
        val += " = ";
        val += e.Generate(this);
        val += ";";
        val += System.getProperty("line.separator");
        return val;
    }

    @Override
    public String IfStatement(AbstractExpression e, ArrayList ifList, ArrayList elseList) {
        String val = "if(";
        val += e.Generate(this);
        val += ") {";
        val += System.getProperty("line.separator");
        for (Object s : ifList) {
            AbstractStatement statement = (AbstractStatement) s;
            val += statement.Generate(this);
        }
        val += "} ";
        val += System.getProperty("line.separator");

        if (elseList.size() > 0) {
            val += "else {";
            val += System.getProperty("line.separator");
            for (Object s : elseList) {
                AbstractStatement statement = (AbstractStatement) s;
                val += statement.Generate(this);
            }
            val += " }";
            val += System.getProperty("line.separator");
        }
        return val;

    }

    @Override
    public String PrintLineStatement(AbstractExpression ex) {

        String val = "print(";
        val += ex.Generate(this);
        val += ");";
        val += System.getProperty("line.separator");
        return val;
    }

    @Override
    public String ReturnStatement(AbstractExpression e) {
        return "return " + e.Generate(this) + ";" + System.getProperty("line.separator");
    }

    @Override
    public String VariableDeclStatement(SymbolInfo inf) {
        return "var " + inf.SymbolName + ";" + System.getProperty("line.separator");
    }

    @Override
    public String WhileStatement(AbstractExpression e, ArrayList statementList) {
        String val = "while(";
        val += e.Generate(this);
        val += ") {";
        val += System.getProperty("line.separator");

        for (Object o : statementList) {
            AbstractStatement statement = (AbstractStatement) o;
            val += statement.Generate(this);
        }
        val += System.getProperty("line.separator");
        val += "}";
        val += System.getProperty("line.separator");
        return val;
    }

    @Override
    public String Procedure(String name, ArrayList formals, ArrayList statements) {

        String val = "function " + name + "(";
        for (Object o : formals) {
            SymbolInfo info = (SymbolInfo) o;
            val += info.SymbolName;
            val += ",";
        }
        val = val.substring(0, val.length() - 1);
        val += ") {";
        val += System.getProperty("line.separator");
        for(Object o: statements){
            AbstractStatement s = (AbstractStatement) o;
            val += s.Generate(this);
        }
        
        val += System.getProperty("line.separator");
        val += "}";
        val += System.getProperty("line.separator");
        return val;
    }
}