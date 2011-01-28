#include "../gcd-body/gcdBody.h"

int main(const int argc,const char* argv[])
{
    // execute body
    std::string intermediateFilePath,outputFilePath;
#ifdef _MSC_VER
    int result = body(intermediateFilePath,outputFilePath,argc,argv,
                      ".clu.i",".clu.cpp.g","de.gaalop.compressed.Plugin",".cpp",".obj","/o");
#else
    int result = body(intermediateFilePath,outputFilePath,argc,argv,
                      ".clu.i",".clu.cpp.g","de.gaalop.compressed.Plugin",".cpp",".o","-o");
#endif
    if(result)
        return result;

    // read settings
#ifdef WIN32
    std::string compilerPath("..\\share\\gcd\\cxx_settings.bat");
#else
    std::string compilerPath("../share/gcd/cxx_settings.sh");
#endif

    // invoke compiler
    invokeCompiler(compilerPath,argc,argv,
                   outputFilePath,intermediateFilePath,"-o");

    return result;
}
