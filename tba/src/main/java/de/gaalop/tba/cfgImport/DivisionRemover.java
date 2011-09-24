package de.gaalop.tba.cfgImport;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.dfg.BinaryOperation;
import de.gaalop.dfg.Division;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.ExpressionVisitor;
import de.gaalop.dfg.MathFunction;
import de.gaalop.dfg.MathFunctionCall;
import de.gaalop.dfg.Multiplication;
import de.gaalop.dfg.UnaryOperation;

/**
 *
 * @author Christian Steinmetz
 */
public class DivisionRemover extends EmptyControlFlowVisitor {

    private Expression resultValue = null;

    private ExpressionVisitor expressionVisitor = new ExpressionTypeVisitor() {

        @Override
        protected void visitBinaryOperation(BinaryOperation node) {
            resultValue = null;
            node.getLeft().accept(this);
            if (resultValue != null) {
                node.setLeft(resultValue);
                resultValue = null;
            }
            node.getRight().accept(this);
            if (resultValue != null) {
                node.setRight(resultValue);
                resultValue = null;
            }

        }

        @Override
        protected void visitUnaryOperation(UnaryOperation node) {
            resultValue = null;
            node.getOperand().accept(this);
            if (resultValue != null) {
                node.setOperand(resultValue);
                resultValue = null;
            }
        }

        @Override
        protected void visitTerminal(Expression node) {
        }

        @Override
        public void visit(Division node) {
            resultValue = new Multiplication(node.getLeft(), new MathFunctionCall(node.getRight(), MathFunction.INVERT));
        }
    };

    @Override
    public void visit(AssignmentNode node) {

        node.getValue().accept(expressionVisitor);
        if (resultValue != null) {
            node.setValue(resultValue);
            resultValue = null;
        }

        super.visit(node);
    }

    
    
}
