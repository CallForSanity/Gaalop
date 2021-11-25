package de.gaalop.productComputer;

/**
 * Provides methods for iterating through a permutation
 * @author Christian Steinmetz
 */
public class Permutation {

    private int curNo;
    private int count;
    private int[] permutation;

    /**
     * Converts a permutation to a blade by using an lengths array
     * @param lengths The lengths array
     * @param result The result blade
     * @param i The permutation array index
     * @param bitCount The maximum number of bits
     * @return true, if the result is not empty; false otherwise
     */
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

    /**
     * Initializes the permutation generator with a lengths array and produce the first permutation
     * @param lengths The lengths array
     * @param result The result blade
     * @param bitCount The maximum number of bits
     * @return true, if the result is not empty; false otherwise
     */
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

    /**
     * Returns, if this iterator has a next permutation
     * @return true, if this iterator has a next permutation; false otherwise
     */
    public boolean hasNextPermutation() {
        return curNo+1<count;
    }

    /**
     * Produce the next permutation
     * @param lengths The lengths array
     * @param result The result blade
     * @param bitCount The maximum number of bits
     * @return true, if the result is not empty; false otherwise
     */
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
