package de.gaalop.tba;

/**
 * Represents a blade by its index.
 * Stores also a prefactor
 * 
 * @author christian
 */
public class BladeRef {

    private byte prefactor;
    private int index;

    public BladeRef(byte prefactor, int index) {
        this.prefactor = prefactor;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public byte getPrefactor() {
        return prefactor;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setPrefactor(byte prefactor) {
        this.prefactor = prefactor;
    }
}
