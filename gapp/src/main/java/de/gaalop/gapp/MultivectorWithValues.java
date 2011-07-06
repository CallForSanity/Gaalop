/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.gapp;

import java.util.Arrays;

/**
 *
 * @author christian
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

    public void clear() {
        Arrays.fill(entries,0.0f);
    }



}
