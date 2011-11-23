package de.gaalop.productcomputer2;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Implements the ProductCalculator interface for the inner product
 * @author christian
 */
public class InnerProductCalculator implements ProductCalculator {

    private HashMap<Integer, Integer> baseSquares;

    public SumOfBlades calculate(Blade blade1, Blade blade2, HashMap<Integer, Integer> baseSquares) {
        this.baseSquares = baseSquares;
        
        LinkedList<Integer> base1 = blade1.getBaseVectors();
        LinkedList<Integer> base2 = blade2.getBaseVectors();

        if (base1.size() == 0 || base2.size() == 0)
            return new SumOfBlades(new LinkedList<Blade>());
        


        Blade result;
        if (base1.size() == 1) {
            if (base2.size() == 1)
                result = calculateProductBlades(base1.getFirst(), base2.getFirst());
            else
                result = calculateProductBlades(base1.getFirst(), base2);
        } else {
            if (base2.size() == 1)
                result = calculateProductBlades(base1, base2.getFirst());
            else
                result = calculateProductBlades(base1, base2);
        }
        result.setPrefactor(result.getPrefactor()*blade1.getPrefactor()*blade2.getPrefactor());

        return new SumOfBlades(result);

    }

    /**
     * Calculates a product of two bases, which have both more than one base element
     * @param base1 The first base
     * @param base2 The second base
     */
    private Blade calculateProductBlades(LinkedList<Integer> base1, LinkedList<Integer> base2) {


        if (base1.size() > base2.size()) {
            // Bl*ak, k<l

            Iterator<Integer> it = base2.listIterator();
            Blade result = calculateProductBlades(base1, it.next());
            while (it.hasNext()) {
                Blade result2 = calculateProductBlades(result.getBaseVectors(), it.next());
                result2.setPrefactor(result2.getPrefactor()*result.getPrefactor());
                result = result2;
            }

            return result;
        } else {
            // ak*bl, k<l
            Iterator<Integer> it = base1.descendingIterator();
            Blade result = calculateProductBlades(it.next(), base2);

            while (it.hasNext()) {
                Blade result2 = calculateProductBlades(it.next(), result.getBaseVectors());
                result2.setPrefactor(result2.getPrefactor()*result.getPrefactor());
                result = result2;
            }


            return result;
        }

    }

    /**
     * Returns the index of an int in an int arr
     * @param search The searched int
     * @param arr The array to search in
     * @return The index, -1 if the array doesn't contain the searched int
     */
    private static int getIndexOfString(Integer search, LinkedList<Integer> arr) {
        int i=0;
        for (Integer j: arr) {
            if (search == j)
                return i;
            i++;
        }
        return -1;
    }

    /**
     * Calculates a product of a base element and a base
     * @param base1 The base element
     * @param base2 The base
     * @return The inner product as Expression
     */
    private Blade calculateProductBlades(Integer base1, LinkedList<Integer> base2) {
        int index = getIndexOfString(base1, base2);
        if (index > -1) {
            LinkedList<Integer> arr = new LinkedList<Integer>();
            int j=0;
            for (Integer i: base2) {
                if (j != index)
                    arr.add(i);
                j++;
            }

            float prefactor = 1;
            if ((index%2) == 1)
                prefactor *= -1;

            if (baseSquares.get(base1) == -1)
                prefactor *= -1;

            return new Blade(prefactor, arr);
        } else
            return new Blade(0, new LinkedList<Integer>());
    }

    /**
     * Calculates a product of a base and a base element
     * @param base1 The base
     * @param base2 The base element
     * @return The inner product as Expression
     */
    private Blade calculateProductBlades(LinkedList<Integer> base1, Integer base2) {
        int index = getIndexOfString(base2, base1);
        if (index > -1) {
            LinkedList<Integer> arr = new LinkedList<Integer>();
            int j=0;
            for (Integer i: base1) {
                if (j != index)
                    arr.add(i);
                j++;
            }

            float prefactor = 1;
            if (((base1.size()-index-1) %2) == 1)
                prefactor *= -1;

            if (baseSquares.get(base2) == -1)
                prefactor *= -1;

            return new Blade(prefactor, arr);
        } else
            return new Blade(0, new LinkedList<Integer>());
    }

    /**
     * Calculates a product two base elements
     * @param base1 The base element
     * @param base2 The base element
     * @return The inner product as Expression
     */
    private Blade calculateProductBlades(int base1, int base2) {
        if (base1 == base2)
            return new Blade((base1 != 0) ? baseSquares.get(base1) : 0, new LinkedList<Integer>());
        return new Blade(0, new LinkedList<Integer>());
    }


}
