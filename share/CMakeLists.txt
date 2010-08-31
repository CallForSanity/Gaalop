FIND_PACKAGE(Qt4)
INCLUDE(${QT_USE_FILE})

# build
FILE(GLOB src "*.cpp")
FILE(GLOB hdr "*.h")
FILE(GLOB uis "*.ui")
QT4_WRAP_CPP(moc_out ${hdr})
QT4_WRAP_UI(ui_out ${uis})
#QT4_ADD_RESOURCES(res_out ${res})
INCLUDE_DIRECTORIES(${CMAKE_CURRENT_BINARY_DIR})
ADD_EXECUTABLE(config ${src} ${moc_out} ${ui_out} ${res_out})
TARGET_LINK_LIBRARIES(config ${QT_LIBRARIES})

# copy
FILE(COPY gcd cmake-2.8 DESTINATION .)

# install
INSTALL(DIRECTORY gcd cmake-2.8 DESTINATION .)
