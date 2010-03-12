package de.gaalop.clucalc.input.function;

import de.gaalop.clucalc.algebra.AlgebraMode;
import de.gaalop.clucalc.algebra.AlgebraN3;
import de.gaalop.clucalc.input.Function;
import de.gaalop.dfg.Expression;
import static de.gaalop.dfg.ExpressionFactory.*;
import de.gaalop.dfg.FloatConstant;
import de.gaalop.dfg.MathFunction;
import de.gaalop.dfg.MathFunctionCall;

/**
 * This class implements the RotorN3 function as defined in the CluCalc documentation.
 * RotorN3:=(x,y,z,ang)->cos(ang/2)+sin(ang/2)*(-x*e23+y*e13-z*e12)/(x^2+y^2+z^2)^(1/2);
 */
public class RotorN3 implements Function {
  private static final Expression HALF = new FloatConstant(0.5f);

  /**
   * Returns true if mode is AlgebraN3, false otherwise.
   */
  @Override
  public boolean isDefinedIn(AlgebraMode mode) {
    return mode instanceof AlgebraN3;
  }

  @Override
  public Expression createExpression(AlgebraMode mode, Expression... args) {
    assert isDefinedIn(mode);

    if (args.length == 4) {
      Expression x = args[0];
      Expression y = args[1];
      Expression z = args[2];
      Expression angle = args[3];

      Expression e23 = wedge(mode.getConstant("e2"), mode.getConstant("e3"));
      Expression e13 = wedge(mode.getConstant("e1"), mode.getConstant("e3"));
      Expression e12 = wedge(mode.getConstant("e1"), mode.getConstant("e2"));

      Expression cos = product(HALF, new MathFunctionCall(angle, MathFunction.COS));
      Expression sin = product(HALF, new MathFunctionCall(angle, MathFunction.SIN));
      Expression metric = new MathFunctionCall(sum(square(x), square(y), square(z)),
          MathFunction.SQRT);
      Expression op = sum(negate(product(x, e23)), product(y, e13), negate(product(z, e12)));

      return sum(cos, product(sin, divide(op, metric)));
    } else {
      throw new IllegalArgumentException("The RotorN3 function needs four arguments: "
        + "x, y, z and angle, not: " + args);
    }
  }
}
