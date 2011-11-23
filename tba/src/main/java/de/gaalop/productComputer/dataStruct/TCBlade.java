package de.gaalop.productComputer.dataStruct;

import de.gaalop.dfg.BaseVector;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.OuterProduct;
import java.util.Arrays;
import de.gaalop.productComputer.dataStruct.visitor.TCExpressionVisitor;

/**
 * Represents a blade
 * @author Christian Steinmetz
 */
public class TCBlade extends TCExpression {

    private String[] base;
    private boolean negated;

    public TCBlade(String[] base) {
        this.base = base;
        this.negated = false;
    }

    public TCBlade(String[] base, boolean negated) {
        this.base = base;
        this.negated = negated;
    }

    public String[] getBase() {
        return base;
    }

    public void setBase(String[] base) {
        this.base = base;
    }

    public boolean isNegated() {
        return negated;
    }

    public void setNegated(boolean negated) {
        this.negated = negated;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TCBlade other = (TCBlade) obj;
        if (!Arrays.deepEquals(this.base, other.base)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Arrays.deepHashCode(this.base);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (String tcBase: base) {
            result.append("^");
            result.append(tcBase);
        }
        String resultStr = result.substring(1);
        
        return (negated) ? "-"+resultStr : resultStr;
    }

    @Override
    public boolean isComposite() {
        return true;
    }

    @Override
    public TCExpression copyExpression() {
        return new TCBlade(Arrays.copyOf(base, base.length), negated);
    }

    @Override
    public Object accept(TCExpressionVisitor visitor, Object arg) {
        return visitor.visitTCBlade(this, arg);
    }

}
