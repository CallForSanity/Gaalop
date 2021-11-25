package de.gaalop.gpc;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.dfg.Addition;
import de.gaalop.dfg.Division;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.FloatConstant;
import de.gaalop.dfg.MacroCall;
import de.gaalop.dfg.Multiplication;
import de.gaalop.dfg.Negation;
import de.gaalop.dfg.OuterProduct;
import de.gaalop.dfg.Subtraction;
import de.gaalop.dfg.Variable;
import java.util.LinkedList;

/**
 *
 * @author Christian Steinmetz
 */
public class GPCMyVisitor extends GPCBaseVisitor<Object> {
    
    public AssignmentNode assignment;

    @Override
    public Object visitProgram(GPCParser.ProgramContext ctx) {
        assignment = null;
        return super.visitProgram(ctx);
    }
    
    @Override
    public Object visitAssignment_expression(GPCParser.Assignment_expressionContext ctx) {
        Variable variable = (Variable) visit(ctx.var);
        Expression expression = (Expression) visit(ctx.expr);
        assignment = new AssignmentNode(null, variable, expression);
        return assignment;
    }
    
    @Override
    public Object visitBracket(GPCParser.BracketContext ctx) {
        return (Expression) visit(ctx.e);
    }
    
    @Override
    public Object visitOuterProduct(GPCParser.OuterProductContext ctx) {
        return new OuterProduct((Expression) visit(ctx.left), (Expression) visit(ctx.right));
    }

    @Override
    public Object visitDivision(GPCParser.DivisionContext ctx) {
        return new Division((Expression) visit(ctx.left), (Expression) visit(ctx.right));
    }
    
    @Override
    public Object visitMultiplication(GPCParser.MultiplicationContext ctx) {
        return new Multiplication((Expression) visit(ctx.left), (Expression) visit(ctx.right));
    }
    
    @Override
    public Object visitSubtraction(GPCParser.SubtractionContext ctx) {
        return new Subtraction((Expression) visit(ctx.left), (Expression) visit(ctx.right));
    }
    
    @Override
    public Object visitAddition(GPCParser.AdditionContext ctx) {
        Expression l = (Expression) visit(ctx.left);
        Expression r = (Expression) visit(ctx.right);
        return new Addition(l,r);
    }

    @Override
    public Object visitFunction(GPCParser.FunctionContext ctx) {
        LinkedList<Expression> args = (ctx.args != null) ? (LinkedList<Expression>) visit(ctx.args) : new LinkedList<Expression>();
        return new MacroCall(ctx.name.getText(), args);
    }
    
    @Override
    public Object visitNegationBracket(GPCParser.NegationBracketContext ctx) {
        return new Negation((Expression) visit(ctx.operand));
    }

    @Override
    public Object visitNegationFunction(GPCParser.NegationFunctionContext ctx) {
        LinkedList<Expression> args = (ctx.args != null) ? (LinkedList<Expression>) visit(ctx.args) : new LinkedList<Expression>();
        return new MacroCall(ctx.name.getText(), args);
    }
    
    @Override
    public Object visitNegation(GPCParser.NegationContext ctx) {
        return new Negation((Expression) visit(ctx.operand));
    }
    
    @Override
    public Object visitVariable(GPCParser.VariableContext ctx) {
        return new Variable(ctx.getText());
    }

    @Override
    public Object visitConstant(GPCParser.ConstantContext ctx) {
        return new FloatConstant(Double.parseDouble(ctx.getText()));
    }
    
    @Override
    public Object visitSingleArgument(GPCParser.SingleArgumentContext ctx) {
        LinkedList<Expression> result = new LinkedList<Expression>();
        result.add((Expression) visit(ctx.arg));
        return result;
    }

    @Override
    public Object visitMultipleArgument(GPCParser.MultipleArgumentContext ctx) {
        LinkedList<Expression> result = new LinkedList<Expression>();
        result.add((Expression) visit(ctx.arg));
        result.addAll((LinkedList<Expression>) visit(ctx.nextarg));
        return result;
    }
    
}
