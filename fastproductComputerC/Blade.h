#pragma once

#include <stdint.h>

#define Bitcon uint_fast16_t
#define MAXBITCOUNT 9
//TODO Blade as super of a bit class
class Blade
{
public:
	Bitcon bits;

	Blade() {
		bits = 0;
	}

	Blade(Bitcon bits) {
		this->bits = bits;
	}

	/**
		Returns the value of a bit
		@param bit The bit to test
		@returns A bool value, if the specified bit is true
	**/
	inline bool isBit(int bit) {
		return (bits & (1 << bit)) > 0;
	}

	/**
		Returns the number of setted bits in a number
		@returns The number of setted bits
	**/
	inline int count() {
		if (bits == 0) return 0;
		int count = 0;
		Bitcon mask = 1;
		for (int bit = 0; bit < MAXBITCOUNT; ++bit) {
			if ((bits & mask) > 0) count++;
			mask <<= 1;
		}
		return count;
	}

	/**
		Returns, if any bit is set in a number
		@returns Is any bit set in the number?
	**/
	inline bool any() {
		return (bits != 0);
	}

	/**
		Compares two numbers and returns true, 
		if both have at least one set bit in common
		@param bits2 The other blade
		@returns Have both numbers at least one set bit in common?
	**/
	inline bool anyDoubleTrueBit(Blade& blade) {
		return (bits & blade.bits) > 0;
	}

	/**
		Sets a bit in a number
		@param bit The bit to set
	**/
	inline void setBit(int bit) {
		 bits |= (1 << bit);
	}

	/**
		Clears a bit in a number
		@param bits The number
		@param bit The bit to clear
	**/
	inline void clearBit(Bitcon& bits, int bit) {
		 bits &= ~(1 << bit);
	}

	/**
		Flips a bit in a number
		@param bits The number
		@param bit The bit to flip
	**/
	inline void flipBit(Bitcon& bits, int bit) {
		 bits ^= (1 << bit);
	}

	/**
		Sets the value of a bit in a number
		@param bit The bit to set
		@param value The value for the bit to set
	**/
	inline void setBitValue(int bit, bool value) {
		if (value)
			bits |= (1 << bit);
		else
			bits ^= (1 << bit);
	}

	/**
		Clears all bits in a number
	**/
	inline void clearAll() {
		bits = 0;
	}

	/**
		Inverts all bits in a number
	**/
	inline void invertAll() {
		bits = ~bits;
	}
};

