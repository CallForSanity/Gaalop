CMAKE_MINIMUM_REQUIRED(VERSION 2.6)

# build executables
ADD_EXECUTABLE(gcd-cxx "gcdCxx.cpp")
TARGET_LINK_LIBRARIES(gcd-cxx gcd-body)
ADD_EXECUTABLE(gcd-cuda "gcdCuda.cpp")
TARGET_LINK_LIBRARIES(gcd-cuda gcd-body)
ADD_EXECUTABLE(gcd-opencl "gcdOpenCL.cpp")
TARGET_LINK_LIBRARIES(gcd-opencl gcd-body)
ADD_EXECUTABLE(gcd-java "gcdJava.cpp")
TARGET_LINK_LIBRARIES(gcd-java gcd-body)

# install
INSTALL(TARGETS gcd-cxx gcd-cuda gcd-opencl gcd-java RUNTIME DESTINATION bin)
