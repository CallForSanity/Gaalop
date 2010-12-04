FILE(GLOB src "*.cpp" "*.gcp")
ADD_LIBRARY(gcd ${src})
INSTALL(TARGETS gcd ARCHIVE DESTINATION lib)
