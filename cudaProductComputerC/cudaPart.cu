#pragma once

#include "AlgebraSetting.h"
#include <assert.h>
#include <cuda_runtime.h>
#include <iostream>

__global__ void simpleCopyKernel(int* in_pos, int* out_pos) {
	int tx = threadIdx.x;
	out_pos[tx] = in_pos[tx]*2;
}

extern "C" void 
cudaCalculateProducts(int summandCountPMTransformedZI, int* positionsPMTransformedZI, int* lengthsPMTransformedZI, float* coefficentsPMTransformedZI) {
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
	dim3 dimBlock(32,32,1);
	dim3 dimGrid(BLADECOUNT/32,BLADECOUNT/32,1); //BLADECOUNT<=11 muss gelten (für diese Hardware)
	std::cout << "Number of blocks: " << BLADECOUNT/32 << " x " << BLADECOUNT/32 << std::endl;

	simpleCopyKernel<<<dimGrid,dimBlock>>>(cin_positionsPMTransformedZI, cout_out);


	// retrieve data from gpu global memory
	int out[BLADECOUNT];

	assert(cudaMemcpy(out, cout_out, BLADECOUNT*sizeof(int), cudaMemcpyDeviceToHost) == cudaSuccess);

	//for (int i = 0;i<BLADECOUNT;i++) 
	//	std::cout << out[i] << std::endl;

	// Free memory
	assert(cudaFree(cout_out) == cudaSuccess);
	assert(cudaFree(cin_coefficentsPMTransformedZI) == cudaSuccess);
	assert(cudaFree(cin_positionsPMTransformedZI) == cudaSuccess);
	assert(cudaFree(cin_lengthsPMTransformedZI) == cudaSuccess);
}