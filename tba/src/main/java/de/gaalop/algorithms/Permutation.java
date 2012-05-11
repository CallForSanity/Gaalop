package de.gaalop.algorithms;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Returns all permutations of a given length array
 * @author Christian Steinmetz
 */
public class Permutation {

    /**
     * Facade method for getting all permutations of a given length array
     * @param lengths The length array
     * @return The permutation list
     */
    public static LinkedList<IntArray> getPermutations(int[] lengths) {
        Permutation permutation = new Permutation(lengths);
        return permutation.getPermutations();
    }

    private LinkedList<IntArray> permutations = new LinkedList<IntArray>();
    private int[] lengths;

    public Permutation(int[] lengths) {
        this.lengths = lengths;
    }

    /**
     * Returns all permutations
     * @return The permutations
     */
    public LinkedList<IntArray> getPermutations() {
        getPermutationsHelp(new IntArray(new int[0]),0);
        return permutations;
    }

    /**
     * Computes the permutations of a subset
     * @param arrTrailing The array to insert before all here computed permutations
     * @param location The location of the first digit to permutate
     */
    private void getPermutationsHelp(IntArray arrTrailing, int location) {
        if (location == lengths.length-1) {
            for (int i=0;i<lengths[location];i++) {
                IntArray arr = new IntArray(Arrays.copyOf(arrTrailing.getArray(), arrTrailing.getArray().length+1));
                arr.getArray()[arr.getArray().length-1] = i;
                permutations.add(arr);
            }
        } else {
            for (int i=0;i<lengths[location];i++) {
                IntArray arr = new IntArray(Arrays.copyOf(arrTrailing.getArray(), arrTrailing.getArray().length+1));
                arr.getArray()[arr.getArray().length-1] = i;
                getPermutationsHelp(arr, location+1);
            }
        }

    }

}
