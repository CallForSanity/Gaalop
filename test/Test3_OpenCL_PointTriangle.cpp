#include <cstdlib>
#include <string>
#include <iostream>
#include <fstream>
#include <sstream>
#include <CL/cl.h>
#include <CL/cl_platform.h>

// Name of the file with the source code for the computation kernel
// *********************************************************************
const char* cSourceFile = "Test3_OpenCL_PointTriangle.gcl.cl";

// OpenCL Vars
cl_context cxGPUContext;        // OpenCL context
cl_command_queue cqCommandQueue;// OpenCL command que
cl_platform_id cpPlatform;      // OpenCL platform
cl_device_id cdDevice;          // OpenCL device
cl_program cpProgram;           // OpenCL program
cl_kernel ckKernel;             // OpenCL kernel
cl_mem cmDevCollision;		// Collision
cl_mem cmDevTriangles;		// Triangles
cl_mem cmDevPoint;		// Point
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
void VectorAddHost(const float* pfData1, const float* pfData2, float* pfResult, int iNumElements);
void Cleanup (int iExitCode);

// Main function 
// *********************************************************************
int main(int argc, char **argv)
{
    //Get an OpenCL platform
    ciErr1 = clGetPlatformIDs(1, &cpPlatform, NULL);
    if (ciErr1 != CL_SUCCESS)
        Cleanup(EXIT_FAILURE);

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
    cmDevCollision = clCreateBuffer(cxGPUContext, CL_MEM_WRITE_ONLY, sizeof(cl_bool), NULL, &ciErr1);
    cmDevTriangles = clCreateBuffer(cxGPUContext, CL_MEM_READ_ONLY, 9 * sizeof(cl_float), NULL, &ciErr1);
    cmDevPoint = clCreateBuffer(cxGPUContext, CL_MEM_READ_ONLY, 3 * sizeof(cl_float), NULL, &ciErr1);

    // settings
    cl_bool collision[] = {false};
    const cl_float triangles[] = {0.0f,0.0f,0.0f,1.0f,0.0f,0.0f,0.0f,1.0f,0.0f};
    const cl_float point[] = {0.2f,0.2f,0.0f};
    const cl_float h = 0.1f;
    const int numTriangles = 1;
    const size_t szGlobalWorkSize = 1;
    
    // Read the OpenCL kernel in from source file
    std::string sourceString;
    readFile(sourceString,cSourceFile);
    const char* cSourceCL = sourceString.c_str();
    const int szKernelLength = sourceString.length();

    // Create the program
    std::cout << cSourceCL << std::endl;

    cpProgram = clCreateProgramWithSource(cxGPUContext, 1, (const char **)&cSourceCL, NULL, &ciErr1);
    if (ciErr1 != CL_SUCCESS)
        Cleanup(EXIT_FAILURE);

    // Build the program with 'mad' Optimization option
    #ifdef MAC
        const char* flags = "-cl-fast-relaxed-math -DMAC";
    #else
        const char* flags = "-cl-fast-relaxed-math";
    #endif
    ciErr1 = clBuildProgram(cpProgram, 0, NULL, NULL, NULL, NULL);
    if (ciErr1 != CL_SUCCESS)
        Cleanup(EXIT_FAILURE);

    // Create the kernel
    ckKernel = clCreateKernel(cpProgram, "pointTriangleTest", &ciErr1);
    if (ciErr1 != CL_SUCCESS)
        Cleanup(EXIT_FAILURE);

    // Set the Argument values
    ciErr1 = clSetKernelArg(ckKernel, 0, sizeof(cl_mem), (void*)&cmDevCollision);
    ciErr1 |= clSetKernelArg(ckKernel, 1, sizeof(cl_mem), (void*)&cmDevTriangles);
    ciErr1 |= clSetKernelArg(ckKernel, 2, sizeof(cl_mem), (void*)&cmDevPoint);
    ciErr1 |= clSetKernelArg(ckKernel, 3, sizeof(cl_float), (void*)&h);
    ciErr1 |= clSetKernelArg(ckKernel, 4, sizeof(cl_int), (void*)&numTriangles);
    if (ciErr1 != CL_SUCCESS)
        Cleanup(EXIT_FAILURE);

    // --------------------------------------------------------
    // Start Core sequence... copy input data to GPU, compute, copy results back

    // Asynchronous write of data to GPU device
    ciErr1 = clEnqueueWriteBuffer(cqCommandQueue, cmDevCollision, CL_FALSE, 0, sizeof(cl_bool), collision, 0, NULL, NULL);
    ciErr1 = clEnqueueWriteBuffer(cqCommandQueue, cmDevTriangles, CL_FALSE, 0, 9 * sizeof(cl_float), triangles, 0, NULL, NULL);
    ciErr1 |= clEnqueueWriteBuffer(cqCommandQueue, cmDevPoint, CL_FALSE, 0, 3 * sizeof(cl_float), point, 0, NULL, NULL);
    if (ciErr1 != CL_SUCCESS)
        Cleanup(EXIT_FAILURE);

    // Launch kernel
    ciErr1 = clEnqueueNDRangeKernel(cqCommandQueue, ckKernel, 1, NULL, &szGlobalWorkSize, NULL, 0, NULL, NULL);
    if (ciErr1 != CL_SUCCESS)
        Cleanup(EXIT_FAILURE);

    // Synchronous/blocking read of results, and check accumulated errors
    ciErr1 = clEnqueueReadBuffer(cqCommandQueue, cmDevCollision, CL_TRUE, 0, sizeof(cl_bool), collision, 0, NULL, NULL);
    if (ciErr1 != CL_SUCCESS)
        Cleanup(EXIT_FAILURE);

    //--------------------------------------------------------

    // Cleanup and leave
    Cleanup (collision[0] == true ? 0 : 1);
}

void Cleanup (int iExitCode)
{
    // Cleanup allocated objects
    if(ckKernel)clReleaseKernel(ckKernel);  
    if(cpProgram)clReleaseProgram(cpProgram);
    if(cqCommandQueue)clReleaseCommandQueue(cqCommandQueue);
    if(cxGPUContext)clReleaseContext(cxGPUContext);
    if(cmDevCollision)clReleaseMemObject(cmDevCollision);
    if(cmDevTriangles)clReleaseMemObject(cmDevTriangles);
    if(cmDevPoint)clReleaseMemObject(cmDevPoint);

    exit (iExitCode);
}
