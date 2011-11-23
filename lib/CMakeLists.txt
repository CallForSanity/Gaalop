FILE(GLOB src "*.cpp" "*.cpg")
ADD_LIBRARY(gpc ${src})
INSTALL(TARGETS gpc DESTINATION lib)
