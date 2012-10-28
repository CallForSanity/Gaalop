#pragma once

#include "AlgebraSetting.h"
#include <assert.h>

#include <iostream>

// CUDA runtime
#include <cuda_runtime.h>

// helper functions and utilities to work with CUDA
#include <helper_cuda.h>
#include <helper_functions.h>

#define THREAD_DIM_X 16
#define THREAD_DIM_Y 16

/**
	Returns the number of setted bits in a number
	@returns The number of setted bits
**/
__device__
int count(int bits) {
	if (bits == 0) return 0;
	int count = 0;
	int mask = 1;
	for (int bit = 0; bit < MAXBITCOUNT; ++bit) {
		if ((bits & mask) > 0) count++;
		mask <<= 1;
	}
	return count;
}

// Computes ‘reordering sign’ to get into canonical order.
// Arguments 'a' and 'b' are both bitmaps representing basis blades.
// Copy a!
// This method is taken from the dissertation of Daniel Fontijne - Efficient Implementation of Gemoetric Algebra
__device__
	int canonicalReorderingSign(int blade1, int blade2) {
	// Count the number of basis vector swaps required to
	// get 'a' and 'b' into canonical order.
	blade1 >>= 1;
	int sum = 0;
	while (blade1 != 0)
	{
		// the function bitCount() counts the number of
		// 1-bits in the argument
		int aAndB = blade1 & blade2;
		sum += count(aAndB);
		blade1 >>= 1;
	}
	// even number of swaps -> return 1
	// odd number of swaps -> return -1
	return ((sum & 1) == 0) ? 1 : -1;
}

__device__ void outerKernelCalc(
	int* c_positionsPMTransformedZI, int* c_lengthsPMTransformedZI, float* c_coefficentsPMTransformedZI, int* c_bladesPMTransformedZI,
	int* c_positionsZITransformedPM, int* c_lengthsZITransformedPM, float* c_coefficentsZITransformedPM, int* c_bladesZITransformedPM,
	float* accumulatorCoefficients, int x, int y, int idInBlock
	) {
	
	
	int posX = c_positionsPMTransformedZI[x];
	int posY = c_positionsPMTransformedZI[y];
	int lenX = c_lengthsPMTransformedZI[x];
	int lenY = c_lengthsPMTransformedZI[y];

	for (int blade1Id = posX;blade1Id < posX+lenX; blade1Id++) 
		for (int blade2Id = posY;blade2Id < posY+lenY; blade2Id++) {
			float coeff1 = c_coefficentsPMTransformedZI[blade1Id];
			int blade1 = c_bladesPMTransformedZI[blade1Id];
			float coeff2 = c_coefficentsPMTransformedZI[blade2Id];
			int blade2 = c_bladesPMTransformedZI[blade2Id];

			float coeff;
			int result;

			//outer product
			if ((blade1 & blade2) == 0) {
				//outer product is not null
				result = blade1 ^ blade2; // also "or"-operation is possible
				coeff = coeff1*coeff2*canonicalReorderingSign(blade1, blade2);

				int pos = c_positionsZITransformedPM[result];
				int len = c_lengthsZITransformedPM[result];
				for (int i=pos;i<pos+len;i++) {
					float coeffA = c_coefficentsZITransformedPM[i];
					int bladeA = c_bladesZITransformedPM[i];

					accumulatorCoefficients[bladeA+idInBlock*BLADECOUNT] += coeff*coeffA;
				}

			} else {
				//outer product is null
				coeff = 0;
				result = 0;
			}
			
		}

}
	
 __global__ void outerKernelPre( //TODO wrong implementation, count is always 32 for dim=5, debug this by copying into normal function
	int* c_positionsPMTransformedZI, int* c_lengthsPMTransformedZI, float* c_coefficentsPMTransformedZI, int* c_bladesPMTransformedZI,
	int* c_positionsZITransformedPM, int* c_lengthsZITransformedPM, float* c_coefficentsZITransformedPM, int* c_bladesZITransformedPM,
	int* c_outCounts
	) {
	__shared__ float accumulatorCoefficients[BLADECOUNT*THREAD_DIM_X*THREAD_DIM_Y];
	for (int i=0;i<BLADECOUNT*THREAD_DIM_X*THREAD_DIM_Y;i++)
		accumulatorCoefficients[i] = 0;

	int x = threadIdx.x+THREAD_DIM_X*blockIdx.x;
	int y = threadIdx.y+THREAD_DIM_Y*blockIdx.y;
	int idInBlock = threadIdx.x*THREAD_DIM_X+threadIdx.y;

	__syncthreads();

	outerKernelCalc(
		c_positionsPMTransformedZI, c_lengthsPMTransformedZI, c_coefficentsPMTransformedZI, c_bladesPMTransformedZI,
	    c_positionsZITransformedPM, c_lengthsZITransformedPM, c_coefficentsZITransformedPM, c_bladesZITransformedPM,
		accumulatorCoefficients, x, y, idInBlock
		);

	// zip accumulator
	int index = 0;
	for (int i=0;i<BLADECOUNT;i++) 
		if (abs(accumulatorCoefficients[i+idInBlock*BLADECOUNT]) > 10E-4)  
			index++;

	c_outCounts[x*BLADECOUNT+y] = index;
}



