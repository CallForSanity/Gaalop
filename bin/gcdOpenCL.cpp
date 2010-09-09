#include "../gcd-body/gcdBody.h"

int main(const int argc,const char* argv[])
{
    // execute body
    std::string intermediateFilePath,outputFilePath;
    int result = body(intermediateFilePath,outputFilePath,argc,argv,
                      ".clu.i",".clu.cpp.g",".cl.i",".cl","-o");
    if(result)
        return result;

    // configure settings
#ifdef WIN32
    std::string copyPath("copy");
#else
    std::string copyPath("cp");
#endif

    //  copy command
    std::stringstream copyCommand;
    copyCommand << copyPath;
    copyCommand << " \"" << intermediateFilePath << "\"";
    copyCommand << " \"" << outputFilePath << "\"";

    // invoke copy command
    std::cout << copyCommand.str() << std::endl;
    system(copyCommand.str().c_str());

    return result;
}
