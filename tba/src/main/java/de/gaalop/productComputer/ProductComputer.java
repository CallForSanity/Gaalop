package de.gaalop.productComputer;

import de.gaalop.tba.Multivector;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Computes the product of two blades
 * @author Christian Steinmetz
 */
public class ProductComputer {

    private int bitCount;
    private byte[] squareMask;
    private HashMap<Integer, SumOfBlades> mapZIToPM;
    private HashMap<Integer, SumOfBlades> mapPMToZI;

    private SumOfBlades[] bladeListPM;
    private HashMap<Blade, Integer> mapBladeToIndex;

    // ============================ INITIALIZATION ============================

    /**
     * Returns the index of an element in an array
     * @param element The element to search
     * @param arr The array to search in
     * @return The index of the element
     */
    private int getIndex(String element, String[] arr) {
        for (int i = 0;i<arr.length;i++)
            if (element.equals(arr[i]))
                return i;
        return -1;
    }

    /**
     * Converts a BladeStr instance into an SignedBlade
     * @param bladeStr The bladeStr instance
     * @param base The base of the underlying algebra
     * @return The created SignedBlade
     */
    private SignedBlade bladeStrToSignedBlade(BladeStr bladeStr, String[] base) {
        SignedBlade sBlade = new SignedBlade(bitCount, bladeStr.getPrefactor());
        Integer[] arr = new Integer[bladeStr.getBaseVectors().length];
        int i=0;
        for (String baseVector: bladeStr.getBaseVectors()) {
            int index = getIndex(baseVector, base);
            arr[i] = index;
            sBlade.set(index);
            i++;
        }
        if ((BubbleSort.doBubbleSort(arr) % 2) == 1) 
            sBlade.coefficient *= -1;
        
        return sBlade;
    }

    /**
     * Initializes a transformation map
     * @param base The base of the underlying algebra
     * @param source The map of the algebra definition
     * @param dest The destination map
     */
    private void initializeMap(String[] base, String[] base2, HashMap<String, LinkedList<BladeStr>> source, HashMap<Integer, SumOfBlades> dest) {
        for (String baseElement: source.keySet()) {
            LinkedList<BladeStr> list = source.get(baseElement);
            SumOfBlades sumOfBlades = new SumOfBlades();
            for (BladeStr element: list) 
                sumOfBlades.add(bladeStrToSignedBlade(element, base));
            
            dest.put(getIndex(baseElement, base2), sumOfBlades);
        }
    }
    
   /**
    * Helper function for createBlades.
    * @param arrTrailing The trailing array to be inserted before each combination
    * @param startPos The start position in the base array
    * @param k The number of base elements to be inserted
    */
    private void createBladesHelp(Blade arrTrailing, int startPos, int k, LinkedList<SumOfBlades> bladelist) {
        if (k == 1) {
            for (int s=startPos;s<bitCount;++s) {
                Blade nbase = new Blade(bitCount, arrTrailing);
                nbase.set(s);
                SumOfBlades su = new SumOfBlades();
                su.add(new SignedBlade(bitCount, nbase));
                bladelist.add(su);
            }
	} else {
            for (int s=startPos;s<bitCount-1;++s) {
                Blade nbase = new Blade(bitCount, arrTrailing);
                nbase.set(s);
                createBladesHelp(nbase, s+1, k-1, bladelist);
            }
	}
    }

    /**
     * Initializes the ProductComputer using an AlgebraPC instance
     * @param algebraPC The AlgebraPC instance
     */
    public void initialize(AlgebraPC algebraPC) {
        bitCount = algebraPC.base.length;

        //set square mask
        squareMask = new byte[algebraPC.base2.length];
        for (int index = 0;index < algebraPC.base2.length; index++) 
            squareMask[index] = algebraPC.baseSquaresStr.get(algebraPC.base2[index]);
        
        //initialise map Zeroinf to Plusminus
        mapZIToPM = new HashMap<Integer, SumOfBlades>();
        initializeMap(algebraPC.base2, algebraPC.base, algebraPC.mapToPlusMinus, mapZIToPM);

        //initialise map Zeroinf to Plusminus
        mapPMToZI = new HashMap<Integer, SumOfBlades>();
        initializeMap(algebraPC.base, algebraPC.base2, algebraPC.mapToZeroInf, mapPMToZI);

        //initialize blade list in zero inf base
        LinkedList<SumOfBlades> bladeListZI = new LinkedList<SumOfBlades>();
        SumOfBlades s1 = new SumOfBlades();
        s1.add(new SignedBlade(bitCount));
        bladeListZI.add(s1);
        for (int k=1;k<=bitCount;k++) {
            Blade list1 = new Blade(bitCount);
            createBladesHelp(list1, 0, k, bladeListZI);
        }

        //fill indices map and convert blade list to plus minus base
        bladeListPM = new SumOfBlades[bladeListZI.size()];
        mapBladeToIndex = new HashMap<Blade, Integer>();
        int i=0;
        for (SumOfBlades s: bladeListZI) {
            SignedBlade sb = s.getFirst();
            mapBladeToIndex.put(new Blade(bitCount, sb), new Integer(i));
            bladeListPM[i] = BaseTransformation.transform(s, mapZIToPM, bitCount);
            i++;
        }

    }

    // ============================ COMPUTATION OF PRODUCTS ============================

    /**
     * Merges equal blades to one blade
     * @param sumOfBlades
     */
    private void group(SumOfBlades sumOfBlades) {
        HashMap<Blade, Float> map = new HashMap<Blade, Float>();
        for (SignedBlade sb: sumOfBlades) {
            Blade b = new Blade(bitCount, sb);
            if (map.containsKey(b))
                map.put(b, map.get(b)+sb.coefficient);
            else
                map.put(b, sb.coefficient);
        }
        sumOfBlades.clear();
        for (Blade b: map.keySet()) {
            float prefactor = map.get(b);
            if (Math.abs(prefactor) > 10E-4) 
                sumOfBlades.add(new SignedBlade(bitCount, b, prefactor));
        }
    }

    /**
     * Computes a product of two blades
     * @param factor1 The index of the first blade
     * @param factor2 The index of the second blade
     * @param calculator The calculator to be used
     * @return The product of the two blades
     */
    public Multivector calcProduct(Integer factor1, Integer factor2, ProductCalculator calculator) {
        SumOfBlades s1 = bladeListPM[factor1];
        SumOfBlades s2 = bladeListPM[factor2];

        SumOfBlades product = new SumOfBlades();
        
        for (SignedBlade b1: s1)
            for (SignedBlade b2: s2) 
                calculator.calcProduct(b1,b2,product,bitCount,squareMask);
            
        product = BaseTransformation.transform(product, mapPMToZI, bitCount);
        group(product);
        return product.toMultivector(mapBladeToIndex, bitCount);
    }

}
