CMAKE_MINIMUM_REQUIRED(VERSION 2.8)

# find
FIND_PATH(GCD_ROOT_DIR
          NAMES bin/gcd-cpp bin/gcd-cuda bin/gcd-opencl include/gcd.h share/cmake-2.8/Modules/FindGCD.cmake share/gcd/maple_settings
          DOC "Gaalop Compiler Driver root directory")
FIND_PROGRAM(GCD_CPP_BINARY gcd-cpp "${GCD_ROOT_DIR}/bin" DOC "Gaalop GCD for C++")
FIND_PROGRAM(GCD_CUDA_BINARY gcd-cuda "${GCD_ROOT_DIR}/bin" DOC "Gaalop GCD for CUDA")
FIND_PROGRAM(GCD_OPENCL_BINARY gcd-opencl "${GCD_ROOT_DIR}/bin" DOC "Gaalop GCD for OpenCL")
FIND_LIBRARY(GCD_LIBRARY gcd HINTS "${GCD_ROOT_DIR}/lib" DOC "GCD helper library")

INCLUDE_DIRECTORIES(${OPENCL_INCLUDE_DIR})
SET(generated_file_ext "o")

# include macro
SET(GCD_ARGS ${CMAKE_CXX_FLAGS} "-I${GCD_ROOT_DIR}/include")
MACRO(GCD_INCLUDE_DIRECTORIES directory)
	SET(GCD_ARGS ${GCD_ARGS} "-I${directory}")
ENDMACRO(GCD_INCLUDE_DIRECTORIES)

# create custom command to compile gcd source files
MACRO(GCD_WRAP_SRCS generated_files)
	SET(generated_files "")
	FOREACH(source_file ${ARGN})
		get_source_file_property(is_header ${source_file} HEADER_FILE_ONLY)
		IF(${source_file} MATCHES ".*\\.gcp$" AND NOT is_header)
			get_filename_component(source_file_name ${source_file} NAME)
			SET(generated_file "${CMAKE_CURRENT_BINARY_DIR}/${source_file_name}.${generated_file_ext}")

			SET_SOURCE_FILES_PROPERTIES(${generated_file}
        	  				    PROPERTIES EXTERNAL_OBJECT true)

			ADD_CUSTOM_COMMAND(OUTPUT ${generated_file}
					   COMMAND "${GCD_CPP_BINARY}"
					   ARGS ${GCD_ARGS} "-c" "-o" "${generated_file}" "${source_file}"
					   MAIN_DEPENDENCY ${source_file})
			#MESSAGE("${GCD_ROOT_DIR}")
			#ADD_CUSTOM_COMMAND(OUTPUT ${generated_file}
			#		   COMMAND ${CMAKE_COMMAND} -E chdir "${GCD_ROOT_DIR}/bin" "gcd-cpp"
			#		   ARGS ${GCD_ARGS} "-c" "-o" "${generated_file}" "${source_file}"
			#		   MAIN_DEPENDENCY ${source_file})
			LIST(APPEND ${generated_files} ${generated_file})
		ELSEIF(${source_file} MATCHES ".*\\.gcu$" AND NOT is_header)
			get_filename_component(source_file_name ${source_file} NAME)
			SET(generated_file "${CMAKE_CURRENT_BINARY_DIR}/${source_file_name}.${generated_file_ext}")

			SET_SOURCE_FILES_PROPERTIES(${generated_file}
        	  				    PROPERTIES EXTERNAL_OBJECT true)

			ADD_CUSTOM_COMMAND(OUTPUT ${generated_file}
					   COMMAND "${GCD_CUDA_BINARY}"
					   ARGS ${GCD_ARGS} "-c" "-o" "${generated_file}" "${source_file}"
					   MAIN_DEPENDENCY ${source_file})
			LIST(APPEND ${generated_files} ${generated_file})
		ELSEIF(${source_file} MATCHES ".*\\.gcl$" AND NOT is_header)
			get_filename_component(source_file_name ${source_file} NAME)
			SET(generated_file "${CMAKE_CURRENT_BINARY_DIR}/${source_file_name}")

			SET_SOURCE_FILES_PROPERTIES(${generated_file}
        	  				    PROPERTIES EXTERNAL_OBJECT true)

			STRING(REPLACE "/" "\\" source_file ${source_file})
			STRING(REPLACE "/" "\\" generated_file ${generated_file})
			ADD_CUSTOM_COMMAND(OUTPUT ${generated_file}
					   COMMAND "${GCD_OPENCL_BINARY}"
					   ARGS "${generated_file}" "${source_file}"
					   MAIN_DEPENDENCY ${source_file})
		ENDIF(${source_file} MATCHES ".*\\.gcp$" AND NOT is_header)
	ENDFOREACH(source_file)
