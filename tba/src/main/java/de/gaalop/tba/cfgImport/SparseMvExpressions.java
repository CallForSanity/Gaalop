package de.gaalop.tba.cfgImport;

import de.gaalop.dfg.Expression;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores blade expressions
 * @author Adrian Kiesthardt
 *
 * This class stores expressions in a hashmap mapping bladeIndices (Integer) to Expressions.
 * May yield memory benefits if multivectors are sparse.
 *
 */
public class SparseMvExpressions {

    public String nameMv;
    public int bladeCount;
    public Map<Integer, Expression> bladeExpressions;

    public SparseMvExpressions(String nameMv, int bladeCount) {
        this.nameMv = nameMv;
        this.bladeCount = bladeCount;
        bladeExpressions = new HashMap<Integer, Expression>();
    }

    /**
     *
     * @param bladeIndex bladeIndex of the expression to get
     * @return expression with given index, if found. Null otherwise
     */
    public Expression getExpression(int bladeIndex) {
        Expression expression = bladeExpressions.get(bladeIndex);
        return expression;
    }

    /**
     *
     * @param bladeIndex The index to set the expression for
     * @param expression expression to set
     *
     */
    public void setExpression(int bladeIndex, Expression expression) {
        bladeExpressions.put(bladeIndex, expression);
    }

}
