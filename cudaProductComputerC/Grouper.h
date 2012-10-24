#include "Definitions.h"

#include <boost/unordered_map.hpp>

typedef boost::unordered_map<Bitcon, float> GROUPMAP;

void group(SumOfBlades& s) {
	GROUPMAP map;
	for (SumOfBlades::iterator blade1=s.begin(); blade1 != s.end(); ++blade1) {
		SignedBlade& blade = *blade1;
		if (map.count(blade.bits) > 0) 
			map[blade.bits] += blade.coefficient; 
		else
			map[blade.bits] = blade.coefficient; 
	}
	s.clear();
	for (GROUPMAP::iterator it=map.begin(); it != map.end(); ++it) 
		if (abs(it->second) > 10E-4)
			s.push_back(SignedBlade(it->second,it->first));
}