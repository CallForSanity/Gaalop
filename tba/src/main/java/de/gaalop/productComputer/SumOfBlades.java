package de.gaalop.productComputer;

import de.gaalop.tba.BladeRef;
import de.gaalop.tba.Multivector;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Represents a sum of weighted blades
 * @author Christian Steinmetz
 */
public class SumOfBlades extends LinkedList<SignedBlade> {

    /**
     * Converts this sum of blades to a multivector
     * @param map The map, which represents the zeroInfBlade->index map
     * @param bitCount The maximum number of bits
     * @return The resulting multivector
     */
    public Multivector toMultivector(HashMap<Blade, Integer> map, int bitCount) {
        Multivector result = new Multivector();
        for (SignedBlade sb: this) {
            Blade b = new Blade(bitCount, sb);
            if (1-Math.abs(sb.coefficient) > 10E-4)
                System.err.println("Error: MvCoeff is not -1,0,1 but "+sb.coefficient);

            Integer m = map.get(b);
            result.addBlade(new BladeRef((sb.coefficient > 0) ? (byte) 1 : (byte) -1, m));
        }
        return result;
    }

    

}
