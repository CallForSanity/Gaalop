#ifndef __GAALET_PART_H
#define __GAALET_PART_H

#include "utility.h"

namespace gaalet {

/*C++0x only: template<class A, conf_t... elements>
struct part : public expression<part<A, elements...> >
{
   typedef typename mv<elements...>::type::clist clist;

   typedef typename A::metric metric;

   part(const A& a_)
      :  a(a_)
   { }

   template<conf_t conf>
   element_t element() const {
      return a.element<conf>();
   }

protected:
   const A& a;
};*/

template<class T, class A>
struct part_type: public expression<part_type<T, A> >
{
   typedef typename T::clist clist;

   typedef typename A::metric metric;

   typedef typename A::element_t element_t;

   part_type(const A& a_)
      :  a(a_)
   { }

   template<conf_t conf>
   GAALET_CUDA_HOST_DEVICE
   element_t element() const {
      return a.template element<conf>();
   }

protected:
   const A& a;
};


}  //end namespace gaalet

/*C++0x only: template<gaalet::conf_t... elements, class A> inline
gaalet::part<A, elements...>
part(const gaalet::expression<A>& a) {
   return gaalet::part<A, elements...>(a);
}*/

template<class T, class A> inline
GAALET_CUDA_HOST_DEVICE
gaalet::part_type<T, A>
part_type(const gaalet::expression<A>& a) {
   return gaalet::part_type<T, A>(a);
}

#endif
