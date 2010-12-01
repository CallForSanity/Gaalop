package de.gaalop.clucalc.input.function;

import de.gaalop.clucalc.algebra.AlgebraMode;
import de.gaalop.clucalc.algebra.AlgebraN3;
import de.gaalop.clucalc.input.Function;
import de.gaalop.dfg.Expression;
import static de.gaalop.dfg.ExpressionFactory.*;
import de.gaalop.dfg.FloatConstant;

/**
 * This class implements the TranslatorN3 function as defined in the CluCalc documentation.
 * RotorN3:=(x,y,z,ang)->cos(ang/2)+sin(ang/2)*(-x*e23+y*e13-z*e12)/(x^2+y^2+z^2)^(1/2);
 */
public class TranslatorN3 implements Function {
  private static final Expression HALF = new FloatConstant(0.5f);
  private static final FloatConstant ONE = new FloatConstant(1);

  /**
   * Returns true if mode is AlgebraN3, false otherwise.
   */
  @Override
  public boolean isDefinedIn(AlgebraMode mode) {
    return mode instanceof AlgebraN3;
  }

  @Override
  public Expression createExpression(AlgebraMode mode, Expression... args) {
    /*
     * TranslatorN3:=proc(x) global einf; local t; if nargs=3 then
     * t:=1-.5*((args[1]*e1+args[2]*e2+args[3]*e3)*einf); else error
     * "TranslatorN3 expects 3 arguments, but received %1 argument",nargs end if: RETURN(t); end:
     */

    assert isDefinedIn(mode);

    if (args.length == 3) {
      Expression x = args[0];
      Expression y = args[1];
      Expression z = args[2];

      Expression e1 = mode.getConstant("e1");
      Expression e2 = mode.getConstant("e2");
      Expression e3 = mode.getConstant("e3");
      Expression einf = mode.getConstant("einf");

      return subtract(ONE, product(HALF, product(
          sum(product(x, e1), product(y, e2), product(z, e3)), einf)));
    } else {
      throw new IllegalArgumentException("The TranslatorN3 function needs three arguments: "
        + "x, y, z, not: " + args);
    }
  }
}
