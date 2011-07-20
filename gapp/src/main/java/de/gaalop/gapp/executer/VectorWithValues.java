package de.gaalop.gapp.executer;

import java.util.Arrays;

/**
 * Stores the values of a vector.
 * The number of entries of a vector is not fixed.
 *
 * A addEntry method isn't implemented, keeping in mind, 
 * that the size of a vector is well-known by it's creation,
 * so the setEntries method can be used.
 *
 * @author christian
 */
public class VectorWithValues {

    private float[] entries;

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
