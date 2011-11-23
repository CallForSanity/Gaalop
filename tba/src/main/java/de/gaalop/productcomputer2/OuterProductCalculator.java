package de.gaalop.productcomputer2;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Implements the ProductCalculator interface for the outer product
 * @author christian
 */
public class OuterProductCalculator implements ProductCalculator {

    public SumOfBlades calculate(Blade blade1, Blade blade2, HashMap<Integer, Integer> baseSquares) {
        Blade result = new Blade(blade1.getPrefactor()*blade2.getPrefactor(), null);


        LinkedList<Integer> base1 = blade1.getBaseVectors();
        LinkedList<Integer> base2 = blade2.getBaseVectors();

        if (base1.isEmpty()) {
            if (base2.isEmpty()) {
                result.setBaseVectors(new LinkedList<Integer>());
                return new SumOfBlades(result);
            } else {
                result.setBaseVectors(base2);
                return new SumOfBlades(result);
            }
        } else {
            if (base2.isEmpty()) {
                result.setBaseVectors(base1);
                return new SumOfBlades(result);
            }
        }

        LinkedList<Integer> baseConcat = new LinkedList<Integer>(base1);
        baseConcat.addAll(base2);

        return new SumOfBlades(new Blade(blade1.getPrefactor()*blade2.getPrefactor(), baseConcat));
    }

}
