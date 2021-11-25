package de.gaalop.visualizer.ia_math;


/**
 * IAMath.java 
 *   -- classes implementing interval arithmetic versions
 *      of the arithmetic and elementary functions,
 *      as part of the "ia_math library" version 0.1beta1, 10/97
 * 
 * <p>
 * Copyright (C) 2000 Timothy J. Hickey
 * <p>
 * License: <a href="http://interval.sourceforge.net/java/ia_math/licence.txt">zlib/png</a>
 * <p>
 * the class IAMath contains methods for performing basic
 * arithmetic operations on intervals. Currently the
 * elementary functions rely on the underlying implementation
 * which uses the netlib fdlibm library. The resulting code
 * is therefore probably unsound for the transcendental functions.
 */

public class IAMath
{

  public static final boolean nonempty(RealInterval x) {
    return (x.lo <= x.hi);
  }

  public static RealInterval intersect(RealInterval x, RealInterval y) 
    throws IAException
  {
    return
      new RealInterval(Math.max(x.lo,y.lo),Math.min(x.hi,y.hi));
  }

  public static RealInterval union(RealInterval x, RealInterval y) 
    throws IAException
  {
    return
      new RealInterval(Math.min(x.lo,y.lo),Math.max(x.hi,y.hi));
  }

  public static RealInterval add(RealInterval x, RealInterval y) {
    RealInterval z = new RealInterval();
    z.lo = RMath.add_lo(x.lo,y.lo);
    z.hi = RMath.add_hi(x.hi,y.hi);
    return(z);
  }

  public static RealInterval sub(RealInterval x, RealInterval y) {
    RealInterval z = new RealInterval();
    z.lo = RMath.sub_lo(x.lo,y.hi);
    z.hi = RMath.sub_hi(x.hi,y.lo);
    return(z);
  }

  public static RealInterval mul(RealInterval x, RealInterval y) {
    RealInterval z = new RealInterval();

    if (((x.lo==0.0)&&(x.hi==0.0)) || ((y.lo==0.0)&&(y.hi==0.0))) {
      z.lo = 0.0; z.hi = RMath.NegZero;
    }
    else if (x.lo >= 0.0) {
      if (y.lo >= 0.0) {
        z.lo = Math.max(0.0,RMath.mul_lo(x.lo,y.lo));
        z.hi = RMath.mul_hi(x.hi,y.hi);
      }
      else if (y.hi <= 0.0) {
        z.lo = RMath.mul_lo(x.hi,y.lo);
        z.hi = Math.min(0.0,RMath.mul_hi(x.lo,y.hi));
      }
      else {
        z.lo = RMath.mul_lo(x.hi,y.lo);
        z.hi = RMath.mul_hi(x.hi,y.hi);
      }
    }
    else if (x.hi <= 0.0) {
      if (y.lo >= 0.0) {
        z.lo = RMath.mul_lo(x.lo,y.hi);
        z.hi = Math.min(0.0,RMath.mul_hi(x.hi,y.lo));
      }
      else if (y.hi <= 0.0) {
        z.lo = Math.max(0.0,RMath.mul_lo(x.hi,y.hi));
        z.hi = RMath.mul_hi(x.lo,y.lo);
      }
      else {
        z.lo = RMath.mul_lo(x.lo,y.hi);
        z.hi = RMath.mul_hi(x.lo,y.lo);
      }
    }
    else {
      if (y.lo >= 0.0) {
        z.lo = RMath.mul_lo(x.lo,y.hi);
        z.hi = RMath.mul_hi(x.hi,y.hi);
      }
      else if (y.hi <= 0.0) {
        z.lo = RMath.mul_lo(x.hi,y.lo);
        z.hi = RMath.mul_hi(x.lo,y.lo);
      }
      else {
        z.lo = Math.min(
                  RMath.mul_lo(x.hi,y.lo),
                  RMath.mul_lo(x.lo,y.hi));
        z.hi = Math.max(
                  RMath.mul_hi(x.lo,y.lo),
                  RMath.mul_hi(x.hi,y.hi));
      }
    }

    //    System.out.println("mul("+x+","+y+")="+z); 

    return(z);
  }




