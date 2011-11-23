package de.gaalop.productComputer2;

import java.util.Arrays;

/**
 * Represents an array of integers
 * @author Christian Steinmetz
 */
public class IntArray {

    private Integer[] array;

    public IntArray(Integer[] array) {
        this.array = array;
    }

    public Integer[] getArray() {
        return array;
    }

    public void setArray(Integer[] array) {
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
