package de.gaalop.productComputer2;

import de.gaalop.tba.Multivector;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * This class computes products
 * @author christian
 */
public class ProductComputer {

    private HashMap<Integer, BladeArray> mapBaseChangeToZeroInf;
    private ListOfBlades listOfBlades;
    private SumOfBlades[] bladesPM;
    private HashMap<Integer, Byte> baseSquares;

    /**
     * Do initializations
     * @param base The ZeroInf Base to use
     * @param base2 The PlusMinus Base to use (here are the products easily defined)
     * @param mapToPlusMinus The map from ZeroInf base to PlusMinus base
     * @param mapToZeroInf The map from PlusMinus base to ZeroInf base
     */
    public void initialize(AlgebraPC algebra) {
        //create blades in "blades"
        int[] intBase = new int[algebra.base.length];
        BaseVectors baseVectors = new BaseVectors();
        baseVectors.addBase("1");
        for (int i=0;i<algebra.base.length;i++)
            intBase[i] = baseVectors.addBase(algebra.base[i]);

        Blade[] bladesZI = BladeArrayRoutines.createBlades(intBase);
        listOfBlades = new ListOfBlades(bladesZI);
        //add base2
        for (int i=0;i<algebra.base2.length;i++)
            if (!baseVectors.containsBase(algebra.base2[i]))
                baseVectors.addBase(algebra.base2[i]);

        //baseSquares to integer system
        baseSquares = new HashMap<Integer, Byte>();
        for (String key: algebra.baseSquaresStr.keySet())
            baseSquares.put(baseVectors.getIndex(key), algebra.baseSquaresStr.get(key));

        //base change of blades to plus minus
        HashMap<Integer, BladeArray> mapBaseChangeToPlusMinus = convertMap(algebra.mapToPlusMinus, baseVectors);
        mapBaseChangeToZeroInf = convertMap(algebra.mapToZeroInf, baseVectors);
        bladesPM = new SumOfBlades[bladesZI.length];
        for (int i=0;i<bladesZI.length;i++)
            bladesPM[i] = changeBaseOfBlade(bladesZI[i], mapBaseChangeToPlusMinus);

        //checking if some blades are zero
        for (int i=0;i<bladesZI.length;i++) {
            bladesPM[i].checkIfSomeBladesAreZero();
            bladesPM[i].normalize();
            bladesPM[i].group();
        }

        //test printing
        //for (int i=0;i<blades.length;i++)
        //    System.out.println(blades[i].print(baseVectors)+" = "+bladesPM[i].print(baseVectors));
    }

    public Multivector calcProduct(int i, int j, ProductCalculator calculator) {
        //use bladesPM and mapBaseChangeToZeroInf

        //calculate real product
        SumOfBlades product = new SumOfBlades(new LinkedList<Blade>());
        LinkedList<Blade> blades1 = bladesPM[i].getBlades();
        LinkedList<Blade> blades2 = bladesPM[j].getBlades();
        LinkedList<Blade> prodBlades = product.getBlades();

        for (Blade blade1: blades1)
            for (Blade blade2: blades2)
                prodBlades.addAll(calculator.calculate(blade1, blade2, baseSquares).getBlades());

        product.checkIfSomeBladesAreZero();
        //base change
        SumOfBlades blade = changeBaseOfBlade(product, mapBaseChangeToZeroInf);

        //checking if some blades are zero
        blade.checkIfSomeBladesAreZero();
        //normalise blades
        blade.normalize();
         //group blades
        blade.group();

        //merge to multivector
        return blade.toMultivector(listOfBlades);
    }

    /**
     * Converts a map from a user-friendly format in a efficient format for accesses
     * @param mapBaseChangeStr The map to convert
     * @param baseVectors The initialized BaseVectors object for finding indices
     * @return The new map
     */
    private static HashMap<Integer, BladeArray> convertMap(HashMap<String, LinkedList<BladeStr>> mapBaseChangeStr, BaseVectors baseVectors) {
        HashMap<Integer, BladeArray> mapBaseChange = new HashMap<Integer, BladeArray>();
        for (String key: mapBaseChangeStr.keySet()) {
            LinkedList<BladeStr> listOfStrBlade = mapBaseChangeStr.get(key);
            Blade[] blades = new Blade[listOfStrBlade.size()];
            int i=0;
            for (BladeStr bladeStr: listOfStrBlade)
                blades[i++] = convertBladeStrToBlade(bladeStr, baseVectors);
            mapBaseChange.put(baseVectors.getIndex(key), new BladeArray(blades));
        }
        return mapBaseChange;
    }

    /**
     * Converts a BladeStr object to a Blade object
     * @param bladeStr The BladeStr object
     * @param baseVectors The baseVectors for finding indices
     * @return The new Blade object
     */
    private static Blade convertBladeStrToBlade(BladeStr bladeStr, BaseVectors baseVectors) {
        String[] baseVectorsStr = bladeStr.getBaseVectors();

        LinkedList<Integer> baseV = new LinkedList<Integer>();
        for (int i=0;i<baseVectorsStr.length;i++) 
            baseV.add(baseVectors.getIndex(baseVectorsStr[i]));

        return new Blade(bladeStr.getPrefactor(), baseV);
    }

    /**
     * Changes the base for a sum of blades and saves the result in a SumOfBlades object
     * e1^einf^e3 -> e1^em^e3 + e1^ep^e3
     * @param sumOfBlades The sum of blades
     * @param map The map to use for the basechange
     * @return The new SumOfBlades object
     */
    private static SumOfBlades changeBaseOfBlade(SumOfBlades sumOfBlades, HashMap<Integer, BladeArray> map) {
        LinkedList<Blade> result = new LinkedList<Blade>();
        for (Blade blade: sumOfBlades.getBlades()) 
            result.addAll(changeBaseOfBlade(blade, map).getBlades());
        return new SumOfBlades(result);
    }

    /**
     * Changes the base for a blade and saves the result in a SumOfBlades object
     * e1^einf^e3 -> e1^em^e3 + e1^ep^e3
     * @param blade The blade
     * @param map The map to use for the basechange
     * @return The new SumOfBlades object
     */
    private static SumOfBlades changeBaseOfBlade(Blade blade, HashMap<Integer, BladeArray> map) {
        LinkedList<Integer> baseVectors = blade.getBaseVectors();
        if (baseVectors.size() == 0) return new SumOfBlades(blade);

        //find the lengths for creating a permutation
        int[] lengths = new int[baseVectors.size()];
        int j = 0;
        for (Integer i: baseVectors) {
            lengths[j] = map.containsKey(i)
                    ? map.get(i).getBlades().length
                    : 1;
            j++;
        }


        //create the permutations
        Permutable iterator = new PermutableIterator();
        iterator.initialize(lengths);

        //apply permutations
        LinkedList<Blade> result = new LinkedList<Blade>();

        while (iterator.hasNextPermutation()) {
            IntArray permutation = iterator.getNextPermutation();
            //build current permutation in baseList
            LinkedList<Integer> baseList = new LinkedList<Integer>();

            float prefactor = 1;
            j=0;
            for (Integer i: baseVectors) {
                if (map.containsKey(i)) {
                    Blade b = map.get(i).getBlades()[permutation.getArray()[j]];
                    prefactor *= b.getPrefactor();
                    baseList.addAll(b.getBaseVectors());
                } else {
                    baseList.add(i);
                }
                j++;
            }

            result.add(new Blade(prefactor*blade.getPrefactor(), baseList));
        }
        
        return new SumOfBlades(result);
    }

}
