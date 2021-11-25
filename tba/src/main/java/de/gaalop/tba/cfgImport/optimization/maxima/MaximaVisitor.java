package de.gaalop.tba.cfgImport.optimization.maxima;

import de.gaalop.dfg.Expression;
import de.gaalop.dfg.*;
import de.gaalop.tba.cfgImport.optimization.maxima.parser.MaximaBaseVisitor;
import de.gaalop.tba.cfgImport.optimization.maxima.parser.MaximaParser;

/**
 * Implements a visitor for the lexer base visitor from ANTLR grammar parser to read in maximas output
 * @author Christian Steinmetz
 */
public class MaximaVisitor extends MaximaBaseVisitor<Expression> {

    private Expression processFunction(String name, Expression arg) {
        for (MathFunction mathFunction : MathFunction.values()) 
            if (mathFunction.toString().toLowerCase().equals(name)) 
                return new MathFunctionCall(arg, mathFunction);

        throw new IllegalArgumentException("Function " + name + " is not supported by maxima parser");
    }

    @Override
    public Expression visitMv_coefficient(MaximaParser.Mv_coefficientContext ctx) {
        return new MultivectorComponent(ctx.name.getText(), Integer.parseInt(ctx.index.getText()));
    }
    

    @Override
    public Expression visitFunction(MaximaParser.FunctionContext ctx) {
        return processFunction(ctx.name.getText(), visit(ctx.arg));
    }

    @Override
    public Expression visitExponentiation(MaximaParser.ExponentiationContext ctx) {
        return new Exponentiation(visit(ctx.left), visit(ctx.right));
    }

    @Override
    public Expression visitDivision(MaximaParser.DivisionContext ctx) {
        return new Division(visit(ctx.left), visit(ctx.right));
    }

    @Override
    public Expression visitVariable(MaximaParser.VariableContext ctx) {
        return new Variable(ctx.name.getText());
    }
    

    @Override
    public Expression visitNegation(MaximaParser.NegationContext ctx) {
        return new Negation(visit(ctx.operand));
    }

    @Override
    public Expression visitConstant(MaximaParser.ConstantContext ctx) {
        String text = ctx.getText();
        if ("%pi".equals(text)) return new FloatConstant(Math.PI);
        if ("%i".equals(text)) return new MathFunctionCall(new FloatConstant(-1), MathFunction.SQRT);
        return new FloatConstant(Double.parseDouble(text));
    }

    @Override
    public Expression visitSubtraction(MaximaParser.SubtractionContext ctx) {
        return new Subtraction(visit(ctx.left), visit(ctx.right));
    }

    @Override
    public Expression visitAddition(MaximaParser.AdditionContext ctx) {
        return new Addition(visit(ctx.left), visit(ctx.right));
    }

    @Override
    public Expression visitMultiplication(MaximaParser.MultiplicationContext ctx) {
        return new Multiplication(visit(ctx.left), visit(ctx.right));
    }

    @Override
    public Expression visitBracket(MaximaParser.BracketContext ctx) {
        return visit(ctx.ex);
    }

    @Override
    public Expression visitNegationBracket(MaximaParser.NegationBracketContext ctx) {
        return new Negation(visit(ctx.operand));
    }
    
    

}
