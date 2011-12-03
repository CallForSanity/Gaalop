package de.gaalop.productComputer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Implements the ProductCalculator interface for the geometric product
 * @author christian
 */
public class GeoProductCalculator implements ProductCalculator {

    public SumOfBlades calculate(Blade blade1, Blade blade2, HashMap<Integer, Byte> baseSquares) {
        //approach method from the dissertation: Daniel Fontijne, "Efficient Implementation of Geometric Algebra" (2007)

        LinkedList<Integer> list1 = new LinkedList(blade1.getBaseVectors());
        LinkedList<Integer> list2 = new LinkedList(blade2.getBaseVectors());

        boolean negate = false;

        list1.removeFirstOccurrence(new Integer(0));
        list2.removeFirstOccurrence(new Integer(0));

        

        Integer element;
        while ((element = commonElement(list1, list2)) != null) {
            int disp1 = list1.size()-list1.indexOf(element)-1;
            int disp2 = list2.indexOf(element);
            if (((disp1+disp2) % 2) == 1)
                negate = !negate;
            list1.remove(element);
            list2.remove(element);
            if (baseSquares.get(element) == -1) negate = !negate;
        }

        LinkedList<Integer> merged = new LinkedList<Integer>();
        merged.addAll(list1);
        merged.addAll(list2);

        float prefactor = blade1.getPrefactor()*blade2.getPrefactor();
        if (negate) prefactor *= -1;

        if (merged.size() == 0) return new SumOfBlades(new Blade(prefactor, new LinkedList<Integer>()));

        return new SumOfBlades(new Blade(prefactor, merged));
    }

    /**
     * Returns a common element of two lists
     * @param list1 The first list
     * @param list2 The second list
     * @return A common element
     */
    private Integer commonElement(List<Integer> list1, List<Integer> list2) {
        for (Integer cur: list1)
            if (list2.contains(cur))
                return cur;
        return null;
    }


}
