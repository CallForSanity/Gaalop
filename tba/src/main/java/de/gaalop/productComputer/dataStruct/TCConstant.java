package de.gaalop.productComputer.dataStruct;

import de.gaalop.productComputer.dataStruct.visitor.TCExpressionVisitor;

/**
 * Represents a constant
 * @author Christian Steinmetz
 */
public class TCConstant extends TCTerminal {

    private static final float EPSILON = 10E-10f;

    private float value;

    public TCConstant(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TCConstant other = (TCConstant) obj;
        if (Math.abs(other.value-value) > EPSILON) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Float.floatToIntBits(this.value);
        return hash;
    }

    @Override
    public String toString() {
        return Float.toString(value);
    }

    @Override
    public TCExpression copyExpression() {
        return new TCConstant(value);
    }

    @Override
    public Object accept(TCExpressionVisitor visitor, Object arg) {
        return visitor.visitTCConstant(this, arg);
    }

}
