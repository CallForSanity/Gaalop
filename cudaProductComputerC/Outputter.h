#pragma once

#include <iostream>

#include <boost/unordered_map.hpp>

#include "Definitions.h"

#include "AlgebraDefinition.h"

#include "Blade.h"

#include "BitWriter.h"

void output(SumOfBlades& s, void (*printer) (Blade&, std::ostream& out), std::ostream& out) {
	bool first = true;
	for (SumOfBlades::iterator blade1=s.begin(); blade1 != s.end(); ++blade1) {
		SignedBlade& blade = *blade1;
		if (blade.coefficient > 0) {
			if (first) {
				out << blade.coefficient << "[";
				printer(blade,out);
				out << "]";
			} else {
				out << "+" << blade.coefficient << "[";
				printer(blade,out);
				out << "]";
			}

		} else {
			out << blade.coefficient << "[";
			printer(blade,out);
			out << "]";
		}

		first = false;
	}
}

void outputExBr(SumOfBlades& s, void (*printer) (Blade&,std::ostream&), boost::unordered_map<Bitcon,int>& mapBladeToIndex,std::ostream& out, int i1, int i2, char c) {
	if (s.empty()) {
		out << "0";
		return;
	}
	
	bool first = true;
	for (SumOfBlades::iterator blade1=s.begin(); blade1 != s.end(); ++blade1) {
		SignedBlade& blade = *blade1;

		if ((1-abs(blade.coefficient)) > 10E-4) 
			std::cerr << "Error: MVCoeff of E"<< i1 << c <<"E" << i2 << " is not like -1 or 1, but " << blade.coefficient << std::endl;

		if (blade.coefficient > 0) {
			if (!first) 
				out << "+";
		} else 
			out << "-";
		
		out << "E" << mapBladeToIndex[blade.bits];
		first = false;
	}
}

int outputCompressed(SumOfBlades& s, boost::unordered_map<Bitcon,int>& mapBladeToIndex, int bitCount, BitWriter& writer,FILE* out) {
	writer.write(s.size(),bitCount,out);
	for (SumOfBlades::iterator blade1=s.begin(); blade1 != s.end(); ++blade1) {
		SignedBlade& blade = *blade1;

		if ((1-abs(blade.coefficient)) > 10E-4) 
			std::cerr << "Error: MVCoeff is not like -1 or 1, but " << blade.coefficient << std::endl;

		writer.write((blade.coefficient < 0) ? 1 : 0,1,out);
		writer.write(mapBladeToIndex[blade.bits],MAXBITCOUNT,out);
	}
	return s.size();
}