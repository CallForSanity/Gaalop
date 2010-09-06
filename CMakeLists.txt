CMAKE_MINIMUM_REQUIRED(VERSION 2.8)
PROJECT(GaalopCompilerDriver)

ENABLE_TESTING()

# setup CPack
SET(CPACK_PACKAGE_CONTACT "Patrick Charrier <patrick.charrier@stud.tu-darmstadt.de>")
SET(CPACK_PACKAGE_DESCRIPTION "Gaalop Compiler Driver (GCD) compiles source files containing Geometric Algebra expressions expressed in CLUCalc into C++-compatible object files.")
if( WIN32 AND NOT UNIX )
    SET(CPACK_PACKAGE_EXECUTABLES config "Configuration Tool")
endif()
INCLUDE(CPack)

# use packages
OPTION(BUILD_WITH_OPENMP "build with OpenMP")
if(BUILD_WITH_OPENMP)
  FIND_PACKAGE(OpenMP)
  if(OPENMP_FOUND)
    SET(CMAKE_CXX_FLAGS ${CMAKE_CXX_FLAGS} ${OpenMP_CXX_FLAGS})
  endif(OPENMP_FOUND)
  if(UNIX)
    TARGET_LINK_LIBRARIES(gcd-body gomp)
  endif(UNIX)
endif(BUILD_WITH_OPENMP)

# build body
ADD_SUBDIRECTORY(gcd-body)

# build and install
ADD_SUBDIRECTORY(bin)
ADD_SUBDIRECTORY(config)
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
OPTION(BUILD_WITH_TESTS "build with tests")
IF(BUILD_WITH_TESTS)
	ADD_SUBDIRECTORY(test)
ENDIF(BUILD_WITH_TESTS)
