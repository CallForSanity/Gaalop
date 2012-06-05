package de.gaalop.algorithms;

import java.util.Arrays;

/**
 * Represents an array of integers
 * @author Christian Steinmetz
 */
public class IntArray {

    private int[] array;

    public IntArray(int[] array) {
        this.array = array;
    }

    public int[] getArray() {
        return array;
    }

    public void setArray(int[] array) {
        this.array = array;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final IntArray other = (IntArray) obj;
        if (!Arrays.equals(this.array, other.array)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Arrays.hashCode(this.array);
        return hash;
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }



}
