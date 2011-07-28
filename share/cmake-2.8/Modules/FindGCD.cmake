CMAKE_MINIMUM_REQUIRED(VERSION 2.6)

# options
OPTION(WITH_MAPLE "wether to use the maple plugin or not." ON)

# find java
FIND_PACKAGE(Java COMPONENTS Runtime REQUIRED)

# find
IF(WITH_MAPLE)
FIND_PATH(MAPLE_BIN_DIR HINTS "C:/Program Files (x86)/Maple 12/bin.win" "/opt/maple13/bin" CACHE PATH "Maple Binary Dir")
ENDIF(WITH_MAPLE)
FIND_PATH(GCD_ROOT_DIR share
          DOC "Gaalop Compiler Driver root directory")
FIND_FILE(GCD_JAR starter-1.0.0.jar "${GCD_ROOT_DIR}/share/gcd/gaalop" DOC "Gaalop GCD")
FIND_LIBRARY(GCD_LIBRARY gcd HINTS "${GCD_ROOT_DIR}/lib" DOC "GCD helper library")
get_filename_component(GCD_JAR_DIR ${GCD_JAR} PATH)
FIND_PATH(GCD_INCLUDE_DIR NAMES gcd.h HINTS "${GCD_ROOT_DIR}/include")
INCLUDE_DIRECTORIES(${GCD_INCLUDE_DIR})

# define common args
IF(WITH_MAPLE)
SET(GCD_COMMON_ARGS -optimizer "de.gaalop.maple.Plugin" -m "${MAPLE_BIN_DIR}")
ELSE(WITH_MAPLE)
SET(GCD_COMMON_ARGS -optimizer "de.gaalop.tba.Plugin")
ENDIF(WITH_MAPLE)

# define specific args
SET(GCD_CXX_ARGS ${GCD_COMMON_ARGS} -generator "de.gaalop.compressed.Plugin")
SET(GCD_CUDA_ARGS ${GCD_COMMON_ARGS} -generator "de.gaalop.compressed.Plugin")
SET(GCD_OPENCL_ARGS ${GCD_COMMON_ARGS} -generator "de.gaalop.cpp.Plugin")
SET(GCD_JAVA_ARGS ${GCD_COMMON_ARGS} -generator "de.gaalop.cpp.Plugin")

# configure compile script
SET(GCD_COMPILE_SCRIPT "${CMAKE_CURRENT_BINARY_DIR}/run_gcd.sh")
get_filename_component(CMAKE_CURRENT_LIST_DIR "${CMAKE_CURRENT_LIST_FILE}" PATH)
CONFIGURE_FILE("${CMAKE_CURRENT_LIST_DIR}/run_gcd.sh.in" ${GCD_COMPILE_SCRIPT})

# custom command to compile gcd source files
MACRO(GCD_WRAP_SRCS generated_files)
	SET(generated_files "")

	FOREACH(source_file ${ARGN})
	    # check for headers
		get_source_file_property(is_header ${source_file} HEADER_FILE_ONLY)
		
		IF(${source_file} MATCHES ".*\\.gcp$" AND NOT is_header)
			get_filename_component(source_file_name ${source_file} NAME)
			SET(generated_file "${CMAKE_CURRENT_BINARY_DIR}/${source_file_name}.cpp")

			ADD_CUSTOM_COMMAND(OUTPUT ${generated_file}
			                COMMAND ${GCD_COMPILE_SCRIPT}
			                ARGS ${GCD_CXX_ARGS} -o "${generated_file}" -i "${source_file}"
					        MAIN_DEPENDENCY ${source_file})
		ELSEIF(${source_file} MATCHES ".*\\.gcu$" AND NOT is_header)
			get_filename_component(source_file_name ${source_file} NAME)
			SET(generated_file "${CMAKE_CURRENT_BINARY_DIR}/${source_file_name}.cu")

			ADD_CUSTOM_COMMAND(OUTPUT ${generated_file}
			                COMMAND ${GCD_COMPILE_SCRIPT}
			                ARGS ${GCD_CXX_ARGS} -o "${generated_file}" -i "${source_file}"
					        MAIN_DEPENDENCY ${source_file})
		ELSEIF(${source_file} MATCHES ".*\\.gcl$" AND NOT is_header)
			get_filename_component(source_file_name ${source_file} NAME)
			SET(generated_file "${CMAKE_CURRENT_BINARY_DIR}/${source_file_name}.cl")

			SET_SOURCE_FILES_PROPERTIES(${generated_file}
        	  				    PROPERTIES EXTERNAL_OBJECT true)

			ADD_CUSTOM_COMMAND(OUTPUT ${generated_file}
			                COMMAND ${GCD_COMPILE_SCRIPT}
			                ARGS ${GCD_CXX_ARGS} -o "${generated_file}" -i "${source_file}"
					        MAIN_DEPENDENCY ${source_file})
		ELSEIF(${source_file} MATCHES ".*\\.gcj$" AND NOT is_header)
			get_filename_component(source_file_name ${source_file} NAME)
			SET(generated_file "${CMAKE_CURRENT_BINARY_DIR}/${source_file_name}.java")

			SET_SOURCE_FILES_PROPERTIES(${generated_file}
        	  				    PROPERTIES EXTERNAL_OBJECT true)

			ADD_CUSTOM_COMMAND(OUTPUT ${generated_file}
			                COMMAND ${GCD_COMPILE_SCRIPT}
			                ARGS ${GCD_CXX_ARGS} -o "${generated_file}" -i "${source_file}"
					        MAIN_DEPENDENCY ${source_file})
		ENDIF(${source_file} MATCHES ".*\\.gcp$" AND NOT is_header)
		
		LIST(APPEND generated_files ${generated_file})
	ENDFOREACH(source_file)
