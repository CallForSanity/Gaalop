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

#License
The code of both projects is licensed under the Apache license.

# Contributions
Contributions are welcome.
Please fork or ask for direct commit access to the repo.

# Used Sources and Licences
package de.gaalop.productComputer. BubbleSort
Source: http://de.wikipedia.org/wiki/Bubblesort#Formaler%20Algorithmus

package de.gaalop.productComputer.GAMethods
public static float canonicalReorderingSign(Blade a_p, Blade b, int bitCount)
    “This method is taken from the dissertation of Daniel Fontijne - Efficient Implementation of Geometric Algebra”
package de.gaalop.visualizer.engines.lwjgl.recording. AnimatedGifEncoder
No copyright asserted on the source code of this class. May be used for any
 * purpose, however, refer to the Unisys LZW patent for restrictions on use of
 * the associated LZWEncoder class. Please forward any corrections to
 * kweiner@fmsware.com.
 * @author Kevin Weiner, FM Software
 * @version 1.03 November 2003

de.gaalop.visualizer.ia_math.*
/**
 * IAMath.java 
 *   -- classes implementing interval arithmetic versions
 *      of the arithmetic and elementary functions,
 *      as part of the "ia_math library" version 0.1beta1, 10/97
 * 
 * <p>
 * Copyright (C) 2000 Timothy J. Hickey
 * <p>
 * License: <a href="http://interval.sourceforge.net/java/ia_math/licence.txt">zlib/png</a>
 * <p>
 * the class IAMath contains methods for performing basic
 * arithmetic operations on intervals. Currently the
 * elementary functions rely on the underlying implementation
 * which uses the netlib fdlibm library. The resulting code
 * is therefore probably unsound for the transcendental functions.
 */

# Weitere Bibliotheken, die genutzt werden

Antlr, [BSD]
antlr-runtime, [BSD]
args4j, [MIT]
commons-beanutils, [Apache]
jdom, [Apache-style open source license]
jna, [This library is licensed under the LGPL, version 2.1 or later, and (from version 4.0 onward) the Apache Software License, version 2.0. Commercial license arrangements are negotiable.]
lwjgl, [BSD]
lwjgl_util, [BSD]
stringtemplate [BSD]
Maven [Apache?]
