#include <cstdlib>
#include <string>
#include <iostream>
#include <fstream>
#include <sstream>
#define __CL_ENABLE_EXCEPTIONS
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

// Main function
// *********************************************************************
int main(int argc, char **argv)
{
	// list platforms
	std::vector<cl::Platform> platforms;
	cl::Platform::get(&platforms);
	std::cout << "listing platforms\n";
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

    // settings
    const size_t numPoints = 10000;
    cl_float circleCenters[3*numPoints];
    cl_float points[3*numPoints];

    // set first point to example point
    points[0] = 1.0f;
    points[1] = 1.0f;
    points[2] = 0.0f;

    // Allocate the OpenCL buffer memory objects for source and result on the device GMEM
    clDeviceVector<cl_float> dev_circle_centers(context,commandQueue,numPoints * 3,CL_MEM_READ_ONLY);
    clDeviceVector<cl_float> dev_points(context,commandQueue,numPoints * 3,CL_MEM_READ_ONLY);

	// read the OpenCL program from source file
	std::string sourceString;
	readFile(sourceString, "Test6_OpenCL_Horizon.clg.cl");
	if(sourceString.empty())
		readFile(sourceString, "test/Test6_OpenCL_Horizon.clg.cl");
	cl::Program::Sources clsource(1, std::make_pair(
			sourceString.c_str(), sourceString.length()));
	cl::Program program(context, clsource);

	std::cout << sourceString;

	// build
	program.build(devices);
	std::cout
			<< program.getBuildInfo<CL_PROGRAM_BUILD_LOG> (device)
			<< std::endl;

    // create kernel and functor
	cl::Kernel horizonKernel(program, "horizonKernel");
	cl::KernelFunctor horizonFunctor = horizonKernel.bind(commandQueue,
			cl::NDRange(numPoints),cl::NullRange);

    // --------------------------------------------------------
    // Start Core sequence... copy input data to GPU, compute, copy results back

    // Asynchronous write of data to GPU device
	dev_points = points;

    // Launch kernel
	horizonFunctor(dev_circle_centers.getBuffer(),dev_points.getBuffer());

    // Synchronous/blocking read of results, and check accumulated errors
	dev_circle_centers.copyTo(circleCenters);

    //--------------------------------------------------------
    // print first circle center
    std::cout << circleCenters[0] << "," << circleCenters[1] << "," << circleCenters[2] << std::endl;

    return !(circleCenters[0] == 0.5f && circleCenters[1] == 0.5f && circleCenters[2] == 0.0f);
}
