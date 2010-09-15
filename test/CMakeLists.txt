CMAKE_MINIMUM_REQUIRED(VERSION 2.8)

INCLUDE("../share/cmake-2.8/Modules/FindGCD.cmake")

# options
OPTION(BUILD_WITH_CUDA "build with CUDA tests")
OPTION(BUILD_WITH_OPENCL "build with OpenCL tests")

GCD_CXX_ADD_EXECUTABLE(test1 "Test1_CXX_PointTriangle.gcp")
ADD_TEST(NAME "Test1_CXX_PointTriangle" COMMAND test1)

IF(BUILD_WITH_CUDA)
FIND_PACKAGE(CUDA)
GCD_CUDA_ADD_EXECUTABLE(test2 "Test2_CUDA_PointTriangle.gcu")
ADD_TEST(NAME "Test2_CUDA_PointTriangle" COMMAND test2)
ENDIF(BUILD_WITH_CUDA)

IF(BUILD_WITH_OPENCL)
INCLUDE("../share/cmake-2.8/Modules/FindOpenCL.cmake")
GCD_OPENCL_ADD_EXECUTABLE(test3 "Test3_OpenCL_PointTriangle.cpp" "Test3_OpenCL_PointTriangle.gcl")
ADD_TEST(NAME "Test3_OpenCL_PointTriangle" COMMAND test3)
ENDIF(BUILD_WITH_OPENCL)

GCD_JAVA_ADD_EXECUTABLE(test4 "Test4_Java_PointTriangle.gcj")
ADD_TEST(NAME "Test4_Java_PointTriangle" COMMAND test4)