  /**
   * The Natural Extension of division in Interval Arithmetic 
   * @param x an interval
   * @param y an interval
   * @returns the smallest IEEE interval containing the set x/y.
   * @exception IAException
   *  is thrown with the message <code>Division by Zero</code>.
   */
  public static RealInterval div(RealInterval x, RealInterval y) {
    if ((y.lo==0.0)&&(y.hi==0.0))
      throw new IAException("div(X,Y): Division by Zero");
    else return odiv(x,y);
  }

  /**
   * This is identical to the standard <code>div(x,y)</code> method,
   * except that if <code>y</code> is identically zero, then
   * the infinite interval (-infinity, infinity) is returned.
   */

  public static RealInterval odiv(RealInterval x, RealInterval y) {
    RealInterval z = new RealInterval();
   

    if (((x.lo<=0.0)&&(0.0<=x.hi)) && ((y.lo<=0.0)&&(0.0<=y.hi))) {
      z.lo = Double.NEGATIVE_INFINITY; z.hi = Double.POSITIVE_INFINITY;
    }
    else {
      if (y.lo==0.0) y.lo = 0.0;
      if (y.hi==0.0) y.hi = RMath.NegZero;

    if (x.lo >= 0.0) {
      if (y.lo >= 0.0) {
        z.lo = Math.max(0.0,RMath.div_lo(x.lo,y.hi));
        z.hi = RMath.div_hi(x.hi,y.lo);
      }
      else if (y.hi <= 0.0) {
        z.lo = RMath.div_lo(x.hi,y.hi);
        z.hi = Math.min(0.0,RMath.div_hi(x.lo,y.lo));
      }
      else {
        z.lo = Double.NEGATIVE_INFINITY;
        z.hi = Double.POSITIVE_INFINITY;
      }
    }
    else if (x.hi <= 0.0) {
      if (y.lo >= 0.0) {
        z.lo = RMath.div_lo(x.lo,y.lo);
        z.hi = Math.min(0.0,RMath.div_hi(x.hi,y.hi));
      }
      else if (y.hi <= 0.0) {
        z.lo = Math.max(0.0,RMath.div_lo(x.hi,y.lo));
        z.hi = RMath.div_hi(x.lo,y.hi);
      }
      else {
        z.lo = Double.NEGATIVE_INFINITY;
        z.hi = Double.POSITIVE_INFINITY;
      }
    }
    else {
      if (y.lo >= 0.0) {
        z.lo = RMath.div_lo(x.lo,y.lo);
        z.hi = RMath.div_hi(x.hi,y.lo);
      }
      else if (y.hi <= 0.0) {
        z.lo = RMath.div_lo(x.hi,y.hi);
        z.hi = RMath.div_hi(x.lo,y.hi);
      }
      else {
        z.lo = Double.NEGATIVE_INFINITY;
        z.hi = Double.POSITIVE_INFINITY;
      }
    }
   }

    //  System.out.println("div("+x+","+y+")="+z); 

  return(z);

  }


  /**
   * this performs (y := y intersect z/x) and succeeds if
   * y is nonempty.
   */
  public static boolean intersect_odiv(
       RealInterval y,RealInterval z,RealInterval x)
    throws IAException
  {
    if ((x.lo >= 0) || (x.hi <= 0)) {
      y.intersect(IAMath.odiv(z,x));
      return true;
    }else
    if (z.lo >0) {
      double tmp_neg = RMath.div_hi(z.lo,x.lo);
      double tmp_pos = RMath.div_lo(z.lo,x.hi);
      if (   ((y.lo > tmp_neg) || (y.lo == 0))
          && (y.lo < tmp_pos)) y.lo = tmp_pos;
      if (   ((y.hi < tmp_pos) || (y.hi == 0))
          && (y.hi > tmp_neg)) y.hi = tmp_neg;
      if (y.lo <= y.hi) return true;
      else throw new IAException("intersect_odiv(Y,Z,X): intersection is an Empty Interval");
    }
    else if (z.hi < 0) {
      double tmp_neg = RMath.div_hi(z.hi,x.hi);
      double tmp_pos = RMath.div_lo(z.hi,x.lo);
      if (   ((y.lo > tmp_neg) || (y.lo == 0))
          && (y.lo < tmp_pos)) y.lo = tmp_pos;
      if (   ((y.hi < tmp_pos) || (y.hi == 0))
          && (y.hi > tmp_neg)) y.hi = tmp_neg;
      if (y.lo <= y.hi) return true;
      else throw new IAException("intersect_odiv(Y,Z,X): intersection is an Empty Interval");
    }
    else return(true);
  }





