/*
 * ListOfBlades.h
 *
 *  Created on: 21.11.2011
 *      Author: christian
 */

#ifndef LISTOFBLADES_H_
#define LISTOFBLADES_H_

#include "Blade.h"
#include "stdTypes.h"
#include "VectorMethods.h"

class ListOfBlades {
private:
	vector<Blade> blades; //TODO hier mit viel Aufwand optimieren?
	unordered_map<intVector, int, hashIntVector> mapIndices;
public:
	ListOfBlades();
	virtual ~ListOfBlades();

	inline vector<Blade> getBlades() const
	{
		return blades;
	}

	void setBlades(const vector<Blade>& blades)
	{
		this->blades = blades;
		mapIndices.clear();
		for (unsigned int i=0;i<blades.size();++i) {
			const intVector& vec = blades[i].baseVectors;
			mapIndices[vec]=i;
		}
	}

	int getIndex(const intVector& bases);

};

#endif /* LISTOFBLADES_H_ */
