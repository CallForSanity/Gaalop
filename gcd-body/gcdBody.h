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
int body(std::string& intermediateFilePath,std::string& outputFilePath,
         const int argc,const char* argv[],const char* gaalopInFileExtension,
         const char* gaalopOutFileExtension,const char* gaalopGenerator,
	 const char* intermediateFileExtension,const char* outputFileExtension,
	 const char* outputOption);
void invokeCompiler(const std::string& compilerPath,const int argc,const char* argv[],
                    const std::string& outputFilePath,const std::string& intermediateFilePath,
                    const char* outputOption);
void findAndReplaceString(std::string& dest,const std::string& source, const std::string& search,const std::string& replace);

#endif
