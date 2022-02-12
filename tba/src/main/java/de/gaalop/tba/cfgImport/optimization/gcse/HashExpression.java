package de.gaalop.tba.cfgImport.optimization.gcse;

import de.gaalop.cfg.SequentialNode;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.Variable;
import java.util.Objects;

/**
 * Stores an expression with a pattern as hash
 * @author CSteinmetz15
 */
public class HashExpression {
    
    /**
     * The node, where the expression is located in
     */
    public SequentialNode node;
    
    /**
     * The temporary variable, which is equal to the expression
     */
    public Variable tempVariable;
    
    /**
     * The expression itself
     */
    public Expression expression;
    
    /**
     * The pattern of the expression.
     * The pattern is computed as a comma-separated list of the literally sorted (!) list of as string representation of the expression elements
     */
    public String pattern;
    
    /**
     * Must the expression be negated
     */
    public boolean isNegated;

    public HashExpression(SequentialNode node, Expression expression, String pattern, Variable tempVariable) {
        this.node = node;
        this.expression = expression;
        this.pattern = pattern;
        this.tempVariable = tempVariable;
        this.isNegated = false;
    }

    @Override
    public int hashCode() {
        return pattern.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HashExpression other = (HashExpression) obj;
        return Objects.equals(this.pattern, other.pattern);
    }
}
