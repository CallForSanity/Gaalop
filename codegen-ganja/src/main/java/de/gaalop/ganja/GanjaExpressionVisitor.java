package de.gaalop.ganja;

import de.gaalop.OperatorPriority;
import de.gaalop.dfg.Addition;
import de.gaalop.dfg.BaseVector;
import de.gaalop.dfg.BinaryOperation;
import de.gaalop.dfg.Division;
import de.gaalop.dfg.Equality;
import de.gaalop.dfg.Exponentiation;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.ExpressionVisitor;
import de.gaalop.dfg.FloatConstant;
import de.gaalop.dfg.FunctionArgument;
import de.gaalop.dfg.Inequality;
import de.gaalop.dfg.InnerProduct;
import de.gaalop.dfg.LogicalAnd;
import de.gaalop.dfg.LogicalNegation;
import de.gaalop.dfg.LogicalOr;
import de.gaalop.dfg.MacroCall;
import de.gaalop.dfg.MathFunctionCall;
import de.gaalop.dfg.Multiplication;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Negation;
import de.gaalop.dfg.OuterProduct;
import de.gaalop.dfg.Relation;
import de.gaalop.dfg.Reverse;
import de.gaalop.dfg.Subtraction;
import de.gaalop.dfg.Variable;

/**
 *
 * @author Christian Steinmetz
 */
public class GanjaExpressionVisitor implements ExpressionVisitor {

    private final OperatorPriority operatorPriority = new OperatorPriority();

    private final StringBuilder code;

    public GanjaExpressionVisitor(StringBuilder code) {
        this.code = code;
    }

    protected void addBinaryInfix(BinaryOperation op, String operator) {
        addChild(op, op.getLeft());
        code.append(operator);
        addChild(op, op.getRight());
    }

    protected void addChild(Expression parent, Expression child) {
        if (operatorPriority.hasLowerPriority(parent, child)) {
            code.append('(');
            child.accept(this);
            code.append(')');
        } else {
            child.accept(this);
        }
    }

    @Override
    public void visit(FloatConstant floatConstant) {
        code.append(Double.toString(floatConstant.getValue()));
    }

    @Override
    public void visit(Negation negation) {
        code.append('(');
        code.append('-');
        addChild(negation, negation.getOperand());
        code.append(')');
    }

    @Override
    public void visit(LogicalOr node) {
        addBinaryInfix(node, " || ");
    }

    @Override
    public void visit(LogicalAnd node) {
        addBinaryInfix(node, " && ");
    }

    @Override
    public void visit(LogicalNegation node) {
        code.append('!');
        addChild(node, node.getOperand());
    }

    @Override
    public void visit(Equality node) {
        addBinaryInfix(node, " == ");
    }

    @Override
    public void visit(Inequality node) {
        addBinaryInfix(node, " != ");
    }

    @Override
    public void visit(Relation relation) {
        addBinaryInfix(relation, relation.getTypeString());
    }
    
    @Override
    public void visit(Subtraction subtraction) {
        addBinaryInfix(subtraction, " - ");
    }

    @Override
    public void visit(Addition addition) {
        addBinaryInfix(addition, " + ");
    }

    @Override
    public void visit(Division division) {
        addBinaryInfix(division, " / ");
    }

    @Override
    public void visit(Multiplication multiplication) {
        addBinaryInfix(multiplication, " * ");
    }

    @Override
    public void visit(MathFunctionCall mathFunctionCall) {
        code.append("Math."+mathFunctionCall.getFunction().toString().toLowerCase());
        code.append('(');
        mathFunctionCall.getOperand().accept(this);
        code.append(')');
    }

    @Override
    public void visit(Variable variable) {
        // usually there are no
        code.append(variable.getName());
    }

    @Override
    public void visit(MultivectorComponent component) {
        code.append(component.getName());
        code.append("[" + component.getBladeIndex() + "]");
    }

    @Override
    public void visit(Exponentiation exponentiation) {
        if (isSquare(exponentiation)) {
            Multiplication m = new Multiplication(exponentiation.getLeft(), exponentiation.getLeft());
            m.accept(this);
        } else {
            code.append("Math.pow(");
            exponentiation.getLeft().accept(this);
            code.append(',');
            exponentiation.getRight().accept(this);
            code.append(')');
        }
    }

    /**
     * Checks if the given exponentiation is a square
     *
     * @param exponentiation The exponentiation
     * @return true, if the given exponentiation is a square
     */
    protected boolean isSquare(Exponentiation exponentiation) {
        final FloatConstant two = new FloatConstant(2.0f);
        return two.equals(exponentiation.getRight());
    }
    
        @Override
    public void visit(Reverse node) {
        throw new UnsupportedOperationException("The Ganja backend does not support the reverse operation.");
    }
    
    @Override
    public void visit(OuterProduct outerProduct) {
        throw new UnsupportedOperationException("The Ganja backend does not support the outer product.");
    }

    @Override
    public void visit(BaseVector baseVector) {
        throw new UnsupportedOperationException("The Ganja backend does not support base vectors.");
    }

    @Override
    public void visit(FunctionArgument node) {
        throw new IllegalStateException("Macros should have been inlined and no function arguments should be the graph.");
    }

    @Override
    public void visit(MacroCall node) {
        throw new IllegalStateException("Macro " + node.getName() + " should have been inlined and no macro calls should be in the graph.");
    }

    @Override
    public void visit(InnerProduct innerProduct) {
        throw new UnsupportedOperationException("The MATLAB backend does not support the inner product.");
    }
}
