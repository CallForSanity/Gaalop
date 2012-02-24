#pragma once

#include <boost/numeric/interval.hpp>

typedef boost::numeric::interval<float> I;

#define center(x) ((x).lower()+(x).upper())/2.0f