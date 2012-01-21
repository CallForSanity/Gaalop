package de.gaalop.productComputer2;

/**
 * Represents a blade
 * @author christian
 */
public class Blade extends BitSet {

    public Blade(int bitCount) {
        super(bitCount);
    }

    public Blade(int bitCount, BitSet blade) {
        super(bitCount);
        or(blade);
    }
    
}
