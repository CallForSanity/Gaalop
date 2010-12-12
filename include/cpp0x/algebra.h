#ifndef __GAALET_ALGEBRA_H
#define __GAALET_ALGEBRA_H

#include "multivector.h"
#include "utility.h"

namespace gaalet
{

//metric of multivector
// --- only definable by signature yet
// --- interface may change in order to allow for a general metric tensor
template<unsigned int P, unsigned Q>
struct signature
{
   static const unsigned int p = P;
   static const unsigned int q = Q;
   static const unsigned int dimension = P+Q;

   static const conf_t signature_bitmap = (Power<2, Q>::value-1)<<P;
};
template<typename ML, typename MR>
struct metric_combination_traits;

//combination of signature metrics -> negative signature dominant
template<unsigned int PL, unsigned QL, unsigned int PR, unsigned QR>
struct metric_combination_traits<signature<PL, QL>, signature<PR, QR>>
{
   static_assert(PL==PR || (PL<PR && QL==0) || (PR<PL && QR==0),
                 "Combination of different metrics: different number of positive signatures");
   static const unsigned int P = (QL==0) ? PR : PL;
   static const unsigned int Q = (QL>QR) ? QL : QR;

   typedef signature<P, Q> metric;
   //typedef ::gaalet::metric<SL | SR> metric;
};

template<typename M, typename T = default_element_t>
struct algebra
{
   typedef M metric;

   typedef T element_t;

   //no cpp0x template aliases supported by gcc yet
   /*template<conf_t head, conf_t... tail>
   using mv = multivector<typename insert_element<head, typename mv<tail...>::clist>::clist>;

   template<>
   using mv = multivector<cl_null>;*/

   //multivector configuration elements unpacking
   template<conf_t... elements>
   struct mv;
   template<conf_t head, conf_t... tail>
   struct mv<head, tail...>
   {
      typedef multivector<typename insert_element<head, typename mv<tail...>::type::clist>::clist, metric, element_t> type;
   };
   template<conf_t head>
   struct mv<head>
   {
      typedef multivector<configuration_list<head, cl_null>, metric, element_t> type;
   };
};

//default multivector: euclidean space (in signature bitmap, P part won't matter if Q part equals zero)
template<conf_t... elements>
struct mv
{
   typedef typename algebra<signature<0,0>>::mv<elements...>::type type;
};


//Algebra definitions
//typedef algebra<signature<4,1>> cm;
struct cm {
   typedef ::gaalet::algebra<signature<4,1>> algebra;

   template<conf_t... elements>
   struct mv
   {
      typedef typename algebra::mv<elements...>::type type;
   };
};


} //end namespace gaalet

#endif
