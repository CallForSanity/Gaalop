CMAKE_MINIMUM_REQUIRED(VERSION 2.6)
CMAKE_POLICY(SET CMP0011 NEW)
PROJECT(GaalopPrecompiler)

# enable testing
ENABLE_TESTING()

# set module find path
SET(CMAKE_MODULE_PATH "${PROJECT_SOURCE_DIR}/share/cmake-2.8/Modules")

# get
#INCLUDE(FindSubversion)
#Subversion_WC_INFO(${PROJECT_SOURCE_DIR} SVN)


# setup CPack
#INCLUDE(InstallRequiredSystemLibraries)
SET(CPACK_PACKAGE_CONTACT "Patrick Charrier <patrick.charrier@stud.tu-darmstadt.de>")
SET(CPACK_PACKAGE_DESCRIPTION "Gaalop Precompiler (GPC) compiles source files containing Geometric Algebra expressions expressed in CLUCalc into C++-compatible object files.")
SET(CPACK_DEBIAN_PACKAGE_DEPENDS "cmake, java-common")
SET(CPACK_PACKAGE_VENDOR "The Gaalop Team")
SET(CPACK_PACKAGE_VERSION_MAJOR 2)
SET(CPACK_PACKAGE_VERSION_MINOR 0)
#SET(CPACK_PACKAGE_VERSION_PATCH ${SVN_WC_REVISION})
SET(CPACK_RESOURCE_FILE_LICENSE "${PROJECT_SOURCE_DIR}/LICENSE.txt")
INCLUDE(CPack)

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
ADD_SUBDIRECTORY(share)

# build
add_custom_target(gaalop ALL COMMENT "Compiling Gaalop.")
add_custom_command(TARGET gaalop COMMAND ${MVN_BINARY} -DupdateOnly=true -Dmaven.test.skip=true package assembly:directory WORKING_DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR})
add_custom_target(mvn_clean ALL COMMENT "Maven Clean Gaalop.")
add_custom_command(TARGET mvn_clean COMMAND ${MVN_BINARY} clean WORKING_DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR})
add_dependencies(gaalop mvn_clean)
# copy
COPY_DIRECTORY(include-copy include include)
COPY_DIRECTORY(gaalop-copy "target/gaalop*" "share/gpc/gaalop")
COPY_DIRECTORY(dependencies-copy dependencies "share/gpc/gaalop/plugins")
IF(TARGET gaalop-copy)
	ADD_DEPENDENCIES(gaalop-copy gaalop)
	ADD_DEPENDENCIES(dependencies-copy gaalop-copy)
ENDIF(TARGET gaalop-copy)

# find paths
FILE(GLOB GAALOP_BUILD_TARGET_DIR_LIST "${CMAKE_CURRENT_SOURCE_DIR}/target/gaalop*")
IF(GAALOP_BUILD_TARGET_DIR_LIST)
	LIST(GET GAALOP_BUILD_TARGET_DIR_LIST 0 GAALOP_BUILD_TARGET_DIR)
ENDIF(GAALOP_BUILD_TARGET_DIR_LIST)

# install
IF(GAALOP_BUILD_TARGET_DIR)
	INSTALL(DIRECTORY "${GAALOP_BUILD_TARGET_DIR}/." DESTINATION share/gpc/gaalop PATTERN ".svn" EXCLUDE)
ENDIF(GAALOP_BUILD_TARGET_DIR)
INSTALL(DIRECTORY "dependencies/." DESTINATION share/gpc/gaalop/plugins PATTERN ".svn" EXCLUDE)

# set root dir
SET(GPC_ROOT_DIR "${CMAKE_CURRENT_BINARY_DIR}" CACHE PATH "Gaalop Compiler Driver root directory" FORCE)

# build and install helper library
ADD_SUBDIRECTORY(include)
ADD_SUBDIRECTORY(lib)

# run tests
OPTION(BUILD_WITH_TESTS "build with tests")
IF(BUILD_WITH_TESTS)
	ADD_SUBDIRECTORY(test)
ENDIF(BUILD_WITH_TESTS)

# install tests
INSTALL(DIRECTORY test DESTINATION share/gpc PATTERN ".svn" EXCLUDE)

# package upload
FILE(GLOB PACKAGES
	"${CMAKE_CURRENT_BINARY_DIR}/*.deb"
	"${CMAKE_CURRENT_BINARY_DIR}/*.rpm"
	"${CMAKE_CURRENT_BINARY_DIR}/*.tar.gz"
	"${CMAKE_CURRENT_BINARY_DIR}/*.exe"
	"${CMAKE_CURRENT_BINARY_DIR}/*.sh")
FOREACH(PACKAGE ${PACKAGES})
	MESSAGE(${PACKAGE})
	#FILE(UPLOAD ${PACKAGE} "sftp://gaalop.de:3")
ENDFOREACH(PACKAGE ${PACKAGES})
