#ifdef WIN32
#include <windows.h>
#else
#include <unistd.h>
#endif
#include "gcdBody.h"

void readFile(std::stringstream& resultStream,const char* filePath);
void readFile(std::stringstream& resultStream,const std::ifstream& fileStream);

int body(std::string& intermediateFilePath,
         const int argc,const char* argv[],const char* gaalopInFileExtension,
         const char* gaalopOutFileExtension,const char* intermediateFileExtension)
{
    // change working directory to application directory
    std::string appPath(argv[0]);
#ifdef WIN32
    const size_t pos = appPath.find_last_of('\\');
    //_chdir(appPath.substr(0,pos).c_str());
    SetCurrentDirectory(appPath.substr(0,pos).c_str());
    char dir[MAX_PATH];
    GetCurrentDirectory(MAX_PATH,dir);
    std::cout << dir << std::endl;
#else
    const size_t pos = appPath.find_last_of('/');
    chdir(appPath.substr(0,pos).c_str());
#endif

    // settings
    std::string gaalopPath;
#ifdef WIN32
    readFile(gaalopPath,"../share/gcd/gaalop_settings.bat");
#else
    readFile(gaalopPath,"../share/gcd/gaalop_settings.sh");
#endif

    // parse command line
    const std::string inputFilePath(argv[argc - 1]);

    // try to find output path
    std::string outputFilePath(inputFilePath);
    for(unsigned int counter = argc - 2; counter > 0; --counter)
    {
        const std::string arg(argv[counter]);
        if(arg.rfind(".o") != std::string::npos || arg.rfind(".obj") != std::string::npos ||
           arg.rfind(".gcp") != std::string::npos || arg.rfind(".gcu") != std::string::npos || arg.rfind(".gcl") != std::string::npos)
        {
            size_t pos = arg.find_last_of(".o");
            if(pos == std::string::npos)
                pos = arg.find_last_of(".obj");

            outputFilePath = arg.substr(0,pos - 1);
            break;
        }
    }

    // set intermediate file path
    intermediateFilePath = inputFilePath + intermediateFileExtension;

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
                gaalopInFilePath << outputFilePath << "." << ++gaalopFileCount << gaalopInFileExtension;
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
    for(gaalopFileCount = 1; gaalopFileCount <= numGaalopFiles; ++gaalopFileCount)
    {
        // run Gaalop
        std::stringstream gaalopCommand;
        gaalopCommand << gaalopPath;
        gaalopCommand << " \"" << outputFilePath << "." << gaalopFileCount << gaalopInFileExtension << "\"";
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
                gaalopOutFilePath << outputFilePath << "." << ++gaalopFileCount << gaalopOutFileExtension;
                std::cout << gaalopOutFilePath.str().c_str() << std::endl;
                std::ifstream gaalopOutFile(gaalopOutFilePath.str().c_str());
                if(!gaalopOutFile.good())
                {
                    std::cerr << "fatal error: Gaalop-generated file not found. Check your Java installation. "\
                                 "Also check your Maple directory using the Configuration Tool.\n";
                    return -1;
                }
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

    return 0;
}

void readFile(std::string& resultString,const char* filePath)
{
  std::stringstream resultStream;
  readFile(resultStream,filePath);
  resultString = resultStream.str();
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
}
