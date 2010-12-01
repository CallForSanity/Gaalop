package de.gaalop.clucalc.input.function;

import de.gaalop.clucalc.algebra.AlgebraE3;
import de.gaalop.clucalc.algebra.AlgebraMode;
import de.gaalop.clucalc.input.Function;
import de.gaalop.dfg.Expression;
import static de.gaalop.dfg.ExpressionFactory.*;
import de.gaalop.dfg.FloatConstant;
import de.gaalop.dfg.MathFunction;
import de.gaalop.dfg.MathFunctionCall;

/**
 * This class implements the RotorE3 function as defined in the CluCalc documentation.
 * RotorE3:=(x,ang)->cos(ang/2)-sin(ang/2)*(x/mul_abs(x));
 */
public class RotorE3 implements Function {
  private static final Expression HALF = new FloatConstant(0.5f);

  /**
   * Returns true if mode is AlgebraE3, false otherwise.
   */
  @Override
  public boolean isDefinedIn(AlgebraMode mode) {
    return mode instanceof AlgebraE3;
  }

  @Override
  public Expression createExpression(AlgebraMode mode, Expression... args) {
    assert isDefinedIn(mode);

    if (args.length == 2) {
      Expression axis = args[0];
      Expression angle = args[1];
      return subtract(product(HALF, new MathFunctionCall(angle, MathFunction.COS)),
          product(product(HALF, new MathFunctionCall(angle, MathFunction.SIN))), divide(axis,
              new MathFunctionCall(axis, MathFunction.ABS)));
    } else {
      throw new IllegalArgumentException("The RotorE3 function needs two arguments: "
        + "axis and angle, not: " + args);
    }
  }
}
