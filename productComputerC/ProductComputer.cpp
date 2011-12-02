/*
 * ProductComputer.cpp
 *
 *  Created on: 22.11.2011
 *      Author: christian
 */

//#define TIMING

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
	void ProductComputer::initialize(const Algebra& algebra) {
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
		for (BaseSquareMap::const_iterator ci = map.begin(); ci != map.end(); ++ci) {
			const std::pair<string,int> pair = *ci;
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

#ifdef TIMING
#include "Timing.h"
#endif


	void ProductComputer::calcProduct(const int i, const int j, ProductCalculator& calculator, Multivector& result) {
        //use bladesPM and mapBaseChangeToZeroInf
#ifdef TIMING
		Timing::timing.startTime("1");
#endif

        //calculate real product
        list<Blade>& blades1 = bladesPM[i].blades;
        list<Blade>& blades2 = bladesPM[j].blades;
        SumOfBlades product;


        const listBlade::const_iterator& endItBlades1 = blades1.end();
        const listBlade::const_iterator& endItBlades2 = blades2.end();
        for (listBlade::const_iterator ci1 = blades1.begin(); ci1 != endItBlades1;++ci1)
            for (listBlade::const_iterator ci2 = blades2.begin(); ci2 != endItBlades2;++ci2) {
            	calculator.calculate(*ci1, *ci2, baseSquares, product);
            }
        product.checkIfSomeBladesAreZero();
#ifdef TIMING
        Timing::timing.stopTime("1");
        Timing::timing.startTime("2");
#endif

        //base change
        SumOfBlades blade;
        changeBaseOfBlade(product, mapBaseChangeToZeroInf, blade);
        //12s
#ifdef TIMING
        Timing::timing.stopTime("2");
        Timing::timing.startTime("3");
#endif
        //checking if some blades are zero
        blade.checkIfSomeBladesAreZero();
#ifdef TIMING
        Timing::timing.stopTime("3");
        Timing::timing.startTime("4");
#endif

        //normalise blades
        blade.normalize();
#ifdef TIMING
        Timing::timing.stopTime("4");
        Timing::timing.startTime("5");
#endif
         //group blades
        blade.group();
#ifdef TIMING
        Timing::timing.stopTime("5");
        Timing::timing.startTime("6");
#endif
        //merge to multivector
        blade.toMultivector(listOfBlades, result);
#ifdef TIMING
        Timing::timing.stopTime("6");
#endif

    }

    /**
     * Converts a map from a user-friendly format in a efficient format for accesses
     * @param mapBaseChangeStr The map to convert
     * @param baseVectors The initialized BaseVectors object for finding indices
     * @return The new map
     */
    void ProductComputer::convertMap(mapStringBladestrlist& mapBaseChangeStr,  BaseVectors& baseVectors, mapIntBladearray& result) {
		for (mapStringBladestrlist::const_iterator ci = mapBaseChangeStr.begin(); ci != mapBaseChangeStr.end(); ++ci) {
			const std::pair<string, bladeStrList> pair = *ci;

			BladeArray bladeArray;
            vector<Blade>& blades = bladeArray.blades;
            for (bladeStrList::const_iterator ci1 = pair.second.begin(); ci1 != pair.second.end(); ++ci1) {
            	BladeStr bladeStr = *ci1;
            	Blade b;
            	convertBladeStrToBlade(bladeStr, baseVectors, b);
            	blades.push_back(b);
            }

            result[baseVectors.getIndex(pair.first)] = bladeArray;
        }
    }

    /**
     * Converts a BladeStr object to a Blade object
     * @param bladeStr The BladeStr object
     * @param baseVectors The baseVectors for finding indices
     * @return The new Blade object
     */
    void ProductComputer::convertBladeStrToBlade(const BladeStr& bladeStr,  BaseVectors& baseVectors, Blade& result) {
        const vector<string>& baseVectorsStr = bladeStr.baseVectors;

        intVector& baseV = result.baseVectors;
        baseV.clear();
        baseV.reserve(baseVectorsStr.size());
        for (unsigned int i=0;i<baseVectorsStr.size();i++)
            baseV.push_back(baseVectors.getIndex(baseVectorsStr[i]));

        result.setPrefactor(bladeStr.getPrefactor());
    }

    /**
     * Changes the base for a sum of blades and saves the result in a SumOfBlades object
     * e1^einf^e3 -> e1^em^e3 + e1^ep^e3
     * @param sumOfBlades The sum of blades
     * @param map The map to use for the basechange
     * @return The new SumOfBlades object
     */
    void ProductComputer::changeBaseOfBlade(const SumOfBlades& sumOfBlades, unordered_map<int, BladeArray>& map, SumOfBlades& resultB) {
    	const listBlade& blades = sumOfBlades.blades;
    	const listBlade::const_iterator& endIt = blades.end();
        for (listBlade::const_iterator ci=blades.begin(); ci != endIt; ++ci)
        	changeBaseOfBlade(*ci, map, resultB);
    }

#include "PermutableIterator.h"

    /**
     * Changes the base for a blade and saves the result in a SumOfBlades object
     * e1^einf^e3 -> e1^em^e3 + e1^ep^e3
     * @param blade The blade
     * @param map The map to use for the basechange
     * @return The new SumOfBlades object
     */
    void ProductComputer::changeBaseOfBlade(const Blade& blade, unordered_map<int, BladeArray>& map, SumOfBlades& resultB) {
    	const intVector& baseVectors = blade.baseVectors;
        if (baseVectors.size() == 0) {
        	resultB.addBlade(blade);
        	return;
        }

        //find the lengths for creating a permutation
        intVector lengths;

        lengths.reserve(baseVectors.size());
        int j = 0;
        const intVector::const_iterator& endItBaseVectors0 = baseVectors.end();
        for (intVector::const_iterator ci=baseVectors.begin(); ci != endItBaseVectors0; ++ci) {
        	const int i = *ci;
            lengths.push_back((map.count(i) == 1)
                    ? map[i].blades.size()
                    : 1);
            j++;
        }


        //create the permutations
        PermutableIterator iterator;
        intVector permutation;
        iterator.initialize(lengths, permutation);

        do {

            //build current permutation in baseList
        	Blade c;
            intVector& baseList = c.baseVectors;
            baseList.reserve(lengths.size());

            float prefactor = 1;
            j=0;



            const intVector::const_iterator& endItBaseVectors = baseVectors.end();
            for (intVector::const_iterator ci=baseVectors.begin(); ci != endItBaseVectors; ++ci) {
            	const int i = *ci;

            	unordered_map<int, BladeArray>::iterator it = map.find(i);
                if (it != map.end()) {
                    Blade b;
                    (*it).second.getBlade(permutation[j],b);
                    prefactor *= b.getPrefactor();
                    const intVector::const_iterator& endItBBaseVectors = b.baseVectors.end();
                    for (intVector::const_iterator ci2 = b.baseVectors.begin(); ci2 != endItBBaseVectors; ++ci2)
                    	baseList.push_back(*ci2);
                } else {
                    baseList.push_back(i);
                }
                j++;
            }


            c.setPrefactor(prefactor*blade.getPrefactor());
            resultB.addBlade(c);
            iterator.getNextPermutation(permutation);

        } while (iterator.curNo<iterator.count);
    }
