package de.gaalop.tba.cfgImport;

import de.gaalop.dfg.Expression;
import de.gaalop.tba.ExpressionArrayElement;

import java.util.LinkedList;

/**
 * Stores blade expressions
 * @author Christian Steinmetz
 */
public class SparseMvExpressions {

    public String nameMv;
    public int bladeCount;
    public LinkedList<ExpressionArrayElement> bladeExpressions;

    public SparseMvExpressions(String nameMv, int bladeCount) {
        this.nameMv = nameMv;
        this.bladeCount = bladeCount;
        bladeExpressions = new LinkedList<ExpressionArrayElement>();
    }


    public ExpressionArrayElement getExpressionArrayElement(int bladeIndex) {
        for(ExpressionArrayElement expression : this.bladeExpressions) {
            if(expression.getIndex() == bladeIndex) {
                return expression;
            }
        }
        return null;
    }

    public Expression getExpression(int bladeIndex) {
        ExpressionArrayElement element = this.getExpressionArrayElement(bladeIndex);
        if(element == null) {
            return null;
        } else {
            return element.getExpression();
        }
    }

    public void setExpression(int bladeIndex, Expression expression) {
        ExpressionArrayElement element = this.getExpressionArrayElement(bladeIndex);
        if(element == null) {
            this.bladeExpressions.add(new ExpressionArrayElement(bladeIndex, expression));
        } else {
            element.setExpression(expression);
        }
    }

}
