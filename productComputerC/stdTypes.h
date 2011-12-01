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
#include <boost/unordered_map.hpp>
#include <boost/unordered_set.hpp>

using std::string; //TODO optional nicht schöner stil
using std::vector;
using std::list;
using boost::unordered_map;
using boost::unordered_set;


typedef list<int> intList;
typedef vector<int> intVector;
typedef unordered_map<string, int> BaseSquareMap;

struct hashIntVector
{
  size_t
  operator()(intVector v) const {
	  int sum = 0;
	  const unsigned int size = v.size();
	  for (unsigned int i=0;i<size;++i) {
		  sum += v[i];
	  }
	  return sum;
  }
};

struct hashIntList
{
  size_t
  operator()(intList v) const {
	  int sum = 0;
	  const intList::const_iterator& endIt = v.end();
	  for (intList::const_iterator ci=v.begin();ci != endIt; ++ci) {
		  sum += (*ci);
	  }
	  return sum;
  }
};

#endif /* STDTYPES_H_ */
