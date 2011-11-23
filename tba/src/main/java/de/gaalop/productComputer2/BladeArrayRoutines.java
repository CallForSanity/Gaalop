package de.gaalop.productComputer2;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Provides methods for blade arrays
 * @author Christian Steinmetz
 */
public class BladeArrayRoutines {

    /**
     * Facilitate the access for creating an array of blades from a base
     * @param base The base
     * @return The array of blades
     */
    public static Blade[] createBlades(int[] base) {
        BladeArrayRoutines bladeArrayRoutines = new BladeArrayRoutines(base);
        return bladeArrayRoutines.createBlades();
    }

    /**
     * Prints an array of blades with a leading index
     * @param blades The array of blades
     */
    public static void printBlades(Blade[] blades, BaseVectors baseVectors) {
        int index = 0;
        for (Blade blade: blades)
            if (blade == null)
                System.out.println(index++ +"null");
            else
                System.out.println(index++ +": "+blade.print(baseVectors));
    }

    private int[] base;
    private LinkedList<Blade> blades = new LinkedList<Blade>();

    public BladeArrayRoutines(int[] base) {
        this.base = base;
    }

    /**
     * Creates an array of blades from a base
     * @param base The base
     * @return The array of blades
     */
    public Blade[] createBlades() {
        blades.clear();
        blades.add(new Blade(1, new LinkedList<Integer>()));
        for (int k=1;k<=base.length;k++)
            createBladesHelp(new LinkedList<Integer>(),0,k);
        return blades.toArray(new Blade[0]);
    }

    /**
     * Helper function for createBlades.
     * @param arrTrailing The trailing array to be inserted before each combination
     * @param startPos The start position in the base array
     * @param k The number of base elements to be inserted
     */
    private void createBladesHelp(LinkedList<Integer> arrTrailing, int startPos, int k) {
        if (k == 1) {
            for (int s=startPos;s<base.length;s++) {
                LinkedList<Integer> nbase = new LinkedList<Integer>(arrTrailing);
                nbase.add(base[s]);
                blades.add(new Blade(1,nbase));
            }
        } else {
            for (int s=startPos;s<base.length-1;s++) {
                LinkedList<Integer> nbase = new LinkedList<Integer>(arrTrailing);
                nbase.add(base[s]);
                createBladesHelp(nbase, s+1, k-1);
            }
        }
    }

}
