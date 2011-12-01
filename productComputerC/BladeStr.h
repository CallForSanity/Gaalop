/*
 * BladeStr.h
 *
 *  Created on: 21.11.2011
 *      Author: christian
 */

#ifndef BLADESTR_H_
#define BLADESTR_H_

#include "stdTypes.h"

class BladeStr {
private:
	float prefactor;

public:
	vector<string> baseVectors;
	virtual ~BladeStr();


    BladeStr(const string& baseVector);
    BladeStr(const vector<string>& baseVectors);
    BladeStr(const float prefactor, const string& baseVector);
    BladeStr(const float prefactor, const vector<string>& baseVectors);

    inline float getPrefactor() const
    {
        return prefactor;
    }

    inline void setPrefactor(const float prefactor)
    {
        this->prefactor = prefactor;
    }

};

#endif /* BLADESTR_H_ */
