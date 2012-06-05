package de.gaalop.tba.cfgImport.optimization.maxima;

import de.gaalop.dfg.Addition;
import de.gaalop.dfg.BaseVector;
import de.gaalop.dfg.BinaryOperation;
import de.gaalop.dfg.Division;
import de.gaalop.dfg.Equality;
import de.gaalop.dfg.Exponentiation;
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
import de.gaalop.dfg.UnaryOperation;
import de.gaalop.dfg.Variable;

/**
 * This visitor generates maxima code from an expression
 * @author Christian Steinmetz
 */
public class DFGToMaximaCode implements ExpressionVisitor {

    private StringBuilder result = new StringBuilder();

    public String getResultString() {
        return result.toString();
    }

    private void handleUnary(UnaryOperation node, String operator) {
        result.append("(");
        result.append(operator);
        result.append("(");
        node.getOperand().accept(this);
        result.append("))");
    }

    private void handleBinary(BinaryOperation node, String operator) {
        result.append("(");
        node.getLeft().accept(this);
        result.append(operator);
        node.getRight().accept(this);
        result.append(")");
    }

    @Override
    public void visit(Subtraction node) {
        handleBinary(node, "-");
    }

    @Override
    public void visit(Addition node) {
        handleBinary(node, "+");
    }

    @Override
    public void visit(Division node) {
        handleBinary(node, "/");
    }

    @Override
    public void visit(InnerProduct node) {
        throw new IllegalStateException("Inner products should have been removed.");
    }

    @Override
    public void visit(Multiplication node) {
        handleBinary(node, "*");
    }

    @Override
    public void visit(MathFunctionCall node) {
        String opName = node.getFunction().name().toLowerCase();
        switch (node.getFunction()) {
            case CEIL:
                opName = "ceiling";
                break;
            case FACT:
                opName = "";
                result.append("(");
                node.getOperand().accept(this);
                result.append("!)");
                return;
        }

        handleUnary(node, opName);
    }

    @Override
    public void visit(Variable node) {
        result.append(node.getName());
    }

    @Override
    public void visit(MultivectorComponent node) {
        result.append(node.getName());
        result.append("\\$");
        result.append(node.getBladeIndex());
    }

    @Override
    public void visit(Exponentiation node) {
        handleBinary(node, "^");
    }

    @Override
    public void visit(FloatConstant node) {
        result.append(Double.toString(node.getValue()));
    }

    @Override
    public void visit(OuterProduct node) {
        throw new IllegalStateException("Outer products should have been removed.");
    }

    @Override
    public void visit(BaseVector node) {
        throw new IllegalStateException("BaseVectors should have been removed.");
    }

    @Override
    public void visit(Negation node) {
        result.append("(");
        handleUnary(node, "-");
        result.append(")");
    }

    @Override
    public void visit(Reverse node) {
        throw new IllegalStateException("Reverses should have been removed.");
    }

    @Override
    public void visit(LogicalOr node) {
        handleBinary(node, " or ");
    }

    @Override
    public void visit(LogicalAnd node) {
        handleBinary(node, " and ");
    }

    @Override
    public void visit(LogicalNegation node) {
        handleUnary(node, "not ");
    }

    @Override
    public void visit(Equality node) {
        result.append("equal(");
        node.getLeft().accept(this);
        result.append(",");
        node.getRight().accept(this);
        result.append(")");
    }

    @Override
    public void visit(Inequality node) {
        result.append("notequal(");
        node.getLeft().accept(this);
        result.append(",");
        node.getRight().accept(this);
        result.append(")");
    }

    @Override
    public void visit(Relation relation) {
        String operator = null;
        switch (relation.getType()) {
            case GREATER:
                operator = ">";
                break;
            case GREATER_OR_EQUAL:
                operator = ">=";
                break;
            case LESS:
                operator = "<";
                break;
            case LESS_OR_EQUAL:
                operator = "<=";
                break;
        }

        if (operator != null) {
            handleBinary(relation, operator);
        }
    }

    @Override
    public void visit(FunctionArgument node) {
        throw new IllegalStateException("FunctionArguments should have been removed.");
    }

    @Override
    public void visit(MacroCall node) {
        throw new IllegalStateException("Macros should have been removed.");
    }
}
