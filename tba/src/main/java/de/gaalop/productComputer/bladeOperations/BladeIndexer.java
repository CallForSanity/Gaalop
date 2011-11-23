package de.gaalop.productComputer.bladeOperations;

import java.util.HashMap;
import de.gaalop.productComputer.dataStruct.TCBlade;
import de.gaalop.tba.Blade;
import java.util.Arrays;

/**
 * Gives blades an index
 * @author Christian Steinmetz
 */
public class BladeIndexer {
    
    private HashMap<Blade, Integer> mapIndices = new HashMap<Blade, Integer>();
    private HashMap<Integer, TCBlade> mapBlades = new HashMap<Integer, TCBlade>();

    private int curCounter = 0;

    /**
     * Returns the index for a blade
     * @param blade The blade
     * @return The index
     */
    public int getIndex(TCBlade blade) {
        Blade arr = new Blade(blade.getBase());
        if (mapIndices.containsKey(arr)) {
            return mapIndices.get(arr).intValue();
        } else {
            mapIndices.put(arr, new Integer(curCounter));
            mapBlades.put(curCounter, new TCBlade(blade.getBase()));
            curCounter++;
            return curCounter-1;
        }
    }

    /**
     * Returns the blade for an index
     * @param index The index
     * @return The blade
     */
    public TCBlade getBlade(int index) {
        return mapBlades.get(index);
    }

    /**
     * Print all blades
     */
    public void printAllBlades() {
        Integer[] arr = mapBlades.keySet().toArray(new Integer[0]);
        Arrays.sort(arr);

        for (Integer key: arr)
            System.out.println(key+" -> "+mapBlades.get(key).toString());
    }

}
