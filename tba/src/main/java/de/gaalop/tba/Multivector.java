package de.gaalop.tba;

import java.util.LinkedList;
import java.util.TreeMap;
import java.util.Vector;

/**
 * Represents a multivector, e.g. a vector of blades
 * @author Christian Steinmetz
 */
public class Multivector {

    private Vector<BladeRef> blades;

    public Multivector() {
        blades = new Vector<BladeRef>();
    }

    /**
     * Adds a BladeRef object to this multivector
     * @param blade The bladeref object to be added
     */
    public void addBlade(BladeRef blade) {
        blades.add(blade);
    }

    /**
     * Returns the values of all non-null blades in this multivector
     * @return The values of all non-null blades
     */
    public TreeMap<Integer, Byte> getValueArr(Algebra algebra) {
        TreeMap<Integer, Byte> result = new TreeMap<>();
        blades.forEach(cur -> {
            result.merge(cur.getIndex(), cur.getPrefactor(), (pL, pR) -> (byte) (pL+pR));
        });
        
        // Remove 0 values
        LinkedList<Integer> nullIndices = new LinkedList<>();
        result.entrySet().forEach(entry -> {
            if (entry.getValue() == 0) 
                nullIndices.add(entry.getKey());
        });
        
        for (Integer nullIndex: nullIndices)
            result.remove(nullIndex);

        return result;
    }

    @Override
    public String toString() {
        return blades.toString();
    }

    public Vector<BladeRef> getBlades() {
        return blades;
    }

    public String print() {
        StringBuilder sb = new StringBuilder();
        for (BladeRef ref: blades) {

            switch (ref.getPrefactor()) {
                case -1:
                    sb.append("-E"+ref.getIndex());
                    break;
                case 0:
                    break;
                case 1:
                    sb.append("+E"+ref.getIndex());
                    break;
                default:
                    System.err.println("Only -1,0,1 allowed as prefactors in multivectors");
                    break;
            }


        }
        if (sb.length()==0) return "";
        if (sb.charAt(0) == '+')
            return sb.substring(1);
        else
            return sb.toString();
    }

}
