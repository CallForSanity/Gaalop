#ifndef __GAALET_REVERSE_H
#define __GAALET_REVERSE_H

#include "utility.h"

namespace gaalet {

template<class A>
struct reverse : public expression<reverse<A>>
{
   typedef typename A::clist clist;

   typedef typename A::metric metric;
   
   typedef typename A::element_t element_t;

   reverse(const A& a_)
      :  a(a_)
   { }

   template<conf_t conf>
   element_t element() const {
      return a.element<conf>() * Power<-1, BitCount<conf>::value*(BitCount<conf>::value-1)/2>::value;
   }

protected:
   const A& a;
};


}  //end namespace gaalet

template <class A> inline
gaalet::reverse<A>
operator~(const gaalet::expression<A>& a) {
   return gaalet::reverse<A>(a);
}

#endif
