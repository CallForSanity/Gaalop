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
    std::string compilerPath;
#ifdef WIN32
    readFile(compilerPath,"../share/gcd/java_settings.bat");
#else
    readFile(compilerPath,"../share/gcd/java_settings.sh");
#endif

    // invoke compiler
    invokeCompiler(compilerPath,argc,argv,
                   outputFilePath,intermediateFilePath,"-o");

    return result;
}
