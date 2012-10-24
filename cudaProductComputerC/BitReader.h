#pragma once

#include <iostream>

/**
 * Implements a bit reader which reads no padding bits,
 * except at the end of the file.
 * @author christian
 */
class BitReader {

private:
	unsigned long cache;
    unsigned int cachedBits;

public:

	BitReader() {
		cache = 0;
		cachedBits = 0;
	}

	unsigned int read(unsigned int bitCount, FILE* in) {

        while (cachedBits < bitCount) {
			unsigned int data = (unsigned int) fgetc(in);
            cache <<= 8;
            cache |= data;
            cachedBits += 8;
        }

        cachedBits -= bitCount;
        unsigned int result = (unsigned int) (cache >> cachedBits);
        
        for (unsigned int i=0;i<bitCount;i++)
            cache &= ~(1 << (i+cachedBits));

        return result;
    }

};
