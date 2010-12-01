CMAKE_MINIMUM_REQUIRED(VERSION 2.6)
CMAKE_POLICY(SET CMP0011 NEW)
PROJECT(GaalopCompilerDriver)

ENABLE_TESTING()

# set module find path
SET(CMAKE_MODULE_PATH "${CMAKE_CURRENT_SOURCE_DIR}/share/cmake-2.8/Modules")

# setup CPack
SET(CPACK_PACKAGE_CONTACT "Patrick Charrier <patrick.charrier@stud.tu-darmstadt.de>")
SET(CPACK_PACKAGE_DESCRIPTION "Gaalop Compiler Driver (GCD) compiles source files containing Geometric Algebra expressions expressed in CLUCalc into C++-compatible object files.")
if( WIN32 AND NOT UNIX )
    SET(CPACK_PACKAGE_EXECUTABLES config "Configuration Tool")
endif()
INCLUDE(CPack)

# build body
ADD_SUBDIRECTORY(gcd-body)

# use packages
FIND_PACKAGE(Maven REQUIRED)
INCLUDE(share/cmake-2.8/Modules/CopyFiles.cmake)
OPTION(BUILD_WITH_OPENMP "build with OpenMP")
if(BUILD_WITH_OPENMP)
  FIND_PACKAGE(OpenMP)
  if(OPENMP_FOUND)
    SET(CMAKE_CXX_FLAGS ${CMAKE_CXX_FLAGS} ${OpenMP_CXX_FLAGS})
  endif(OPENMP_FOUND)
endif(BUILD_WITH_OPENMP)

# build and install
ADD_SUBDIRECTORY(bin)
ADD_SUBDIRECTORY(gcd-config)
ADD_SUBDIRECTORY(share)

# build
add_custom_target(gaalop ALL COMMENT "Compiling Gaalop.")
add_custom_command(TARGET gaalop COMMAND ${MVN_BINARY} -DupdateOnly=true package assembly:directory WORKING_DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR})
add_custom_command(TARGET clean COMMAND ${MVN_BINARY} clean WORKING_DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR})
# copy
COPY_DIRECTORY(gaalop-copy "target/gaalop*" "share/gcd/gaalop")
COPY_DIRECTORY(dependencies-copy dependencies "share/gcd/gaalop/plugins")
# install
FILE(GLOB GAALOP_BUILD_TARGET_DIR "target/gaalop*")
INSTALL(DIRECTORY ${GAALOP_BUILD_TARGET_DIR} DESTINATION share/gcd PATTERN ".svn" EXCLUDE)
INSTALL(DIRECTORY dependencies DESTINATION share/gcd/gaalop/plugins PATTERN ".svn" EXCLUDE)

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

# install tests
INSTALL(DIRECTORY test DESTINATION share/gcd PATTERN ".svn" EXCLUDE)
