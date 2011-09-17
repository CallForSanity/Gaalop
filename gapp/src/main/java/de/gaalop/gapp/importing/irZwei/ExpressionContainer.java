package de.gaalop.gapp.importing.irZwei;

import de.gaalop.dfg.Expression;

/**
 *
 * @author Christian Steinmetz
 */
public class ExpressionContainer extends ParallelObject {

    private Expression expression;

    public ExpressionContainer(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public Object accept(ParallelObjectVisitor visitor, Object arg) {
        return visitor.visitExpressionContainer(this, arg);
    }

}
