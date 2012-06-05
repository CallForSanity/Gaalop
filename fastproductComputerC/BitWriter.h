#pragma once

#include <iostream>

/**
 * Implements a bit writes which produces no padding bits,
 * except at the end of the file.
 * @author christian
 */
class BitWriter {

private:
	unsigned long cache;
    unsigned int cachedBits;

public:
	BitWriter() {
		cache = 0;
		cachedBits = 0;
	}

    void write(unsigned int data, unsigned int bitCount, FILE* out) {
        cache <<= bitCount;
        cache |= data;
        cachedBits += bitCount;
        while (cachedBits>=8) {
            unsigned int toWrite = (unsigned int) ((cache >> (cachedBits-8)) & 0xFF);
			fputc(toWrite, out);
            cachedBits -= 8;
            cache &= ~(0xFF << cachedBits);
        }
    }

	void finish(FILE* out) {
        if (cachedBits > 0) {
            cache <<= 8-cachedBits;
            unsigned int toWrite = (unsigned int) (cache & 0xFF);
            fputc(toWrite, out);
            cache = 0;
            cachedBits = 0;
        }
    }
};
