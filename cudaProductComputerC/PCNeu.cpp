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
extern "C" void cudaCalculateProducts(int summandCountPMTransformedZI, int* positionsPMTransformedZI, int* lengthsPMTransformedZI, float* coefficentsPMTransformedZI);

void cudaCalculateProducts1(SumOfBlades* pmTransformedZI) {
	// == allocate memories ==
	
	// retrieve informations about positions, lenghts and number of elements
	int positionsPMTransformedZI[BLADECOUNT];
	int lengthsPMTransformedZI[BLADECOUNT];
	int summandCountPMTransformedZI = 0;
	int position = 0;
	for (int i=0;i<BLADECOUNT;i++) {
		positionsPMTransformedZI[i] = position;
		//std::cout << position << std::endl;
		lengthsPMTransformedZI[i] = pmTransformedZI[i].size();
		summandCountPMTransformedZI += lengthsPMTransformedZI[i];
		position += lengthsPMTransformedZI[i];
	}

	float* coefficentsPMTransformedZI = new float[summandCountPMTransformedZI];
	int index = 0;
	for (int i=0;i<BLADECOUNT;i++) 
		for (SumOfBlades::iterator sblade1 = pmTransformedZI[i].begin(); sblade1 != pmTransformedZI[i].end(); ++sblade1) {
			coefficentsPMTransformedZI[index] = (*sblade1).coefficient;
			index++;
		}

	cudaCalculateProducts(summandCountPMTransformedZI, positionsPMTransformedZI, lengthsPMTransformedZI, coefficentsPMTransformedZI);
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
	cudaCalculateProducts1(pmTransformedZI);
	

	time_t ende;
	time(&ende);
	std::cout << "Ready in " << difftime(ende, start) << " seconds" << std::endl;
	getchar();
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