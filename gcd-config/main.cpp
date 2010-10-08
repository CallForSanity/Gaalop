#include <QtGui/QApplication>
#include "dialog.h"

#include <unistd.h>
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
    chdir(appPath.substr(0,pos).c_str());

    QApplication a(argc, argv);
    Dialog w;
    w.show();
    return a.exec();
}
