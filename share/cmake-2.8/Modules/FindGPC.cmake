CMAKE_MINIMUM_REQUIRED(VERSION 2.6)

# options
OPTION(GPC_WITH_MAPLE "wether to use the maple plugin or not." OFF)
OPTION(GPC_USE_GAPP "wether to use Geometric Algebra Parallelism Programs (GAPP) language or not." OFF)

OPTION(GPC_ALGEBRA_USEPRECALCUTEDTABLES "wether to use precalculated algebra tables or not." ON)
OPTION(GPC_ALGEBRA_USEBUILTINTABLES "wether to use built-in algebra tables or not." ON)
SET(GPC_ALGEBRA_BASEDIRECTORY "algebra/5d/" CACHE STRING "algebra base directory")
SET(GPC_ALGEBRA_USERMACROFILEPATH "" CACHE STRING "user macro file path")

# find java
FIND_PACKAGE(Java COMPONENTS Runtime REQUIRED)

# find
IF(GPC_WITH_MAPLE)
FIND_PATH(MAPLE_BIN_DIR HINTS "C:/Program Files (x86)/Maple 12/bin.win" "/opt/maple13/bin" CACHE PATH "Maple Binary Dir")
ELSE(GPC_WITH_MAPLE)
OPTION(GPC_WITH_MAXIMA "whether to use the maxima in tba plugin or not." OFF)
IF(GPC_WITH_MAXIMA)
FIND_PROGRAM(MAXIMA_BIN NAMES "maxima" "maxima.sh" "maxima.bat" HINTS "C:/Program Files/Maxima" CACHE PATH "Maxima binary path")
ENDIF(GPC_WITH_MAXIMA)
ENDIF(GPC_WITH_MAPLE)

FIND_PATH(GPC_ROOT_DIR share PATH_SUFFIXES GaalopCompilerDriver 0.1.1
          DOC "Gaalop Precompiler root directory")
FIND_FILE(GPC_JAR starter-1.0.0.jar "${GPC_ROOT_DIR}/share/gpc/gaalop" DOC "Gaalop GPC")
FIND_LIBRARY(GPC_LIBRARY gpc HINTS "${GPC_ROOT_DIR}/lib" DOC "GPC helper library")
get_filename_component(GPC_JAR_DIR ${GPC_JAR} PATH)
FIND_PATH(GPC_INCLUDE_DIR NAMES gpc.h HINTS "${GPC_ROOT_DIR}/include")
INCLUDE_DIRECTORIES(${GPC_INCLUDE_DIR})

# define common args
IF(GPC_WITH_MAPLE)
SET(GPC_COMMON_ARGS -optimizer "de.gaalop.maple.Plugin" -m "${MAPLE_BIN_DIR}")
SET(GPC_EXTOPT_ARGS -m "${MAPLE_BIN_DIR}")
ELSEIF(GPC_WITH_MAXIMA)
SET(GPC_COMMON_ARGS -optimizer "de.gaalop.tba.Plugin" -m "${MAXIMA_BIN}")
SET(GPC_EXTOPT_ARGS -m "${MAXIMA_BIN}")
ELSE(GPC_WITH_MAPLE)
SET(GPC_COMMON_ARGS -optimizer "de.gaalop.tba.Plugin")
ENDIF(GPC_WITH_MAPLE)

IF(GPC_ALGEBRA_USEPRECALCUTEDTABLES)
SET(GPC_COMMON_ARGS ${GPC_COMMON_ARGS} -algebra_usePrecalulatedTables)
ENDIF(GPC_ALGEBRA_USEPRECALCUTEDTABLES)
IF(GPC_ALGEBRA_USEBUILTINTABLES)
SET(GPC_COMMON_ARGS ${GPC_COMMON_ARGS} -algebra_useBuiltInFiles)
ENDIF(GPC_ALGEBRA_USEBUILTINTABLES)
SET(GPC_COMMON_ARGS ${GPC_COMMON_ARGS} -algebra_baseDirectory "${GPC_ALGEBRA_BASEDIRECTORY}")
IF(NOT GPC_ALGEBRA_USERMACROFILEPATH STREQUAL "")
SET(GPC_COMMON_ARGS ${GPC_COMMON_ARGS} -algebra_userMacroFilePath "${GPC_ALGEBRA_USERMACROFILEPATH}")
ENDIF(NOT GPC_ALGEBRA_USERMACROFILEPATH STREQUAL "")

