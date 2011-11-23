package de.gaalop.visitors;

import de.gaalop.dfg.BinaryOperation;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.UnaryOperation;


/**
 * Implements a replace visitor, which replaces a child of an expression,
 * if the returned object of the latter method is not null
 * @author Christian Steinmetz
 */
public class ReplaceVisitor extends ExpressionTypeVisitor {

    public Expression result = null;

    /**
     * Facade method for replacing expressions
     * @param expression The expression to search in
     * @return The new expression, if modified
     */
    public Expression replace(Expression expression) {
        expression.accept(this);
        return (result != null) ? result : expression;
    }

    @Override
    protected void visitBinaryOperation(BinaryOperation node) {
        node.getLeft().accept(this);
        if (result != null) {
            node.setLeft(result);
            result = null;
        }
        node.getRight().accept(this);
        if (result != null) {
            node.setRight(result);
            result = null;
        }
    }

    @Override
    protected void visitUnaryOperation(UnaryOperation node) {
        node.getOperand().accept(this);
        if (result != null) {
            node.setOperand(result);
            result = null;
        }
    }

    @Override
    protected void visitTerminal(Expression node) {
    }

}
