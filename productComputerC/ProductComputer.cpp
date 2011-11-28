/*
 * ProductComputer.cpp
 *
 *  Created on: 22.11.2011
 *      Author: christian
 */

#include "ProductComputer.h"

ProductComputer::ProductComputer() {
}

ProductComputer::~ProductComputer() {
}

#include "BladeArrayRoutines.h"

#include <iostream>

    /**
     * Do initializations
     * @param base The ZeroInf Base to use
     * @param base2 The PlusMinus Base to use (here are the products easily defined)
     * @param mapToPlusMinus The map from ZeroInf base to PlusMinus base
     * @param mapToZeroInf The map from PlusMinus base to ZeroInf base
     */
	void ProductComputer::initialize(Algebra& algebra) {
		//create blades in "blades"
		intVector intBase;
		intBase.reserve(algebra.getBase().size());
		BaseVectors baseVectors;
		string one = "1";
		baseVectors.addBase(one);
		for (unsigned int i=0;i<algebra.getBase().size();i++)
			intBase.push_back(baseVectors.addBase(algebra.getBase()[i]));

		BladeArrayRoutines bladeArrayRoutines;

		vector<Blade> bladesZI;
		bladeArrayRoutines.createBlades(intBase,bladesZI);

		listOfBlades.setBlades(bladesZI);
		//add base2
		for (unsigned int i=0;i<algebra.getBase2().size();i++)
			if (!baseVectors.containsBase(algebra.getBase2()[i]))
				baseVectors.addBase(algebra.getBase2()[i]);

		//baseSquares to integer system

		BaseSquareMap map = algebra.getBaseSquaresStr();
		for (BaseSquareMap::iterator ci = map.begin(); ci != map.end(); ++ci) {
			std::pair<string,int> pair = *ci;
			baseSquares[baseVectors.getIndex(pair.first)] = pair.second;
		}

		//base change of blades to plus minus
		mapStringBladestrlist mapPlusminusStr = algebra.getMapToPlusMinus();
		mapIntBladearray mapBaseChangeToPlusMinus;
		convertMap(mapPlusminusStr, baseVectors, mapBaseChangeToPlusMinus);

		mapStringBladestrlist mapZeroInfStr = algebra.getMapToZeroInf();
		convertMap(mapZeroInfStr, baseVectors, mapBaseChangeToZeroInf);

		for (unsigned int i=0;i<bladesZI.size();i++) {
			SumOfBlades s;
			changeBaseOfBlade(bladesZI[i], mapBaseChangeToPlusMinus, s);
			bladesPM.push_back(s);
		}

		//checking if some blades are zero
		for (unsigned int i=0;i<bladesZI.size();i++) {
			bladesPM[i].checkIfSomeBladesAreZero();
			bladesPM[i].normalize();
			bladesPM[i].group();
		}

	}

	typedef list<Blade> listBlade;

