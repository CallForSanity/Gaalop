package de.gaalop.productComputer2;

import de.gaalop.tba.BladeRef;
import de.gaalop.tba.Multivector;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Represents a sum of blades
 * @author christian
 */
public class SumOfBlades {

    private LinkedList<Blade> blades;

    public SumOfBlades(Blade blade) {
        blades = new LinkedList<Blade>();
        blades.add(blade);
    }

    public SumOfBlades(LinkedList<Blade> blades) {
        this.blades = blades;
    }

    public LinkedList<Blade> getBlades() {
        return blades;
    }

    public void setBlades(LinkedList<Blade> blades) {
        this.blades = blades;
    }

    @Override
    public String toString() {
        return blades.toString();
    }

    /**
     * Checks, if some blades are zero and removes these ones
     */
    public void checkIfSomeBladesAreZero() {
        LinkedList<Blade> newList = new LinkedList<Blade>();
        for (Blade b: blades)
            if (!containsDoubleIndex(b.getBaseVectors())) 
                newList.add(b);
        blades = newList;
    }

    /**
     * Determines, if an array contains an element at least two times
     * @param arr The array to use
     * @return Contains the given array an element at least two times?
     */
    private boolean containsDoubleIndex(LinkedList<Integer> arr) {
        HashSet<Integer> members = new HashSet<Integer>(arr.size());
        
        for (Integer j: arr)
            if (members.contains(j))
                return true;
            else
                members.add(j);

        return false;
    }

    public String print(BaseVectors baseVectors) {
        StringBuilder sb = new StringBuilder();
        for (Blade b: blades) {
            sb.append(" + ");
            sb.append(b.print(baseVectors).toString());
        }
        if (blades.size() > 0)
            return sb.substring(3);
        else
            return "";
    }

    public void normalize() {
        for (Blade b: blades)
            b.normalize();
    }

    public void group() {
        HashMap<IntArray, Float> groups = new HashMap<IntArray, Float>();

        while (!blades.isEmpty()) {
            Blade b = blades.removeFirst();
            IntArray intArray = new IntArray(b.getBaseVectors().toArray(new Integer[0]));
            
            if (groups.containsKey(intArray)) 
                groups.put(intArray, groups.get(intArray)+b.getPrefactor());
            else
                groups.put(intArray, b.getPrefactor());
        }

        for (IntArray cur: groups.keySet()) 
            blades.add(new Blade(groups.get(cur), new LinkedList<Integer>(Arrays.asList(cur.getArray()))));
    }

    public Multivector toMultivector(ListOfBlades listOfBlades) {
        Multivector result = new Multivector();
        for (Blade blade: blades) {
            float prefactor = blade.getPrefactor();
            byte prefactorB = (byte) Math.round(prefactor);
            if ((Math.abs(prefactor-prefactorB) <= 10E-7) && (Math.abs(prefactorB) <= 1)) {
                if (prefactorB != 0)
                    result.addBlade(new BladeRef(prefactorB, listOfBlades.getIndex(blade.getBaseVectors().toArray(new Integer[0]))));
            }
            else
                System.err.println("Error: Prefactor of multivector is not -1, 0 or 1!");
        }
        return result;
    }

}
