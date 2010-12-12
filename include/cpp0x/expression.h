#ifndef __GAALET_EXPRESSION_H
#define __GAALET_EXPRESSION_H

#include "configuration_list.h"


#include <iostream>
#include <iomanip>


namespace gaalet
{

//Wrapper class for CRTP
template <class E>
struct expression {
   operator const E& () const {
      return *static_cast<const E*>(this);
   }
};

} //end namespace gaalet


#endif
