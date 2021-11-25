CMAKE_MINIMUM_REQUIRED(VERSION 2.9)

macro(copy_files TARGET_NAME GLOBPAT DESTINATION)
  file(GLOB COPY_FILES
    RELATIVE ${CMAKE_CURRENT_SOURCE_DIR}
    ${GLOBPAT})
  add_custom_target(${TARGET_NAME} ALL
    COMMENT "Copying files ${GLOBPAT}.")

  foreach(FILENAME ${COPY_FILES})
    SET(SRC "${CMAKE_CURRENT_SOURCE_DIR}/${FILENAME}")
    SET(DST "${CMAKE_CURRENT_BINARY_DIR}/${DESTINATION}/${FILENAME}")

    add_custom_command(
      TARGET ${TARGET_NAME}
      COMMAND ${CMAKE_COMMAND} -E copy ${SRC} ${DST}
      )
  endforeach(FILENAME)
endmacro(copy_files)

macro(copy_directory TARGET_NAME SOURCE DESTINATION)
    # In case of multiple results, take first one
    FILE(GLOB SRC_LIST "${CMAKE_CURRENT_SOURCE_DIR}/${SOURCE}")
    LIST(LENGTH SRC_LIST SRC_LIST_LEN)
    IF(SRC_LIST_LEN GREATER 0)
    LIST(GET SRC_LIST 0 SRC)
    
    SET(DST "${CMAKE_CURRENT_BINARY_DIR}/${DESTINATION}")

  add_custom_target(${TARGET_NAME} ALL
    COMMENT "Copying directory ${SOURCE} to ${DESTINATION}.")

    add_custom_command(
      TARGET ${TARGET_NAME}
      COMMAND ${CMAKE_COMMAND} -E copy_directory ${SRC} ${DST})
  ENDIF(SRC_LIST_LEN GREATER 0)
endmacro(copy_directory)

macro(copy_static_directory TARGET_NAME SOURCE DESTINATION)
	SET(SRC "${CMAKE_CURRENT_SOURCE_DIR}/${SOURCE}")
    SET(DST "${CMAKE_CURRENT_BINARY_DIR}/${DESTINATION}")
    add_custom_target(${TARGET_NAME} ALL 
        COMMENT "Copying directory ${SOURCE} to ${DESTINATION}.")

    add_custom_command(
    	TARGET ${TARGET_NAME} 
        COMMAND ${CMAKE_COMMAND} -E copy_directory ${SRC} ${DST}
    )
endmacro(copy_static_directory)