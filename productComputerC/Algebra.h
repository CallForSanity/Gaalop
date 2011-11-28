/*
 * Algebra.h
 *
 *  Created on: 21.11.2011
 *      Author: christian
 */

#ifndef ALGEBRA_H_
#define ALGEBRA_H_

#include "stdTypes.h"
#include "BladeStr.h"

typedef list<BladeStr> ListOfBladeStr;
typedef unordered_map<string, ListOfBladeStr> BaseChangeStrMap;

class Algebra {
protected:
	vector<string> base;
    vector<string> base2;
    BaseSquareMap baseSquaresStr;
    BaseChangeStrMap mapToPlusMinus;
    BaseChangeStrMap mapToZeroInf;

public:
	Algebra();
	virtual ~Algebra();

	virtual void create() = 0;

	inline vector<string> getBase() const
    {
        return base;
    }

	inline vector<string> getBase2() const
    {
        return base2;
    }

	inline BaseSquareMap getBaseSquaresStr() const
    {
        return baseSquaresStr;
    }

	inline BaseChangeStrMap getMapToPlusMinus() const
    {
        return mapToPlusMinus;
    }

	inline BaseChangeStrMap getMapToZeroInf() const
    {
        return mapToZeroInf;
    }

	inline void setBase(vector<string>& base)
    {
        this->base = base;
    }

	inline void setBase2(vector<string>& base2)
    {
        this->base2 = base2;
    }

	inline void setBaseSquaresStr(BaseSquareMap& baseSquaresStr)
    {
        this->baseSquaresStr = baseSquaresStr;
    }

	inline void setMapToPlusMinus(BaseChangeStrMap& mapToPlusMinus)
    {
        this->mapToPlusMinus = mapToPlusMinus;
    }

	inline void setMapToZeroInf(BaseChangeStrMap& mapToZeroInf)
    {
        this->mapToZeroInf = mapToZeroInf;
    }

};

#endif /* ALGEBRA_H_ */