  public static RealInterval uminus(RealInterval x) {
    RealInterval z = new RealInterval();
    z.lo = -x.hi;
    z.hi = -x.lo;
    return(z);
  }

  public static RealInterval exp(RealInterval x) {
    RealInterval z = new RealInterval();
    z.lo = RMath.exp_lo(x.lo);
    z.hi = RMath.exp_hi(x.hi);
    //    System.out.println("exp("+x+")= "+z);
    return(z);
  }

  public static RealInterval log(RealInterval x) 
    throws IAException {
    RealInterval z = new RealInterval();
    if (x.hi <= 0) 
      throw new IAException("log(X): X<=0 not allowed");

    if (x.lo < 0) x.lo = 0.0;

    z.lo = RMath.log_lo(x.lo);
    z.hi = RMath.log_hi(x.hi);
    //    System.out.println("log("+x+")= "+z);
    return(z);
  }





  public static RealInterval sin(RealInterval x) {
    RealInterval y = new RealInterval();
    RealInterval z = new RealInterval();
    y = div(x,new RealInterval(RMath.prevfp(2*Math.PI),RMath.nextfp(2*Math.PI)));
    z = sin2pi(y);
    return(z);
  }

  public static RealInterval cos(RealInterval x) {
    RealInterval y = new RealInterval();
    RealInterval z = new RealInterval();
    y = div(x,new RealInterval(RMath.prevfp(2*Math.PI),RMath.nextfp(2*Math.PI)));
    z = cos2pi(y);
    return(z);
  }

  public static RealInterval tan(RealInterval x) {
    RealInterval y = new RealInterval();
    RealInterval z = new RealInterval();
    y = div(x,new RealInterval(RMath.prevfp(2*Math.PI),RMath.nextfp(2*Math.PI)));
    z = tan2pi(y);
    return(z);
  }

  public static RealInterval asin(RealInterval x) 
    throws IAException {
    RealInterval z = new RealInterval();
    x.intersect(new RealInterval(-1.0,1.0));
    z.lo = RMath.asin_lo(x.lo);
    z.hi = RMath.asin_hi(x.hi);
    return(z);
  }

  public static RealInterval acos(RealInterval x) {
    RealInterval z = new RealInterval();
    z.lo = RMath.acos_lo(x.hi);
    z.hi = RMath.acos_hi(x.lo);
    return(z);
  }

  public static RealInterval atan(RealInterval x) {
    RealInterval z = new RealInterval();
    z.lo = RMath.atan_lo(x.lo);
    z.hi = RMath.atan_hi(x.hi);
    return(z);
  }



  public static RealInterval sinRange(int a, int b) {
    switch(4*a + b) {
    case  0: return(new RealInterval(-1.0, 1.0));
    case  1: return(new RealInterval( 1.0, 1.0));
    case  2: return(new RealInterval( 0.0, 1.0));
    case  3: return(new RealInterval(-1.0, 1.0));
    case  4: return(new RealInterval(-1.0, 0.0));
    case  5: return(new RealInterval(-1.0, 1.0));
    case  6: return(new RealInterval( 0.0, 0.0));
    case  7: return(new RealInterval(-1.0, 0.0));
    case  8: return(new RealInterval(-1.0, 0.0));
    case  9: return(new RealInterval(-1.0, 1.0));
    case 10: return(new RealInterval(-1.0, 1.0));
    case 11: return(new RealInterval(-1.0,-1.0));
    case 12: return(new RealInterval( 0.0, 0.0));
    case 13: return(new RealInterval( 0.0, 1.0));
    case 14: return(new RealInterval( 0.0, 1.0));
    case 15: return(new RealInterval(-1.0, 1.0));
    }
    System.out.println("ERROR in sinRange("+a+","+b+")");
    return new RealInterval(-1,1);
  }


