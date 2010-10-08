CMAKE_MINIMUM_REQUIRED(VERSION 2.8)

# options
OPTION(BUILD_WITH_QT "build with Qt")

IF(BUILD_WITH_QT)

# find QT
FIND_PACKAGE(Qt4 REQUIRED)
INCLUDE(${QT_USE_FILE})

# build
FILE(GLOB src "*.cpp")
FILE(GLOB hdr "*.h")
FILE(GLOB uis "*.ui")
QT4_WRAP_CPP(moc_out ${hdr})
QT4_WRAP_UI(ui_out ${uis})
#QT4_ADD_RESOURCES(res_out ${res})
INCLUDE_DIRECTORIES(${CMAKE_CURRENT_BINARY_DIR})
ADD_EXECUTABLE(gcd-config ${src} ${moc_out} ${ui_out} ${res_out})
TARGET_LINK_LIBRARIES(gcd-config ${QT_LIBRARIES})

# install
INSTALL(TARGETS gcd-config RUNTIME DESTINATION bin)
IF(WIN32)
SET(qt_dlls "${QT_BINARY_DIR}/QtCore4.dll" "${QT_BINARY_DIR}/QtGui4.dll")
INSTALL(PROGRAMS ${qt_dlls} DESTINATION bin)
ENDIF(WIN32)
#SET(CPACK_DEBIAN_PACKAGE_CONTROL_EXTRA "")
#SET(CPACK_NSIS_EXTRA_INSTALL_COMMANDS "ExecWait '\\\"$INSTDIR\\\\bin\\\\gcd-config.exe\\\" /q:a'")

ENDIF(BUILD_WITH_QT)
