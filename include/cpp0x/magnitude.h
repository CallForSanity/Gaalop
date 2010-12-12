#ifndef __GAALET_MAGNITUDE_H
#define __GAALET_MAGNITUDE_H

#include "geometric_product.h"
#include "reverse.h"
#include "grade.h"


namespace gaalet {

template<class A>
struct magnitude : public expression<magnitude<A>>
{
   typedef configuration_list<0x00, cl_null> clist;

   typedef typename A::metric metric;
   
   typedef typename A::element_t element_t;

   magnitude(const A& a_)
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
gaalet::magnitude<A>
magnitude(const gaalet::expression<A>& a)
{
   return gaalet::magnitude<A>(a);
}




/*template <class A> inline
auto magnitude(const gaalet::expression<A>& a) -> decltype(grade<0>((~a)*a))
{
   return grade<0>((~a)*a);
}*/

#endif
