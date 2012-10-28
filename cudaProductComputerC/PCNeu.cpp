// PCNeu.cpp : Definiert den Einstiegspunkt für die Konsolenanwendung.
//

#include "Definitions.h"
#include "SignedBlade.h"
#include "GAMethods.h"
#include <iostream>

#include "BladelistCreator.h"

#include "InnerProductComputing.h"
#include "OuterGeoProductComputing.h"
#include "Grouper.h"
#include "Outputter.h"

#include <boost/unordered_map.hpp>

#include <time.h>
#include <fstream>

#include "BitWriter.h"
#include "BitReader.h"


#define COMPUTE_INNER_PRODUCT
#define COMPUTE_OUTER_AND_GEO_PRODUCT

#define PRINT_TO_FILE

void printBladelist(Bladelist& list, void (*printer) (Blade&, std::ostream&)) {
	std::fstream out("D:\\blades.csv",std::fstream::out);
	int index = 0;
	for (Bladelist::iterator sblade1=list.begin(); sblade1 != list.end(); ++sblade1) {
		SumOfBlades&s = *sblade1;
		out << index << ": ";
		output(s,printer,out);
		out << std::endl;
		index++;
	}
	out.close();
}

#include "CalcThread.h"



// CUDA runtime

#include <assert.h>
#include <iostream>

#include "Definitions.h"


// declaration, forward
extern "C" void cudaCalculateProducts(
	int summandCountPMTransformedZI, int* positionsPMTransformedZI, 
	int* lengthsPMTransformedZI, float* coefficentsPMTransformedZI, int* bladesPMTransformedZI,
	int summandCountZITransformedPM, int* positionsZITransformedPM, 
	int* lengthsZITransformedPM, float* coefficentsZITransformedPM, int* bladesZITransformedPM
	);

int getSummandCountAssignPositionsAndLenghts(SumOfBlades* pmTransformedZI, int* positionsPMTransformedZI, int* lengthsPMTransformedZI) {
	int count = 0;
	int position = 0;
	for (int i=0;i<BLADECOUNT;i++) {
		positionsPMTransformedZI[i] = position;
		lengthsPMTransformedZI[i] = pmTransformedZI[i].size();
		count += lengthsPMTransformedZI[i];
		position += lengthsPMTransformedZI[i];
	}
	return count;
}

void assignBladesAndCoefficients(SumOfBlades* pmTransformedZI, float* coefficentsPMTransformedZI, int* bladesPMTransformedZI) {
	// retrieve informations about positions, lenghts and number of elements
	int index = 0;
	for (int i=0;i<BLADECOUNT;i++) 
		for (SumOfBlades::iterator sblade1 = pmTransformedZI[i].begin(); sblade1 != pmTransformedZI[i].end(); ++sblade1) {
			SignedBlade& b = *sblade1;
			bladesPMTransformedZI[index] = b.bits;
			coefficentsPMTransformedZI[index] = b.coefficient;
			index++;
		}
}

void cudaCalculateProducts_Host(SumOfBlades* pmTransformedZI, SumOfBlades* ziTransformedPM) {
	// == allocate memories ==
	//PMTransformedZI
	int positionsPMTransformedZI[BLADECOUNT];
	int lengthsPMTransformedZI[BLADECOUNT];
	int summandCountPMTransformedZI = getSummandCountAssignPositionsAndLenghts(pmTransformedZI, positionsPMTransformedZI, lengthsPMTransformedZI);
	float* coefficentsPMTransformedZI = new float[summandCountPMTransformedZI];
	int* bladesPMTransformedZI = new int[summandCountPMTransformedZI];
	assignBladesAndCoefficients(pmTransformedZI, coefficentsPMTransformedZI, bladesPMTransformedZI);

	//ZITransformedPM
	int positionsZITransformedPM[BLADECOUNT];
	int lengthsZITransformedPM[BLADECOUNT];
	int summandCountZITransformedPM = getSummandCountAssignPositionsAndLenghts(ziTransformedPM, positionsZITransformedPM, lengthsZITransformedPM);
	float* coefficentsZITransformedPM = new float[summandCountZITransformedPM];
	int* bladesZITransformedPM = new int[summandCountZITransformedPM];
	assignBladesAndCoefficients(ziTransformedPM, coefficentsZITransformedPM, bladesZITransformedPM);

	cudaCalculateProducts(
		summandCountPMTransformedZI, positionsPMTransformedZI, lengthsPMTransformedZI, coefficentsPMTransformedZI, bladesPMTransformedZI,
		summandCountZITransformedPM, positionsZITransformedPM, lengthsZITransformedPM, coefficentsZITransformedPM, bladesZITransformedPM
		);

	// == free memories ==
	delete[] coefficentsPMTransformedZI;
	coefficentsPMTransformedZI = 0;
	delete[] bladesPMTransformedZI;
	bladesPMTransformedZI = 0;
}

/**
	Creates the producttables of a geometric algebra using CUDA.
 **/
int main(int argc, char* argv[])
{
	time_t start;
	time(&start);

	// CPU: create bladeListZI
	Bladelist bladelistZI;
	initializeBladelist(bladelistZI);
	
	// CPU: transform bladeListZI into Sum-Of-PMBlades variable pmTransformedZI
	SumOfBlades pmTransformedZI[BLADECOUNT];
	for (int index=0;index<BLADECOUNT;index++) {
		SumOfBlades b;
		b.push_back(SignedBlade(Blade(index)));
		SumOfBlades& pm = pmTransformedZI[index];
		basetransformationZeroInfToPlusMinus(b, pm);
		group(pm);
	}
	
	// CPU: create transformations from PM to ZI 
	SumOfBlades ziTransformedPM[BLADECOUNT];
	for (int index=0;index<BLADECOUNT;index++) {
		SumOfBlades b;
		b.push_back(SignedBlade(Blade(index)));
		SumOfBlades& zi = ziTransformedPM[index];
		basetransformationPlusMinusToZeroInf(b,zi);
		group(zi);
	}

	// TODO CUDA: calculate all products in PM, transform it into ZI, store it on host-memory
	cudaCalculateProducts_Host(pmTransformedZI, ziTransformedPM);
	

	time_t ende;
	time(&ende);
	std::cout << "Ready in " << difftime(ende, start) << " seconds" << std::endl;
	
	return 0;
}


/*
//int maxNumber = 0; //TODO
	//std::cout << "MaxNumber = " << maxNumber <<std::endl;
// komprimieren
	int number = 2;
    int bitCount2 = 1;
    while (number < maxNumber+1) {
        bitCount2++;
        number *= 2;
    }
	*/