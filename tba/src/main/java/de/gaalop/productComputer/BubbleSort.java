package de.gaalop.productComputer;

/**
 * This class provides the BubbleSort algorithm using a comparator
 * @author Christian Steinmetz
 */
public class BubbleSort {
   
    /**
     * BubbleSort Algorithm - from
     * http://de.wikipedia.org/wiki/Bubblesort#Formaler%20Algorithmus
     * @param arr The array to sort in place
     * @return The count of exchanges
     */
    public static int doBubbleSort(Integer[] arr) {

        boolean swapped = true;
        int count = 0;
        for(int i = arr.length - 1; i > 0 && swapped; i--) {
            swapped = false;
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j+1]) {
                    Integer temp = arr[j];
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
