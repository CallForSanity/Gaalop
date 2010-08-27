CMAKE_MINIMUM_REQUIRED(VERSION 2.8)

INCLUDE("../cmake/FindGCD.cmake")

GCD_CPP_ADD_EXECUTABLE(test1 "Test1_PointTriangle.gcp")
ADD_TEST(NAME "Test1_PointTriangle" COMMAND test1)
