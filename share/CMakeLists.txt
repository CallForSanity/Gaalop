# copy
COPY_DIRECTORY(gcd-copy gcd "share/gcd")
COPY_DIRECTORY(cmake-copy cmake-2.8 "share/cmake-2.8")

# install
INSTALL(DIRECTORY gcd cmake-2.8 DESTINATION share PATTERN ".svn" EXCLUDE)