  static RealInterval sin2pi0DI(double x) {
     return new RealInterval(RMath.sin2pi_lo(x),RMath.sin2pi_hi(x));
  }

  static RealInterval cos2pi0DI(double x) {
     return new RealInterval(RMath.cos2pi_lo(x),RMath.cos2pi_hi(x));
  }

  /* this returns an interval containing sin(x+a/4)
     assuming -1/4 <= x < 1/4, and a in {0,1,2,3}
     */
  static RealInterval eval_sin2pi(double x, int a) {
    switch (a) {
    case 0:  return sin2pi0DI(x);
    case 1:  return cos2pi0DI(x);
    case 2:  return uminus(sin2pi0DI(x));
    case 3:  return uminus(cos2pi0DI(x));
    }
    System.out.println("ERROR in eval_sin2pi("+x+","+a+")");
    return new RealInterval();
  }
    

  public static RealInterval sin2pi(RealInterval x) {
    RealInterval r = new RealInterval();
    RealInterval z=null;
    RealInterval y1=null,y2=null;
    int a=0,b=0;
    double t1=0,t2=0;
    double w;

    double m1,m2,n1,n2,z1,z2,width;
    int j1,j2;
    long mlo,mhi;

//    System.out.println("ENTERING sin2pi("+x+")");

    if (Double.isInfinite(x.lo) ||
        Double.isInfinite(x.hi)) {
      return new RealInterval(-1.0,1.0);
    }

    m1 = Math.rint(4*x.lo);
    j1 = (int) Math.round(m1 - 4*Math.floor(m1/4.0));
    z1 = RMath.sub_lo(x.lo,m1/4.0);
    n1 = Math.floor(m1/4.0);

    m2 = Math.rint(4*x.hi);
    j2 = (int) Math.round(m2 - 4*Math.floor(m2/4.0));
    z2 = RMath.sub_hi(x.hi,m2/4.0);
    n2 = Math.floor(m2/4.0);

    //    System.out.println("in sin2pi: "+" x.lo="+x.lo+" x.hi="+x.hi);
    //    System.out.println("         : "+" m1="+m1+" m2="+m2);
    //    System.out.println("         : "+" z1="+z1+" z2="+z2);
    //    System.out.println("         : "+" j1="+j1+" j2="+j2);
    //    System.out.println("         : "+" n1="+n1+" n2="+n2);

    if ((z1<= -0.25) || (z1 >= 0.25) ||
        (z2<= -0.25) || (z2 >= 0.25)) 
      return new RealInterval(-1.0,1.0);

    mlo = (z1>=0)?j1:j1-1;
    mhi = (z2<=0)?j2:j2+1;

    width = (mhi-mlo+4*(n2-n1));

    //    System.out.println("         : "+" mlo="+mlo+" mhi="+mhi);
    //    System.out.println("         : "+" width"+width);

    if (width > 4)
      return new RealInterval(-1.0,1.0);


    y1 = eval_sin2pi(z1,j1);
    y2 = eval_sin2pi(z2,j2);

    z = union(y1,y2);

    a = (int) ((mlo +4)%4);
    b = (int) ((mhi +3)%4);
   

    //    System.out.println("in sin2pi: "+" y1="+y1+" y2="+y2+" z="+z+
    //               "\n  j1="+j1+" j2="+j2+" mlo="+mlo+" mhi="+mhi +
    //               "\n  w ="+width+" a="+a+" b="+b+"\n  sinRange="+sinRange(a,b));
    //    if (r.lo < 0) a = (a+3)%4;
    //    if (r.hi < 0) b = (b+3)%4;

    if (width <= 1)
      return z;
    else {
      //      return union(z,sinRange(a,b));
      return union(z,sinRange(a,b));
    }
  }

