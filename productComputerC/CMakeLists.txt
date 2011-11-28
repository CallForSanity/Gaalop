CMAKE_MINIMUM_REQUIRED(VERSION 2.8)
PROJECT(productComputer)

# set cmake module path
SET(CMAKE_MODULE_PATH "${PROJECT_SOURCE_DIR}/cmake")

# includes

# setup CPack
INCLUDE(InstallRequiredSystemLibraries)
SET(CPACK_PACKAGE_CONTACT "Patrick Charrier <patrick.charrier@stud.tu-darmstadt.de>")
SET(CPACK_PACKAGE_DESCRIPTION "Gaalop Compiler Driver (GCD) compiles source files containing Geometric Algebra expressions expressed in CLUCalc into C++-compatible object files.")
if( WIN32 AND NOT UNIX )
    SET(CPACK_PACKAGE_EXECUTABLES config "Configuration Tool")
endif()
INCLUDE(CPack)

# use packages
#FIND_PACKAGE(Boost COMPONENTS thread date_time system REQUIRED)

# set DEBUG define
IF("${CMAKE_BUILD_TYPE}" STREQUAL "Debug")
    ADD_DEFINITIONS(-DDEBUG)
ENDIF("${CMAKE_BUILD_TYPE}" STREQUAL "Debug")

# build main executable
FILE(GLOB_RECURSE src "*.cpp" "*.h")
ADD_EXECUTABLE(productComputer ${src})
#TARGET_LINK_LIBRARIES(productComputer)
INSTALL(TARGETS productComputer DESTINATION .)

# OpenMP settings
if(WITH_OPENMP)
  FIND_PACKAGE(OpenMP REQUIRED)
  if(OPENMP_FOUND)
    set(CMAKE_CXX_FLAGS ${CMAKE_CXX_FLAGS} ${OpenMP_CXX_FLAGS})
  endif(OPENMP_FOUND)
  if(UNIX)
    TARGET_LINK_LIBRARIES(BeeGame gomp)
    #ADD_DEFINITIONS(-D_GLIBCXX_PARALLEL) # CAUSING LINKER ERROR
  endif(UNIX)
endif(WITH_OPENMP)

# config files

# install stuff

