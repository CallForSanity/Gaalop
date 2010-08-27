#include "../gcd-body/gcdBody.h"

int main(const int argc,const char* argv[])
{
    // read settings
    std::string compilerPath;
#ifdef WIN32
    readFile(compilerPath,"../config/cuda_settings.bat");
#else
    readFile(compilerPath,"../config/cuda_settings.sh");
#endif

    // execute body
    return body(argc,argv,".clu.i",".clu.cpp.g",".cu.i",compilerPath.c_str());
}