  public static RealInterval cos2pi(RealInterval x) {
    RealInterval r = new RealInterval();
    RealInterval z=null;
    RealInterval y1=null,y2=null;
    int a=0,b=0;
    double t1=0,t2=0;
    double w;

    double m1,m2,n1,n2,z1,z2,width;
    int j1,j2;
    long mlo,mhi;

    if (Double.isInfinite(x.lo) ||
        Double.isInfinite(x.hi)) {
      return new RealInterval(-1.0,1.0);
    }

    m1 = Math.rint(4*x.lo);
    j1 = (int) Math.round(m1 - 4*Math.floor(m1/4.0));
    z1 = RMath.sub_lo(x.lo,m1/4.0);
    n1 = Math.floor(m1/4.0);

    m2 = Math.rint(4*x.hi);
    j2 = (int) Math.round(m2 - 4*Math.floor(m2/4.0));
    z2 = RMath.sub_hi(x.hi,m2/4.0);
    n2 = Math.floor(m2/4.0);

    if ((z1<= -0.25) || (z1 >= 0.25) ||
        (z2<= -0.25) || (z2 >= 0.25)) 
      return new RealInterval(-1.0,1.0);

    mlo = (z1>=0)?j1:j1-1;
    mhi = (z2<=0)?j2:j2+1;

    width = (mhi-mlo+4*(n2-n1));

    if (width > 4)
      return new RealInterval(-1.0,1.0);


    y1 = eval_sin2pi(z1,(j1+1)%4);
    y2 = eval_sin2pi(z2,(j2+1)%4);

    z = union(y1,y2);

    a = (int) ((mlo +4+1)%4);
    b = (int) ((mhi +3+1)%4);
   

    //    System.out.println("in sin2pi: "+" y1="+y1+" y2="+y2+" z="+z+
    //               "\n  j1="+j1+" j2="+j2+" mlo="+mlo+" mhi="+mhi +
    //               "\n  w ="+width+" a="+a+" b="+b+"\n  sinRange="+sinRange(a,b));
    //    if (r.lo < 0) a = (a+3)%4;
    //    if (r.hi < 0) b = (b+3)%4;

    if (width <= 1)
      return z;
    else {
      //      return union(z,sinRange(a,b));
      return union(z,sinRange(a,b));
    }
  }

  public static RealInterval tan2pi(RealInterval x) {
    return(div(sin2pi(x),cos2pi(x)));
  }


  public static RealInterval asin2pi(RealInterval x) 
    throws IAException {
    RealInterval z = new RealInterval();
    x.intersect(new RealInterval(-1.0,1.0));
    z.lo = RMath.asin2pi_lo(x.lo);
    z.hi = RMath.asin2pi_hi(x.hi);
    return(z);
  }

  public static RealInterval acos2pi(RealInterval x) {
    RealInterval z = new RealInterval();
    z.lo = RMath.acos2pi_lo(x.hi);
    z.hi = RMath.acos2pi_hi(x.lo);
    return(z);
  }

  public static RealInterval atan2pi(RealInterval x) {
    RealInterval z = new RealInterval();
    z.lo = RMath.atan2pi_lo(x.lo);
    z.hi = RMath.atan2pi_hi(x.hi);
    return(z);
  }






