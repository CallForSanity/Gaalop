package de.gaalop.algorithms;

import java.util.Comparator;

/**
 * This class provides the BubbleSort algorithm using a comparator
 * @author Christian Steinmetz
 */
public class BubbleSortComp<T> {
   
    /**
     * BubbleSort Algorithm - from
     * http://de.wikipedia.org/wiki/Bubblesort#Formaler%20Algorithmus
     * @param arr The array to sort in place
     * @return The count of exchanges
     */
    public int doBubbleSort(T[] arr, Comparator<T> comparator) {

        boolean swapped = true;
        int count = 0;
        for(int i = arr.length - 1; i > 0 && swapped; i--) {
            swapped = false;
            for (int j = 0; j < i; j++) {
                if (comparator.compare(arr[j], arr[j+1]) > 0) {
                    T temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                    swapped = true;
                    count++;
                }
            }
        }
        return count;

    }

}
