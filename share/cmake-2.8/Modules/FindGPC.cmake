CMAKE_MINIMUM_REQUIRED(VERSION 2.6)

# options
OPTION(GPC_WITH_MAPLE "whether to use the maple plugin or not." OFF)
OPTION(GPC_USE_GAPP "whether to use Geometric Algebra Parallelism Programs (GAPP) language or not." OFF)
OPTION(GPC_USE_DOUBLE "whether to use double precision floating point format or not." OFF)

SET(GPC_ALGEBRA_NAME "cga" CACHE STRING "algebra name")
SET(GPC_ALGEBRA_BASEDIRECTORY "" CACHE STRING "algebra base directory")

# find java
FIND_PACKAGE(Java COMPONENTS Runtime REQUIRED)
IF(NOT Java_JAVA_EXECUTABLE)
	SET(Java_JAVA_EXECUTABLE ${JAVA_RUNTIME})
ENDIF(NOT Java_JAVA_EXECUTABLE)

# find
IF(GPC_WITH_MAPLE)
	FIND_PATH(MAPLE_BIN_DIR HINTS "C:/Program Files (x86)/Maple 12/bin.win" "/opt/maple13/bin" CACHE PATH "Maple Binary Dir")
ELSE(GPC_WITH_MAPLE)
	OPTION(GPC_WITH_MAXIMA "whether to use the maxima in tba plugin or not." OFF)
	IF(GPC_WITH_MAXIMA)
		FIND_PROGRAM(MAXIMA_BIN NAMES "maxima" "maxima.sh" "maxima.bat" HINTS "/usr/bin/maxima" CACHE PATH "Maxima binary path")
	ENDIF(GPC_WITH_MAXIMA)
ENDIF(GPC_WITH_MAPLE)

FIND_PATH(GPC_ROOT_DIR share PATH_SUFFIXES GaalopCompilerDriver 0.1.1
          DOC "Gaalop Precompiler root directory")
FIND_FILE(GPC_JAR starter-1.0.0.jar "${GPC_ROOT_DIR}/share/gpc/gaalop" DOC "Gaalop GPC")
FIND_LIBRARY(GPC_BASE_LIBRARY gpc-base HINTS "${GPC_ROOT_DIR}/lib" DOC "GPC helper library")
get_filename_component(GPC_JAR_DIR ${GPC_JAR} PATH)
FIND_PATH(GPC_INCLUDE_DIR NAMES GPCUtils.h HINTS "${GPC_ROOT_DIR}/include")
INCLUDE_DIRECTORIES(${GPC_INCLUDE_DIR})
SET(GPC_LIBRARIES ${GPC_BASE_LIBRARY})

# define common args
SET(GPC_COMMON_ARGS --algebraName "${GPC_ALGEBRA_NAME}")
IF(NOT GPC_ALGEBRA_BASEDIRECTORY STREQUAL "")
	SET(GPC_COMMON_ARGS ${GPC_COMMON_ARGS} --algebraBaseDir "${GPC_ALGEBRA_BASEDIRECTORY}")
ENDIF(NOT GPC_ALGEBRA_BASEDIRECTORY STREQUAL "")

IF(GPC_USE_DOUBLE)
	SET(GPC_COMMON_ARGS ${GPC_COMMON_ARGS} --double)
ENDIF(GPC_USE_DOUBLE)

# define optimizer args
IF(GPC_WITH_MAPLE)
	SET(GPC_COMMON_ARGS ${GPC_COMMON_ARGS} -m "${MAPLE_BIN_DIR}")
	SET(GPC_COMMON_ARGS ${GPC_COMMON_ARGS} --optimizationPlugin "de.gaalop.maple.Plugin")
ELSE(GPC_WITH_MAPLE)
	# Maxima works commonly
	IF(GPC_WITH_MAXIMA)
		SET(GPC_COMMON_ARGS ${GPC_COMMON_ARGS} -m "${MAXIMA_BIN}")
	ENDIF(GPC_WITH_MAXIMA)

	# GAPP only works with OpenCL
	IF(GPC_USE_GAPP)
		SET(GPC_GAPP_ARGS ${GPC_COMMON_ARGS} --optimizationPlugin "de.gaalop.gapp.Plugin")
	ENDIF(GPC_USE_GAPP)

	# TBA works commonly
	SET(GPC_COMMON_ARGS ${GPC_COMMON_ARGS} --optimizationPlugin "de.gaalop.tba.Plugin")
ENDIF(GPC_WITH_MAPLE)

# define target specific args
SET(GPC_CXX_ARGS ${GPC_COMMON_ARGS} --codeGeneratorPlugin "de.gaalop.compressed.Plugin" --pragmas)
SET(GPC_CUDA_ARGS ${GPC_COMMON_ARGS} --codeGeneratorPlugin "de.gaalop.compressed.Plugin" --pragmas)
IF(GPC_USE_GAPP)
	SET(GPC_OPENCL_ARGS ${GPC_GAPP_ARGS} --codeGeneratorPlugin "de.gaalop.gappopencl.Plugin")
