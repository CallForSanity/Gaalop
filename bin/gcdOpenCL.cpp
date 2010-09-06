#include "../gcd-body/gcdBody.h"

int main(const int argc,const char* argv[])
{
    // execute body
    std::string intermediateFilePath;
    int result = body(intermediateFilePath,argc,argv,".clu.i",".clu.cpp.g",".cl.i");
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
    for(unsigned int counter = 1; counter < argc - 1; ++counter)
	copyCommand << " \"" << argv[counter] << "\"";

    // invoke copy command
    std::cout << copyCommand.str() << std::endl;
    system(copyCommand.str().c_str());

    return result;
}
