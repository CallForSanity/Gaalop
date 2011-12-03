package de.gaalop.tba;

import java.util.Arrays;
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
     * Returns the values of all blades in this multivector
     * @return The values of all blades
     */
    public byte[] getValueArr(Algebra algebra) {
        int size = algebra.getBladeCount();
        byte[] result = new byte[size];
        Arrays.fill(result, (byte) 0);

        for (BladeRef cur : blades) {
            result[cur.getIndex()] += cur.getPrefactor();
        }

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
