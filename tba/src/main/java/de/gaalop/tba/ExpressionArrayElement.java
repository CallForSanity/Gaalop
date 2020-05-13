package de.gaalop.tba;

import de.gaalop.dfg.Expression;

public class ExpressionArrayElement {



    private int index;
    private Expression expression;



    public ExpressionArrayElement(int index, Expression expression) {
        this.index = index;
        this.expression = expression;
    }




    public int getIndex() {
        return index;
    }


    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }



}
