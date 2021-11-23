package de.gaalop.tba.cfgImport.optimization;

import de.gaalop.dfg.BinaryOperation;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.UnaryOperation;
import de.gaalop.visitors.ReplaceVisitor;

/**
 * Expression visitor for replacing certain expressions
 * @author Christian Steinmetz
 */
public class ExpressionReplacer extends ReplaceVisitor {
    
    private final Expression toReplace;
    private final Expression replacement;

    public ExpressionReplacer(Expression toReplace, Expression replacement) {
        this.toReplace = toReplace;
        this.replacement = replacement;
    }

    @Override
    public Expression replace(Expression expression) {
        if (expression == toReplace)
            return replacement;
        else
            return super.replace(expression);
    }

    @Override
    protected void visitBinaryOperation(BinaryOperation node) {
        if (node == toReplace) {
            result = replacement;
            return;
        }
        if (node.getLeft() == toReplace) {
            node.setLeft(replacement);
        }
        if (node.getRight() == toReplace) {
            node.setRight(replacement);
        }   

        super.visitBinaryOperation(node);
    }

    @Override
    protected void visitUnaryOperation(UnaryOperation node) {
        if (node == toReplace) {
            result = replacement;
            return;
        }
        if (node.getOperand() == toReplace) {
            node.setOperand(replacement);
        }  
        
        super.visitUnaryOperation(node);
    }
    
    @Override
    protected void visitTerminal(Expression node) {
        if (node == toReplace) {
            result = replacement;
        }
    }

}
