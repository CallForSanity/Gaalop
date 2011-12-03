package de.gaalop.productComputer;

import java.util.HashMap;

/**
 * Represents a list of blades and provides an efficient method for determining the index of a blade
 * @author christian
 */
public class ListOfBlades {

    private Blade[] blades;
    private HashMap<IntArray, Integer> mapIndices = new HashMap<IntArray, Integer>();

    public ListOfBlades(Blade[] bladesZI) {
        setBlades(bladesZI);
    }

    public Blade[] getBlades() {
        return blades;
    }

    public void setBlades(Blade[] blades) {
        this.blades = blades;
        mapIndices.clear();
        for (int i=0;i<blades.length;i++)
            mapIndices.put(new IntArray(blades[i].getBaseVectors().toArray(new Integer[0])), new Integer(i));
    }

    /**
     * Determines the index of a base
     * @param bases The base
     * @return The index
     */
    public int getIndex(Integer[] bases) {
        IntArray arr = new IntArray(bases);
        if (mapIndices.containsKey(arr))
            return mapIndices.get(arr);
        else
            return 0;
    }

}
