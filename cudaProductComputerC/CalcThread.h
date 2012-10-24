#pragma once

#include "BitWriter.h"
#include "BitReader.h"
#include <iostream>
#include <fstream>
#include <sstream>
#include "Basetransformation.h"

inline void trafo(BitReader& reader, BitWriter& rewriter, FILE* in, FILE* out2, int bitCount1, int bitCount2) {
			unsigned int size = reader.read(bitCount1, in);
			rewriter.write(size,bitCount2,out2);

			for (unsigned int k=0;k<size;++k) {
				unsigned int pre = reader.read(1,in);
				rewriter.write(pre,1,out2); //prefactor
				unsigned int data = reader.read(MAXBITCOUNT,in);
				rewriter.write(data,MAXBITCOUNT,out2); //data
			}
		}

class CalcThread {
	private:
		int iInc;
		int iExc;
		int no;
		Bladelist& bladelistPM;
		boost::unordered_map<Bitcon,int>& mapBladesToIndex;
		int bitCount1;
		int* max;
		const char* filename;

	public:

		CalcThread(int iInc, int iExc, const char* filename, Bladelist& bladelistPM, boost::unordered_map<Bitcon,int>& mapBladesToIndex, int bitCount1, int* max) :
			iInc(iInc),iExc(iExc),filename(filename),bladelistPM(bladelistPM),mapBladesToIndex(mapBladesToIndex),bitCount1(bitCount1),max(max)
		{ }

		void operator()() {
			calculateProducts();
		}

	private:
		

	// returns maxNumber
		void calculateProducts() {
			int maxNumber = 0;

			FILE* out = fopen(filename,"w+b");
			BitWriter writer;
			
			int i1=0;
			Bladelist::iterator sblade1=bladelistPM.begin();
			for (int k=0;k<iInc;k++)
				sblade1++;
		
			for (int i1=0;i1<iExc-iInc;++i1) {
				SumOfBlades& s1 = *sblade1;
				//std::cout << i1 << std::endl;
				for (Bladelist::iterator sblade2=bladelistPM.begin(); sblade2 != bladelistPM.end(); ++sblade2) {
					SumOfBlades innerProduct;
					SumOfBlades outerProduct;
					SumOfBlades geoProduct;
			
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

					int innerNum = outputCompressed(innerProductZI,mapBladesToIndex,bitCount1, writer, out);
					if (innerNum > maxNumber) maxNumber = innerNum;
					int outerNum = outputCompressed(outerProductZI,mapBladesToIndex,bitCount1,writer, out);
					if (outerNum > maxNumber) maxNumber = outerNum;
					int geoNum = outputCompressed(geoProductZI,mapBladesToIndex,bitCount1,writer, out);
					if (geoNum > maxNumber) maxNumber = geoNum;
					}
					sblade1++;
				
			}
			writer.finish(out);
			fclose(out);
			
			*max = maxNumber; 
			
		}
};
