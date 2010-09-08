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
    QFile mapleBinaryDirFile("..\\share\\gcd\\maple_settings.bat");
    QFile cppCompileCommandFile("..\\share\\gcd\\cpp_settings.bat");
    QFile cudaCompileCommandFile("..\\share\\gcd\\cuda_settings.bat");
#else
    QFile mapleBinaryDirFile("../share/gcd/maple_settings.sh");
    QFile cppCompileCommandFile("../share/gcd/cpp_settings.sh");
    QFile cudaCompileCommandFile("../share/gcd/cuda_settings.sh");
#endif
    mapleBinaryDirFile.open(QIODevice::ReadOnly);
    cppCompileCommandFile.open(QIODevice::ReadOnly);
    cudaCompileCommandFile.open(QIODevice::ReadOnly);
    QTextStream mapleBinaryDirStream(&mapleBinaryDirFile);
    QTextStream cppCompileCommandStream(&cppCompileCommandFile);
    QTextStream cudaCompileCommandStream(&cudaCompileCommandFile);
    ui->mapleBinaryDir->setText(mapleBinaryDirStream.readAll());
    ui->cppCompileCommand->setText(cppCompileCommandStream.readAll());
    ui->cudaCompileCommand->setText(cudaCompileCommandStream.readAll());
}

Dialog::~Dialog()
{
    delete ui;
}

void Dialog::on_selectMapleBinaryDir_clicked()
{
    ui->mapleBinaryDir->setText(QFileDialog::getExistingDirectory(this,tr("Open Directory"),ui->mapleBinaryDir->text()));
}

void Dialog::on_selectCppCompileCommand_clicked()
{
    ui->cppCompileCommand->setText(QFileDialog::getExistingDirectory(this,tr("Open Directory"),ui->cppCompileCommand->text()));
}

void Dialog::on_selectCudaCompileCommand_clicked()
{
    ui->cudaCompileCommand->setText(QFileDialog::getExistingDirectory(this,tr("Open Directory"),ui->cudaCompileCommand->text()));
}

void Dialog::on_saveSettings_clicked()
{
#ifdef WIN32
    QFile mapleBinaryDirFile("..\\share\\gcd\\maple_settings.bat");
    QFile gaalopCommandFile("..\\share\\gcd\\gaalop_settings.bat");
    QFile cppCompileCommandFile("..\\share\\gcd\\cpp_settings.bat");
    QFile cudaCompileCommandFile("..\\share\\gcd\\cuda_settings.bat");
#else
    QFile mapleBinaryDirFile("../share/gcd/maple_settings.sh");
    QFile gaalopCommandFile("../share/gcd/gaalop_settings.sh");
    QFile cppCompileCommandFile("../share/gcd/cpp_settings.sh");
    QFile cudaCompileCommandFile("../share/gcd/cuda_settings.sh");
#endif
    mapleBinaryDirFile.open(QIODevice::WriteOnly);
    gaalopCommandFile.open(QIODevice::WriteOnly);
    cppCompileCommandFile.open(QIODevice::WriteOnly);
    cudaCompileCommandFile.open(QIODevice::WriteOnly);
    QTextStream mapleBinaryDirStream(&mapleBinaryDirFile);
    QTextStream gaalopCommandStream(&gaalopCommandFile);
    QTextStream cppCompileCommandStream(&cppCompileCommandFile);
    QTextStream cudaCompileCommandStream(&cudaCompileCommandFile);

    mapleBinaryDirStream << ui->mapleBinaryDir->text();
    gaalopCommandStream << "java -jar starter-1.0.0.jar -m \"" << ui->mapleBinaryDir->text() << "\" -i";
    cppCompileCommandStream << ui->cppCompileCommand->text();
    cudaCompileCommandStream << ui->cudaCompileCommand->text();
}
