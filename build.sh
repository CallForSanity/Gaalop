mvn clean package assembly:directory
rm -R target/gaalop-1.0.0-bin
mv target/gaalop-1.0.0-bin.dir target/gaalop-1.0.0-bin
cd makefile_gcc
make clean
make
