package de.gaalop.tba.cfgImport;

import de.gaalop.dfg.Expression;
import java.util.Collection;
import java.util.TreeMap;

/**
 * Stores blade expressions
 * @author Adrian Kiesthardt, adapted to using TreeMap by Christian Steinmetz
 *
 * This class stores expressions in a hashmap mapping bladeIndices (Integer) to Expressions.
 * May yield memory benefits if multivectors are sparse.
 *
 */
public class MvExpressions {

    public String nameMv;
    public int bladeCount;
    public TreeMap<Integer, Expression> bladeExpressions;   //Stores non-null blade expressions using their bladeIndex as key

    public MvExpressions(String nameMv, int bladeCount) {
        this.nameMv = nameMv;
        this.bladeCount = bladeCount;
        bladeExpressions = new TreeMap<Integer, Expression>();
    }

    /**
     *
     * @param bladeIndex bladeIndex of the expression to get
     * @return expression with given index, if found. Null otherwise
     */
    public Expression getExpression(int bladeIndex) {
        return bladeExpressions.get(bladeIndex);
    }

    /**
     *
     * @param bladeIndex The index to set the expression for
     * @param expression expression to set
     *
     */
    public void setExpression(int bladeIndex, Expression expression) {
        if (expression == null)
            bladeExpressions.remove(bladeIndex);    // We would like store only non-null blades -> We remove the blade from the map
        else
            bladeExpressions.put(bladeIndex, expression);
    }

    public Collection<Expression> getAllExpressions() {
        return this.bladeExpressions.values();
    }
}