ELSE(GPC_USE_GAPP)
	SET(GPC_OPENCL_ARGS ${GPC_COMMON_ARGS} --codeGeneratorPlugin "de.gaalop.compressed.Plugin")
ENDIF(GPC_USE_GAPP)
SET(GPC_JAVA_ARGS ${GPC_COMMON_ARGS} --codeGeneratorPlugin "de.gaalop.compressed.Plugin")

# configure compile script
get_filename_component(CMAKE_CURRENT_LIST_DIR "${CMAKE_CURRENT_LIST_FILE}" PATH)
IF(WIN32 AND NOT UNIX)
    IF(MAPLE_BIN_DIR)
        FILE(TO_NATIVE_PATH ${MAPLE_BIN_DIR} MAPLE_BIN_DIR_NATIVE)
    ELSE(MAPLE_BIN_DIR)
        SET(MAPLE_BIN_DIR_NATIVE .)
    ENDIF(MAPLE_BIN_DIR)
    FILE(TO_NATIVE_PATH ${GPC_JAR_DIR} GPC_JAR_DIR_NATIVE)
    FILE(TO_NATIVE_PATH ${Java_JAVA_EXECUTABLE} Java_JAVA_EXECUTABLE_NATIVE)
    SET(GPC_COMPILE_SCRIPT "${CMAKE_CURRENT_BINARY_DIR}/run_gpc.bat")
    CONFIGURE_FILE("${CMAKE_CURRENT_LIST_DIR}/run_gpc.bat.in" ${GPC_COMPILE_SCRIPT})
ELSE(WIN32 AND NOT UNIX)
    SET(GPC_COMPILE_SCRIPT "${CMAKE_CURRENT_BINARY_DIR}/run_gpc.sh")
    CONFIGURE_FILE("${CMAKE_CURRENT_LIST_DIR}/run_gpc.sh.in" ${GPC_COMPILE_SCRIPT})
ENDIF(WIN32 AND NOT UNIX)

# custom command to compile gpc source files
MACRO(GPC_WRAP_SRCS src_unmodified generated_link_files generated_unlink_files)
	UNSET(${src_unmodified}) # actually needed
	UNSET(${generated_link_files}) # actually needed
	UNSET(${generated_unlink_files}) # actually needed

	FOREACH(source_file ${ARGN})
	    # check for headers
		get_source_file_property(is_header ${source_file} HEADER_FILE_ONLY)
		
		IF(${source_file} MATCHES ".*\\.cpg$" AND NOT is_header)
			get_filename_component(source_file_name ${source_file} NAME)
			SET(generated_file "${CMAKE_CURRENT_BINARY_DIR}/${source_file_name}.cpp")

			ADD_CUSTOM_COMMAND(OUTPUT ${generated_file}
			                COMMAND ${GPC_COMPILE_SCRIPT}
			                ARGS ${GPC_CXX_ARGS} --of "${generated_file}" -i "${source_file}"
					        MAIN_DEPENDENCY ${source_file})
			LIST(APPEND ${generated_link_files} ${generated_file}) # needed here, to exclude non-gpc files
		ELSEIF(${source_file} MATCHES ".*\\.cug$" AND NOT is_header)
			get_filename_component(source_file_name ${source_file} NAME)
			SET(generated_file "${CMAKE_CURRENT_BINARY_DIR}/${source_file_name}.cu")

			ADD_CUSTOM_COMMAND(OUTPUT ${generated_file}
			                COMMAND ${GPC_COMPILE_SCRIPT}
			                ARGS ${GPC_CUDA_ARGS} --of "${generated_file}" -i "${source_file}"
					        MAIN_DEPENDENCY ${source_file})
			LIST(APPEND ${generated_link_files} ${generated_file})
		ELSEIF(${source_file} MATCHES ".*\\.clg$" AND NOT is_header)
			get_filename_component(source_file_name ${source_file} NAME)
			SET(generated_file "${CMAKE_CURRENT_BINARY_DIR}/${source_file_name}.cl")

			SET_SOURCE_FILES_PROPERTIES(${generated_file}
        	  				    PROPERTIES EXTERNAL_OBJECT true)

			ADD_CUSTOM_COMMAND(OUTPUT ${generated_file}
			                COMMAND ${GPC_COMPILE_SCRIPT}
			                ARGS ${GPC_OPENCL_ARGS} --of "${generated_file}" -i "${source_file}"
					        MAIN_DEPENDENCY ${source_file})
			LIST(APPEND ${generated_unlink_files} ${generated_file})
		ELSEIF(${source_file} MATCHES ".*\\.jgp$" AND NOT is_header)
			get_filename_component(source_file_name ${source_file} NAME)
			SET(generated_file "${CMAKE_CURRENT_BINARY_DIR}/${source_file_name}.java")

			SET_SOURCE_FILES_PROPERTIES(${generated_file}
        	  				    PROPERTIES EXTERNAL_OBJECT true)

			ADD_CUSTOM_COMMAND(OUTPUT ${generated_file}
			                COMMAND ${GPC_COMPILE_SCRIPT}
			                ARGS ${GPC_JAVA_ARGS} --of "${generated_file}" -i "${source_file}"
					        MAIN_DEPENDENCY ${source_file})
			LIST(APPEND ${generated_unlink_files} ${generated_file})
		ELSE()
			LIST(APPEND ${src_unmodified} ${source_file})
		ENDIF()
	ENDFOREACH(source_file)
