#include "../gcd-body/gcdBody.h"

int main(const int argc,const char* argv[])
{
    // execute body
    std::string intermediateFilePath;
    int result = body(intermediateFilePath,argc,argv,".clu.i",".clu.cpp.g",".cpp.i");
    if(result)
	return result;

    // read settings
    std::string compilerPath;
#ifdef WIN32
    readFile(compilerPath,"../share/gcd/cpp_settings.bat");
#else
    readFile(compilerPath,"../share/gcd/cpp_settings.sh");
#endif

    //  compiler command
    std::stringstream compilerCommand;
    compilerCommand << compilerPath;
    for(unsigned int counter = 1; counter < argc - 1; ++counter)
	compilerCommand << " \"" << argv[counter] << "\"";
    compilerCommand << " \"" << intermediateFilePath << "\"";

    // invoke regular C++ compiler
    std::cout << compilerCommand.str() << std::endl;
    system(compilerCommand.str().c_str());

    return result;
}
