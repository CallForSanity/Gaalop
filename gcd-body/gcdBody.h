#ifndef GCDBODY_H_INCLUDED
#define GCDBODY_H_INCLUDED

#include <cstdlib>
#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <vector>

void readFile(std::string& resultString,const char* filePath);
void readFile(std::stringstream& resultStream,const char* filePath);
void readFile(std::stringstream& resultStream,std::ifstream& fileStream);
int body(std::string& intermediateFilePath,
	 const int argc,const char* argv[],const char* gaalopInFileExtension,
	 const char* gaalopOutFileExtension,const char* intermediateFileExtension);

#endif
