#include <cstdlib>
#include <string>
#include <iostream>
#include <fstream>
#include <sstream>
#include <CL/cl.h>
#include <CL/cl_platform.h>

// Name of the file with the source code for the computation kernel
// *********************************************************************
const char* cSourceFile = "Test8_OpenCL_Grasping.gcl.cl";

// OpenCL Vars
cl_context cxGPUContext;        // OpenCL context
cl_command_queue cqCommandQueue;// OpenCL command que
cl_platform_id cpPlatform;      // OpenCL platform
cl_device_id cdDevice;          // OpenCL device
cl_program cpProgram;           // OpenCL program
cl_kernel ckKernel;             // OpenCL kernel
cl_mem cmDevFinalPosition;	// Final position of gripper
cl_mem cmDevTargetPosition;	// Target position for gripper
cl_mem cmDevGripper;		// Initial position of Gripper and radius
cl_mem cmDevX1;			// Base Point x1
cl_mem cmDevX2;			// Base Point x2
cl_mem cmDevX3;			// Base Point x3
cl_mem cmDevX4;			// Apex Point
cl_int ciErr1, ciErr2;		// Error code var

void readFile(std::stringstream& resultStream,std::ifstream& fileStream)
{
  std::string line;
  while(fileStream.good())
  {
    getline(fileStream,line);
    resultStream << line << std::endl;
  }
}

void readFile(std::stringstream& resultStream,const char* filePath)
{
  std::ifstream fileStream(filePath);
  readFile(resultStream,fileStream);
}

void readFile(std::string& resultString,const char* filePath)
{
  std::stringstream resultStream;
  readFile(resultStream,filePath);
  resultString = resultStream.str();
}

// Forward Declarations
// *********************************************************************
void VectorAddHost(const float* pfData1, const float* pfData2, float* pfResult, size_t iNumElements);
void Cleanup (int iExitCode);
void printMultivector(float *v);