  public static RealInterval midpoint(RealInterval x) {
    RealInterval z = new RealInterval();
    z.lo = (x.lo + x.hi)/2.0;
    z.hi = z.lo;

    if ((Double.NEGATIVE_INFINITY < z.lo) &&
        (Double.POSITIVE_INFINITY > z.lo)) {
      return(z);
    }
    else if ((Double.NEGATIVE_INFINITY == x.lo)) {
      if (x.hi > 0.0) {
        z.lo = 0.0; z.hi = z.lo; return(z);
      } else if (x.hi == 0.0){
        z.lo = -1.0; z.hi = z.lo; return(z);
      } else {
        z.lo = x.hi*2; z.hi = z.lo; return(z);
      }
    } else if ((Double.POSITIVE_INFINITY == x.hi)) {
      if (x.lo < 0.0) {
        z.lo = 0.0; z.hi = z.lo; return(z);
      } else if (x.lo == 0.0){
        z.lo = 1.0; z.hi = z.lo; return(z);
      } else {
        z.lo = x.lo*2; z.hi = z.lo; return(z);
      }
    } else {
      z.lo = x.lo; z.hi = x.hi;
      System.out.println("Error in RealInterval.midpoint");
      return(z);
    }
  }


  public static RealInterval leftendpoint(RealInterval x) {
    RealInterval z = new RealInterval();
    z.lo = x.lo;
    if ((Double.NEGATIVE_INFINITY < z.lo) &&
        (Double.POSITIVE_INFINITY > z.lo)) {
      z.hi = z.lo;
      return(z);
    }else {
      z.lo = RMath.nextfp(x.lo);
      z.hi = z.lo;
     return(z);
    }
  }


  public static RealInterval rightendpoint(RealInterval x) {
    RealInterval z = new RealInterval();
    z.lo = x.hi;
    if ((Double.NEGATIVE_INFINITY < z.lo) &&
        (Double.POSITIVE_INFINITY > z.lo)) {
      z.hi = z.lo;
      return(z);
    }else {
      z.lo = RMath.prevfp(x.hi);
      z.hi = z.lo;
     return(z);
    }
  }


  /**
   *  returns (x**y) computed as exp(y*log(x))
   */
  public static RealInterval power(RealInterval x, RealInterval y)
    throws IAException
 {

      if (x.hi <= 0) 
        throw new IAException("power(X,Y): X<=0 not allowed");
      else if (x.lo<0) {
        x.lo = 0.0;
      }

      RealInterval z = exp(mul(y,log(x)));

      return z;

  }

  /**
   * this is the Natural Interval extension of <code>|x|**y<\code}
   * where <code>x</code> is an interval and
   * <code>y</code> is a double.
   */
  public static RealInterval evenPower(RealInterval x, double y)
    throws IAException
  {
    double zlo,zhi;
    //    System.out.println("evenPower: x^y with (x,y) = "+x+" "+y);

    if (y == 0.0)
      return(new RealInterval(1.0));
    else if (y > 0.0) {
      if (x.lo >=0) {
        zlo = RMath.pow_lo(x.lo,y);
        zhi = RMath.pow_hi(x.hi,y);
      }else if (x.hi <=0) {
        zlo = RMath.pow_lo(-x.hi,y);
        zhi = RMath.pow_hi(-x.lo,y);
      }else {
        zlo = 0.0;
        zhi = Math.max(RMath.pow_lo(-x.lo,y),RMath.pow_hi(x.hi,y));
      }
    }
    else if (y < 0.0) {
      return div(new RealInterval(1.0),evenPower(x,-y));
    }
    else
      throw new IAException("evenPower(X,y): y=Nan not allowed");

    //    System.out.println("evenPower: computed x^y = ["+zlo+","+zhi+"]");

    return new RealInterval(zlo,zhi);
  }

