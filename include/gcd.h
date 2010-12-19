#ifndef GCD_H_INCLUDED
#define GCD_H_INCLUDED

#include "gaalet.h"

#ifdef GCD_OLD_GAALET_MV_IDX
enum MV_BLADE_INDEX
{
SCALAR,

E1, // 1
E2,
E3,
EINF,
E0,

E12, // 6
E13,
E1INF,
E10,
E23,
E2INF,
E20,
E3INF,
E30,
EINF0,

E123, // 16
E12INF,
E120,
E13INF,
E130,
E1INF0,
E23INF,
E230,
E2INF0,
E3INF0,

E123INF, // 26
E1230,
E12INF0,
E13INF0,
E23INF0,

E123INF0 // 31
};
#else
#define SCALAR 0

#define E1 (1 << 0)
#define E2 (1 << 1)
#define E3 (1 << 2)
#define EINF (1 << 3)
#define E0 (1 << 4)

#define E12 (E1 | E2)
#define E13 (E1 | E3)
#define E1INF (E1 | EINF)
#define E10 (E1 | E0)
#define E23 (E2 | E3)
#define E2INF (E2 | EINF)
#define E20 (E2 | E0)
#define E3INF (E3 | EINF)
#define E30 (E3 | E0)
#define EINF0 (EINF | E0)

#define E123 (E1 | E2 | E3)
#define E12INF (E1 | E2 | EINF)
#define E120 (E1 | E2 | E0)
#define E13INF (E1 | E3 | EINF)
#define E130 (E1 | E3 | E0)
#define E1INF0 (E1 | EINF | E0)
#define E23INF (E2 | E3 | EINF)
#define E230 (E2 | E3 | E0)
#define E2INF0 (E2 | EINF | E0)
#define E3INF0 (E3 | EINF | E0)

#define E123INF (E1 | E2 | E3 | EINF)
#define E1230 (E1 | E2 | E3 | E0)
#define E12INF0 (E1 | E2 | EINF | E0)
#define E13INF0 (E1 | E3 | EINF | E0)
#define E23INF0 (E2 | E3 | EINF | E0)

#define E123INF0 (E1 | E2 | E3 | EINF | E0)
#endif

void GaalopMapPosition(float* p,const float* p_in);
void GaalopMapRotation(float* r,const float* r_in);
void GaalopMapLinearVelocity(float* lv,const float* lv_in);
void GaalopMapAngularVelocity(float* av,const float* av_in);
void GaalopMapVersor(float* D,const float* D_in);
void GaalopMapVelocityScrew(float* V,const float* V_in);

#endif
