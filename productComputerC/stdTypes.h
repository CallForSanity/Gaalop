/*
 * stdTypes.h
 *
 *  Created on: 21.11.2011
 *      Author: christian
 */

#ifndef STDTYPES_H_
#define STDTYPES_H_

#include <iostream>

#include <string>
#include <vector>
#include <list>
#include <tr1/unordered_map>
#include <tr1/unordered_set>

using std::string; //TODO optional nicht schöner stil
using std::vector;
using std::list;
using std::tr1::unordered_map;
using std::tr1::unordered_set;


typedef list<int> intList;
typedef vector<int> intVector;
typedef unordered_map<string, int> BaseSquareMap;

struct hashIntVector
{
  size_t
  operator()(intVector v) const {
	  int sum = 0;
	  int fac = 7;
	  for (unsigned int i=0;i<v.size();++i) {
		  sum += fac*v[i];
		  fac *= 7;
	  }
	  return sum;
  }
};

#endif /* STDTYPES_H_ */
