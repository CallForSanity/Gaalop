package de.gaalop.productComputer2;

import java.util.Arrays;

/**
 *
 * @author Christian Steinmetz
 */
public class PermutableIterator implements Permutable {

    private IntArray cur;
    private int curNo;
    private int count;

    private int[] lengths;

    public void initialize(int[] lengths) {
        this.lengths = lengths;
        Integer[] array = new Integer[lengths.length];
        Arrays.fill(array, 0);
        curNo = 0;
        count = 1;
        for (int i=0;i<lengths.length;i++)
            count *= lengths[i];
        cur = new IntArray(array);
    }

    public IntArray getNextPermutation() {
        IntArray result = cur;
        curNo++;
        if (!hasNextPermutation()) return result;
        //Integer[] curNew = Arrays.copyOf(cur.getArray(), cur.getArray().length);
        Integer[] arr = cur.getArray();
        Integer[] curNew = new Integer[arr.length];
        for (int i=0;i<curNew.length;i++)
            curNew[i] = new Integer(arr[i]);

        
        
        int pos = curNew.length;
        boolean ueberlauf = true;
        while (ueberlauf) {
            pos--;
            ueberlauf = (curNew[pos]+1 == lengths[pos]);
            if (ueberlauf) 
                curNew[pos] = 0;
            else
                curNew[pos]++;
        }

        cur = new IntArray(curNew);
        return result;
    }

    public boolean hasNextPermutation() {
        return (curNo<count);
    }

}
