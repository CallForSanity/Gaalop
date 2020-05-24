package de.gaalop.tba.cfgImport;

import de.gaalop.dfg.Expression;

import java.util.Arrays;
import java.util.Collection;

/**
 * Stores blade expressions
 * @author Christian Steinmetz
 */
public class DenseMvExpressions implements MvExpressions {

    public String nameMv;
    public Expression[] bladeExpressions;

    public DenseMvExpressions(String nameMv, int bladeCount) {
        this.nameMv = nameMv;
        bladeExpressions = new Expression[bladeCount];
    }

    @Override
    public Expression getExpression(int bladeIndex) {
        return bladeExpressions[bladeIndex];
    }

    @Override
    public void setExpression(int bladeIndex, Expression expression) {
        bladeExpressions[bladeIndex] = expression;
    }

    @Override
    public Collection<Expression> getAllExpressions() {
        return Arrays.asList(this.bladeExpressions);
    }
}
