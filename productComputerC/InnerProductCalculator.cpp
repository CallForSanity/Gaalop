/*
 * InnerProductCalculator.cpp
 *
 *  Created on: 22.11.2011
 *      Author: christian
 */

#include "InnerProductCalculator.h"

InnerProductCalculator::InnerProductCalculator() {
}

InnerProductCalculator::~InnerProductCalculator() {
}

void InnerProductCalculator::calculate(Blade& blade1, Blade& blade2, unordered_map<int,int>& baseSquares, SumOfBlades& result) {
        this->baseSquares = baseSquares;

        intList base1 = blade1.getBaseVectors();
        intList base2 = blade2.getBaseVectors();

        if (base1.size() == 0 || base2.size() == 0)
        	return;

        if (base1.size() == 1) {
            if (base2.size() == 1) {
            	Blade resultB;
                calculateProductBlades(base1.front(), base2.front(), resultB);
                resultB.setPrefactor(resultB.getPrefactor()*blade1.getPrefactor()*blade2.getPrefactor());
                result.addBlade(resultB);
                return;
            }
            else {
                Blade resultB;
                calculateProductBlades(base1.front(), base2, resultB);
                resultB.setPrefactor(resultB.getPrefactor()*blade1.getPrefactor()*blade2.getPrefactor());
                result.addBlade(resultB);
                return;
				}
        } else {
            if (base2.size() == 1) {
                Blade resultB;
                calculateProductBlades(base1, base2.front(),resultB);
                resultB.setPrefactor(resultB.getPrefactor()*blade1.getPrefactor()*blade2.getPrefactor());
                result.addBlade(resultB);
                return;
				}
            else {
                Blade resultB;
                calculateProductBlades(base1, base2,resultB);
                resultB.setPrefactor(resultB.getPrefactor()*blade1.getPrefactor()*blade2.getPrefactor());
                result.addBlade(resultB);
                return;
				}
        }
    }

    /**
     * Calculates a product of two bases, which have both more than one base element
     * @param base1 The first base
     * @param base2 The second base
     */
    void InnerProductCalculator::calculateProductBlades(intList& base1, intList& base2, Blade& result) {


        if (base1.size() > base2.size()) {
            // Bl*ak, k<l

            intList::iterator it = base2.begin();
            Blade result;
            calculateProductBlades(base1, *it, result);
            while (it != base2.end()) {
            	++it;
            	intList baseVectors = result.getBaseVectors();
                Blade result2;
                calculateProductBlades(baseVectors, *it, result2);
                result2.setPrefactor(result2.getPrefactor()*result.getPrefactor());
                result = result2;
            }

        } else {
            // ak*bl, k<l
        	intList::reverse_iterator it = base1.rbegin();
            Blade result;
            calculateProductBlades(*it, base2, result);

            while (it != base1.rend()) {
            	++it;
            	intList baseVectors = result.getBaseVectors();
                Blade result2;
                calculateProductBlades(*it, baseVectors, result2);
                result2.setPrefactor(result2.getPrefactor()*result.getPrefactor());
                result = result2;
            }
        }

    }



    /**
     * Calculates a product of a base element and a base
     * @param base1 The base element
     * @param base2 The base
     * @return The inner product as Expression
     */
    void InnerProductCalculator::calculateProductBlades(int base1, intList& base2, Blade& result) {
        int index = VectorMethods::getIndexOfString(base1, base2);
        if (index > -1) {
            intList arr;
            int j=0;
            for (intList::const_iterator i=base2.begin(); i != base2.end(); ++i) {
                if (j != index)
                    arr.push_back(*i);
                j++;
            }

            float prefactor = 1;
            if ((index%2) == 1)
                prefactor *= -1;

            if (baseSquares[base1] == -1)
                prefactor *= -1;

            result.setBaseVectors(arr);
            result.setPrefactor(prefactor);
        } else {
        	intList arr;
        	result.setPrefactor(0);
        	result.setBaseVectors(arr);
        }
    }

    /**
     * Calculates a product of a base and a base element
     * @param base1 The base
     * @param base2 The base element
     * @return The inner product as Expression
     */
    void InnerProductCalculator::calculateProductBlades(intList& base1, int base2, Blade& result) {
        int index = VectorMethods::getIndexOfString(base2, base1);
        if (index > -1) {
        	intList arr;
            int j=0;
            for (intList::const_iterator i=base1.begin(); i != base1.end(); ++i) {
                if (j != index)
                    arr.push_back(*i);
                j++;
            }

            float prefactor = 1;
            if (((base1.size()-index-1) %2) == 1)
                prefactor *= -1;

            if (baseSquares[base2] == -1)
                prefactor *= -1;

            result.setBaseVectors(arr);
            result.setPrefactor(prefactor);
        } else {
        	intList arr;
            result.setBaseVectors(arr);
            result.setPrefactor(0);
        }
    }

    /**
     * Calculates a product two base elements
     * @param base1 The base element
     * @param base2 The base element
     * @return The inner product as Expression
     */
    void InnerProductCalculator::calculateProductBlades(int base1, int base2, Blade& result) {
    	intList arr;
        if (base1 == base2 && base1 != 0) {
			result.setPrefactor(baseSquares[base1]);
			result.setBaseVectors(arr);
			return;
        }
		result.setPrefactor(0);
		result.setBaseVectors(arr);
    }
