#include <qlineedit.h>
#include <qfile.h>
#include <qtextstream.h>
#include <qfiledialog.h>
#include "dialog.h"
#include "ui_dialog.h"

#include <iostream>

Dialog::Dialog(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::Dialog)
{
    ui->setupUi(this);

#ifdef WIN32
    ui->mapleBinaryDir->addItem("C:\\Programme\\Maple 12\\bin.win");
    ui->mapleBinaryDir->addItem("C:\\Program Files\\Maple 12\\bin.win");
    ui->mapleBinaryDir->addItem("C:\\Program Files (x86)\\Maple 12\\bin.win");
    ui->cppCompileCommand->addItem("mingw32-g++ -c");
    ui->cppCompileCommand->addItem("cl /c");
    ui->cudaCompileCommand->addItem("nvcc -c");
    ui->javaCompileCommand->addItem("javac");
#else
    ui->mapleBinaryDir->addItem("/opt/maple13/bin.X86_64_LINUX");
    ui->cppCompileCommand->addItem("g++ -c");
    ui->cppCompileCommand->addItem("c++ -c");
    ui->cppCompileCommand->addItem("gcc -c");
    ui->cudaCompileCommand->addItem("nvcc -c");
    ui->javaCompileCommand->addItem("javac");
#endif

#ifdef WIN32
    QFile mapleBinaryDirFile("..\\share\\gcd\\maple_settings.bat");
    QFile cppCompileCommandFile("..\\share\\gcd\\cpp_settings.bat");
    QFile cudaCompileCommandFile("..\\share\\gcd\\cuda_settings.bat");
    QFile javaCompileCommandFile("..\\share\\gcd\\java_settings.bat");
#else
    QFile mapleBinaryDirFile("../share/gcd/maple_settings.sh");
    QFile cppCompileCommandFile("../share/gcd/cpp_settings.sh");
    QFile cudaCompileCommandFile("../share/gcd/cuda_settings.sh");
    QFile javaCompileCommandFile("../share/gcd/java_settings.sh");
#endif
    mapleBinaryDirFile.open(QIODevice::ReadOnly);
    cppCompileCommandFile.open(QIODevice::ReadOnly);
    cudaCompileCommandFile.open(QIODevice::ReadOnly);
    javaCompileCommandFile.open(QIODevice::ReadOnly);
    QTextStream mapleBinaryDirStream(&mapleBinaryDirFile);
    QTextStream cppCompileCommandStream(&cppCompileCommandFile);
    QTextStream cudaCompileCommandStream(&cudaCompileCommandFile);
    QTextStream javaCompileCommandStream(&javaCompileCommandFile);
    ui->mapleBinaryDir->lineEdit()->setText(mapleBinaryDirStream.readAll());
    ui->cppCompileCommand->lineEdit()->setText(cppCompileCommandStream.readAll());
    ui->cudaCompileCommand->lineEdit()->setText(cudaCompileCommandStream.readAll());
    ui->javaCompileCommand->lineEdit()->setText(javaCompileCommandStream.readAll());
}

Dialog::~Dialog()
{
    delete ui;
}

void Dialog::on_selectMapleBinaryDir_clicked()
{
    ui->mapleBinaryDir->lineEdit()->setText(QFileDialog::getExistingDirectory(this,tr("Open Directory"),ui->mapleBinaryDir->lineEdit()->text()));
}

void Dialog::on_selectCppCompileCommand_clicked()
{
    ui->cppCompileCommand->lineEdit()->setText(QFileDialog::getExistingDirectory(this,tr("Open Directory"),ui->cppCompileCommand->lineEdit()->text()));
}

void Dialog::on_selectCudaCompileCommand_clicked()
{
    ui->cudaCompileCommand->lineEdit()->setText(QFileDialog::getExistingDirectory(this,tr("Open Directory"),ui->cudaCompileCommand->lineEdit()->text()));
}

void Dialog::on_selectJavaCompileCommand_clicked()
{
    ui->javaCompileCommand->lineEdit()->setText(QFileDialog::getExistingDirectory(this,tr("Open Directory"),ui->javaCompileCommand->lineEdit()->text()));
}

void Dialog::on_saveSettings_clicked()
{
#ifdef WIN32
    QFile mapleBinaryDirFile("..\\share\\gcd\\maple_settings.bat");
    QFile gaalopCommandFile("..\\share\\gcd\\gaalop_settings.bat");
    QFile cppCompileCommandFile("..\\share\\gcd\\cpp_settings.bat");
    QFile cudaCompileCommandFile("..\\share\\gcd\\cuda_settings.bat");
    QFile javaCompileCommandFile("..\\share\\gcd\\java_settings.bat");
#else
    QFile mapleBinaryDirFile("../share/gcd/maple_settings.sh");
    QFile gaalopCommandFile("../share/gcd/gaalop_settings.sh");
    QFile cppCompileCommandFile("../share/gcd/cpp_settings.sh");
    QFile cudaCompileCommandFile("../share/gcd/cuda_settings.sh");
    QFile javaCompileCommandFile("../share/gcd/java_settings.sh");
#endif
    mapleBinaryDirFile.open(QIODevice::WriteOnly);
    gaalopCommandFile.open(QIODevice::WriteOnly);
    cppCompileCommandFile.open(QIODevice::WriteOnly);
    cudaCompileCommandFile.open(QIODevice::WriteOnly);
    javaCompileCommandFile.open(QIODevice::WriteOnly);
    QTextStream mapleBinaryDirStream(&mapleBinaryDirFile);
    QTextStream gaalopCommandStream(&gaalopCommandFile);
    QTextStream cppCompileCommandStream(&cppCompileCommandFile);
    QTextStream cudaCompileCommandStream(&cudaCompileCommandFile);
    QTextStream javaCompileCommandStream(&javaCompileCommandFile);

    mapleBinaryDirStream << ui->mapleBinaryDir->lineEdit()->text();
#ifdef WIN32
    gaalopCommandStream << "java -jar starter-1.0.0.jar -m \""
			<< ui->mapleBinaryDir->lineEdit()->text() << "\" -i";
#else
    gaalopCommandStream << "export MAPLE=\"" << ui->mapleBinaryDir->lineEdit()->text()
			<< "/..\"; java -jar starter-1.0.0.jar -m \""
			<< ui->mapleBinaryDir->lineEdit()->text() << "\" -i";
#endif
    cppCompileCommandStream << ui->cppCompileCommand->lineEdit()->text();
    cudaCompileCommandStream << ui->cudaCompileCommand->lineEdit()->text();
    javaCompileCommandStream << ui->javaCompileCommand->lineEdit()->text();
}
