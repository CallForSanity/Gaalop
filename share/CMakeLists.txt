# copy
COPY_DIRECTORY(cmake-copy cmake-2.8 cmake-2.8)

# install
INSTALL(DIRECTORY cmake-2.8 DESTINATION share PATTERN ".svn" EXCLUDE)
INSTALL(DIRECTORY doc DESTINATION share PATTERN ".svn" EXCLUDE)
