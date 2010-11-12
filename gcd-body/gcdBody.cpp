#include <assert.h>
#include <unistd.h>
#include "gcdBody.h"

#ifdef WIN32
#define PATH_SEP '\\'
#else
#define PATH_SEP '/'
#endif

void readFile(std::stringstream& resultStream,const char* filePath);
void readFile(std::stringstream& resultStream,const std::ifstream& fileStream);

int body(std::string& intermediateFilePath,std::string& outputFilePath,
         const int argc,const char* argv[],const char* gaalopInFileExtension,
         const char* gaalopOutFileExtension,const char* intermediateFileExtension,
         const char* outputFileExtension,const char* outputOption)
{
    if(argc <= 1)
    {
	std::cout << "usage: specify build parameters/files.\n";
	return -1;
    }

    // save working directory
    char runPath[4096];
    getcwd(runPath,4096);

    // change working directory to application directory
    std::string appPath(argv[0]);
    const size_t pos = appPath.find_last_of(PATH_SEP);
    appPath = appPath.substr(0,pos);
    std::cout << appPath << std::endl;
    chdir(appPath.c_str());

    // parse command line
    std::string inputFilePath(argv[argc - 1]);
    if(inputFilePath.find('\\') == std::string::npos &&
       inputFilePath.find('/') == std::string::npos)
    {
        std::stringstream inputFilePathStream;
        inputFilePathStream << runPath << PATH_SEP << inputFilePath;
        inputFilePath = inputFilePathStream.str();
    }

    // try to find temp file path and remove trailing extension
    std::string tempFilePath(inputFilePath + outputFileExtension);
    for(unsigned int counter = argc - 2; counter > 0; --counter)
    {
        std::string arg(argv[counter]);
        if(arg.rfind(outputFileExtension) != std::string::npos)
        {
            const size_t pos = arg.find_last_of('.');
            tempFilePath = arg.substr(0,pos);
            outputFilePath = arg;
            break;
        }
        else if(arg.rfind("-o") != std::string::npos ||
                arg.rfind("/o") != std::string::npos ||
                arg.rfind(outputOption) != std::string::npos)
        {
            arg = argv[++counter];
            const size_t pos = arg.find_last_of('.');
            tempFilePath = arg.substr(0,pos);
            outputFilePath = arg;
            break;
        }
    }

    // convert to absolute paths
    if(tempFilePath.find('\\') == std::string::npos &&
       tempFilePath.find('/') == std::string::npos)
    {
        // gaalop output file
        {
            std::stringstream tempFilePathStream;
            tempFilePathStream << runPath << PATH_SEP << tempFilePath;
            tempFilePath = tempFilePathStream.str();
        }

        // output file
        {
            std::stringstream outputFilePathStream;
            outputFilePathStream << runPath << PATH_SEP << outputFilePath;
            outputFilePath = outputFilePathStream.str();
        }
    }

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
                gaalopInFilePath << tempFilePath << '.' << ++gaalopFileCount << gaalopInFileExtension;
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
    std::string gaalopPath;
#ifdef WIN32
    readFile(gaalopPath,"..\\share\\gcd\\gaalop_settings.bat");
    chdir("..\\share\\gcd\\target\\gaalop-1.0.0-bin");
#else
    readFile(gaalopPath,"../share/gcd/gaalop_settings.sh");
    chdir("../share/gcd/target/gaalop-1.0.0-bin");
#endif
    const int numGaalopFiles = gaalopInFilePathVector.size();
    #pragma omp parallel for
    for(gaalopFileCount = 1; gaalopFileCount <= numGaalopFiles; ++gaalopFileCount)
    {
        // run Gaalop
        std::stringstream gaalopCommand;
        gaalopCommand << gaalopPath;
        gaalopCommand << " \"" << tempFilePath << '.' << gaalopFileCount << gaalopInFileExtension << '\"';
        std::cout << gaalopCommand.str() << std::endl;
        system(gaalopCommand.str().c_str());
    }
    //chdir(appPath.c_str());
#ifdef WIN32
    chdir("..\\..\\..\\..\\bin");
#else
    chdir("../../../../bin");
#endif

    // compose intermediate file
    {
        size_t pos = inputFilePath.find_last_of('/');
        if(pos == std::string::npos)
            pos = inputFilePath.find_last_of('\\');
        assert(pos != std::string::npos);
        const std::string inputFileDir(inputFilePath.substr(0,pos + 1));

        intermediateFilePath = tempFilePath + intermediateFileExtension;
        std::ofstream intermediateFile(intermediateFilePath.c_str());
        std::ifstream inputFile(inputFilePath.c_str());
        gaalopFileCount = 0;
	unsigned int lineCount = 1; // think one line ahead
        while(inputFile.good())
        {
            // read line
            getline(inputFile,line);
	    ++lineCount;

            if(line.find("#include") != std::string::npos &&
               line.find('\"') != std::string::npos)
            {
                const size_t pos = line.find_first_of('\"') + 1;
                intermediateFile << line.substr(0,pos) << inputFileDir;
                intermediateFile << line.substr(pos,std::string::npos) << std::endl;
            }
            // found gaalop line - insert intermediate gaalop file
            else if(line.find("#pragma gcd begin") != std::string::npos)
            {
		// line pragma for compile errors
		intermediateFile << "#line " << lineCount << " \"" << inputFilePath << "\"\n";

                // merge optimized code
                std::stringstream gaalopOutFilePath;
                gaalopOutFilePath << tempFilePath << '.' << ++gaalopFileCount << gaalopOutFileExtension;
                std::cout << gaalopOutFilePath.str().c_str() << std::endl;
                std::ifstream gaalopOutFile(gaalopOutFilePath.str().c_str());
                if(!gaalopOutFile.good())
                {
                    std::cerr << "fatal error: Gaalop-generated file not found. Check your Java installation. "\
                                 "Also check your Maple directory and Cliffordlib using the Configuration Tool.\n";
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
		    ++lineCount;
                    if(line.find("#pragma gcd end") != std::string::npos)
                        break;
                }

		// line pragma for compile errors
		intermediateFile << "#line " << lineCount << " \"" << inputFilePath << "\"\n";
            }
            else
                intermediateFile << line << std::endl;
        }
    }

    return 0;
}

void invokeCompiler(const std::string& compilerPath,const int argc,const char* argv[],
                    const std::string& outputFilePath,const std::string& intermediateFilePath,
                    const char* outputOption)
{
    // compose compiler command
    std::stringstream compilerCommand;
    compilerCommand << compilerPath;
    for(unsigned int counter = 1; counter < argc - 1; ++counter)
    {
        const std::string arg(argv[counter]);
        if(arg.find(outputOption) != std::string::npos)
        {
            compilerCommand << ' ' << arg << " \"" << outputFilePath << '\"';
            ++counter;
        }
        else
            compilerCommand << " \"" << arg << '\"';
    }
    compilerCommand << " \"" << intermediateFilePath << '\"';

    // invoke regular C++ compiler
    std::cout << compilerCommand.str() << std::endl;
    system(compilerCommand.str().c_str());
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
