CMAKE_MINIMUM_REQUIRED(VERSION 2.6)

# required packages
FIND_PACKAGE(GCD REQUIRED)

# options
OPTION(BUILD_WITH_CUDA "build with CUDA tests")
OPTION(BUILD_WITH_OPENCL "build with OpenCL tests")

GCD_CXX_ADD_EXECUTABLE(test1 "Test1_CXX_PointTriangle.gcp")
ADD_TEST(NAME "Test1_CXX_PointTriangle" COMMAND test1)

IF(BUILD_WITH_CUDA)
FIND_PACKAGE(CUDA REQUIRED)
GCD_CUDA_ADD_EXECUTABLE(test2 "Test2_CUDA_PointTriangle.gcu")
ADD_TEST(NAME "Test2_CUDA_PointTriangle" COMMAND test2)
ENDIF(BUILD_WITH_CUDA)

IF(BUILD_WITH_OPENCL)
FIND_PACKAGE(OpenCL REQUIRED)
GCD_OPENCL_ADD_EXECUTABLE(test3 "Test3_OpenCL_PointTriangle.cpp" "Test3_OpenCL_PointTriangle.gcl" "cl.hpp" "clDeviceVector.h")
ADD_TEST(NAME "Test3_OpenCL_PointTriangle" COMMAND test3)
ENDIF(BUILD_WITH_OPENCL)

#GCD_JAVA_ADD_EXECUTABLE(test4 "Test4_Java_PointTriangle.gcj")
#ADD_TEST(NAME "Test4_Java_PointTriangle" COMMAND test4)

GCD_CXX_ADD_EXECUTABLE(test5 "Test5_CXX_Horizon.gcp")
ADD_TEST(NAME "Test5_CXX_Horizon" COMMAND test5)

IF(BUILD_WITH_OPENCL)
GCD_OPENCL_ADD_EXECUTABLE(test6 "Test6_OpenCL_Horizon.cpp" "Test6_OpenCL_Horizon.gcl" "cl.hpp" "clDeviceVector.h")
ADD_TEST(NAME "Test6_OpenCL_Horizon" COMMAND test6)
ENDIF(BUILD_WITH_OPENCL)

GCD_CXX_ADD_EXECUTABLE(test7 "Test7_CXX_Multiblock.gcp")
ADD_TEST(NAME "Test7_CXX_Multiblock" COMMAND test7)

IF(BUILD_WITH_OPENCL)
GCD_OPENCL_ADD_EXECUTABLE(test8 "Test8_OpenCL_Grasping.cpp" "Test8_OpenCL_Grasping.gcl" "cl.hpp" "clDeviceVector.h")
ADD_TEST(NAME "Test8_OpenCL_Grasping" COMMAND test8)
ENDIF(BUILD_WITH_OPENCL)
