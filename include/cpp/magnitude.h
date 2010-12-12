#ifndef __GAALET_MAGNITUDE_H
#define __GAALET_MAGNITUDE_H

#include "geometric_product.h"
#include "reverse.h"
#include "grade.h"


namespace gaalet {

template<class A>
struct Magnitude : public expression<Magnitude<A> >
{
   typedef configuration_list<0x00, cl_null> clist;

   typedef typename A::metric metric;
   
   typedef typename A::element_t element_t;

   Magnitude(const A& a_)
      :  a(a_)
   { }

   template<conf_t conf>
   element_t element() const {
      return (conf==0x00) ? sqrt(eval(::grade<0>((~a)*a))) : 0.0;
   }

protected:
   const A& a;
};

}  //end namespace gaalet


template <class A> inline
gaalet::Magnitude<A>
magnitude(const gaalet::expression<A>& a)
{
   return gaalet::Magnitude<A>(a);
}


#endif
