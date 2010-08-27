#include "../gcd-body/gcdBody.h"

int main(const int argc,const char* argv[])
{
    // read settings
    std::string compilerPath("g++ -x c++");
#ifdef WIN32
    readFile(compilerPath,"../config/cpp_settings.bat");
#else
    //readFile(compilerPath,"../config/cpp_settings.sh");
#endif

    // execute body
    int result = body(argc,argv,".clu.i",".clu.cpp.g",".cpp.i",compilerPath.c_str());

    return result;
}
