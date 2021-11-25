package de.gaalop.visualizer.ia_math;

/**
 * IANarrow.java 
 *   -- classes implementing narrowing of arithmetic and elementary functions,
 *      as part of the "ia_math library" version 0.1beta1, 10/97
 * 
 * <p>
 * Copyright (C) 2000 Timothy J. Hickey
 * <p>
 * License: <a href="http://interval.sourceforge.net/java/ia_math/licence.txt">zlib/png</a>
 * <p>
 * the class RealIntervalNarrow contains methods for narrowing
 * the arithmetic operations and elementary functions.
 */
public class IANarrow
{

  public static boolean 
    narrow_add(RealInterval c,RealInterval a,RealInterval b) {
      try {
        c.intersect(IAMath.add(a,b));
        a.intersect(IAMath.sub(c,b));
        b.intersect(IAMath.sub(c,a));
        return true;
      }
      catch (IAException e) {
        return false;
      }
  }

  public static boolean narrow_sub(
       RealInterval c,RealInterval a,RealInterval b) {
    return narrow_add(a,c,b);
  }

  /* z = x*y */
  public static boolean narrow_mul(
       RealInterval z,RealInterval x,RealInterval y) {
    try {
       z.intersect(IAMath.mul(x,y));
       IAMath.intersect_odiv(y,z,x);
       IAMath.intersect_odiv(x,z,y);
       return true;
    } catch (IAException e) {
       return false;
    }
  }

  public static boolean narrow_div(
       RealInterval a,RealInterval b,RealInterval c) {
    return 
      narrow_mul(b,a,c);
  }

  public static boolean narrow_uminus(
       RealInterval a,RealInterval b) {
    try {
      a.intersect(IAMath.uminus(b));
      b.intersect(IAMath.uminus(a));
      return true;
    } catch (IAException e) {
      return false;
    }
  }

  public static boolean 
    narrow_exp(RealInterval a,RealInterval b) {
    double tmp;
    try {
      b.intersect(IAMath.exp(a));
      a.intersect(IAMath.log(b));
      return true;
    } catch (IAException e) {
      return false;
    }
  }

  public static boolean 
    narrow_log(RealInterval a,RealInterval b) {
    return narrow_exp(b,a);
  }






  public static boolean 
    narrow_sin(RealInterval a,RealInterval b) {
      try {
        b.intersect(IAMath.sin(a));
        return true;
      }
      catch (IAException e) {
        return false;
      }
    //    System.out.println("narrow_sin not yet implemented");
  }

  public static boolean 
    narrow_cos(RealInterval a,RealInterval b) {
      try {
        b.intersect(IAMath.cos(a));
        return true;
      }
      catch (IAException e) {
        return false;
      }
    //    System.out.println("narrow_cos not yet implemented");
  }

  public static boolean 
    narrow_tan(RealInterval a,RealInterval b) {
      try {
        b.intersect(IAMath.tan(a));
        return true;
      }
      catch (IAException e) {
        return false;
      }
      //    System.out.println("narrow_tan not yet implemented");
  }

  // a = asin(b)
  public static boolean 
    narrow_asin(RealInterval b,RealInterval a) {
    try {
         b.intersect(new RealInterval(-1.0,1.0));
         a.intersect(IAMath.asin(b));
         b.intersect(IAMath.sin(a));
         return true;
    } catch (IAException e) {
      return false;
    }
  }

  // a = acos(b)
  public static boolean 
    narrow_acos(RealInterval b,RealInterval a) {
    try {
         b.intersect(new RealInterval(-1,1));
         a.intersect(IAMath.acos(b));
         b.intersect(IAMath.cos(a));
         return true;
    } catch (IAException e) {
      return false;
    }
  }


  // a = atan(b) 
  public static boolean 
    narrow_atan(RealInterval b,RealInterval a) {
    try {
      a.intersect(IAMath.atan(b));
      b.intersect(IAMath.tan(a));
      return true;
    } catch (IAException e) {
      return false;
    }
  }






  public static boolean 
    narrow_sin2pi(RealInterval a,RealInterval b) {
    System.out.println("narrow_sin2pi not yet implemented");
    return true;
  }

  public static boolean 
    narrow_cos2pi(RealInterval a,RealInterval b) {
    System.out.println("narrow_cos2pi not yet implemented");
    return true;
  }

  public static boolean 
    narrow_tan2pi(RealInterval a,RealInterval b) {
    System.out.println("narrow_tan2pi not yet implemented");
    return true;
  }

