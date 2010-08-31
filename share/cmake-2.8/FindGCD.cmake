CMAKE_MINIMUM_REQUIRED(VERSION 2.8)

FIND_PACKAGE(CUDA)

# find
FIND_PATH(GCD_ROOT_DIR
          NAMES bin/gcd-cpp bin/gcd-cuda include/gcd.h
          DOC "Gaalop Compiler Driver root directory")
FIND_LIBRARY(GCD_LIBRARY gcd HINTS "${GCD_ROOT_DIR}/lib")

# determine system-specific call convention
IF(WIN32)
SET(CGD_CPP_COMMAND "gcd-cpp")
SET(CGD_CUDA_COMMAND "gcd-cuda")
ELSE(WIN32)
SET(CGD_CPP_COMMAND "./gcd-cpp")
SET(CGD_CUDA_COMMAND "./gcd-cuda")
ENDIF(WIN32)

# include macro
MACRO(GCD_INCLUDE_DIRECTORIES directory)
ENDMACRO(GCD_INCLUDE_DIRECTORIES)

# create custom command to compile gcd source files
MACRO(GCD_WRAP_SRCS generated_files gcd_command)
	SET(generated_files "")
	FOREACH(source_file ${ARGN})
		get_filename_component(source_file_name ${source_file} NAME)
		SET(generated_file "${CMAKE_CURRENT_BINARY_DIR}/${source_file_name}.o")

		SET_SOURCE_FILES_PROPERTIES(${generated_file}
          				    PROPERTIES EXTERNAL_OBJECT true)

		# careful here!
		ADD_CUSTOM_COMMAND(OUTPUT ${generated_file}
				   COMMAND ${CMAKE_COMMAND} -E chdir "${GCD_ROOT_DIR}/bin" "${gcd_command}"
				   ARGS ${CMAKE_CXX_FLAGS} "-I${GCD_ROOT_DIR}/include" "-c" "-o" "${generated_file}" "${source_file}"
				   MAIN_DEPENDENCY ${source_file})
		LIST(APPEND ${generated_files} ${generated_file})
	ENDFOREACH(source_file)
ENDMACRO(GCD_WRAP_SRCS)

MACRO(GCD_CPP_ADD_LIBRARY target)
    FILE(GLOB src ${ARGN})
    GCD_WRAP_SRCS(generated_files ${CGD_CPP_COMMAND} ${src})
    ADD_LIBRARY(${target} ${generated_files} ${src})
    SET_TARGET_PROPERTIES(${target} PROPERTIES LINKER_LANGUAGE CXX)
    TARGET_LINK_LIBRARIES(${target} ${GCD_LIBRARY})
ENDMACRO(GCD_CPP_ADD_LIBRARY)

MACRO(GCD_CPP_ADD_EXECUTABLE target)
    FILE(GLOB src ${ARGN})
    GCD_WRAP_SRCS(generated_files ${CGD_CPP_COMMAND} ${src})
    ADD_EXECUTABLE(${target} ${generated_files} ${src})
    SET_TARGET_PROPERTIES(${target} PROPERTIES LINKER_LANGUAGE CXX)
    TARGET_LINK_LIBRARIES(${target} ${GCD_LIBRARY})
ENDMACRO(GCD_CPP_ADD_EXECUTABLE)

MACRO(GCD_CUDA_ADD_LIBRARY target)
    FILE(GLOB src ${ARGN})
    GCD_WRAP_SRCS(generated_files ${CGD_CUDA_COMMAND} ${src})
    CUDA_ADD_LIBRARY(${target} ${generated_files} ${src})
    SET_TARGET_PROPERTIES(${target} PROPERTIES LINKER_LANGUAGE CXX)
    TARGET_LINK_LIBRARIES(${target} ${GCD_LIBRARY})
ENDMACRO(GCD_CUDA_ADD_LIBRARY)

MACRO(GCD_CUDA_ADD_EXECUTABLE target)
    FILE(GLOB src ${ARGN})
    GCD_WRAP_SRCS(generated_files ${CGD_CUDA_COMMAND} ${src})
    CUDA_ADD_EXECUTABLE(${target} ${generated_files} ${src})
    SET_TARGET_PROPERTIES(${target} PROPERTIES LINKER_LANGUAGE CXX)
    TARGET_LINK_LIBRARIES(${target} ${GCD_LIBRARY})
ENDMACRO(GCD_CUDA_ADD_EXECUTABLE)
