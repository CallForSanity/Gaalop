/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.productComputer2;

/**
 *
 * @author christian
 */
public class Permutation {

    private int curNo;
    private int count;
    private int[] permutation;

    private boolean permutationToBlade(SumOfBlades[] lengths, SignedBlade result, int i, int bitCount) {
	SumOfBlades s = lengths[i];
	SignedBlade b = s.get(permutation[i]);

	result.coefficient *= b.coefficient;
	for (int j=0;j<bitCount;++j) {
            boolean v = b.get(j);
            if (v && result.get(j)) return false; // = 0 , because of double indices in a wedge operation
            if (v) {
                result.set(j);
                for (int k=j+1;k<bitCount;++k)
                    if (result.get(k))
                        result.coefficient *= -1;
            }
	}
	return true;
    }

    public boolean initialize(SumOfBlades[] lengths, SignedBlade result, int bitCount) {
        permutation = new int[bitCount];
        result.clear();
        curNo = 0;
        count = 1;
        for (int i=0;i<bitCount;++i) {
            if (lengths[i].size() > 0) {
                    count *= lengths[i].size();
                    permutation[i] = 0;
            } else
                    permutation[i] = -1;
	}

	for (int i=0;i<bitCount;++i) {
            if (lengths[i].size() > 0)
                    if (!permutationToBlade(lengths, result,i, bitCount)) return false;
	}


	return true;
    }

    public boolean hasNextPermutation() {
        return curNo+1<count;
    }

    public boolean getNextPermutation(SumOfBlades[] lengths, SignedBlade result, int bitCount) {
	curNo++;
	if (!(curNo<count)) return false;

	int pos = 0;
	boolean ueberlauf = true;
	while (ueberlauf) {
            if (permutation[pos] != -1) {
                ueberlauf = (permutation[pos]+1 == lengths[pos].size());
                if (ueberlauf)
                    permutation[pos] = 0;
                else
                    permutation[pos]++;
            }
            pos++;
	}

	result.clear();
	for (int i=0;i<bitCount;++i)
            if (lengths[i].size() > 0)
                if (!permutationToBlade(lengths,result,i, bitCount)) return false;

	return true;
    }



}
