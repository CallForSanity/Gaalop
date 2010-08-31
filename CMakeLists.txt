CMAKE_MINIMUM_REQUIRED(VERSION 2.8)
PROJECT(GaalopCompilerDriver)

ENABLE_TESTING()

# setup CPack
SET(CPACK_PACKAGE_CONTACT "Patrick Charrier <patrick.charrier@stud.tu-darmstadt.de>")
SET(CPACK_PACKAGE_DESCRIPTION "Gaalop Compiler Driver (GCD) compiles source files containing Geometric Algebra expressions expressed in CLUCalc into ")
INCLUDE(CPack)

# use packages
FIND_PACKAGE(OpenMP)

# build body
ADD_SUBDIRECTORY(gcd-body)

# build and install
ADD_SUBDIRECTORY(bin)
ADD_SUBDIRECTORY(share)

# copy
FILE(COPY target DESTINATION share/gcd)

# install
INSTALL(DIRECTORY target DESTINATION share/gcd)

# set root dir
SET(GCD_ROOT_DIR "${CMAKE_CURRENT_BINARY_DIR}")

# build and install helper library
ADD_SUBDIRECTORY(include)
ADD_SUBDIRECTORY(lib)

# run tests
ADD_SUBDIRECTORY(test)
