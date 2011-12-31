#pragma once
#include "blade.h"
class SignedBlade :
	public Blade
{
public:
	float coefficient;

	SignedBlade(void)
	{
		coefficient = 1.0;
		bits = 0;
	}

	SignedBlade(Blade blade)
	{
		coefficient = 1.0;
		this->bits = blade.bits;
	}

	SignedBlade(float coefficient, Blade blade)
	{
		this->coefficient = coefficient;
		this->bits = blade.bits;
	}
};

