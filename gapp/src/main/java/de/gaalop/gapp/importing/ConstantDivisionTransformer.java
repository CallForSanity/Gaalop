package de.gaalop.gapp.importing;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.dfg.BinaryOperation;
import de.gaalop.dfg.Division;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.ExpressionVisitor;
import de.gaalop.dfg.FloatConstant;
import de.gaalop.dfg.Multiplication;
import de.gaalop.dfg.UnaryOperation;
import de.gaalop.visitors.ExpressionTypeVisitor;

/**
 * Replaces divisions with constants with multiplications
 * @author Christian Steinmetz
 */
public class ConstantDivisionTransformer extends EmptyControlFlowVisitor {

    //private constructor to make usage of transform-method mandatory
    private ConstantDivisionTransformer() {
    }

    /**
     * Replaces divisions with constants with multiplications
     * @param graph The graph
     */
    public static void transform(ControlFlowGraph graph) {
        ConstantDivisionTransformer transformer = new ConstantDivisionTransformer();
        graph.accept(transformer);
    }

    private Expression replaceWith = null;

    private ExpressionVisitor expressionVisitor = new ExpressionTypeVisitor() {

        @Override
        protected void visitBinaryOperation(BinaryOperation node) {
            node.getLeft().accept(expressionVisitor);
            if (replaceWith != null) {
                node.setLeft(replaceWith);
                replaceWith = null;
            }
            node.getRight().accept(expressionVisitor);
            if (replaceWith != null) {
                node.setRight(replaceWith);
                replaceWith = null;
            }
        }

        @Override
        protected void visitUnaryOperation(UnaryOperation node) {
            node.getOperand().accept(expressionVisitor);
            if (replaceWith != null) {
                node.setOperand(replaceWith);
                replaceWith = null;
            }
        }

        @Override
        protected void visitTerminal(Expression node) {
        }

        @Override
        public void visit(Division node) {
            if (node.getRight() instanceof FloatConstant) {
                FloatConstant newLeft = new FloatConstant(1/((FloatConstant) node.getRight()).getValue());
                replaceWith = new Multiplication(newLeft, node.getLeft());
            }
        }
    };

    @Override
    public void visit(AssignmentNode node) {
        node.getValue().accept(expressionVisitor);
        if (replaceWith != null) {
            node.setValue(replaceWith);
            replaceWith = null;
        }
        super.visit(node);
    }
}
