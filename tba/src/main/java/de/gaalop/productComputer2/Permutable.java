package de.gaalop.productComputer2;

/**
 *
 * @author Christian Steinmetz
 */
public interface Permutable {

    public void initialize(int[] lengths);

    public IntArray getNextPermutation();

    public boolean hasNextPermutation();

}
