package de.gaalop.productComputer;

/**
 * Represents a blade
 * @author Christian Steinmetz
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