ENDMACRO(GCD_WRAP_SRCS)

MACRO(GCD_CPP_ADD_LIBRARY target)
    FILE(GLOB src ${ARGN})
    GCD_WRAP_SRCS(generated_files ${src})
    ADD_LIBRARY(${target} ${generated_files} ${src})
    SET_TARGET_PROPERTIES(${target} PROPERTIES LINKER_LANGUAGE CXX)
    TARGET_LINK_LIBRARIES(${target} ${GCD_LIBRARY})
ENDMACRO(GCD_CPP_ADD_LIBRARY)

MACRO(GCD_CPP_ADD_EXECUTABLE target)
    FILE(GLOB src ${ARGN})
    GCD_WRAP_SRCS(generated_files ${src})
    ADD_EXECUTABLE(${target} ${generated_files} ${src})
    SET_TARGET_PROPERTIES(${target} PROPERTIES LINKER_LANGUAGE CXX)
    TARGET_LINK_LIBRARIES(${target} ${GCD_LIBRARY})
ENDMACRO(GCD_CPP_ADD_EXECUTABLE)

MACRO(GCD_CUDA_ADD_LIBRARY target)
    FILE(GLOB src ${ARGN})
    GCD_WRAP_SRCS(generated_files ${src})
    CUDA_ADD_LIBRARY(${target} ${generated_files} ${src})
    SET_TARGET_PROPERTIES(${target} PROPERTIES LINKER_LANGUAGE CXX)
    TARGET_LINK_LIBRARIES(${target} ${GCD_LIBRARY})
ENDMACRO(GCD_CUDA_ADD_LIBRARY)

MACRO(GCD_CUDA_ADD_EXECUTABLE target)
    FILE(GLOB src ${ARGN})
    GCD_WRAP_SRCS(generated_files ${src})
    CUDA_ADD_EXECUTABLE(${target} ${generated_files} ${src})
    SET_TARGET_PROPERTIES(${target} PROPERTIES LINKER_LANGUAGE CXX)
    TARGET_LINK_LIBRARIES(${target} ${GCD_LIBRARY})
ENDMACRO(GCD_CUDA_ADD_EXECUTABLE)

MACRO(GCD_OPENCL_ADD_LIBRARY target)
    FILE(GLOB src ${ARGN})
    GCD_WRAP_SRCS(generated_files ${src})
    ADD_LIBRARY(${target} ${generated_files} ${src})
    SET_TARGET_PROPERTIES(${target} PROPERTIES LINKER_LANGUAGE CXX)
    TARGET_LINK_LIBRARIES(${target} ${OPENCL_LIBRARIES} ${GCD_LIBRARY})
ENDMACRO(GCD_OPENCL_ADD_LIBRARY)

MACRO(GCD_OPENCL_ADD_EXECUTABLE target)
    FILE(GLOB src ${ARGN})
    GCD_WRAP_SRCS(generated_files ${src})
    ADD_EXECUTABLE(${target} ${generated_files} ${src})
    SET_TARGET_PROPERTIES(${target} PROPERTIES LINKER_LANGUAGE CXX)
    TARGET_LINK_LIBRARIES(${target} ${OPENCL_LIBRARIES} ${GCD_LIBRARY})
ENDMACRO(GCD_OPENCL_ADD_EXECUTABLE)
