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

	// read the OpenCL program from source file
	std::string sourceString;
	readFile(sourceString, "Test3_OpenCL_PointTriangle.clg.cl");
	if(sourceString.empty())
		readFile(sourceString, "test/Test3_OpenCL_PointTriangle.clg.cl");
	cl::Program::Sources clsource(1, std::make_pair(
			sourceString.c_str(), sourceString.length()));
	cl::Program program(context, clsource);

	// build
	program.build(devices);
	std::cout
			<< program.getBuildInfo<CL_PROGRAM_BUILD_LOG> (device)
			<< std::endl;

    // settings
    cl_bool collisions[] = {false};
    const cl_float triangles[] = {0.0f,0.0f,0.0f,1.0f,0.0f,0.0f,0.0f,1.0f,0.0f};
    const cl_float points[] = {0.2f,0.2f,0.0f};
    const cl_float h = 0.1f;
    const size_t numTriangles = 1;

    // Allocate the OpenCL buffer memory objects for source and result on the device GMEM
	clDeviceVector<cl_bool> dev_collisions(context,commandQueue,numTriangles,CL_MEM_WRITE_ONLY);
    clDeviceVector<cl_float> dev_triangles(context,commandQueue,numTriangles * 9,CL_MEM_READ_ONLY);
    clDeviceVector<cl_float> dev_points(context,commandQueue,numTriangles * 3,CL_MEM_READ_ONLY);

    // create kernel and functor
	cl::Kernel pointTriangleTestKernel(program, "pointTriangleTest");
	cl::KernelFunctor pointTriangleTest = pointTriangleTestKernel.bind(commandQueue,
			cl::NDRange(numTriangles),cl::NullRange);


    // --------------------------------------------------------
    // Start Core sequence... copy input data to GPU, compute, copy results back

    // Asynchronous write of data to GPU device
	dev_triangles = triangles;
	dev_points = points;

    // Launch kernel
	pointTriangleTest(dev_collisions.getBuffer(),
					  dev_triangles.getBuffer(),
					  dev_points.getBuffer(),
					  h,(unsigned int)numTriangles);

    // Synchronous/blocking read of results, and check accumulated errors
	dev_collisions.copyTo(collisions);

    //--------------------------------------------------------

    // Cleanup and leave
    return collisions[0] ? 0 : 1;
}