ENDMACRO(GCD_WRAP_SRCS)

MACRO(GCD_CXX_ADD_LIBRARY target)
    FILE(GLOB src ${ARGN})
    GCD_WRAP_SRCS(generated_files ${src})
    ADD_LIBRARY(${target} ${generated_files})
    TARGET_LINK_LIBRARIES(${target} ${GCD_LIBRARY})
ENDMACRO(GCD_CXX_ADD_LIBRARY)

MACRO(GCD_CXX_ADD_EXECUTABLE target)
    FILE(GLOB src ${ARGN})
    GCD_WRAP_SRCS(generated_files ${src})
    ADD_EXECUTABLE(${target} ${generated_files})
    TARGET_LINK_LIBRARIES(${target} ${GCD_LIBRARY})
ENDMACRO(GCD_CXX_ADD_EXECUTABLE)

MACRO(GCD_CUDA_ADD_LIBRARY target)
    FILE(GLOB src ${ARGN})
    GCD_WRAP_SRCS(generated_files ${src})
    CUDA_ADD_LIBRARY(${target} ${generated_files})
    TARGET_LINK_LIBRARIES(${target} ${GCD_LIBRARY})
ENDMACRO(GCD_CUDA_ADD_LIBRARY)

MACRO(GCD_CUDA_ADD_EXECUTABLE target)
    FILE(GLOB src ${ARGN})
    GCD_WRAP_SRCS(generated_files ${src})
    CUDA_ADD_EXECUTABLE(${target} ${generated_files})
    TARGET_LINK_LIBRARIES(${target} ${GCD_LIBRARY})
ENDMACRO(GCD_CUDA_ADD_EXECUTABLE)

MACRO(GCD_OPENCL_ADD_LIBRARY target)
    FILE(GLOB src ${ARGN})
    GCD_WRAP_SRCS(generated_files ${src})
    ADD_LIBRARY(${target} ${generated_files})
    SET_TARGET_PROPERTIES(${target} PROPERTIES LINKER_LANGUAGE CXX)
    TARGET_LINK_LIBRARIES(${target} ${OPENCL_LIBRARIES} ${GCD_LIBRARY})
ENDMACRO(GCD_OPENCL_ADD_LIBRARY)

MACRO(GCD_OPENCL_ADD_EXECUTABLE target)
    FILE(GLOB src ${ARGN})
    GCD_WRAP_SRCS(generated_files ${src})
    ADD_EXECUTABLE(${target} ${generated_files})
    SET_TARGET_PROPERTIES(${target} PROPERTIES LINKER_LANGUAGE CXX)
    TARGET_LINK_LIBRARIES(${target} ${OPENCL_LIBRARIES} ${GCD_LIBRARY})
ENDMACRO(GCD_OPENCL_ADD_EXECUTABLE)

MACRO(GCD_JAVA_ADD_LIBRARY target)
    FILE(GLOB src ${ARGN})
    GCD_WRAP_SRCS(generated_files ${src})
ENDMACRO(GCD_JAVA_ADD_LIBRARY)

MACRO(GCD_JAVA_ADD_EXECUTABLE target)
    FILE(GLOB src ${ARGN})
    GCD_WRAP_SRCS(generated_files ${src})
ENDMACRO(GCD_JAVA_ADD_EXECUTABLE)
