package de.gaalop.productComputer2;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Represents a blade
 * @author christian
 */
public class Blade {

    private float prefactor;
    private LinkedList<Integer> baseVectors;

    public Blade(float prefactor, int baseVector) {
        this.prefactor = prefactor;
        this.baseVectors = new LinkedList<Integer>();
        this.baseVectors.add(baseVector);
    }

    public Blade(float prefactor, LinkedList<Integer> baseVectors) {
        this.prefactor = prefactor;
        this.baseVectors = baseVectors;
    }

    public LinkedList<Integer> getBaseVectors() {
        return baseVectors;
    }

    public float getPrefactor() {
        return prefactor;
    }

    public void setBaseVectors(LinkedList<Integer> baseVectors) {
        this.baseVectors = baseVectors;
    }

    public void setPrefactor(float prefactor) {
        this.prefactor = prefactor;
    }

    @Override
    public String toString() {
        return prefactor+baseVectors.toString();
    }

    public String print(BaseVectors baseVectors) {
        StringBuilder sb = new StringBuilder();
        for (Integer i: this.baseVectors) {
            sb.append(',');
            sb.append(baseVectors.getBaseString(i));
        }

        if (sb.length() > 0)
            return prefactor+"["+sb.substring(1)+"]";
        else
            return prefactor+"["+sb.toString()+"]";
    }

    public void normalize() {
        Integer[] arr = baseVectors.toArray(new Integer[0]);
        if ((BubbleSort.doBubbleSort(arr) % 2) == 1)
            prefactor *= -1;
        baseVectors.clear();
        baseVectors.addAll(Arrays.asList(arr));
    }

    public void negate() {
        prefactor *= -1;
    }


}
