/*
 * AlgebraFromFile.cpp
 *
 *  Created on: 04.12.2011
 *      Author: christian
 */

#include "AlgebraFromFile.h"

AlgebraFromFile::AlgebraFromFile() {
}

AlgebraFromFile::~AlgebraFromFile() {
}

void AlgebraFromFile::create() {

}

#include <boost/algorithm/string/erase.hpp>

void AlgebraFromFile::facadeParseStrings(const string& sbase, const string& sbase2, const string& sbaseSquaresStr, std::string& smapToPlusMinus, std::string& smapToZeroInf) {
    parseBaseArr(sbase, base);
    parseBaseArr(sbase2, base2);
    parseMapStrByte(sbaseSquaresStr, baseSquaresStr);
    parseMapStringListBladeStr(smapToPlusMinus, mapToPlusMinus);
    parseMapStringListBladeStr(smapToZeroInf, mapToZeroInf);
    }

#include <string.h>

void tokenize(const string& str, const char* tokens, vector<string>& result) {
	char * pch;
	char * strC = (char *) str.c_str();
	pch = strtok(strC, tokens);
	while (pch != NULL)
	{
	  result.push_back(pch);
	  pch = strtok (NULL, tokens);
	}
}

void AlgebraFromFile::parseBaseArr(const string& sbase, vector<string>& result) {
	string s = sbase;
	result.clear();
	boost::erase_all(s, " ");
	tokenize(s,",",result);
	result.erase(result.begin());
}

/**
 * Parses a map<String, Byte>
 * @param The string to be parsed
 */
void AlgebraFromFile::parseMapStrByte(const string& smap, BaseSquareMap& result) {
	result.clear();
	string s = smap;
	boost::erase_all(s, " ");

	if (s.empty())
		return;

	vector<string> entries;
	tokenize(s,",",entries);
	for (vector<string>::const_iterator it=entries.begin(); it != entries.end(); ++it) {
		vector<string> sides;
		tokenize(*it,"=",sides);
		result[sides[0]] = atoi(sides[1].c_str());
	}
}

/**
 * Parses a map <string, LinkedList<BladeStr>>
 * @param str The string to be parsed
 * @return The parsed map
 */
void AlgebraFromFile::parseMapStringListBladeStr(const string& smap, BaseChangeStrMap& result) {
	result.clear();
	string s = smap;
	boost::erase_all(s, " ");

	if (s.empty())
		return;

	vector<string> entries;
	tokenize(s,",",entries);
	for (vector<string>::const_iterator it=entries.begin(); it != entries.end(); ++it) {
		vector<string> sides;
		tokenize(*it,"=",sides);
		ListOfBladeStr l;
		parseListBladeStr(sides[1].c_str(),l);
		result[sides[0]] = l;
	}
}

#define MAXINT (int) pow(2,sizeof(int)*8-1)

/**
 * Returns the index from the next '+' or '-' character
 * @param str The string to be parsed
 * @return The index
 */
int suchenextIndex(const string& str) {
	int indexPlus = str.find("+");
	int indexMinus = str.find("-", 1);
	if (indexPlus == -1) {
		indexPlus = MAXINT;
	}
	if (indexMinus == -1) {
		indexMinus = MAXINT;
	}
	return (indexPlus < indexMinus) ? indexPlus : indexMinus;
}

/**
 * Parses a list of bladeStr
 * @param str The string to be parsed
 * @return The parsed list of bladeStr
 */
void AlgebraFromFile::parseListBladeStr(const string& slist, ListOfBladeStr& result) {
	string& str = (string&) slist;
	int index;
	while ((index = suchenextIndex(slist)) != MAXINT) {
		BladeStr bstr;
		string str1 = slist.substr(0, index);
		parseBladeStr(str1, bstr);
		result.push_back(bstr);
		if (slist[index] == '-') {
			str = str.substr(index);
		} else {
			str = str.substr(index + 1);
		}
	}
	if (!str.empty()) {
		BladeStr bstr1;
		parseBladeStr(str, bstr1);
		result.push_back(bstr1);
	}
}


    /**
     * Parses a bladeStr
     * @param str The string to be parsed
     * @return The parsed BladeStr
     */
    void AlgebraFromFile::parseBladeStr(string& str, BladeStr& result) {
        // Contains only Product, Blade or Constant!
        if (str.find("*") != string::npos) {
            parseProduct(str, result);
        } else if (str.find("e") != string::npos) {
            parseBlade(str, result);
        } else {
        	string s = str;
        	boost::erase_all(s, " ");
        	result.setPrefactor(atof(s.c_str()));
        	result.baseVectors.clear();
        	result.baseVectors.push_back("0");
        }
    }

    /**
     * Parses a Blade
     * @param str The string to be parsed
     * @return The parsed Blade
     */
    void AlgebraFromFile::parseBlade(string& str, BladeStr& result) {
    	string s = str;
        bool negated = false;
        if (str[0] == '-') {
            s = s.substr(1);
            negated = true;
        }
        result.setPrefactor(negated ? -1 : 1);
        result.baseVectors.clear();
        result.baseVectors.push_back(s);
    }

    /**
     * Parses a Product
     * @param str The string to be parsed
     * @return The parsed Product
     */
   void AlgebraFromFile::parseProduct(string& str, BladeStr& result) {
	   vector<string> parts;
	   tokenize(str,"*",parts);

        if (parts[0].find("e") != string::npos) {
            //parts[0]: Blade
            //parts[1]: Constant
            parseBlade(parts[0],result);

            string s = parts[1];
			boost::erase_all(s, " ");
			result.setPrefactor(result.getPrefactor()*atof(s.c_str()));

        } else {
            //parts[0]: Constant
            //parts[1]: Blade
            parseBlade(parts[1],result);
            string s = parts[0];
			boost::erase_all(s, " ");
			result.setPrefactor(result.getPrefactor()*atof(s.c_str()));

        }
    }

#include <fstream>

void AlgebraFromFile::loadFromFile(const char* filename) {

	std::ifstream file(filename);
	string sbase;
	getline ( file, sbase);
	string smapToZeroInf;
	getline ( file, smapToZeroInf);
	string sbase2;
	getline ( file, sbase2);
	string sbaseSquaresStr;
	getline ( file, sbaseSquaresStr);
	string smapToPlusMinus;
	getline ( file, smapToPlusMinus);

	file.close();
	facadeParseStrings(sbase,sbase2,sbaseSquaresStr,smapToPlusMinus,smapToZeroInf);
}
