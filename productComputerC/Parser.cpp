/*
 * Parser.cpp
 *
 *  Created on: 30.11.2011
 *      Author: christian
 */

#include "Parser.h"

Parser::Parser() {


}

Parser::~Parser() {

}

#include <boost/algorithm/string/trim.hpp>
#include <boost/algorithm/string/predicate.hpp>

    /**
     * Parses a blade refrence from a string
     * @param parse The string to parse
     * @return The parsed blade reference
     */
void Parser::parseBladeRef(string parse, BladeRef& result) {

        char prefactor = 1;
        int index = 0;

        boost::trim(parse);

        if (parse.length() == 0 || parse.compare("0") == 0) {
            prefactor = 0;
        } else {
            if (boost::starts_with(parse,"-E")) {
                // for instance -E10
                prefactor = -1;
                index = atoi(parse.substr(2).c_str());
            } else {
                if (boost::starts_with(parse,"E")) {
                    // for instance E10
                	index = atoi(parse.substr(1).c_str());
                } else {
                    // for instance -1E10
                	char* c = (char*) parse.c_str();

                    prefactor = (char) atoi(strtok(c,"E"));
                    index = atoi(strtok(NULL,"E"));
                }
            }
        }

        result.setPrefactor(prefactor);
        result.setIndex(index);
    }

inline int min(int& x,int& y) {
	return (x<y) ?x :y;
}

    /**
     * Parses a multivector from a string
     * @param readed The string to parse
     * @param algebra The current algebra
     * @return The parsed blade
     */
void Parser::parseMultivector(string parse, Multivector& result) {
		boost::trim(parse);
        string& trimmed = parse; //copy!

        while (trimmed.length() != 0) {
            int index = -1;
            int indexPlus = -1;
            int indexMinus = -1;

            if (trimmed.at(0) == '-') {
                indexPlus = trimmed.find_first_of('+', 1);
                indexMinus = trimmed.find_first_of('-', 1);
            } else {
                indexPlus = trimmed.find_first_of('+');
                indexMinus = trimmed.find_first_of('-');
            }

            if (indexPlus == -1) {
                index = (indexMinus == -1) ? -1 : indexMinus;
            } else {
                index = (indexMinus == -1) ? indexPlus : min(indexMinus, indexPlus);
            }


            if (index == -1) {
                // one blade only
            	BladeRef b(0,0);
            	parseBladeRef(trimmed,b);
                result.addBlade(b);
                trimmed = "";
            } else {
                // more blades
            	BladeRef b(0,0);
            	parseBladeRef(trimmed.substr(0,index),b);
				result.addBlade(b);

                if (trimmed.at(index) == '+') {
                    trimmed = trimmed.substr(index + 1);
                } else {
                    trimmed = trimmed.substr(index);
                }
            }
        }

    }