  /**
   * this is the Natural Interval extension of <code>sgn(x)*(|x|**y)<\code}
   * where <code>x</code> is an interval and
   * <code>y</code> is a double.
   */
  public static RealInterval oddPower(RealInterval x, double y)
    throws IAException
  {
    double zlo,zhi;

    //    System.out.println("oddPower: x^y with (x,y) = "+x+" "+y);

    if (y == 0.0) {
      if (x.lo > 0.0)
        return(new RealInterval(1.0));
      else if (x.hi < 0.0)
        return(new RealInterval(-1.0));
      else
        return(new RealInterval(-1.0,1.0));
    }
    else if (y > 0.0) {
      if (x.lo >=0) {
         zlo = RMath.pow_lo(x.lo,y); 
         zhi = RMath.pow_hi(x.hi,y); }
      else if (x.hi <=0) {
         zlo = -RMath.pow_hi(-x.lo,y);
         zhi = -RMath.pow_lo(-x.hi,y);}
      else {
        zlo = -RMath.pow_hi(-x.lo,y);
        zhi =  RMath.pow_hi(x.hi,y); }
    }
    else if (y < 0.0) {
      return div(new RealInterval(1.0),oddPower(x,-y));
    }
    else
      throw new IAException("oddPower(X,y): X = NaN not allowed");

    //    System.out.println("oddPower: computed x^y = ["+zlo+","+zhi+"]");

    return new RealInterval(zlo,zhi);



  }




  /**
   * this is the Natural Interval extension of <code>xpos**(1/y)<\code}
   * where <code>x</code> is an interval and <code>xpos</code> is the
   * set of positive numbers contained in x and
   * <code>y</code> is a non-zero double.
   */
  public static RealInterval evenRoot(RealInterval x, double y)
    throws IAException
  {
    double ylo,yhi,zlo,zhi,zlo1,zhi1;

    //    System.out.println("evenRoot x^(1/y) with (x,y) = "+x+y);

    if (y == 0.0)
      throw new IAException("evenRoot(X,y): y=0 not allowed");
    else if (y > 0.0) {
      ylo = RMath.div_lo(1.0,y);
      yhi = RMath.div_hi(1.0,y);

      //   System.out.println("evenRoot with (ylo,yhi) = "+ylo+yhi);

      if (x.lo >= 1.0)
        zlo = RMath.pow_lo(x.lo,ylo);
      else if (x.lo >=  0.0)
        zlo = RMath.pow_lo(x.lo,yhi);
      else
        zlo = 0.0;

      //   System.out.println("evenRoot with zlo = "+zlo);

      if (x.hi >= 1.0)
        zhi = RMath.pow_hi( x.hi,yhi);
      else if (x.lo >=  0.0)
        zhi = RMath.pow_hi( x.hi,ylo);
      else 
        throw new IAException("evenRoot(X,y): X <=0 not allowed");

      //   System.out.println("evenRoot with zhi = "+zhi);

      return new RealInterval(zlo,zhi);
    }
    else if (y < 0.0) {
      return div(new RealInterval(1.0),evenRoot(x,-y));
    }
    else
      throw new IAException("evenRoot(X,y): y=NaN not allowed");
  }

  /**
   * this is the Natural Interval extension of <code>sgn(x)*|x|**(1/y)<\code}
   * where <code>x</code> is an interval and
   * <code>y</code> is a non-zero double.
   */
  public static RealInterval oddRoot(RealInterval x, double y)
    throws IAException
  {
    double ylo,yhi,zlo,zhi;

    if (y == 0.0)
      //      throw new IAException("oddRoot(X,y): y=0 not allowed");
      return RealInterval.fullInterval();
    else if (y > 0.0) {
      ylo = RMath.div_lo(1.0,y);
      yhi = RMath.div_hi(1.0,y);
      if (x.lo >= 1.0)
        zlo = RMath.pow_lo(x.lo,ylo);
      else if (x.lo >=  0.0)
        zlo = RMath.pow_lo(x.lo,yhi);
      else if (x.lo >= -1.0) 
        zlo = -RMath.pow_hi(-x.lo,ylo);
      else 
        zlo = -RMath.pow_hi(-x.lo,yhi);

      if (x.hi >= 1.0)
        zhi = RMath.pow_hi( x.hi,yhi);
      else if (x.hi >=  0.0)
        zhi = RMath.pow_hi( x.hi,ylo);
      else if (x.hi >= -1.0) 
        zhi = -RMath.pow_lo(-x.hi,yhi);
      else 
        zhi = -RMath.pow_lo(-x.hi,ylo);


      return new RealInterval(zlo,zhi);
    }
    else if (y < 0.0) {
      return div(new RealInterval(1.0),oddRoot(x,-y));
    }
    else
      throw new IAException("oddRoot(X,y): y=NaN not allowed");
  }









