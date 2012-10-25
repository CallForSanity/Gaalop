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

#define BLADECOUNT 32

// CUDA runtime
#include <cuda_runtime.h>
#include <assert.h>
#include <iostream>

#include "Definitions.h"

void cudaCalculateProducts(SumOfBlades* pmTransformedZI);

__global__ void simpleCopyKernel(int* in_pos, int* out_pos) {
	int tx = threadIdx.x;
	out_pos[tx] = in_pos[tx]*2;
}

void cudaCalculateProducts(SumOfBlades* pmTransformedZI) {
	// == allocate memories ==
	
	// retrieve informations about positions, lenghts and number of elements
	int positionsPMTransformedZI[BLADECOUNT];
	int lengthsPMTransformedZI[BLADECOUNT];
	int summandCountPMTransformedZI = 0;
	int position = 0;
	for (int i=0;i<BLADECOUNT;i++) {
		positionsPMTransformedZI[i] = position;
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

	// allocate memory pmTransformedZI
	float* cin_coefficentsPMTransformedZI;
	int size = summandCountPMTransformedZI * sizeof(float);
	assert(cudaMalloc((void**) &cin_coefficentsPMTransformedZI, size) == cudaSuccess);
	assert(cudaMemcpy(cin_coefficentsPMTransformedZI, coefficentsPMTransformedZI, size, cudaMemcpyHostToDevice) == cudaSuccess);

	int* cin_positionsPMTransformedZI;
	size = BLADECOUNT*sizeof(int);
	assert(cudaMalloc((void**) &cin_positionsPMTransformedZI, size) == cudaSuccess);
	assert(cudaMemcpy(cin_positionsPMTransformedZI, positionsPMTransformedZI, size, cudaMemcpyHostToDevice) == cudaSuccess);

	int* cin_lengthsPMTransformedZI;
	assert(cudaMalloc((void**) &cin_lengthsPMTransformedZI, size) == cudaSuccess);
	assert(cudaMemcpy(cin_lengthsPMTransformedZI, lengthsPMTransformedZI, size, cudaMemcpyHostToDevice) == cudaSuccess);

	// allocate memory for output
	//TODO first some testing
	int* cout_out;
	assert(cudaMalloc((void**) &cout_out, BLADECOUNT*sizeof(int)) == cudaSuccess);
	assert(cudaMemset(cout_out,0,BLADECOUNT*sizeof(int)) == cudaSuccess);

	// TODO CPU: collect results on host-memory, print it, or store it into a binary file for loading in Gaalop
	dim3 dimBlock(1,1,1);
	dim3 dimGrid(BLADECOUNT,1,1);
	simpleCopyKernel<<<dimGrid,dimBlock>>>(cin_positionsPMTransformedZI, cout_out);


	// retrieve data from gpu global memory
	int out[BLADECOUNT];

	assert(cudaMemcpy(out, cout_out, BLADECOUNT*sizeof(int), cudaMemcpyDeviceToHost) == cudaSuccess);

	for (int i = 0;i<BLADECOUNT;i++) 
		std::cout << out[i] << std::endl;

	// Free memory
	assert(cudaFree(cout_out) == cudaSuccess);
	assert(cudaFree(cin_coefficentsPMTransformedZI) == cudaSuccess);
	assert(cudaFree(cin_positionsPMTransformedZI) == cudaSuccess);
	assert(cudaFree(cin_lengthsPMTransformedZI) == cudaSuccess);
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
	cudaCalculateProducts(pmTransformedZI);
	

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