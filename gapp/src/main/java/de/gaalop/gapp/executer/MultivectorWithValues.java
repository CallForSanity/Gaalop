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

    private double[] entries;
    private boolean multivector;

    public MultivectorWithValues(int bladeCount, boolean isMultivector) {
        entries = new double[bladeCount];
        multivector = isMultivector;
        clear();
    }

    public boolean isMultivector() {
        return multivector;
    }

    public void setMultivector(boolean multivector) {
        this.multivector = multivector;
    }

    public double[] getEntries() {
        return entries;
    }

    public void setEntries(double[] entries) {
        this.entries = entries;
    }

    public double getEntry(int blade) {
        return entries[blade];
    }

    public void setEntry(int blade, double value) {
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
        Arrays.fill(entries, 0.0f);
    }
}
