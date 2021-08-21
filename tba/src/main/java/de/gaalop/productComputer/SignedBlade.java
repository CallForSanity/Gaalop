package de.gaalop.productComputer;

/**
 * Represents a weighted blade
 * @author Christian Steinmetz
 */
public class SignedBlade extends Blade {

    public float coefficient;

    public SignedBlade(int bitCount) {
        super(bitCount);
        coefficient = 1;
    }

    public SignedBlade(int bitCount, float coefficient) {
        super(bitCount);
        this.coefficient = coefficient;
    }

    public SignedBlade(int bitCount, Blade blade) {
        super(bitCount, blade);
        coefficient = 1;
    }

    public SignedBlade(int bitCount, Blade blade, float coefficient) {
        super(bitCount, blade);
        this.coefficient = coefficient;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        return (Float.floatToIntBits(this.coefficient) == Float.floatToIntBits(((SignedBlade) obj).coefficient));
    }

    @Override
    public int hashCode() {
        return 53*super.hashCode() + Float.floatToIntBits(this.coefficient);
    }

    @Override
    public String toString() {
        return coefficient+"["+super.toString()+"]";
    }
}
