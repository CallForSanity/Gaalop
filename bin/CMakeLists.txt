CMAKE_MINIMUM_REQUIRED(VERSION 2.8)

# build executables
ADD_EXECUTABLE(gcd-cpp "gcdCpp.cpp")
TARGET_LINK_LIBRARIES(gcd-cpp gcd-body)
ADD_EXECUTABLE(gcd-cuda "gcdCuda.cpp")
TARGET_LINK_LIBRARIES(gcd-cuda gcd-body)
ADD_EXECUTABLE(gcd-opencl "gcdOpenCL.cpp")
TARGET_LINK_LIBRARIES(gcd-opencl gcd-body)

# install
INSTALL(TARGETS gcd-cpp gcd-cuda gcd-opencl RUNTIME DESTINATION bin)