  /**
   * returns (x**y) assuming that y is restricted to integer values
   * currently returns (-infty,infty) if y is not bound to an
   * interval containing a single integer
   */
  public static RealInterval integerPower(RealInterval x, RealInterval y)
    throws IAException
 {
    double yy;

    //    System.out.println("integerPower: x^y with (x,y) = "+x+" "+y);

    if ((y.lo!=y.hi) || 
      (Math.IEEEremainder(y.lo,1.0)!=0.0)) 
          return RealInterval.fullInterval();
          //             throw new IAException("integerPower(x,y): y must be a constant integer interval [i,i]");

    yy = y.lo;

    //    System.out.println("integerPower: calling even/odd power");

    if (Math.IEEEremainder(yy,2.0) == 0.0)
         return evenPower(x,yy);
    else return oddPower(x,yy);
 }

  /**
   * returns (x**1/y) assuming that y is restricted to integer values
   * currently returns (-infty,infty) if y is not bound to an
   * interval containing a single integer
   */
  public static RealInterval integerRoot(RealInterval x, RealInterval y) 
    throws IAException
  {
    double yy;

    if ((y.lo!=y.hi) || 
      (Math.IEEEremainder(y.lo,1.0)!=0.0)) 
      return RealInterval.fullInterval();
      //       throw new IAException("intgerRoot(x,y): y must be a constant integer interval [i,i]");

    yy = y.lo;

    if (Math.IEEEremainder(yy,2.0) == 0.0)
         return evenRoot(x,yy);
    else return oddRoot(x,yy);
    
  }




  /**
   * computes 
   * <code> u :=  (u intersect ((x**1/y) union -(x**1/y)))</code>
   * and returns true if u is nonempty
   * Also, assumes that y is a constant integer interval
   */
  public static boolean intersectIntegerRoot
     (RealInterval x, RealInterval y, RealInterval u)
    throws IAException
 {
    double yy;
    RealInterval tmp;

    //      System.out.println("intersectIntegerRoot u = u cap x^(1/y) with (u,x,y) = "+u+x+y);
    if ((y.lo!=y.hi) || 
      (Math.IEEEremainder(y.lo,1.0)!=0.0)) 
      return true; // the conservative answer
      //       throw new IAException("integerRoot(x,y): y must be a constant integer interval [i,i]");

    yy = y.lo;

    if (Math.IEEEremainder(yy,2.0) != 0.0) {
      //             System.out.println("odd case with yy = "+yy);
      //             System.out.println("x^(1/y) = "+oddRoot(x,yy));
       u.intersect(oddRoot(x,yy));
       //             System.out.println("did odd case u = u cap x^(1/y) with (u,x,y) = "+u+x+y);
    }
    else {
      //             System.out.println("even case with yy = "+yy);
      //             System.out.println("x^(1/y) = "+evenRoot(x,yy));
       tmp =  evenRoot(x,yy);
       if (u.hi < tmp.lo)
         u.intersect(uminus(tmp));
       else if (-tmp.lo < u.lo )
         u.intersect(tmp);
       else 
         u.intersect(new RealInterval(-tmp.hi,tmp.hi));

       //              System.out.println("did even case u = u cap x^(1/y) with (u,x,y) = "+u+x+y);
    }

    return true;
 }    



  public static void main(String argv[]) {

     RealInterval a,b,c;

     a = new RealInterval(5.0);
     b = log(a);
     c = exp(b);

     System.out.println("a= "+a);
     System.out.println("log(a)= "+b);
     System.out.println("exp(log(a))= "+c);

    try {
     a = new RealInterval(-5.0,0.0);
     c = exp(log(a));

     System.out.println("a= "+a);
     System.out.println("exp(log(a))= "+c);
    } catch (Exception e) {
     System.out.println("Caught exception "+e);
    }
  }

}

