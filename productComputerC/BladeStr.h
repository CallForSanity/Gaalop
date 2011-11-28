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
    vector<string> baseVectors;
public:
	virtual ~BladeStr();


    BladeStr(string& baseVector);
    BladeStr(vector<string>& baseVectors);
    BladeStr(float prefactor, string& baseVector);
    BladeStr(float prefactor, vector<string>& baseVectors);

    inline vector<string> getBaseVectors() const
    {
        return baseVectors;
    }

    inline float getPrefactor() const
    {
        return prefactor;
    }

    inline void setBaseVectors(vector<string>& baseVectors)
    {
        this->baseVectors = baseVectors;
    }

    inline void setPrefactor(float prefactor)
    {
        this->prefactor = prefactor;
    }

};

#endif /* BLADESTR_H_ */
