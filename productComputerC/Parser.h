/*
 * Parser.h
 *
 *  Created on: 30.11.2011
 *      Author: christian
 */

#ifndef PARSER_H_
#define PARSER_H_

#include <string>
#include "BladeRef.h"
#include "Parser.h"
#include "Multivector.h"

using std::string;

class Parser {
public:
	Parser();
	virtual ~Parser();

	void parseBladeRef(string parse, BladeRef& result);
	void parseMultivector(string parse, Multivector& result);
};

#endif /* PARSER_H_ */
