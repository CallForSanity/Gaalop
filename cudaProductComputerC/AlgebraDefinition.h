#pragma once

#include <iostream>
#include "Definitions.h"
#include "Blade.h"

#define ALGEBRA_9D

#ifdef ALGEBRA_5D
	//== 5d ==

#define DIMENSION 5

#define EINF 8
#define E0 16
#define EP 8
#define EM 16

	const char* BASE_ELEMENTS_ZI[] = {"e1","e2","e3","einf","e0"}; 
	const char* BASE_ELEMENTS_PM[] = {"e1","e2","e3","ep","em"}; 
	
	const Blade squareMask(EM); // initialize squareMask (all -1 squares are marked with an set bit, all others are clear)

	void transformZI_PM(SignedBlade& blade, SumOfBlades& result) {
		switch (blade.bits) {
		case EINF: //einf=em+ep
			result.push_back(SignedBlade(blade.coefficient,EM));
			result.push_back(SignedBlade(blade.coefficient,EP));
			break;
		case E0: //e0=0.5*em-0.5*ep
			result.push_back(SignedBlade(blade.coefficient/2,EM));
			result.push_back(SignedBlade(-blade.coefficient/2,EP));
			break;
		default:
			result.push_back(blade);
		}
	}

	void transformPM_ZI(SignedBlade& blade, SumOfBlades& result) {
		switch (blade.bits) {
		case EP: //ep=0.5*einf-e0
			result.push_back(SignedBlade(blade.coefficient/2,EINF));
			result.push_back(SignedBlade(-blade.coefficient,E0));
			break;
		case EM: //em=0.5*einf+e0
			result.push_back(SignedBlade(blade.coefficient/2,EINF));
			result.push_back(SignedBlade(blade.coefficient,E0));
			break;
		default:
			result.push_back(blade);
		}
	}

#endif

#ifdef ALGEBRA_9D
	//== 9d ==

	#define DIMENSION 9

	#define EINFX 8
	#define EINFY 16
	#define EINFZ 32
	#define E0X 64
	#define E0Y 128
	#define E0Z 256

	#define E4 8
	#define E5 16
	#define E6 32
	#define E7 64
	#define E8 128
	#define E9 256

	const char* BASE_ELEMENTS_ZI[] = {"e1","e2","e3","einfx","einfy","einfz","e0x","e0y","e0z"}; 
	const char* BASE_ELEMENTS_PM[] = {"e1","e2","e3","e4","e5","e6","e7","e8","e9"}; 
	const Blade squareMask(E7+E8+E9); // initialize squareMask (all -1 squares are marked with an set bit, all others are clear)

	void transformZI_PM(SignedBlade& blade, SumOfBlades& result) {
		switch (blade.bits) {
		case EINFX: //einfx=e4+e7,einfy=e5+e8,einfz=e6+e9
			result.push_back(SignedBlade(blade.coefficient,E4));
			result.push_back(SignedBlade(blade.coefficient,E7));
			break;
		case EINFY: //einfx=e4+e7,einfy=e5+e8,einfz=e6+e9
			result.push_back(SignedBlade(blade.coefficient,E5));
			result.push_back(SignedBlade(blade.coefficient,E8));
			break;
		case EINFZ: //einfx=e4+e7,einfy=e5+e8,einfz=e6+e9
			result.push_back(SignedBlade(blade.coefficient,E6));
			result.push_back(SignedBlade(blade.coefficient,E9));
			break;
		case E0X: //e0x=0.5*e7-0.5*e4,e0y=0.5*e8-0.5*e5,e0z=0.5*e9-0.5*e6
			result.push_back(SignedBlade(blade.coefficient/2,E7));
			result.push_back(SignedBlade(-blade.coefficient/2,E4));
			break;
		case E0Y: //e0x=0.5*e7-0.5*e4,e0y=0.5*e8-0.5*e5,e0z=0.5*e9-0.5*e6
			result.push_back(SignedBlade(blade.coefficient/2,E8));
			result.push_back(SignedBlade(-blade.coefficient/2,E5));
			break;
		case E0Z: //e0x=0.5*e7-0.5*e4,e0y=0.5*e8-0.5*e5,e0z=0.5*e9-0.5*e6
			result.push_back(SignedBlade(blade.coefficient/2,E9));
			result.push_back(SignedBlade(-blade.coefficient/2,E6));
			break;
		default:
			result.push_back(blade);
		}
	}

	void transformPM_ZI(SignedBlade& blade, SumOfBlades& result) {
		switch (blade.bits) {
		case E4: //e4=0.5*einfx-e0x,e5=0.5*einfy-e0y,e6=0.5*einfz-e0z
			result.push_back(SignedBlade(blade.coefficient/2,EINFX));
			result.push_back(SignedBlade(-blade.coefficient,E0X));
			break;
		case E5: //e4=0.5*einfx-e0x,e5=0.5*einfy-e0y,e6=0.5*einfz-e0z
			result.push_back(SignedBlade(blade.coefficient/2,EINFY));
			result.push_back(SignedBlade(-blade.coefficient,E0Y));
			break;
		case E6: //e4=0.5*einfx-e0x,e5=0.5*einfy-e0y,e6=0.5*einfz-e0z
			result.push_back(SignedBlade(blade.coefficient/2,EINFZ));
			result.push_back(SignedBlade(-blade.coefficient,E0Z));
			break;
		case E7: //e7=0.5*einfx+e0x,e8=0.5*einfy+e0y,e9=0.5*einfz+e0z
			result.push_back(SignedBlade(blade.coefficient/2,EINFX));
			result.push_back(SignedBlade(blade.coefficient,E0X));
			break;
		case E8: //e7=0.5*einfx+e0x,e8=0.5*einfy+e0y,e9=0.5*einfz+e0z
			result.push_back(SignedBlade(blade.coefficient/2,EINFY));
			result.push_back(SignedBlade(blade.coefficient,E0Y));
			break;
		case E9: //e7=0.5*einfx+e0x,e8=0.5*einfy+e0y,e9=0.5*einfz+e0z
			result.push_back(SignedBlade(blade.coefficient/2,EINFZ));
			result.push_back(SignedBlade(blade.coefficient,E0Z));
			break;
		default:
			result.push_back(blade);
		}
	}
#endif

void printBlade(Blade& blade, const char** arr, std::ostream& out) {
	if (!blade.any()) {
		out << "1";
		return;
	}
	bool printWedge = false;
	for (int bit=0;bit<MAXBITCOUNT;++bit)
		if (blade.isBit(bit)) {
			if (printWedge) 
				out << "^";
			else
				printWedge = true;
			out << arr[bit];
		}
}


inline void printBladeZI(Blade& blade, std::ostream& out) {
	printBlade(blade,BASE_ELEMENTS_ZI,out);
}
	

inline void printBladePM(Blade& blade, std::ostream& out) {
	printBlade(blade,BASE_ELEMENTS_PM,out);
}