ENDMACRO(GPC_WRAP_SRCS)

MACRO(GPC_ADD_LIBRARY target)
	CMAKE_PARSE_ARGUMENTS(
		ARG
		""
		"SHARED"
		${ARGN}
    )

    #UNSET(src)
    FILE(GLOB src ${ARGN})
    GPC_WRAP_SRCS(src_unmodified generated_link_files generated_unlink_files ${src})
	SET(all_src ${src_unmodified} ${generated_link_files})
    ADD_LIBRARY(${target} ${ARG_SHARED} ${all_src})
	SET_TARGET_PROPERTIES(${target} PROPERTIES LINKER_LANGUAGE CXX)
	IF(GPC_LIBRARIES)
		TARGET_LINK_LIBRARIES(${target} ${GPC_LIBRARIES})
	ENDIF(GPC_LIBRARIES)
ENDMACRO(GPC_ADD_LIBRARY)

MACRO(GPC_ADD_EXECUTABLE target)
	CMAKE_PARSE_ARGUMENTS(
		ARG
		""
		"SHARED"
		${ARGN}
    )

    #UNSET(src)
    FILE(GLOB src ${ARGN})
    GPC_WRAP_SRCS(src_unmodified generated_link_files generated_unlink_files ${src})
	SET(all_src ${src_unmodified} ${generated_link_files})
    ADD_EXECUTABLE(${target} ${ARG_SHARED} ${all_src})
	SET_TARGET_PROPERTIES(${target} PROPERTIES LINKER_LANGUAGE CXX)
	IF(GPC_LIBRARIES)
		TARGET_LINK_LIBRARIES(${target} ${GPC_LIBRARIES})
	ENDIF(GPC_LIBRARIES)
ENDMACRO(GPC_ADD_EXECUTABLE)

MACRO(GPC_CXX_ADD_LIBRARY target lib_type)
	GPC_ADD_LIBRARY(${target} ${lib_type} ${ARGN})
ENDMACRO(GPC_CXX_ADD_LIBRARY)

MACRO(GPC_CXX_ADD_EXECUTABLE target lib_type)
	GPC_ADD_EXECUTABLE(${target} ${lib_type} ${ARGN})
ENDMACRO(GPC_CXX_ADD_EXECUTABLE)

MACRO(GPC_CUDA_ADD_LIBRARY target lib_type)
	GPC_ADD_LIBRARY(${target} ${lib_type} ${ARGN})
ENDMACRO(GPC_CUDA_ADD_LIBRARY)

MACRO(GPC_CUDA_ADD_EXECUTABLE target lib_type)
	GPC_ADD_EXECUTABLE(${target} ${lib_type} ${ARGN})
ENDMACRO(GPC_CUDA_ADD_EXECUTABLE)

MACRO(GPC_OPENCL_ADD_LIBRARY target lib_type)
	GPC_ADD_LIBRARY(${target} ${lib_type} ${ARGN})
    TARGET_LINK_LIBRARIES(${target} ${OPENCL_LIBRARIES})	
ENDMACRO(GPC_OPENCL_ADD_LIBRARY)

MACRO(GPC_OPENCL_ADD_EXECUTABLE target lib_type)
	GPC_ADD_EXECUTABLE(${target} ${lib_type} ${ARGN})
    TARGET_LINK_LIBRARIES(${target} ${OPENCL_LIBRARIES})	
ENDMACRO(GPC_OPENCL_ADD_EXECUTABLE)

MACRO(GPC_JAVA_ADD_LIBRARY target)
    FILE(GLOB src ${ARGN})
    GPC_WRAP_SRCS(generated_files ${src})
ENDMACRO(GPC_JAVA_ADD_LIBRARY)

MACRO(GPC_JAVA_ADD_EXECUTABLE target)
    FILE(GLOB src ${ARGN})
    GPC_WRAP_SRCS(generated_files ${src})
ENDMACRO(GPC_JAVA_ADD_EXECUTABLE)

SET( GPC_FOUND "NO" )
IF(GPC_JAR)
    SET( GPC_FOUND "YES" )
ENDIF(GPC_JAR)
