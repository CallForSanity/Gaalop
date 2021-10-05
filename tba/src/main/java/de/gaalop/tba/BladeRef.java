package de.gaalop.tba;

/**
 * Represents a blade by its index.
 * Stores also a prefactor
 * 
 * @author Christian Steinmetz
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

    @Override
    public String toString() {
        return prefactor+"["+index+"]";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.prefactor;
        hash = 59 * hash + this.index;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BladeRef other = (BladeRef) obj;
        if (this.prefactor != other.prefactor) {
            return false;
        }
        if (this.index != other.index) {
            return false;
        }
        return true;
    }

    

}
