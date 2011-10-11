package de.gaalop.api.dfg;

import de.gaalop.dfg.Addition;
import de.gaalop.dfg.BaseVector;
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
 * Returns the type of an Expression
 * @author Christian Steinmetz
 */
public class DFGNodeTypeGetter implements ExpressionVisitor {

    private DFGNodeType type;
    private static final DFGNodeTypeGetter getter = new DFGNodeTypeGetter();

    /**
     * Returns the type of a Expression
     * @param expression The expression
     * @return The type of the given expression
     */
    public static DFGNodeType getTypeOfDFGNode(Expression expression) {
        if (expression == null) {
            return null;
        }

        expression.accept(getter);
        return getter.type;
    }

    @Override
    public void visit(Subtraction node) {
        type = DFGNodeType.Subtraction;
    }

    @Override
    public void visit(Addition node) {
        type = DFGNodeType.Addition;
    }

    @Override
    public void visit(Division node) {
        type = DFGNodeType.Division;
    }

    @Override
    public void visit(InnerProduct node) {
        type = DFGNodeType.InnerProduct;
    }

    @Override
    public void visit(Multiplication node) {
        type = DFGNodeType.Multiplication;
    }

    @Override
    public void visit(MathFunctionCall node) {
        type = DFGNodeType.MathFunctionCall;
    }

    @Override
    public void visit(Variable node) {
        type = DFGNodeType.Variable;
    }

    @Override
    public void visit(MultivectorComponent node) {
        type = DFGNodeType.MultivectorComponent;
    }

    @Override
    public void visit(Exponentiation node) {
        type = DFGNodeType.Exponentiation;
    }

    @Override
    public void visit(FloatConstant node) {
        type = DFGNodeType.FloatConstant;
    }

    @Override
    public void visit(OuterProduct node) {
        type = DFGNodeType.OuterProduct;
    }

    @Override
    public void visit(BaseVector node) {
        type = DFGNodeType.BaseVector;
    }

    @Override
    public void visit(Negation node) {
        type = DFGNodeType.Negation;
    }

    @Override
    public void visit(Reverse node) {
        type = DFGNodeType.Reverse;
    }

    @Override
    public void visit(LogicalOr node) {
        type = DFGNodeType.LogicalOr;
    }

    @Override
    public void visit(LogicalAnd node) {
        type = DFGNodeType.LogicalAnd;
    }

    @Override
    public void visit(LogicalNegation node) {
        type = DFGNodeType.LogicalNegation;
    }

    @Override
    public void visit(Equality node) {
        type = DFGNodeType.Equality;
    }

    @Override
    public void visit(Inequality node) {
        type = DFGNodeType.Inequality;
    }

    @Override
    public void visit(Relation relation) {
        type = DFGNodeType.Relation;
    }

    @Override
    public void visit(FunctionArgument node) {
        type = DFGNodeType.FunctionArgument;
    }

    @Override
    public void visit(MacroCall node) {
        type = DFGNodeType.MacroCall;
    }
}