extern "C" void 
cudaCalculateProducts(
	int summandCountPMTransformedZI, int* positionsPMTransformedZI, 
	int* lengthsPMTransformedZI, float* coefficentsPMTransformedZI, int* bladesPMTransformedZI,
	int summandCountZITransformedPM, int* positionsZITransformedPM, 
	int* lengthsZITransformedPM, float* coefficentsZITransformedPM, int* bladesZITransformedPM
	) {
		
// allocate memory pmTransformedZI
	float* cin_coefficentsPMTransformedZI;
	int size = summandCountPMTransformedZI * sizeof(float);
	checkCudaErrors(cudaMalloc((void**) &cin_coefficentsPMTransformedZI, size));
	checkCudaErrors(cudaMemcpy(cin_coefficentsPMTransformedZI, coefficentsPMTransformedZI, size, cudaMemcpyHostToDevice));

	int* cin_bladesPMTransformedZI;
	size = summandCountPMTransformedZI * sizeof(int);
	checkCudaErrors(cudaMalloc((void**) &cin_bladesPMTransformedZI, size));
	checkCudaErrors(cudaMemcpy(cin_bladesPMTransformedZI, bladesPMTransformedZI, size, cudaMemcpyHostToDevice));

	int* cin_positionsPMTransformedZI;
	size = BLADECOUNT*sizeof(int);
	checkCudaErrors(cudaMalloc((void**) &cin_positionsPMTransformedZI, size));
	checkCudaErrors(cudaMemcpy(cin_positionsPMTransformedZI, positionsPMTransformedZI, size, cudaMemcpyHostToDevice));

	int* cin_lengthsPMTransformedZI;
	checkCudaErrors(cudaMalloc((void**) &cin_lengthsPMTransformedZI, size));
	checkCudaErrors(cudaMemcpy(cin_lengthsPMTransformedZI, lengthsPMTransformedZI, size, cudaMemcpyHostToDevice));
	
// allocate memory ziTransformedPM
	float* cin_coefficentsZITransformedPM;
	size = summandCountZITransformedPM * sizeof(float);
	checkCudaErrors(cudaMalloc((void**) &cin_coefficentsZITransformedPM, size));
	checkCudaErrors(cudaMemcpy(cin_coefficentsZITransformedPM, coefficentsZITransformedPM, size, cudaMemcpyHostToDevice));

	int* cin_bladesZITransformedPM;
	size = summandCountZITransformedPM * sizeof(int);
	checkCudaErrors(cudaMalloc((void**) &cin_bladesZITransformedPM, size));
	checkCudaErrors(cudaMemcpy(cin_bladesZITransformedPM, bladesZITransformedPM, size, cudaMemcpyHostToDevice));

	int* cin_positionsZITransformedPM;
	size = BLADECOUNT*sizeof(int);
	checkCudaErrors(cudaMalloc((void**) &cin_positionsZITransformedPM, size));
	checkCudaErrors(cudaMemcpy(cin_positionsZITransformedPM, positionsZITransformedPM, size, cudaMemcpyHostToDevice));

	int* cin_lengthsZITransformedPM;
	checkCudaErrors(cudaMalloc((void**) &cin_lengthsZITransformedPM, size));
	checkCudaErrors(cudaMemcpy(cin_lengthsZITransformedPM, lengthsZITransformedPM, size, cudaMemcpyHostToDevice));
	
// allocate memory for output
	int* c_outCounts;
	checkCudaErrors(cudaMalloc((void**) &c_outCounts, BLADECOUNT*BLADECOUNT*sizeof(int)));
	checkCudaErrors(cudaMemset(c_outCounts,0,BLADECOUNT*BLADECOUNT*sizeof(int)));
	
	// TODO CPU: collect results on host-memory, print it, or store it into a binary file for loading in Gaalop
	dim3 dimBlock(THREAD_DIM_X,THREAD_DIM_Y,1);
	dim3 dimGrid(BLADECOUNT/THREAD_DIM_X,BLADECOUNT/THREAD_DIM_Y,1); //BLADECOUNT<=11 muss gelten (für diese Hardware)
	std::cout << "Number of blocks: " << BLADECOUNT/THREAD_DIM_X << " x " << BLADECOUNT/THREAD_DIM_Y << std::endl;
	//TODO inner and geo kernel
	
	
	//precompute
	outerKernelPre<<<dimGrid,dimBlock>>>(
		cin_positionsPMTransformedZI, cin_lengthsPMTransformedZI, cin_coefficentsPMTransformedZI, cin_bladesPMTransformedZI,
		cin_positionsZITransformedPM, cin_lengthsZITransformedPM, cin_coefficentsZITransformedPM, cin_bladesZITransformedPM,
		c_outCounts
		);
	
	int* out = new int[BLADECOUNT*BLADECOUNT];

	checkCudaErrors(cudaMemcpy(out, c_outCounts, BLADECOUNT*BLADECOUNT*sizeof(int), cudaMemcpyDeviceToHost));
	

	int max = 0;
	int sum = 0;
	for (int i=0;i<BLADECOUNT*BLADECOUNT;i++) {
		if (max < out[i]) 
			max = out[i];
		sum += out[i];
		//std::cout << i << ": " << out[i] << std::endl;
	}

	std::cout << "Max: " << max << " , Sum: " << sum << std::endl;
	getchar();
	// retrieve data from gpu global memory

	// Free memory
	
	checkCudaErrors(cudaFree(c_outCounts));
	checkCudaErrors(cudaFree(cin_bladesZITransformedPM));
	checkCudaErrors(cudaFree(cin_coefficentsZITransformedPM));
	checkCudaErrors(cudaFree(cin_positionsZITransformedPM));
	checkCudaErrors(cudaFree(cin_lengthsZITransformedPM));
	
	checkCudaErrors(cudaFree(cin_bladesPMTransformedZI));
	checkCudaErrors(cudaFree(cin_coefficentsPMTransformedZI));
	checkCudaErrors(cudaFree(cin_positionsPMTransformedZI));
	checkCudaErrors(cudaFree(cin_lengthsPMTransformedZI));
	delete[] out;
	out = 0;
	cudaDeviceReset();
}