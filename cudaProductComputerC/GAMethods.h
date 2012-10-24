#pragma once

#include "Blade.h"

// Computes ‘reordering sign’ to get into canonical order.
// Arguments 'a' and 'b' are both bitmaps representing basis blades.
// Copy a!
// This method is taken from the dissertation of Daniel Fontijne - Efficient Implementation of Gemoetric Algebra
float canonicalReorderingSign(Blade a, Blade& b)
{
	// Count the number of basis vector swaps required to
	// get 'a' and 'b' into canonical order.
	a.bits >>= 1;
	int sum = 0;
	while (a.any())
	{
		// the function bitCount() counts the number of
		// 1-bits in the argument
		Blade aAndB(a.bits & b.bits);
		sum += aAndB.count();
		a.bits >>= 1;
	}
	// even number of swaps -> return 1
	// odd number of swaps -> return -1
	return ((sum & 1) == 0) ? 1.0f : -1.0f;
}