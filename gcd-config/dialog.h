#ifndef DIALOG_H
#define DIALOG_H

#include <QDialog>

namespace Ui {
    class Dialog;
}

class Dialog : public QDialog {
    Q_OBJECT
public:
    Dialog(QWidget *parent = 0);
    ~Dialog();

private:
    Ui::Dialog *ui;

private slots:
    void on_selectJavaCompileCommand_clicked();
    void on_selectMapleBinaryDir_clicked();
    void on_selectCppCompileCommand_clicked();
    void on_selectCudaCompileCommand_clicked();
    void on_saveSettings_clicked();
};

#endif // DIALOG_H
