#ifndef __GAALET_SCALAR_H
#define __GAALET_SCALAR_H

#include "grade.h"
#include "geometric_product.h"

template <class L, class R> inline
gaalet::Grade<0, gaalet::geometric_product<L, R> >
scalar(const gaalet::expression<L>& l, const gaalet::expression<R>& r)
{
   return grade<0>(l*r);
}


#endif
