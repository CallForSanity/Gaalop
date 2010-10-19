mvn clean package assembly:directory
del /S target/gaalop-1.0.0-bin
ren target/gaalop-1.0.0-bin.dir target/gaalop-1.0.0-bin
cd makefile_mingw32
mingw32-make clean
mingw32-make