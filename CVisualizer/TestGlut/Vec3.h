// ========================================================================= //
// Author: Matthias Bein                                                     //
// mailto:matthias.bein@gris.informatik.tu-darmstadt.de                      //
// GRIS - Graphisch Interaktive Systeme                                      //
// Technische Universität Darmstadt                                          //
// Fraunhoferstrasse 5                                                       //
// D-64283 Darmstadt, Germany                                                //
//                                                                           //
// Creation Date: 04.11.2009                                                 //
// ========================================================================= //

// ========================================================================= //
// Simple Vector Class consisting of 3 primitives and following operations   //
// Constructors: Vec3(), Vec3(T v1, T v2, T v3)                              //
// Index: [i] for i = 0,1,2                                                  //
// Two sided Vec3: +, -, * (dot product), ^ (cross product), ==, !=          //
// Left sided Vec3: +=, -=, *= (scalar mul), /= (scalar div)                 //
// Left or right sided float: *                                              //
// Right sided float: /                                                      //
// Functions: length(), sqlength(), distance(Vec3 v), normalize(),           //
//            normalized(), clear(), set(f,f,f), rotations                   //
// ========================================================================= //

#ifndef VEC3_H
#define VEC3_H
#include <math.h>

#define M_RadToDeg 0.0174532925f

template<class T>
class Vec3 {
public:
  // values
  T x,y,z;
  // initializes with (0,0,0)
  Vec3() {
    x = 0;
    y = 0;
    z = 0;
  }
  // initializes with (v1,v2,v3)
  Vec3(T v1, T v2, T v3) {
    x = v1;
    y = v2;
    z = v3;
  }
  Vec3(T v)
  {
	  x = y = z = v;
  }
  // returns i-th komponent (i=0,1,2) (RHS array operator)
  const T operator[] (unsigned int i) const {
    return *(&x+i);
  }
  // LHS array operator
  T & operator [] (unsigned int i) {
    return *(&x+i);
  }
  // Vec3 = Vec3 + Vec3 (vector addition)
  Vec3 operator+ (const Vec3 &v) const {
    Vec3 result;
    result.x = x + v.x;
    result.y = y + v.y;
    result.z = z + v.z;
    return result;
  }
  // Vec3 = Vec3 - Vec3 (normal vector subtraction)
  Vec3 operator- (const Vec3 &v) const {
    Vec3 result;
    result.x = x - v.x;
    result.y = y - v.y;
    result.z = z - v.z;
    return result;
  }
  // T = Vec3 * Vec3 (dot product)
  T operator* (const Vec3 &v) const {
    return x*v.x + y*v.y + z*v.z;
  }
  // Vec3 = Vec3 ^ Vec3 (cross product)
  Vec3 operator^ (const Vec3 &v) const {
    Vec3 result;
    result.x = y*v.z - z*v.y;
    result.y = z*v.x - x*v.z;
    result.z = x*v.y - y*v.x;
    return result;
  }
  // Vec3 += Vec3 (vector addition)
  Vec3 operator+= (const Vec3 &v) {
    *this = *this + v;
    return *this;
  }
  // Vec3 -= Vec3 (vector subtraction)
  Vec3 operator-= (const Vec3 &v) {
    *this = *this - v;
    return *this;
  }
  // Vec3 *= T (scalar multiplication)
  Vec3 operator*= (const T f) {
    *this = *this * f;
    return *this;
  }
  // Vec3 /= T (scalar division)
  Vec3 operator/= (const T f) {
    *this = *this / f;
    return *this;
  }
  // Vec3 == Vec3 (equals)
  bool operator== (const Vec3 &v) const {
    if (v.x == x && v.y == y && v.z == z) return true;
    else return false;
  }
  // Vec3 != Vec3 (not equal)
  bool operator!= (const Vec3 &v) const {
    return !(*this == v);
  }
  // Vec3 = Vec3 * T (scalar multiplication)
  Vec3 operator* (const T &f) const {
    Vec3 result;
    result.x = x * f;
    result.y = y * f;
    result.z = z * f;
    return result;
  }
  // Vec3 = Vec3 / T (scalar division)
  Vec3 operator/ (const T &f) const {
    Vec3 result;
    result.x = x / f;
    result.y = y / f;
    result.z = z / f;
    return result;
  }
  // returns euclidic length
  float length() const {
    return sqrt(x*x + y*y + z*z);
  }
  // returns squared euclidic length (faster then length())
  float sqlength() const {
    return x*x + y*y + z*z;
  }
  // returns distance to v
  float distance(const Vec3 &v) const {
    return (v - *this).length();
  }
  // normalizes this vector (division by length). returns false when length is < 0.00001
  bool normalize() {
    T l = this->length();
    if (fabs(l) < 0.00001) return false;
    *this /= l;
    return true;
  }
  // returns normalized vector but does not change this Vec3
  Vec3 normalized() const {
    T l = this->length();
    if (fabs(l) < 0.00001) return *this;
    return *this / l;
  }
  // normalizes this vector (division by length). returns false when length is < 0.00001
  void clear() {
    x = 0;
    y = 0;
    z = 0;
  }
  // rotates the vector around x (angle in degree)
  void rotX(float angle) {
    float y_new = cos(angle*M_RadToDeg)*y - sin(angle*M_RadToDeg)*z;
    float z_new = sin(angle*M_RadToDeg)*y + cos(angle*M_RadToDeg)*z;
    y = y_new;
    z = z_new;
  }
  // rotates the vector around y (angle in degree)
  void rotY(float angle) {
    float x_new = cos(angle*M_RadToDeg)*x + sin(angle*M_RadToDeg)*z;
    float z_new = -sin(angle*M_RadToDeg)*x + cos(angle*M_RadToDeg)*z;
    x = x_new;
    z = z_new;
  }
  // rotates the vector around z (angle in degree)
  void rotZ(float angle) {
    float x_new = cos(angle*M_RadToDeg)*x - sin(angle*M_RadToDeg)*y;
    float y_new = sin(angle*M_RadToDeg)*x + cos(angle*M_RadToDeg)*y;
    x = x_new;
    y = y_new;
  }
  // set values
  void set(T _x, T _y, T _z) {
    x = _x;
    y = _y;
    z = _z;
  }
  void set(const Vec3 &v) {
    x = v.x;
    y = v.y;
    z = v.z;
  }
};

// Vec3 = T * Vec3 (scalar multiplication)
template <class T>
Vec3<T> operator* (const T &f, const Vec3<T> &v) {
  return v * f;
}

typedef Vec3<float>         Vec3f;
typedef Vec3<int>           Vec3i;
typedef Vec3<unsigned int>  Vec3ui;

#endif