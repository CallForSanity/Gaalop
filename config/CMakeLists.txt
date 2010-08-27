FIND_PACKAGE(Qt4)
INCLUDE(${QT_USE_FILE})

# build
FILE(GLOB src "*.cpp")
ADD_EXECUTABLE(config ${src})
TARGET_LINK_LIBRARIES(config ${QT_LIBRARIES})

# copy
FILE(COPY gaalop_settings.sh gaalop_settings.bat cpp_settings.sh cpp_settings.bat cuda_settings.sh cuda_settings.bat DESTINATION .)

# install
INSTALL(FILES gaalop_settings.sh gaalop_settings.bat cpp_settings.sh cpp_settings.bat cuda_settings.sh cuda_settings.bat DESTINATION .)
