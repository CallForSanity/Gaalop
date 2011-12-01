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

void InnerProductCalculator::calculate(const Blade& blade1,const  Blade& blade2, unordered_map<int,int>& baseSquares, SumOfBlades& result) {
        this->baseSquares = baseSquares;

        const intVector& base1 = blade1.baseVectors;
        const intVector& base2 = blade2.baseVectors;

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
    void InnerProductCalculator::calculateProductBlades(const intVector& base1, const intVector& base2, Blade& resultF) {


        if (base1.size() > base2.size()) {
            // Bl*ak, k<l

            intVector::const_iterator it = base2.begin();
            Blade result;
            calculateProductBlades(base1, *it, result);
            resultF = result;
            ++it;
            if (it == base2.end()) return;
            do {

            	intVector& baseVectors = result.baseVectors;
                Blade result2;
                calculateProductBlades(baseVectors, *it, result2);
                result2.setPrefactor(result2.getPrefactor()*result.getPrefactor());
                resultF = result2;
                result = result2;
                ++it;
            } while (it != base2.end());

        } else {
            // ak*bl, k<l
        	intVector::const_reverse_iterator it = base1.rbegin();
            Blade result;
            calculateProductBlades(*it, base2, result);

            ++it;
            resultF = result;
            if (it == base1.rend()) return;
            do {
            	intVector& baseVectors = result.baseVectors;
                Blade result2;
                calculateProductBlades(*it, baseVectors, result2);
                result2.setPrefactor(result2.getPrefactor()*result.getPrefactor());
                resultF = result2;
                result = result2;
                ++it;
            } while (it != base1.rend());
        }

    }



    /**
     * Calculates a product of a base element and a base
     * @param base1 The base element
     * @param base2 The base
     * @return The inner product as Expression
     */
    void InnerProductCalculator::calculateProductBlades(const int base1, const intVector& base2, Blade& result) {
        int index = VectorMethods::getIndexOfString(base1, base2);
        if (index > -1) {
            intVector& arr = result.baseVectors;
            arr.clear();
            int j=0;
            for (intVector::const_iterator i=base2.begin(); i != base2.end(); ++i) {
                if (j != index)
                    arr.push_back(*i);
                j++;
            }

            float prefactor = 1;
            if ((index%2) == 1)
                prefactor *= -1;

            if (baseSquares[base1] == -1)
                prefactor *= -1;

            result.setPrefactor(prefactor);
        } else {
        	result.baseVectors.clear();
        	result.setPrefactor(0);
        }
    }

    /**
     * Calculates a product of a base and a base element
     * @param base1 The base
     * @param base2 The base element
     * @return The inner product as Expression
     */
    void InnerProductCalculator::calculateProductBlades(const intVector& base1, const int base2, Blade& result) {
        int index = VectorMethods::getIndexOfString(base2, base1);
        if (index > -1) {
        	intVector& arr = result.baseVectors;
        	arr.clear();
            int j=0;
            for (intVector::const_iterator i=base1.begin(); i != base1.end(); ++i) {
                if (j != index)
                    arr.push_back(*i);
                j++;
            }

            float prefactor = 1;
            if (((base1.size()-index-1) %2) == 1)
                prefactor *= -1;

            if (baseSquares[base2] == -1)
                prefactor *= -1;

            result.setPrefactor(prefactor);
        } else {
        	result.baseVectors.clear();
            result.setPrefactor(0);
        }
    }

    /**
     * Calculates a product two base elements
     * @param base1 The base element
     * @param base2 The base element
     * @return The inner product as Expression
     */
    void InnerProductCalculator::calculateProductBlades(const int base1, const int base2, Blade& result) {
    	result.baseVectors.clear();
        if (base1 == base2 && base1 != 0) {
			result.setPrefactor(baseSquares[base1]);
			return;
        }
		result.setPrefactor(0);
    }
