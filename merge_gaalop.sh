#!/bin/sh
export USER=pc
export START_REV=1223

svn merge -r $START_REV:HEAD svn+ssh://${USER}@erebor.esa.informatik.tu-darmstadt.de/home/wimi/svn/GaalopCompiler/gaalop-2.0/trunk/api api
svn merge -r $START_REV:HEAD svn+ssh://${USER}@erebor.esa.informatik.tu-darmstadt.de/home/wimi/svn/GaalopCompiler/gaalop-2.0/trunk/algebra algebra
svn merge -r $START_REV:HEAD svn+ssh://${USER}@erebor.esa.informatik.tu-darmstadt.de/home/wimi/svn/GaalopCompiler/gaalop-2.0/trunk/visualCodeInserter visualCodeInserter
svn merge -r $START_REV:HEAD svn+ssh://${USER}@erebor.esa.informatik.tu-darmstadt.de/home/wimi/svn/GaalopCompiler/gaalop-2.0/trunk/clucalc clucalc
svn merge -r $START_REV:HEAD svn+ssh://${USER}@erebor.esa.informatik.tu-darmstadt.de/home/wimi/svn/GaalopCompiler/gaalop-2.0/trunk/maple maple
svn merge -r $START_REV:HEAD svn+ssh://${USER}@erebor.esa.informatik.tu-darmstadt.de/home/wimi/svn/GaalopCompiler/gaalop-2.0/trunk/tba tba
svn merge -r $START_REV:HEAD svn+ssh://${USER}@erebor.esa.informatik.tu-darmstadt.de/home/wimi/svn/GaalopCompiler/gaalop-2.0/trunk/gapp gapp
svn merge -r $START_REV:HEAD svn+ssh://${USER}@erebor.esa.informatik.tu-darmstadt.de/home/wimi/svn/GaalopCompiler/gaalop-2.0/trunk/gaalet gaalet
svn merge -r $START_REV:HEAD svn+ssh://${USER}@erebor.esa.informatik.tu-darmstadt.de/home/wimi/svn/GaalopCompiler/gaalop-2.0/trunk/codegen-cpp codegen-cpp
svn merge -r $START_REV:HEAD svn+ssh://${USER}@erebor.esa.informatik.tu-darmstadt.de/home/wimi/svn/GaalopCompiler/gaalop-2.0/trunk/codegen-compressed codegen-compressed
svn merge -r $START_REV:HEAD svn+ssh://${USER}@erebor.esa.informatik.tu-darmstadt.de/home/wimi/svn/GaalopCompiler/gaalop-2.0/trunk/codegen-dot codegen-dot
svn merge -r $START_REV:HEAD svn+ssh://${USER}@erebor.esa.informatik.tu-darmstadt.de/home/wimi/svn/GaalopCompiler/gaalop-2.0/trunk/codegen-gapp codegen-gapp
svn merge -r $START_REV:HEAD svn+ssh://${USER}@erebor.esa.informatik.tu-darmstadt.de/home/wimi/svn/GaalopCompiler/gaalop-2.0/trunk/codegen-gappopencl codegen-gappopencl
svn merge -r $START_REV:HEAD svn+ssh://${USER}@erebor.esa.informatik.tu-darmstadt.de/home/wimi/svn/GaalopCompiler/gaalop-2.0/trunk/codegen-java codegen-java
