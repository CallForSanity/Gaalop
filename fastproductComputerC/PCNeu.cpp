// PCNeu.cpp : Definiert den Einstiegspunkt für die Konsolenanwendung.
//

#include "Definitions.h"
#include "SignedBlade.h"
#include "GAMethods.h"
#include <iostream>

#include "BladelistCreator.h"

#include "InnerProductComputing.h"
#include "OuterGeoProductComputing.h"
#include "Grouper.h"
#include "Outputter.h"

#include <boost/unordered_map.hpp>

#include <time.h>
#include <fstream>

#include "BitWriter.h"
#include "BitReader.h"

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

#include "CalcThread.h"

#include <boost\thread.hpp>

void write(int count, int bitCount1, int bitCount2, BitWriter& rewriter, Bladelist& bladelistPM, FILE* out2, const char* filename) {
	FILE* in = fopen(filename,"rb");
	fputc(1,out2); //format
	fputc(DIMENSION,out2); //dimension
	fputc(bitCount2,out2); //bitCount
	int bladeCount = bladelistPM.size();
	BitReader reader;
	
	for (int i=0;i<count;++i)
		for (int j=0;j<bladeCount;++j) {
			trafo(reader, rewriter, in, out2, bitCount1, bitCount2);
			trafo(reader, rewriter, in, out2, bitCount1, bitCount2);
			trafo(reader, rewriter, in, out2, bitCount1, bitCount2);
		}

		
	fclose(in);
}

#define max(x,y) (((x)>(y)) ? (x) : (y))

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

	// calculate products
	
	time_t start;
	time(&start);

	int bitCount1 = 31;

	int max1=0;
	int max2=0;
	int max3=0;
	int max4=0;

	CalcThread c1(0,128,"products0.csv",bladelistPM, mapBladesToIndex, bitCount1,&max1);
	CalcThread c2(128,256,"products1.csv",bladelistPM, mapBladesToIndex, bitCount1,&max2);
	CalcThread c3(256,384,"products2.csv",bladelistPM, mapBladesToIndex, bitCount1,&max3);
	CalcThread c4(384,512,"products3.csv",bladelistPM, mapBladesToIndex, bitCount1,&max4);

	boost::thread t1(c1);
	boost::thread t2(c2);
	boost::thread t3(c3);
	boost::thread t4(c4);

	t1.join();
	t2.join();
	t3.join();
	t4.join();

	int maxNumber = max(max(max1,max2),max(max3,max4));
	//std::cout << maxNumber <<std::endl;


	// komprimieren
	int number = 2;
    int bitCount2 = 1;
    while (number < maxNumber+1) {
        bitCount2++;
        number *= 2;
    }

	int count = 128;

	FILE* out2 = fopen("products.csv","w+b");
	BitWriter rewriter;
	
	write(count,bitCount1,bitCount2,rewriter,bladelistPM,out2,"products0.csv");
	write(count,bitCount1,bitCount2,rewriter,bladelistPM,out2,"products1.csv");
	write(count,bitCount1,bitCount2,rewriter,bladelistPM,out2,"products2.csv");
	write(count,bitCount1,bitCount2,rewriter,bladelistPM,out2,"products3.csv");

	remove("products0.csv");
	remove("products1.csv");
	remove("products2.csv");
	remove("products3.csv");

	rewriter.finish(out2);
	fclose(out2);

	time_t ende;
	time(&ende);

	std::cout << "Ready in " << difftime(ende, start) << " seconds" << std::endl;
	//getchar();
	return 0;
}


