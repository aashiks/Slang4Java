/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slang4java.generators;

import java.util.ArrayList;
import slang4java.expressions.AbstractExpression;
import slang4java.expressions.Variable;
import slang4java.metainfo.RelationalOperators;
import slang4java.metainfo.SymbolInfo;
import slang4java.metainfo.Token;

/**
 *
 * @author aashiks
 */
public interface IGenerator {
    // Expressions
    
    public String NumericConstant(SymbolInfo s);
    public String StringLiteral(SymbolInfo s);
    public String BinaryMinus(AbstractExpression left, AbstractExpression right);
    public String BinaryPlus(AbstractExpression left, AbstractExpression right);
    public String BooleanConstant(SymbolInfo s);
    public String Division(AbstractExpression left, AbstractExpression right);
    public String Multiply(AbstractExpression left, AbstractExpression right);
    public String CallExpression(ArrayList actuals, String procname);
    public String LogicalExpression(Token op, AbstractExpression left, AbstractExpression right);
    public String LogicalNot(AbstractExpression e);
    public String UnaryMinus(AbstractExpression e);
    public String UnaryPlus(AbstractExpression e);
    public String Variable(String variableName);
    public String RelationalExpression(RelationalOperators op, AbstractExpression left, AbstractExpression right);
    
    // Statements
    public String AssignmentStatement(Variable var, AbstractExpression e);
    public String IfStatement(AbstractExpression e, ArrayList ifList, ArrayList elseList);
    public String PrintLineStatement(AbstractExpression ex) ;
    public String ReturnStatement(AbstractExpression e);
    public String VariableDeclStatement(SymbolInfo inf);
    public String WhileStatement(AbstractExpression e, ArrayList statementList);
    
    // Procedure
    public String Procedure(String name, ArrayList formals, ArrayList statements);
}
