package de.gaalop.gapp.importing;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.EmptyControlFlowVisitor;
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
 * Implements a ControlFlowVisitor and ExpressionVisitor,
 * which searches occourences of a variable name and replaces this occourences
 * with a given MultivectorComponent instance
 * 
 * @author Christian Steinmetz
 */
public class ReplaceVisitor extends EmptyControlFlowVisitor implements ExpressionVisitor {

    private Variable search;
    private MultivectorComponent toReplace;
    private boolean replace;

    public ReplaceVisitor(Variable search, MultivectorComponent toReplace) {
        this.search = search;
        this.toReplace = toReplace;
        replace = false;
    }

    @Override
    public void visit(AssignmentNode node) {
        node.getValue().accept(this);
        if (replace) {
            node.setValue(toReplace);
            replace = false;
        }

        super.visit(node);
    }

    /**
     * Visits a BinaryOperation instance
     * @param binaryOperation The BinaryOperation
     */
    private void visitBinaryOperation(BinaryOperation binaryOperation) {
        binaryOperation.getLeft().accept(this);
        if (replace) {
            binaryOperation.setLeft(toReplace);
            replace = false;
        }
        binaryOperation.getRight().accept(this);
        if (replace) {
            binaryOperation.setRight(toReplace);
            replace = false;
        }
    }

    /**
     * Visits a UnaryOperation instance
     * @param unaryOperation The UnaryOperation
     */
    private void visitUnaryOperation(UnaryOperation unaryOperation) {
        unaryOperation.getOperand().accept(this);
        if (replace) {
            unaryOperation.setOperand(toReplace);
            replace = false;
        }
    }

    @Override
    public void visit(Subtraction node) {
        visitBinaryOperation(node);
    }

    @Override
    public void visit(Addition node) {
        visitBinaryOperation(node);
    }

    @Override
    public void visit(Division node) {
        visitBinaryOperation(node);
    }

    @Override
    public void visit(InnerProduct node) {
        visitBinaryOperation(node);
    }

    @Override
    public void visit(Multiplication node) {
        visitBinaryOperation(node);
    }

    @Override
    public void visit(MathFunctionCall node) {
        visitUnaryOperation(node);
    }

    @Override
    public void visit(Variable node) {
        if (node.getName().equals(search.getName())) {
            replace = true;
        }
    }

    @Override
    public void visit(MultivectorComponent node) {
    }

    @Override
    public void visit(Exponentiation node) {
        visitBinaryOperation(node);
    }

    @Override
    public void visit(FloatConstant node) {
    }

    @Override
    public void visit(OuterProduct node) {
        visitBinaryOperation(node);
    }

    @Override
    public void visit(BaseVector node) {
    }

    @Override
    public void visit(Negation node) {
        visitUnaryOperation(node);
    }

    @Override
    public void visit(Reverse node) {
        visitUnaryOperation(node);
    }

    @Override
    public void visit(LogicalOr node) {
        visitBinaryOperation(node);
    }

    @Override
    public void visit(LogicalAnd node) {
        visitBinaryOperation(node);
    }

    @Override
    public void visit(LogicalNegation node) {
        visitUnaryOperation(node);
    }

    @Override
    public void visit(Equality node) {
        visitBinaryOperation(node);
    }

    @Override
    public void visit(Inequality node) {
        visitBinaryOperation(node);
    }

    @Override
    public void visit(Relation relation) {
        visitBinaryOperation(relation);
    }

    @Override
    public void visit(FunctionArgument node) {
        throw new IllegalStateException("Macros should have been inlined");
    }

    @Override
    public void visit(MacroCall node) {
        throw new IllegalStateException("Macros should have been inlined");
    }
}
