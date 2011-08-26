#include <cstdlib>
#include <string>
#include <iostream>
#include <fstream>
#include <sstream>
#include "cl.hpp"
#include "clDeviceVector.h"

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
void printMultivector(float *v);

// Main function 
// *********************************************************************
int main(int argc, char **argv)
{

    // settings
    cl_float finalPosition[] = {0.0f,0.0f,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    cl_float targetPosition[] = {0.0f,0.0f,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    const cl_float gripper[] = {-1.15f,0.0f,0.0f,0.6f};
    const cl_float pointX1[] = {0.6f,0.2f,0.2f};
    const cl_float pointX2[] = {0.7f,0.2f,1.0f};
    const cl_float pointX3[] = {1.0f,0.15f,1.0f};
    const cl_float pointX4[] = {1.1f,1.9f,0.2f};
    const size_t szGlobalWorkSize = 1;

    // list platforms
	std::vector<cl::Platform> platforms;
	cl::Platform::get(&platforms);
	std::cout << "listings platforms\n";
	for (std::vector<cl::Platform>::const_iterator it =
			platforms.begin(); it != platforms.end(); ++it)
		std::cout << it->getInfo<CL_PLATFORM_NAME> () << std::endl;

	// create context
	cl_context_properties properties[] = { CL_CONTEXT_PLATFORM,
			(cl_context_properties)(platforms[0])(), 0 };
	cl::Context context(CL_DEVICE_TYPE_ALL, properties);
	std::vector<cl::Device> devices = context.getInfo<
			CL_CONTEXT_DEVICES> ();
	cl::Device& device = devices.front();

	// create command queue
	cl::CommandQueue commandQueue(context, device);

	// read the OpenCL program from source file
	std::string sourceString;
	readFile(sourceString, "Test8_OpenCL_Grasping.gcl.cl");
	cl::Program::Sources clsource(1, std::make_pair(
			sourceString.c_str(), sourceString.length()));
	cl::Program program(context, clsource);

	// build
	program.build(devices);
	std::cout
			<< program.getBuildInfo<CL_PROGRAM_BUILD_LOG> (device)
			<< std::endl;

    // create kernel and functor
	cl::Kernel graspingKernel(program, "grasping");
	cl::KernelFunctor grasping = graspingKernel.bind(commandQueue,
			cl::NDRange(szGlobalWorkSize),cl::NullRange);

    // Allocate the OpenCL buffer memory objects for source and result on the device GMEM
	clDeviceVector<cl_float> dev_final_position(context,commandQueue,CL_MEM_WRITE_ONLY,32);
    clDeviceVector<cl_float> dev_target_position(context,commandQueue,CL_MEM_WRITE_ONLY,32);
    clDeviceVector<cl_float> dev_gripper(context,commandQueue,CL_MEM_READ_ONLY,4);
    clDeviceVector<cl_float> dev_X1(context,commandQueue,CL_MEM_READ_ONLY,3);
    clDeviceVector<cl_float> dev_X2(context,commandQueue,CL_MEM_READ_ONLY,3);
    clDeviceVector<cl_float> dev_X3(context,commandQueue,CL_MEM_READ_ONLY,3);
    clDeviceVector<cl_float> dev_X4(context,commandQueue,CL_MEM_READ_ONLY,3);

    // --------------------------------------------------------
    // Start Core sequence... copy input data to GPU, compute, copy results back

    // Asynchronous write of data to GPU device
    dev_final_position = finalPosition;
    dev_target_position = targetPosition;
    dev_gripper = gripper;
    dev_X1 = pointX1;
    dev_X2 = pointX2;
    dev_X3 = pointX3;
    dev_X4 = pointX4;

    // Launch kernel
    grasping(dev_final_position,dev_target_position,dev_gripper,
			 dev_X1,dev_X2,dev_X3,dev_X4);

    // Synchronous/blocking read of results, and check accumulated errors
    dev_final_position.copyTo(finalPosition);
    dev_target_position.copyTo(targetPosition);

    //--------------------------------------------------------
	std::cout << "Target position:" << std::endl;
	printMultivector(targetPosition);
	std::cout << "Final position:" << std::endl;
	printMultivector(finalPosition);

	return 0;
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
