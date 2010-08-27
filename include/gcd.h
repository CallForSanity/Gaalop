#ifndef GCD_H_INCLUDED
#define GCD_H_INCLUDED

#define SCALAR 0
#define E12 6
#define E13 7
#define E23 9
#define E1x 8
#define E2x 11
#define E3x 13
#define E123x 26

void GaalopMapPosition(float* p,const float* p_in);
void GaalopMapRotation(float* r,const float* r_in);
void GaalopMapLinearVelocity(float* lv,const float* lv_in);
void GaalopMapAngularVelocity(float* av,const float* av_in);
void GaalopMapVersor(float* D,const float* D_in);
void GaalopMapVelocityScrew(float* V,const float* V_in);

#endif
