package de.gaalop.productComputer;



/**
 * Provides a bitset.
 * Internally the bits are stored in a variable with the type long
 * @author Christian Steinmetz
 */
public class BitSet {
    
    private long bits;

    public BitSet(int bitCount) {
        bits = 0; //all bits are false
    }
    
    //set and get

    /**
     * Gets the bit at a specified position
     * @param bit The bit index
     * @return The bit
     */
    public boolean get(int bit) {
        return (bits & (1 << bit)) != 0;
    }

    /**
     * Sets the bit at a specified position
     * @param bit The bit index
     */
    public void set(int bit) {
        bits |= (1 << bit);
    }

    /**
     * Clears all bits in this bitset
     */
    public void clear() {
        bits = 0;
    }

    //logics

    /**
     * Perfoms an OR operation with another bitset.
     * The result is stored in this bitset
     * @param bitSet The another bitset to perform the OR operation with
     */
    public void or(BitSet bitSet) {
        bits |= bitSet.bits;
    }

    /**
     * Perfoms an XOR operation with another bitset.
     * The result is stored in this bitset
     * @param bitSet The another bitset to perform the XOR operation with
     */
    public void xor(BitSet bitSet) {
        bits ^= bitSet.bits;
    }

    /**
     * Perfoms an AND operation with another bitset.
     * The result is stored in this bitset
     * @param bitSet The another bitset to perform the AND operation with
     */
    public void and(BitSet bitSet) {
        bits &= bitSet.bits;
    }

    //operations

    /**
     * Shifts all bits right for one position
     */
    public void shiftRight() {
        bits >>= 1;
    }

    //queries
    /**
     * Returns the cardinality of this bitset.
     * The cardinality is the number of all bits, which are set.
     * @return The cardinality
     */
    public int cardinality() {
        return Long.bitCount(bits);
    }

    /**
     * Returns, if this bitset intersects with another bitset
     * @param bitSet The other bitset
     * @return true, if the intersection is not empty; false otherwise
     */
    public boolean intersects(BitSet bitSet) {
        return ((bits & bitSet.bits) != 0);
    }

    /**
     * Tests, if no bit is set in this bitset
     * @return true, if no bit is set; false otherwise
     */
    public boolean isEmpty() {
        return bits == 0;
    }

    @Override
    public boolean equals(Object obj) {
       
        if (!(obj instanceof BitSet)) return false;
        BitSet other = (BitSet) obj;

        if (other.bits != bits) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (int) (this.bits ^ (this.bits >>> 32));
        return hash;
    }

   

}
