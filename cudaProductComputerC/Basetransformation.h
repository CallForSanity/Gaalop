#include "Definitions.h"
#include "AlgebraDefinition.h"
#include "Permutation.h"
#include "SignedBlade.h"
#include "Outputter.h"

void basetransformation(SumOfBlades& from, SumOfBlades& to, void (*transform) (SignedBlade&,SumOfBlades&)) {
	for (SumOfBlades::iterator itZI=from.begin();itZI!=from.end();++itZI) {
		SignedBlade blade = *itZI;
		//create lengths array for permutation
		SumOfBlades lengths[MAXBITCOUNT];
		SignedBlade mask(1, 1);
		for (int bit=0;bit<MAXBITCOUNT;++bit) {
			if (blade.isBit(bit))
				transform(mask,lengths[bit]);

			mask.bits <<= 1;
		}

		//permutate over lengths array
		Permutation permutation;
		SignedBlade p;
		if (permutation.initialize(lengths,p)) {
			p.coefficient *= blade.coefficient;
			to.push_back(p);
		}
		
		while (permutation.hasNextPermutation()) {
			SignedBlade p1;
			if (permutation.getNextPermutation(lengths,p1)) {
				p1.coefficient *= blade.coefficient;
				to.push_back(p1);
			}
		}
	}
}

inline void basetransformationZeroInfToPlusMinus(SumOfBlades& zeroinf, SumOfBlades& plusminus) {
	basetransformation(zeroinf,plusminus,transformZI_PM);
}

inline void basetransformationPlusMinusToZeroInf(SumOfBlades& plusminus, SumOfBlades& zeroinf) {
	basetransformation(plusminus,zeroinf,transformPM_ZI);
}