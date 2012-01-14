/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.productComputer2;

import java.util.HashMap;

/**
 *
 * @author christian
 */
public class BaseTransformation {

    public static SumOfBlades transform(SumOfBlades s, HashMap<Integer, SumOfBlades> map, int bitCount) {
        SumOfBlades result = new SumOfBlades();
        for (SignedBlade blade: s) {
            //create lengths array for permutation
            SumOfBlades[] lengths = new SumOfBlades[bitCount];
            for (int bit=0;bit<bitCount;++bit) {
                lengths[bit] = new SumOfBlades();
                if (blade.get(bit)) {
                    //transform(mask,lengths[bit]);
                    if (map.containsKey(bit)) {
                        SumOfBlades t = map.get(bit);
                        for (SignedBlade tb: t)
                            lengths[bit].add(new SignedBlade(bitCount, tb, tb.coefficient));
                    } else {
                        SignedBlade sb = new SignedBlade(bitCount);
                        sb.set(bit);
                        lengths[bit].add(sb);
                    }
                }
            }

            //permutate over lengths array
            Permutation permutation = new Permutation();
            SignedBlade p = new SignedBlade(bitCount);
            if (permutation.initialize(lengths,p, bitCount)) {
                p.coefficient *= blade.coefficient;
                result.add(p);
            }

            while (permutation.hasNextPermutation()) {
                SignedBlade p1 = new SignedBlade(bitCount);
                if (permutation.getNextPermutation(lengths,p1, bitCount)) {
                    p1.coefficient *= blade.coefficient;
                    result.add(p1);
                }
            }
        }

        return result;
    }

}
