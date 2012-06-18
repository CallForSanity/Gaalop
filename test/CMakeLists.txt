CMAKE_MINIMUM_REQUIRED(VERSION 2.6)

# required packages
FIND_PACKAGE(GPC REQUIRED)

# options
OPTION(BUILD_WITH_CUDA "build with CUDA tests")
OPTION(BUILD_WITH_OPENCL "build with OpenCL tests")

GPC_CXX_ADD_EXECUTABLE(test1 "Test1_CXX_PointTriangle.cpg")
ADD_DEPENDENCIES(test1 gaalop-copy)
ADD_TEST(NAME "Test1_CXX_PointTriangle" COMMAND test1)

IF(BUILD_WITH_CUDA)
FIND_PACKAGE(CUDA REQUIRED)
GPC_CUDA_ADD_EXECUTABLE(test2 "Test2_CUDA_PointTriangle.gcu")
ADD_DEPENDENCIES(test2 gaalop-copy)
ADD_TEST(NAME "Test2_CUDA_PointTriangle" COMMAND test2)
ENDIF(BUILD_WITH_CUDA)

IF(BUILD_WITH_OPENCL)
FIND_PACKAGE(OpenCL REQUIRED)
GPC_OPENCL_ADD_EXECUTABLE(test3 "Test3_OpenCL_PointTriangle.cpp" "Test3_OpenCL_PointTriangle.clg" "cl.hpp" "clDeviceVector.h")
ADD_DEPENDENCIES(test3 gaalop-copy)
ADD_TEST(NAME "Test3_OpenCL_PointTriangle" COMMAND test3)
ENDIF(BUILD_WITH_OPENCL)

#GPC_JAVA_ADD_EXECUTABLE(test4 "Test4_Java_PointTriangle.gcj")
#ADD_DEPENDENCIES(test4 gaalop-copy)
#ADD_TEST(NAME "Test4_Java_PointTriangle" COMMAND test4)

GPC_CXX_ADD_EXECUTABLE(test5 "Test5_CXX_Horizon.cpg")
ADD_DEPENDENCIES(test5 gaalop-copy)
ADD_TEST(NAME "Test5_CXX_Horizon" COMMAND test5)

IF(BUILD_WITH_OPENCL)
GPC_OPENCL_ADD_EXECUTABLE(test6 "Test6_OpenCL_Horizon.cpp" "Test6_OpenCL_Horizon.clg" "cl.hpp" "clDeviceVector.h")
ADD_DEPENDENCIES(test6 gaalop-copy)
ADD_TEST(NAME "Test6_OpenCL_Horizon" COMMAND test6)
ENDIF(BUILD_WITH_OPENCL)

GPC_CXX_ADD_EXECUTABLE(test7 "Test7_CXX_Multiblock.cpg")
ADD_DEPENDENCIES(test7 gaalop-copy)
ADD_TEST(NAME "Test7_CXX_Multiblock" COMMAND test7)

IF(BUILD_WITH_OPENCL)
GPC_OPENCL_ADD_EXECUTABLE(test8 "Test8_OpenCL_Grasping.cpp" "Test8_OpenCL_Grasping.clg" "cl.hpp" "clDeviceVector.h")
ADD_DEPENDENCIES(test8 gaalop-copy)
ADD_TEST(NAME "Test8_OpenCL_Grasping" COMMAND test8)
ENDIF(BUILD_WITH_OPENCL)

#GPC_CXX_ADD_EXECUTABLE(test9 "Test9_CXX_AllOut.cpg")
#ADD_DEPENDENCIES(test9 gaalop-copy)
#ADD_TEST(NAME "Test9_CXX_AllOut" COMMAND test9)