  // a = asin(b)
  public static boolean 
    narrow_asin2pi(RealInterval a,RealInterval b) {
    System.out.println("asin2pi not yet implemented");
    return true;
  }

  // a = acos(b)
  public static boolean 
    narrow_acos2pi(RealInterval a,RealInterval b) {
    System.out.println("acos2pi not yet implemented");
    return true;

  }


  // a = atan(b) 
  public static boolean 
    narrow_atan2pi(RealInterval a,RealInterval b) {
    System.out.println("atan2pi not yet implemented");
    return true;
  }




  /**
   * z = x^y, where y is an integer
   */
  public static boolean narrow_carot(
       RealInterval z,RealInterval x,RealInterval y) {
    try {
        //          System.out.println("narrow_carot z=x^y with (x,y,z)= "+x+y+z);
        //          System.out.println(" and  x^y = "+IAMath.integerPower(x,y));
        z.intersect(IAMath.integerPower(x,y));
	//	  System.out.println(" did z=z cap x^y with (x,y,z)= "+x+y+z);
        IAMath.intersectIntegerRoot(z,y,x);
	//	  System.out.println(" did x=x cap z^1/y with (x,y,z)= "+x+y+z);
        return true;
    } catch (IAException e) {
      return false;
    }
  }

  /**
   * z = x**y, assuming x > 0 and y is a real number
   */
  public static boolean narrow_power(
       RealInterval z,RealInterval x,RealInterval y) {
    try {
       z.intersect(IAMath.power(x,y));
       x.intersect(IAMath.power(z,
                          IAMath.odiv(new RealInterval(1.0),y)));
       y.intersect(IAMath.div(IAMath.log(z),IAMath.log(x)));
       return true;
    } catch (IAException e) {
       return false;
    }
  }
      

  public static boolean narrow_semi(
       RealInterval a,RealInterval b,RealInterval c) {
    return true;
  }
  public static boolean narrow_colon_equals(
       RealInterval a,RealInterval b,RealInterval c) {
    b.lo = c.lo; b.hi = c.hi;
    return b.nonEmpty();
  }

  public static boolean 
  narrow_equals(RealInterval b,RealInterval c) {
    if ((b.lo==b.hi) && b.equals(c))
      return(true);
    else 
      try {
        b.intersect(c);
        c.intersect(b);
        return true;
    } catch (IAException e) {
        return false;
    }
  }

  public static boolean 
  narrow_eq(RealInterval a,RealInterval b,RealInterval c) {
    if ((b.lo==b.hi) && b.equals(c)) {
      a.lo = 1.0; a.hi = 1.0; 
      return(true);
    }
    else
      try {
        b.intersect(c);
        c.intersect(b);
        return true;
    } catch (IAException e) {
        return false;
    }
  }

  /* x < y */
  public static boolean narrow_lt(
       RealInterval result,RealInterval x,RealInterval y) {
    try {
       if (y.lo < x.lo) y.lo = x.lo;
       if (x.hi > y.hi) x.hi = y.hi;
       if (y.hi <= x.lo)
         return false;
       else if (x.hi < y.lo) {
         result.lo = 1.0; result.hi=1.0;
       }
       else {
         result.intersect(new RealInterval(0.0,1.0));
       }
       return(x.nonEmpty()&&y.nonEmpty());
    } catch (IAException e) {
      return false;
    }
  }

  public static boolean narrow_le(
       RealInterval r,RealInterval x,RealInterval y) {
    try {
       if (y.lo <= x.lo) y.lo = x.lo;
       if (x.hi >= y.hi) x.hi = y.hi;
       if (y.hi < x.lo)
         return false;
       else if (x.hi <= y.lo) {
         r.lo = 1.0; r.hi=1.0;
       }
       else {
         r.intersect(new RealInterval(0.0,1.0));
       }
       return(x.nonEmpty()&&y.nonEmpty());
    } catch (IAException e) {
      return false;
    }
  }

  public static boolean narrow_gt(
       RealInterval r,RealInterval x,RealInterval y) {
    return narrow_lt(r,y,x);
  }

  public static boolean narrow_ge(
       RealInterval r,RealInterval x,RealInterval y) {
    return narrow_le(r,y,x);
  }

  public static boolean narrow_ne(
       RealInterval r,RealInterval x,RealInterval y) {
    return ((x.lo < x.hi) || (y.lo < y.hi) || (x.lo != y.lo));
  }


}