# define specific args
SET(GPC_CXX_ARGS ${GPC_COMMON_ARGS} -generator "de.gaalop.compressed.Plugin" -pragmas)
SET(GPC_CUDA_ARGS ${GPC_COMMON_ARGS} -generator "de.gaalop.compressed.Plugin" -pragmas)
IF(GPC_USE_GAPP)
SET(GPC_OPENCL_ARGS ${GPC_EXTOPT_ARGS} -optimizer "de.gaalop.gapp.Plugin" -generator "de.gaalop.gappopencl.Plugin")
ELSE(GPC_USE_GAPP)
SET(GPC_OPENCL_ARGS ${GPC_COMMON_ARGS} -generator "de.gaalop.compressed.Plugin")
ENDIF(GPC_USE_GAPP)
SET(GPC_JAVA_ARGS ${GPC_COMMON_ARGS} -generator "de.gaalop.compressed.Plugin")

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
MACRO(GPC_WRAP_SRCS generated_files)
	UNSET(${generated_files}) # actually needed

	FOREACH(source_file ${ARGN})
	    # check for headers
		get_source_file_property(is_header ${source_file} HEADER_FILE_ONLY)
		
		IF(${source_file} MATCHES ".*\\.cpg$" AND NOT is_header)
			get_filename_component(source_file_name ${source_file} NAME)
			SET(generated_file "${CMAKE_CURRENT_BINARY_DIR}/${source_file_name}.cpp")

			ADD_CUSTOM_COMMAND(OUTPUT ${generated_file}
			                COMMAND ${GPC_COMPILE_SCRIPT}
			                ARGS ${GPC_CXX_ARGS} -o "${generated_file}" -i "${source_file}"
					        MAIN_DEPENDENCY ${source_file})
			LIST(APPEND ${generated_files} ${generated_file}) # needed here, to exclude non-gpc files
		ELSEIF(${source_file} MATCHES ".*\\.cug$" AND NOT is_header)
			get_filename_component(source_file_name ${source_file} NAME)
			SET(generated_file "${CMAKE_CURRENT_BINARY_DIR}/${source_file_name}.cu")

			ADD_CUSTOM_COMMAND(OUTPUT ${generated_file}
			                COMMAND ${GPC_COMPILE_SCRIPT}
			                ARGS ${GPC_CUDA_ARGS} -o "${generated_file}" -i "${source_file}"
					        MAIN_DEPENDENCY ${source_file})
			LIST(APPEND ${generated_files} ${generated_file}) # needed here, to exclude non-gpc files
		ELSEIF(${source_file} MATCHES ".*\\.clg$" AND NOT is_header)
			get_filename_component(source_file_name ${source_file} NAME)
			SET(generated_file "${CMAKE_CURRENT_BINARY_DIR}/${source_file_name}.cl")

			SET_SOURCE_FILES_PROPERTIES(${generated_file}
        	  				    PROPERTIES EXTERNAL_OBJECT true)

			ADD_CUSTOM_COMMAND(OUTPUT ${generated_file}
			                COMMAND ${GPC_COMPILE_SCRIPT}
			                ARGS ${GPC_OPENCL_ARGS} -o "${generated_file}" -i "${source_file}"
					        MAIN_DEPENDENCY ${source_file})
			LIST(APPEND ${generated_files} ${generated_file}) # needed here, to exclude non-gpc files
		ELSEIF(${source_file} MATCHES ".*\\.jgp$" AND NOT is_header)
			get_filename_component(source_file_name ${source_file} NAME)
			SET(generated_file "${CMAKE_CURRENT_BINARY_DIR}/${source_file_name}.java")

			SET_SOURCE_FILES_PROPERTIES(${generated_file}
        	  				    PROPERTIES EXTERNAL_OBJECT true)

			ADD_CUSTOM_COMMAND(OUTPUT ${generated_file}
			                COMMAND ${GPC_COMPILE_SCRIPT}
			                ARGS ${GPC_JAVA_ARGS} -o "${generated_file}" -i "${source_file}"
					        MAIN_DEPENDENCY ${source_file})
			LIST(APPEND ${generated_files} ${generated_file}) # needed here, to exclude non-GPC files
		ENDIF(${source_file} MATCHES ".*\\.cpg$" AND NOT is_header)
	ENDFOREACH(source_file)
