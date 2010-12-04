#include "../gcd-body/gcdBody.h"

int main(const int argc,const char* argv[])
{
    // execute body
    std::string intermediateFilePath,outputFilePath;
    int result = body(intermediateFilePath,outputFilePath,argc,argv,
                      ".clu.i",".clu.cpp.g",".java.i",".class","-o");
    if(result)
        return result;

    // read settings
#ifdef WIN32
    std::string compilerPath("../share/gcd/java_settings.bat");
#else
    std::string compilerPath("../share/gcd/java_settings.sh");
#endif

    // invoke compiler
    invokeCompiler(compilerPath,argc,argv,
                   outputFilePath,intermediateFilePath,"-o");

    return result;
}
