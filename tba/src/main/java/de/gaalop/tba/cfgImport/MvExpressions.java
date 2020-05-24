package de.gaalop.tba.cfgImport;

import de.gaalop.dfg.Expression;

import java.util.Collection;

public interface MvExpressions {

    /**
     *
     * @param bladeIndex bladeIndex of the expression to get
     * @return expression with given index, if found. Null otherwise
     */
    public Expression getExpression(int bladeIndex);

    /**
     *
     * @param bladeIndex The index to set the expression for
     * @param expression expression to set
     *
     */
    public void setExpression(int bladeIndex, Expression expression);

    public Collection<Expression> getAllExpressions();
}
