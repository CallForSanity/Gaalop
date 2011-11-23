package de.gaalop.productComputer2;

import java.util.HashMap;

/**
 * Defines methods for calculating a product of two blades
 * @author christian
 */
public interface ProductCalculator {

    /**
     * Calculates the product of two blades in PlusMinus base
     * @param blade1 The first blade
     * @param blade2 The second blade
     * @return The product as sum of blades
     */
    public SumOfBlades calculate(Blade blade1, Blade blade2, HashMap<Integer, Integer> baseSquares);

}
