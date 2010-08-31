#include "gcdBody.h"

void readFile(std::stringstream& resultStream,const char* filePath);
void readFile(std::stringstream& resultStream,const std::ifstream& fileStream);

int body(const int argc,const char* argv[],const char* gaalopInFileExtension,
  	 const char* gaalopOutFileExtension,const char* intermediateFileExtension,
	 const char* compilerPath)
{
    // settings
    std::string gaalopPath;
#ifdef WIN32
    readFile(gaalopPath,"../config/gcd/gaalop_settings.bat");
#else
    readFile(gaalopPath,"../share/gcd/gaalop_settings.sh");
#endif

    // parse command line
    const std::string inputFilePath(argv[argc - 1]);
    const std::string intermediateFilePath(inputFilePath + intermediateFileExtension);
    //  compiler command
    std::stringstream compilerCommand;
    //compilerCommand << "\"" << compilerPath << "\"";
    compilerCommand << compilerPath;
    for(unsigned int counter = 1; counter < argc - 1; ++counter)
	compilerCommand << " \"" << argv[counter] << "\"";
    compilerCommand << " \"" << intermediateFilePath << "\"";

    // process input file
    // split into gaalop input files
    int gaalopFileCount = 0;
    std::vector<std::string> gaalopInFilePathVector;
    std::string line;
    {
        std::ifstream inputFile(inputFilePath.c_str());
        while(inputFile.good())
        {
            // read line
            getline(inputFile,line);

            // found gaalop line
            if(line.find("#pragma gcd begin") != std::string::npos)
            {
                std::stringstream gaalopInFilePath;
                gaalopInFilePath << inputFilePath << "." << ++gaalopFileCount << gaalopInFileExtension;
                std::ofstream gaalopInFile(gaalopInFilePath.str().c_str());

                // read until end of optimized file
                while(inputFile.good())
                {
                    getline(inputFile,line);
                    if(line.find("#pragma gcd end") != std::string::npos)
                        break;
                    else
                        gaalopInFile << line << std::endl;
                }

                // add to vector
                gaalopInFilePathVector.push_back(gaalopInFilePath.str().c_str());
            }
        }
    }

    // process gaalop intermediate files
    const int numGaalopFiles = gaalopInFilePathVector.size();
    #pragma omp parallel for
    for(gaalopFileCount = 1; gaalopFileCount <= numGaalopFiles;
        ++gaalopFileCount)
    {
        // run Gaalop
        std::stringstream gaalopCommand;
        gaalopCommand << gaalopPath;
	gaalopCommand << " \"" << inputFilePath << "." << gaalopFileCount << gaalopInFileExtension << "\"";
	std::cout << gaalopCommand.str() << std::endl;
        system(gaalopCommand.str().c_str());
    }

    // compose intermediate file
    {
        std::ofstream intermediateFile(intermediateFilePath.c_str());
        std::ifstream inputFile(inputFilePath.c_str());
        gaalopFileCount = 0;
        while(inputFile.good())
        {
            // read line
            getline(inputFile,line);

            // found gaalop line - insert intermediate gaalop file
            if(line.find("#pragma gcd begin") != std::string::npos)
            {
                // merge optimized code
                std::stringstream gaalopOutFilePath;
                gaalopOutFilePath << inputFilePath << "." << ++gaalopFileCount << gaalopOutFileExtension;
                std::cout << gaalopOutFilePath.str().c_str() << std::endl;
                std::ifstream gaalopOutFile(gaalopOutFilePath.str().c_str());
                while(gaalopOutFile.good())
                {
                    getline(gaalopOutFile,line);
                    intermediateFile << line << std::endl;
                }

                // skip original code
                while(inputFile.good())
                {
                    getline(inputFile,line);
                    if(line.find("#pragma gcd end") != std::string::npos)
                        break;
                }
            }
            else
                intermediateFile << line << std::endl;
        }
    }

    // invoke regular C++ compiler
    std::cout << compilerCommand.str() << std::endl;
    system(compilerCommand.str().c_str());

    return 0;
}

void readFile(std::string& resultString,const char* filePath)
{
  std::stringstream resultStream;
  readFile(resultStream,filePath);
  resultString = resultStream.str();
  //std::cout << resultString << std::endl;
}

void readFile(std::stringstream& resultStream,const char* filePath)
{
  std::ifstream fileStream(filePath);
  readFile(resultStream,fileStream);
}

void readFile(std::stringstream& resultStream,std::ifstream& fileStream)
{
  std::string line;
  while(fileStream.good())
  {
    getline(fileStream,line);
    resultStream << line;
  }

  //resultStream << fileStream;
}
