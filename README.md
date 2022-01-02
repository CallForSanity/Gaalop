Linux [![Build Status](https://travis-ci.org/CallForSanity/Gaalop.svg?branch=master)](https://travis-ci.org/CallForSanity/Gaalop)
Windows [![Build status](https://ci.appveyor.com/api/projects/status/g7y459h6sa6kn39h/branch/master?svg=true)](https://ci.appveyor.com/project/CallForSanity/gaalop/branch/master)

# Gaalop
Gaalop (Geometic Algebra Algorithms Optimizer) is a software to compile and optimize geometric algebra (GA) expressions into high-level programming language code. Geometric algebra expressions can be developed using the freely available CLUCalc software by Christian Perwass. Gaalop optimizes CLUCalc expressions and produces C++ (AMP), OpenCL, CUDA, CLUCalc or LaTeX output. The optimized code is free of geometric algebra operations and runs very efficiently on various platforms.

# What is Geometric Algebra?
This is probably best explained in this article:
https://slehar.wordpress.com/2014/03/18/clifford-algebra-a-visual-introduction/
or this book:
http://www.amazon.de/Foundations-Geometric-Algebra-Computing-Geometry/dp/3642317936

# Gaalop Precompiler (GPC)
Note that this repository also contains Gaalop Precompiler (GPC)
in the branch gaalop_precompiler.
GPC integrates Gaalop directly into CMake-generated C/C++-toolchains.
Gaalop Precompiler reuses most of the code of Gaalop
which is why merges from Gaalop are required quite often.

# License
The code of both projects is licensed under the LGPL 3.0.

# Contributions
Contributions are welcome.
Please fork and create a pull request.

# How to compile?
Please visit the [wiki](https://github.com/CallForSanity/Gaalop/wiki) on this repository. 
You will find manuals for compiling GAALOP and GAALOP Precompiler.
