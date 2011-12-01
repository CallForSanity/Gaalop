/*
 * VectorMethods.h
 *
 *  Created on: 21.11.2011
 *      Author: christian
 */

#ifndef VECTORMETHODS_H_
#define VECTORMETHODS_H_

#include "stdTypes.h"



class VectorMethods {
public:
	VectorMethods();
	virtual ~VectorMethods();

	static bool intVectorEquals(const intVector& v1, const intVector& v2) {
		if (v1.size() != v2.size()) return false;
		const unsigned int size = v1.size();
		for (unsigned int i=0;i<size;++i)
			if (v1[i] != v2[i]) return false;
		return true;
	}

	static void intListToVector(const intList& list, intVector& vec) {
		vec.clear();
		vec.reserve(list.size());
		const intList::const_iterator& endIt = list.end();
		for (intList::const_iterator ci = list.begin(); ci != endIt; ++ci)
			vec.push_back(*ci);
	}

	static void intVectorToList(const intVector& vec, intList& list) {
		list.clear();
		const intVector::const_iterator& endIt = vec.end();
		for (intVector::const_iterator ci = vec.begin(); ci != endIt; ++ci)
			list.push_back(*ci);
	}

	static void mergeLists(intList& listDestSrc, const intList& listSrc) {
		const intList::const_iterator& endIt = listSrc.end();
		for (intList::const_iterator ci = listSrc.begin(); ci != endIt; ++ci)
			listDestSrc.push_back(*ci);
	}

	static void mergeVectors(intVector& listDestSrc, const intVector& listSrc) {
		listDestSrc.reserve(listDestSrc.size()+listSrc.size());
		const intVector::const_iterator& endIt = listSrc.end();
		for (intVector::const_iterator ci = listSrc.begin(); ci != endIt; ++ci)
			listDestSrc.push_back(*ci);
	}

    /**
     * Returns the index of an int in an int list
     * @param search The searched int
     * @param list The list to search in
     * @return The index, -1 if the list doesn't contain the searched int
     */
	static int getIndexOfString(const int search, const intVector& list) {
        int i=0;
        const intVector::const_iterator& endIt = list.end();
        for (intVector::const_iterator ci=list.begin();ci != endIt;++ci) {
            if (search == *ci)
                return i;
            i++;
        }
        return -1;
    }



	static void printIntList(const intList& list) {
		std::cout << "{";
		int size = list.size();
		int cur = 0;
		for (intList::const_iterator ci = list.begin(); ci != list.end(); ++ci) {
			cur++;
			std::cout << *ci;
			if (cur != size) std::cout << ",";
		}
		std::cout << "}";
	}

	static void printIntVector(const intVector& v) {
		std::cout << "{";
		int size = v.size();
		int cur = 0;
		for (intVector::const_iterator ci = v.begin(); ci != v.end(); ++ci) {
			cur++;
			std::cout << *ci;
			if (cur != size) std::cout << ",";
		}
		std::cout << "}";
	}
};

#endif /* VECTORMETHODS_H_ */