// Main function 
// *********************************************************************
int main(int argc, char **argv)
{
    //Get an OpenCL platform
    ciErr1 = clGetPlatformIDs(1, &cpPlatform, NULL);
    if (ciErr1 != CL_SUCCESS){
    	std::cout << "ERROR: clGetPlatformIDs: " << ciErr1 << std::endl;
        Cleanup(EXIT_FAILURE);
	}

    //Get the devices
    ciErr1 = clGetDeviceIDs(cpPlatform, CL_DEVICE_TYPE_GPU, 1, &cdDevice, NULL);
    if(ciErr1 != CL_SUCCESS)
    	ciErr1 = clGetDeviceIDs(cpPlatform, CL_DEVICE_TYPE_CPU, 1, &cdDevice, NULL);
    if (ciErr1 != CL_SUCCESS)
        Cleanup(EXIT_FAILURE);

    //Create the context
    cxGPUContext = clCreateContext(0, 1, &cdDevice, NULL, NULL, &ciErr1);
    if (ciErr1 != CL_SUCCESS)
        Cleanup(EXIT_FAILURE);

    // Create a command-queue
    cqCommandQueue = clCreateCommandQueue(cxGPUContext, cdDevice, 0, &ciErr1);
    if (ciErr1 != CL_SUCCESS)
        Cleanup(EXIT_FAILURE);

    // Allocate the OpenCL buffer memory objects for source and result on the device GMEM
    cmDevFinalPosition = clCreateBuffer(cxGPUContext, CL_MEM_WRITE_ONLY, 32*sizeof(cl_float), NULL, &ciErr1);
    cmDevTargetPosition = clCreateBuffer(cxGPUContext, CL_MEM_WRITE_ONLY, 32*sizeof(cl_float), NULL, &ciErr1);
    cmDevGripper = clCreateBuffer(cxGPUContext, CL_MEM_READ_ONLY, 4 * sizeof(cl_float), NULL, &ciErr1);
    cmDevX1 = clCreateBuffer(cxGPUContext, CL_MEM_READ_ONLY, 3 * sizeof(cl_float), NULL, &ciErr1);
    cmDevX2 = clCreateBuffer(cxGPUContext, CL_MEM_READ_ONLY, 3 * sizeof(cl_float), NULL, &ciErr1);
    cmDevX3 = clCreateBuffer(cxGPUContext, CL_MEM_READ_ONLY, 3 * sizeof(cl_float), NULL, &ciErr1);
    cmDevX4 = clCreateBuffer(cxGPUContext, CL_MEM_READ_ONLY, 3 * sizeof(cl_float), NULL, &ciErr1);

    // settings
    cl_float finalPosition[] = {0.0f,0.0f,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    cl_float targetPosition[] = {0.0f,0.0f,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    const cl_float gripper[] = {-1.15f,0.0f,0.0f,0.6f};
    const cl_float pointX1[] = {0.6f,0.2f,0.2f};
    const cl_float pointX2[] = {0.7f,0.2f,1.0f};
    const cl_float pointX3[] = {1.0f,0.15f,1.0f};
    const cl_float pointX4[] = {1.1f,1.9f,0.2f};
    const size_t szGlobalWorkSize = 1;
    
    // Read the OpenCL kernel in from source file
    std::string sourceString;
    readFile(sourceString,cSourceFile);
    const char* cSourceCL = sourceString.c_str();
    const size_t szKernelLength = sourceString.length();

    // Create the program
    //std::cout << cSourceCL << std::endl;

    cpProgram = clCreateProgramWithSource(cxGPUContext, 1, (const char **)&cSourceCL, NULL, &ciErr1);
    if (ciErr1 != CL_SUCCESS){
    	std::cout << "ERROR: clCreateProgramWithSource: " << ciErr1 << std::endl;
        Cleanup(EXIT_FAILURE);
	}

    // Build the program with 'mad' Optimization option
    #ifdef MAC
        const char* flags = "-cl-fast-relaxed-math -DMAC";
    #else
        const char* flags = "-cl-fast-relaxed-math";
    #endif
    ciErr1 = clBuildProgram(cpProgram, 0, NULL, NULL, NULL, NULL);
    if (ciErr1 != CL_SUCCESS){
    	std::cout << "clBuildProgram: " <<ciErr1 << std::endl;
        Cleanup(EXIT_FAILURE);
	}

    // Create the kernel
    ckKernel = clCreateKernel(cpProgram, "pointTriangleTest", &ciErr1);
    if (ciErr1 != CL_SUCCESS)
        Cleanup(EXIT_FAILURE);


    // Set the Argument values
    ciErr1 = clSetKernelArg(ckKernel, 0, sizeof(cl_mem), (void*)&cmDevFinalPosition);
    ciErr1 = clSetKernelArg(ckKernel, 1, sizeof(cl_mem), (void*)&cmDevTargetPosition);
    ciErr1 |= clSetKernelArg(ckKernel, 2, sizeof(cl_mem), (void*)&cmDevGripper);
    ciErr1 |= clSetKernelArg(ckKernel, 3, sizeof(cl_mem), (void*)&cmDevX1);
    ciErr1 |= clSetKernelArg(ckKernel, 4, sizeof(cl_mem), (void*)&cmDevX2);
    ciErr1 |= clSetKernelArg(ckKernel, 5, sizeof(cl_mem), (void*)&cmDevX3);
    ciErr1 |= clSetKernelArg(ckKernel, 6, sizeof(cl_mem), (void*)&cmDevX4);
    if (ciErr1 != CL_SUCCESS)
        Cleanup(EXIT_FAILURE);

    // --------------------------------------------------------
    // Start Core sequence... copy input data to GPU, compute, copy results back

    // Asynchronous write of data to GPU device
    ciErr1 = clEnqueueWriteBuffer(cqCommandQueue, cmDevFinalPosition, CL_FALSE, 0, 32 * sizeof(cl_float), finalPosition, 0, NULL, NULL);
    ciErr1 = clEnqueueWriteBuffer(cqCommandQueue, cmDevTargetPosition, CL_FALSE, 0, 32 * sizeof(cl_float), targetPosition, 0, NULL, NULL);
    ciErr1 = clEnqueueWriteBuffer(cqCommandQueue, cmDevGripper, CL_FALSE, 0, 4 * sizeof(cl_float), gripper, 0, NULL, NULL);
    ciErr1 |= clEnqueueWriteBuffer(cqCommandQueue, cmDevX1, CL_FALSE, 0, 3 * sizeof(cl_float), pointX1, 0, NULL, NULL);
    ciErr1 |= clEnqueueWriteBuffer(cqCommandQueue, cmDevX2, CL_FALSE, 0, 3 * sizeof(cl_float), pointX2, 0, NULL, NULL);
    ciErr1 |= clEnqueueWriteBuffer(cqCommandQueue, cmDevX3, CL_FALSE, 0, 3 * sizeof(cl_float), pointX3, 0, NULL, NULL);
    ciErr1 |= clEnqueueWriteBuffer(cqCommandQueue, cmDevX4, CL_FALSE, 0, 3 * sizeof(cl_float), pointX4, 0, NULL, NULL);
    if (ciErr1 != CL_SUCCESS)
        Cleanup(EXIT_FAILURE);

    // Launch kernel
    ciErr1 = clEnqueueNDRangeKernel(cqCommandQueue, ckKernel, 1, NULL, &szGlobalWorkSize, NULL, 0, NULL, NULL);
    if (ciErr1 != CL_SUCCESS)
        Cleanup(EXIT_FAILURE);

    // Synchronous/blocking read of results, and check accumulated errors
    ciErr1 = clEnqueueReadBuffer(cqCommandQueue, cmDevFinalPosition, CL_TRUE, 0, 32 * sizeof(cl_float), finalPosition, 0, NULL, NULL);
    ciErr1 = clEnqueueReadBuffer(cqCommandQueue, cmDevTargetPosition, CL_TRUE, 0, 32 * sizeof(cl_float), targetPosition, 0, NULL, NULL);
    if (ciErr1 != CL_SUCCESS)
        Cleanup(EXIT_FAILURE);

    //--------------------------------------------------------
	std::cout << "Target position:" << std::endl;
	printMultivector(targetPosition);
	std::cout << "Final position:" << std::endl;
	printMultivector(finalPosition);

    // Cleanup and leave
    Cleanup(EXIT_SUCCESS);
}

void Cleanup (int iExitCode)
{
    if(iExitCode){
		// Shows the log
		char* build_log;
		size_t log_size;
		// // First call to know the proper size
		clGetProgramBuildInfo(cpProgram, cdDevice, CL_PROGRAM_BUILD_LOG, 0, NULL, &log_size);
		build_log = new char[log_size+1];
		// // Second call to get the log
		clGetProgramBuildInfo(cpProgram, cdDevice, CL_PROGRAM_BUILD_LOG, log_size, build_log, NULL);
		build_log[log_size] = '\0';
		std::cout << build_log << std::endl;
		delete[] build_log;
	}

    // Cleanup allocated objects
    if(ckKernel)clReleaseKernel(ckKernel);  
    if(cpProgram)clReleaseProgram(cpProgram);
    if(cqCommandQueue)clReleaseCommandQueue(cqCommandQueue);
    if(cxGPUContext)clReleaseContext(cxGPUContext);
    if(cmDevFinalPosition)clReleaseMemObject(cmDevFinalPosition);
    if(cmDevTargetPosition)clReleaseMemObject(cmDevTargetPosition);
    if(cmDevGripper)clReleaseMemObject(cmDevGripper);
    if(cmDevX1)clReleaseMemObject(cmDevX1);
    if(cmDevX2)clReleaseMemObject(cmDevX2);
    if(cmDevX3)clReleaseMemObject(cmDevX3);
    if(cmDevX4)clReleaseMemObject(cmDevX4);

    std::cout << "Exit: " << iExitCode << std::endl;

    exit (iExitCode);
}

void printMultivector(float *v){
    for(size_t i = 0; i < 32; ++i) {
		if(v[i]) { // check if entry is non-zero
			switch(i) {
				case  0: std::cout << i << ": " << v[i] << std::endl; break;
				case  1: std::cout << i << ": " << v[i] << " e1" << std::endl; break;
				case  2: std::cout << i << ": " << v[i] << " e2" << std::endl; break;
				case  3: std::cout << i << ": " << v[i] << " e3" << std::endl; break;
				case  4: std::cout << i << ": " << v[i] << " ei" << std::endl; break;
				case  5: std::cout << i << ": " << v[i] << " e0" << std::endl; break;
				case  6: std::cout << i << ": " << v[i] << " e12" << std::endl; break;
				case  7: std::cout << i << ": " << v[i] << " e13" << std::endl; break;
				case  8: std::cout << i << ": " << v[i] << " e1i" << std::endl; break;
				case  9: std::cout << i << ": " << v[i] << " e10" << std::endl; break;
				case 10: std::cout << i << ": " << v[i] << " e23" << std::endl; break;
				case 11: std::cout << i << ": " << v[i] << " e2i" << std::endl; break;
				case 12: std::cout << i << ": " << v[i] << " e20" << std::endl; break;
				case 13: std::cout << i << ": " << v[i] << " e3i" << std::endl; break;
				case 14: std::cout << i << ": " << v[i] << " e30" << std::endl; break;
				case 15: std::cout << i << ": " << v[i] << " ei0" << std::endl; break;
				case 16: std::cout << i << ": " << v[i] << " e123" << std::endl; break;
				case 17: std::cout << i << ": " << v[i] << " e12i" << std::endl; break;
				case 18: std::cout << i << ": " << v[i] << " e120" << std::endl; break;
				case 19: std::cout << i << ": " << v[i] << " e13i" << std::endl; break;
				case 20: std::cout << i << ": " << v[i] << " e130" << std::endl; break;
				case 21: std::cout << i << ": " << v[i] << " e1i0" << std::endl; break;
				case 22: std::cout << i << ": " << v[i] << " e23i" << std::endl; break;
				case 23: std::cout << i << ": " << v[i] << " e230" << std::endl; break;
				case 24: std::cout << i << ": " << v[i] << " e2i0" << std::endl; break;
				case 25: std::cout << i << ": " << v[i] << " e3i0" << std::endl; break;
				case 26: std::cout << i << ": " << v[i] << " e123i" << std::endl; break;
				case 27: std::cout << i << ": " << v[i] << " e1230" << std::endl; break;
				case 28: std::cout << i << ": " << v[i] << " e12i0" << std::endl; break;
				case 29: std::cout << i << ": " << v[i] << " e13i0" << std::endl; break;
				case 30: std::cout << i << ": " << v[i] << " e23i0" << std::endl; break;
				case 31: std::cout << i << ": " << v[i] << " e123i0" << std::endl; break;
				default: std::cout << i << ": " << "ERROR: Index out of range";
			}
		}
	}
	std::cout << std::endl;
}