ENDMACRO(GPC_WRAP_SRCS)

MACRO(GPC_CXX_ADD_LIBRARY target)
    #UNSET(src)
    FILE(GLOB src ${ARGN})
    GPC_WRAP_SRCS(generated_files ${src})
    ADD_LIBRARY(${target} ${generated_files} ${src}) # need to add src, so that normal files get compiled too
    TARGET_LINK_LIBRARIES(${target} ${GPC_LIBRARY})
ENDMACRO(GPC_CXX_ADD_LIBRARY)

MACRO(GPC_CXX_ADD_EXECUTABLE target)
    #UNSET(src)
    FILE(GLOB src ${ARGN})
    GPC_WRAP_SRCS(generated_files ${src})
    ADD_EXECUTABLE(${target} ${generated_files} ${src}) # need to add src, so that normal files get compiled too
    TARGET_LINK_LIBRARIES(${target} ${GPC_LIBRARY})
ENDMACRO(GPC_CXX_ADD_EXECUTABLE)

MACRO(GPC_CUDA_ADD_LIBRARY target)
    #UNSET(src)
    FILE(GLOB src ${ARGN})
    GPC_WRAP_SRCS(generated_files ${src})
    CUDA_ADD_LIBRARY(${target} ${generated_files} ${src}) # need to add src, so that normal files get compiled too
    TARGET_LINK_LIBRARIES(${target} ${GPC_LIBRARY})
ENDMACRO(GPC_CUDA_ADD_LIBRARY)

MACRO(GPC_CUDA_ADD_EXECUTABLE target)
    #UNSET(src)
    FILE(GLOB src ${ARGN})
    GPC_WRAP_SRCS(generated_files ${src})
    CUDA_ADD_EXECUTABLE(${target} ${generated_files} ${src}) # need to add src, so that normal files get compiled too
    TARGET_LINK_LIBRARIES(${target} ${GPC_LIBRARY})
ENDMACRO(GPC_CUDA_ADD_EXECUTABLE)

MACRO(GPC_OPENCL_ADD_LIBRARY target)
    #UNSET(src)
    FILE(GLOB src ${ARGN})
    GPC_WRAP_SRCS(generated_files ${src})
    ADD_LIBRARY(${target} ${generated_files} ${src}) # need to add src, so that normal files get compiled too
    SET_TARGET_PROPERTIES(${target} PROPERTIES LINKER_LANGUAGE CXX)
    TARGET_LINK_LIBRARIES(${target} ${OPENCL_LIBRARIES} ${GPC_LIBRARY})
ENDMACRO(GPC_OPENCL_ADD_LIBRARY)

MACRO(GPC_OPENCL_ADD_EXECUTABLE target)
    #UNSET(src)
    FILE(GLOB src ${ARGN})
    GPC_WRAP_SRCS(generated_files ${src})
    ADD_EXECUTABLE(${target} ${generated_files} ${src}) # need to add src, so that normal files get compiled too
    SET_TARGET_PROPERTIES(${target} PROPERTIES LINKER_LANGUAGE CXX)
    TARGET_LINK_LIBRARIES(${target} ${OPENCL_LIBRARIES} ${GPC_LIBRARY})
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
