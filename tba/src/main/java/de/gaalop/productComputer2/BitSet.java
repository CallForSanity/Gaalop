/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.productComputer2;



/**
 *
 * @author christian
 */
public class BitSet {
    
    private long bits;

    public BitSet(int bitCount) {
        bits = 0; //all bits are false
    }
    
    //set and get
    
    public boolean get(int bit) {
        return (bits & (1 << bit)) != 0;
    }

    public void set(int bit) {
        bits |= (1 << bit);
    }
    
    public void clear() {
        bits = 0;
    }

    //logics

    public void or(BitSet bitSet) {
        bits |= bitSet.bits;
    }

    public void xor(BitSet bitSet) {
        bits ^= bitSet.bits;
    }

    public void and(BitSet bitSet) {
        bits &= bitSet.bits;
    }

    //operations

    public void shiftRight() {
        bits >>= 1;
    }

    //queries
    public int cardinality() {
        return Long.bitCount(bits);
    }

    public boolean intersects(BitSet bitSet) {
        return ((bits & bitSet.bits) != 0);
    }

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
