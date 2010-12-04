# copy
COPY_DIRECTORY(cmake-copy cmake-2.8 "share/cmake-2.8")

ADD_SUBDIRECTORY(gcd)

# install
INSTALL(DIRECTORY cmake-2.8 DESTINATION share PATTERN ".svn" EXCLUDE)
