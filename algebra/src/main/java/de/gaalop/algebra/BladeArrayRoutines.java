package de.gaalop.algebra;

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
    public static TCBlade[] createBlades(String[] base) {
        BladeArrayRoutines bladeArrayRoutines = new BladeArrayRoutines(base);
        return bladeArrayRoutines.createBlades();
    }

    /**
     * Prints an array of blades with a leading index
     * @param blades The array of blades
     */
    public static void printBlades(TCBlade[] blades) {
        int index = 0;
        for (TCBlade blade: blades)
            if (blade == null)
                System.out.println(index++ +"null");
            else
                System.out.println(index++ +": "+blade.toString());
    }

    private String[] base;
    private LinkedList<TCBlade> blades = new LinkedList<TCBlade>();

    public BladeArrayRoutines(String[] base) {
        this.base = base;
    }
    
    /**
     * Creates an array of blades from a base
     * @return The array of blades
     */
    public TCBlade[] createBlades() {
        blades.clear();
        blades.add(new TCBlade(new String[]{"1"}));
        for (int k=1;k<=base.length;k++)
            createBladesHelp(new String[0],0,k);
        return blades.toArray(new TCBlade[0]);
    }

    /**
     * Helper function for createBlades.
     * @param arrTrailing The trailing array to be inserted before each combination
     * @param startPos The start position in the base array
     * @param k The number of base elements to be inserted
     */
    private void createBladesHelp(String[] arrTrailing, int startPos, int k) {
        if (k == 1) {
            for (int s=startPos;s<base.length;s++) {
                String[] nbase = Arrays.copyOf(arrTrailing, arrTrailing.length+1);
                nbase[nbase.length-1] = base[s];
                blades.add(new TCBlade(nbase));
            }
        } else {
            for (int s=startPos;s<base.length-1;s++) {
                String[] nbase = Arrays.copyOf(arrTrailing, arrTrailing.length+1);
                nbase[nbase.length-1] = base[s];
                createBladesHelp(nbase, s+1, k-1);
            }
        }
    }

}
