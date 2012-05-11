#!/bin/sh
export USER=pc
export START_REV=1223

svn merge -r $START_REV:HEAD svn+ssh://${USER}@erebor.esa.informatik.tu-darmstadt.de/home/wimi/svn/GaalopCompiler/gaalop-2.0/branches/tbaAndGapp/api api
svn merge -r $START_REV:HEAD svn+ssh://${USER}@erebor.esa.informatik.tu-darmstadt.de/home/wimi/svn/GaalopCompiler/gaalop-2.0/branches/tbaAndGapp/algebra algebra
svn merge -r $START_REV:HEAD svn+ssh://${USER}@erebor.esa.informatik.tu-darmstadt.de/home/wimi/svn/GaalopCompiler/gaalop-2.0/branches/tbaAndGapp/visualCodeInserter visualCodeInserter
svn merge -r $START_REV:HEAD svn+ssh://${USER}@erebor.esa.informatik.tu-darmstadt.de/home/wimi/svn/GaalopCompiler/gaalop-2.0/branches/tbaAndGapp/clucalc clucalc
svn merge -r $START_REV:HEAD svn+ssh://${USER}@erebor.esa.informatik.tu-darmstadt.de/home/wimi/svn/GaalopCompiler/gaalop-2.0/branches/tbaAndGapp/maple maple
svn merge -r $START_REV:HEAD svn+ssh://${USER}@erebor.esa.informatik.tu-darmstadt.de/home/wimi/svn/GaalopCompiler/gaalop-2.0/branches/tbaAndGapp/tba tba
svn merge -r $START_REV:HEAD svn+ssh://${USER}@erebor.esa.informatik.tu-darmstadt.de/home/wimi/svn/GaalopCompiler/gaalop-2.0/branches/tbaAndGapp/gapp gapp
svn merge -r $START_REV:HEAD svn+ssh://${USER}@erebor.esa.informatik.tu-darmstadt.de/home/wimi/svn/GaalopCompiler/gaalop-2.0/branches/tbaAndGapp/gaalet gaalet
svn merge -r $START_REV:HEAD svn+ssh://${USER}@erebor.esa.informatik.tu-darmstadt.de/home/wimi/svn/GaalopCompiler/gaalop-2.0/branches/tbaAndGapp/codegen-cpp codegen-cpp
svn merge -r $START_REV:HEAD svn+ssh://${USER}@erebor.esa.informatik.tu-darmstadt.de/home/wimi/svn/GaalopCompiler/gaalop-2.0/branches/tbaAndGapp/codegen-compressed codegen-compressed
svn merge -r $START_REV:HEAD svn+ssh://${USER}@erebor.esa.informatik.tu-darmstadt.de/home/wimi/svn/GaalopCompiler/gaalop-2.0/branches/tbaAndGapp/codegen-dot codegen-dot
svn merge -r $START_REV:HEAD svn+ssh://${USER}@erebor.esa.informatik.tu-darmstadt.de/home/wimi/svn/GaalopCompiler/gaalop-2.0/branches/tbaAndGapp/codegen-gapp codegen-gapp
svn merge -r $START_REV:HEAD svn+ssh://${USER}@erebor.esa.informatik.tu-darmstadt.de/home/wimi/svn/GaalopCompiler/gaalop-2.0/branches/tbaAndGapp/codegen-gappopencl codegen-gappopencl
svn merge -r $START_REV:HEAD svn+ssh://${USER}@erebor.esa.informatik.tu-darmstadt.de/home/wimi/svn/GaalopCompiler/gaalop-2.0/branches/tbaAndGapp/codegen-java codegen-java
