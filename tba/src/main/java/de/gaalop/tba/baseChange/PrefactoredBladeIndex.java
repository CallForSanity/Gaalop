package de.gaalop.tba.baseChange;

/**
 * A class that represents a prefactored blade using a prefactor and bladeIndex
 * @author Christian Steinmetz
 */
public class PrefactoredBladeIndex {
    
    public float prefactor;
    public int bladeIndex;

    public PrefactoredBladeIndex(float prefactor, int bladeIndex) {
        this.prefactor = prefactor;
        this.bladeIndex = bladeIndex;
    }

    @Override
    public String toString() {
        return prefactor+"["+bladeIndex+"]";
    }

}
