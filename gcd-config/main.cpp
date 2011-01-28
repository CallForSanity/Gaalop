#include <QtGui/QApplication>
#include "dialog.h"

#ifdef _MSC_VER
#include <direct.h>
#else
#include <unistd.h>
#endif
#include <iostream>

int main(int argc, char *argv[])
{
    std::cout << argv[0] << std::endl;
    std::string appPath(argv[0]);
#ifdef WIN32
    const size_t pos = appPath.find_last_of('\\');
#else
    const size_t pos = appPath.find_last_of('/');
#endif
#ifdef _MSC_VER
    _chdir(appPath.substr(0,pos).c_str());
#else
    chdir(appPath.substr(0,pos).c_str());
#endif

    QApplication a(argc, argv);
    Dialog w;
    w.show();
    return a.exec();
}
