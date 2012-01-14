/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.productComputer2;

import de.gaalop.tba.BladeRef;
import de.gaalop.tba.Multivector;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author christian
 */
public class SumOfBlades extends LinkedList<SignedBlade> {

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