#include "Timing.h"
Timing Timing::timing;


	void ProductComputer::calcProduct(int i, int j, ProductCalculator& calculator, Multivector& result) {
        //use bladesPM and mapBaseChangeToZeroInf

        //calculate real product
        list<Blade> blades1 = bladesPM[i].getBlades();
        list<Blade> blades2 = bladesPM[j].getBlades();
        SumOfBlades product;


        for (listBlade::iterator ci1 = blades1.begin(); ci1 != blades1.end();++ci1)
            for (listBlade::iterator ci2 = blades2.begin(); ci2 != blades2.end();++ci2) {
            	Blade blade1 = *ci1;
            	Blade blade2 = *ci2;
            	SumOfBlades s;
            	calculator.calculate(blade1, blade2, baseSquares, s);
            	listBlade lis = s.getBlades();
            	for (listBlade::iterator ci = lis.begin(); ci != lis.end(); ++ci)
					product.addBlade(*ci);
            }


        //base change
        SumOfBlades blade;
        changeBaseOfBlade(product, mapBaseChangeToZeroInf, blade);
        //12s

        //checking if some blades are zero
        blade.checkIfSomeBladesAreZero();
        //normalise blades
        blade.normalize();
         //group blades
        blade.group();
        //merge to multivector
        blade.toMultivector(listOfBlades, result);

    }

    /**
     * Converts a map from a user-friendly format in a efficient format for accesses
     * @param mapBaseChangeStr The map to convert
     * @param baseVectors The initialized BaseVectors object for finding indices
     * @return The new map
     */
    void ProductComputer::convertMap(mapStringBladestrlist& mapBaseChangeStr, BaseVectors& baseVectors, mapIntBladearray& result) {
		for (mapStringBladestrlist::iterator ci = mapBaseChangeStr.begin(); ci != mapBaseChangeStr.end(); ++ci) {
			std::pair<string, bladeStrList> pair = *ci;

            vector<Blade> blades;
            for (bladeStrList::iterator ci1 = pair.second.begin(); ci1 != pair.second.end(); ++ci1) {
            	BladeStr bladeStr = *ci1;
            	Blade b;
            	convertBladeStrToBlade(bladeStr, baseVectors, b);
            	blades.push_back(b);
            }

            BladeArray bladeArray;
            bladeArray.setBlades(blades);
            result[baseVectors.getIndex(pair.first)] = bladeArray;
        }
    }

    /**
     * Converts a BladeStr object to a Blade object
     * @param bladeStr The BladeStr object
     * @param baseVectors The baseVectors for finding indices
     * @return The new Blade object
     */
    void ProductComputer::convertBladeStrToBlade(BladeStr& bladeStr, BaseVectors& baseVectors, Blade& result) {
        vector<string> baseVectorsStr = bladeStr.getBaseVectors();

        intList baseV;
        for (unsigned int i=0;i<baseVectorsStr.size();i++)
            baseV.push_back(baseVectors.getIndex(baseVectorsStr[i]));

        result.setPrefactor(bladeStr.getPrefactor());
        result.setBaseVectors(baseV);
    }

    /**
     * Changes the base for a sum of blades and saves the result in a SumOfBlades object
     * e1^einf^e3 -> e1^em^e3 + e1^ep^e3
     * @param sumOfBlades The sum of blades
     * @param map The map to use for the basechange
     * @return The new SumOfBlades object
     */
    void ProductComputer::changeBaseOfBlade(SumOfBlades& sumOfBlades, unordered_map<int, BladeArray>& map, SumOfBlades& resultB) {
        listBlade blades = sumOfBlades.getBlades();
        for (listBlade::iterator ci=blades.begin(); ci != blades.end(); ++ci) {
        	Blade blade = *ci;
        	changeBaseOfBlade(blade, map, resultB);
        }

    }

#include "PermutableIterator.h"

    /**
     * Changes the base for a blade and saves the result in a SumOfBlades object
     * e1^einf^e3 -> e1^em^e3 + e1^ep^e3
     * @param blade The blade
     * @param map The map to use for the basechange
     * @return The new SumOfBlades object
     */
    void ProductComputer::changeBaseOfBlade(Blade& blade, unordered_map<int, BladeArray>& map, SumOfBlades& resultB) {
    	intList& baseVectors = blade.baseVectors;
        if (baseVectors.size() == 0) {
        	resultB.addBlade(blade);
        	return;
        }

        //find the lengths for creating a permutation
        intVector lengths;
        lengths.reserve(baseVectors.size());
        int j = 0;
        for (intList::const_iterator ci=baseVectors.begin(); ci != baseVectors.end(); ++ci) {
        	int i = *ci;
            lengths.push_back((map.count(i) == 1)
                    ? map[i].getBlades().size()
                    : 1);
            j++;
        }


        //create the permutations
        PermutableIterator iterator;
        intVector permutation;
        iterator.initialize(lengths, permutation);


        do {

            //build current permutation in baseList
            intList baseList;

            float prefactor = 1;
            j=0;

            for (intList::iterator ci=baseVectors.begin(); ci != baseVectors.end(); ++ci) {
            	int i = *ci;
                if (map.count(i) == 1) {
                    Blade b;
                    map[i].getBlade(permutation[j],b);
                    prefactor *= b.getPrefactor();
                    VectorMethods::mergeLists(baseList, b.baseVectors);
                } else {
                    baseList.push_back(i);
                }
                j++;
            }

            Blade c(prefactor*blade.getPrefactor(), baseList);
            resultB.addBlade(c);
            iterator.getNextPermutation(permutation);

        } while (iterator.hasNextPermutation());

    }
