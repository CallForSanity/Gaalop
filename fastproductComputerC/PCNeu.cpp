// PCNeu.cpp : Definiert den Einstiegspunkt für die Konsolenanwendung.
//

#include "Definitions.h"
#include "SignedBlade.h"
#include "GAMethods.h"
#include "Basetransformation.h"
#include <iostream>

#include "BladelistCreator.h"

#include "InnerProductComputing.h"
#include "OuterGeoProductComputing.h"
#include "Grouper.h"
#include "Outputter.h"

#include <boost/unordered_map.hpp>

#include <time.h>
#include <fstream>

#define COMPUTE_INNER_PRODUCT
#define COMPUTE_OUTER_AND_GEO_PRODUCT

#define PRINT_TO_FILE

void printBladelist(Bladelist& list, void (*printer) (Blade&, std::ostream&)) {
	std::fstream out("E:\\blades.csv",std::fstream::out);
	int index = 0;
	for (Bladelist::iterator sblade1=list.begin(); sblade1 != list.end(); ++sblade1) {
		SumOfBlades&s = *sblade1;
		out << index << ": ";
		output(s,printer,out);
		out << std::endl;
		index++;
	}
	out.close();
}

int main(int argc, char* argv[])
{
	// initialize all sumOfBlades
	Bladelist bladelistZI;
	initializeBladelist(bladelistZI);
	Bladelist bladelistPM;

	boost::unordered_map<Bitcon,int> mapBladesToIndex;
	int index=0;
	for (Bladelist::iterator sblade1=bladelistZI.begin(); sblade1 != bladelistZI.end(); ++sblade1) {
		SumOfBlades& s = *sblade1;
		SignedBlade b = *s.begin();
		mapBladesToIndex[b.bits] = index;
		SumOfBlades pm;
		basetransformationZeroInfToPlusMinus(s,pm);
		group(pm);
		bladelistPM.push_back(pm);
		index++;
	}

#ifdef PRINT_TO_FILE
	std::fstream out("E:\\products.csv",std::fstream::out);
#endif

	time_t start;
	time(&start);
	// calculate products
	int i1 = 0;
	int i2 = 0;
	for (Bladelist::iterator sblade1=bladelistPM.begin(); sblade1 != bladelistPM.end(); ++sblade1) {
		//std::cout << i1 << std::endl;
		for (Bladelist::iterator sblade2=bladelistPM.begin(); sblade2 != bladelistPM.end(); ++sblade2) {
			SumOfBlades innerProduct;
			SumOfBlades outerProduct;
			SumOfBlades geoProduct;

			SumOfBlades& s1 = *sblade1;
		    SumOfBlades& s2 = *sblade2;
			for (SumOfBlades::iterator blade1=s1.begin(); blade1 != s1.end(); ++blade1)
				for (SumOfBlades::iterator blade2=s2.begin(); blade2 != s2.end(); ++blade2) {
					SignedBlade& b1 = *blade1;
					SignedBlade& b2 = *blade2;

					//calculate inner product in plusminus base
					computeInnerOfTwoSignedBlades(b1,b2,innerProduct);

					//calculate outer and geo product in plusminus base
					computeOuterGeoOfTwoSignedBlades(b1,b2,outerProduct,geoProduct);		
			}

				
			// Base transformation to zero inf base
			SumOfBlades innerProductZI;
			basetransformationPlusMinusToZeroInf(innerProduct,innerProductZI);
			SumOfBlades outerProductZI;
			basetransformationPlusMinusToZeroInf(outerProduct,outerProductZI);
			SumOfBlades geoProductZI;
			basetransformationPlusMinusToZeroInf(geoProduct,geoProductZI);

			// group
			group(innerProductZI);
			group(outerProductZI);
			group(geoProductZI);

#ifdef PRINT_TO_FILE
			// output (to file)
			out << "E" << i1 << ";E" << i2 << ";";
			outputExBr(innerProductZI,&printBladeZI,mapBladesToIndex,out,i1,i2,'.');
			out << ";";
			outputExBr(outerProductZI,&printBladeZI,mapBladesToIndex,out,i1,i2,'^');
			out << ";";
			outputExBr(geoProductZI,&printBladeZI,mapBladesToIndex,out,i1,i2,'*');
			out << std::endl;
#endif

			i2++;
		}
		i1++;
		i2 = 0;
	}

#ifdef PRINT_TO_FILE
	out.close();
#endif

	time_t ende;
	time(&ende);

	std::cout << "Ready in " << difftime(ende, start) << " seconds" << std::endl;
	getchar();
	return 0;
}

