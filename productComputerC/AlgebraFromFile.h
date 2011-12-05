/*
 * AlgebraFromFile.h
 *
 *  Created on: 04.12.2011
 *      Author: christian
 */

#ifndef ALGEBRAFROMFILE_H_
#define ALGEBRAFROMFILE_H_

#include "Algebra.h"

class AlgebraFromFile: public Algebra {
private:
	void parseBaseArr(const string& sbase, vector<string>& result);
	void parseMapStrByte(const string& smap, BaseSquareMap& result);
	void parseMapStringListBladeStr(const string& smap, BaseChangeStrMap& result);
	void parseListBladeStr(const string& slist, ListOfBladeStr& result);
	void parseBladeStr(string& str, BladeStr& result);
	void parseBlade(string& str, BladeStr& result);
	void parseProduct(string& str, BladeStr& result);
public:
	AlgebraFromFile();
	virtual ~AlgebraFromFile();

	virtual void create();

	void facadeParseStrings(const string& sbase, const string& sbase2, const string& sbaseSquaresStr, std::string& smapToPlusMinus, std::string& smapToZeroInf);
	void loadFromFile(const char* filename);

};

#endif /* ALGEBRAFROMFILE_H_ */
