package de.gaalop.gapp.executer;

import java.util.Arrays;

/**
 * Stores the values of a multivector.
 * The number of entries of a multivector is fixed.
 * It depends only of the used algebra.
 * 
 * @author Christian Steinmetz
 */
public class MultivectorWithValues {

    private float[] entries;

    public MultivectorWithValues(int bladeCount) {
        entries = new float[bladeCount];
        clear();
    }

    public float[] getEntries() {
        return entries;
    }

    public void setEntries(float[] entries) {
        this.entries = entries;
    }

    public float getEntry(int blade) {
        return entries[blade];
    }
    
    public void setEntry(int blade, float value) {
        entries[blade] = value;
    }

    @Override
    public String toString() {
        return Arrays.toString(entries);
    }

    /**
     * Clears all components to zero
     */
    public void clear() {
        Arrays.fill(entries,0.0f);
    }



}
