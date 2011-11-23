//#include "../include/gcd.h"

void GaalopMapVersor(float* D,const float* D_result)
{
  D[0] = D_result[0];
  D[1] = D_result[6];
  D[2] = D_result[7];
  D[3] = D_result[8];
  D[4] = D_result[10];
  D[5] = D_result[11];
  D[6] = D_result[13];
  D[7] = D_result[26];
}

void GaalopMapVelocityScrew(float* V,const float* V_result)
{
  V[0] = V_result[6];
  V[1] = V_result[7];
  V[2] = V_result[8];
  V[3] = V_result[10];
  V[4] = V_result[11];
  V[5] = V_result[13];
}
