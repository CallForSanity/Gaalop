#ifndef GCD_H_INCLUDED
#define GCD_H_INCLUDED

#ifdef GCD_GAALET_MV_IDX

#define E1 0x1
#define E2 0x2
#define E3 0x4
#define EINF 0x8
#define E0 0x10

#else
enum BLADES
{
SCALAR,

E1,
E2,
E3,
EINF,
E0,

E12,
E13,
E1INF,
E10,
E23,
E2INF,
E20,
E3INF,
E30,
EINF0,

E123,
E12INF,
E120,
E13INF,
E130,
E1INF0,
E23INF,
E230,
E2INF0,
E3INF0,

E123INF,
E1230,
E12INF0,
E13INF0,
E23INF0,

E123INF0
};
#endif

void GaalopMapPosition(float* p,const float* p_in);
void GaalopMapRotation(float* r,const float* r_in);
void GaalopMapLinearVelocity(float* lv,const float* lv_in);
void GaalopMapAngularVelocity(float* av,const float* av_in);
void GaalopMapVersor(float* D,const float* D_in);
void GaalopMapVelocityScrew(float* V,const float* V_in);

#endif
