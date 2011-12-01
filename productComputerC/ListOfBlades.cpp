/*
 * ListOfBlades.cpp
 *
 *  Created on: 21.11.2011
 *      Author: christian
 */

#include "ListOfBlades.h"

ListOfBlades::ListOfBlades() {
}

ListOfBlades::~ListOfBlades() {
}

/**
 * Determines the index of a base
 * @param bases The base
 * @return The index
 */
int ListOfBlades::getIndex(const intVector& bases) {
	if (mapIndices.count(bases) == 1)
		return mapIndices[bases];
	else
		return -1;
}
