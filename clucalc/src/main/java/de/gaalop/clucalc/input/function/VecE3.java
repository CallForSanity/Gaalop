package de.gaalop.clucalc.input.function;

import de.gaalop.clucalc.algebra.AlgebraE3;
import de.gaalop.clucalc.algebra.AlgebraMode;
import de.gaalop.clucalc.input.Function;
import de.gaalop.dfg.Expression;
import static de.gaalop.dfg.ExpressionFactory.*;

/**
 * This class implements the VecE3 function as defined in the CluCalc documentation.
 * VecE3:= x1*e1 + x2*e2 + x3*e3;
 */
public class VecE3 implements Function {

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

    if (args.length == 3) {
      Expression x1 = args[0];
      Expression x2 = args[1];
      Expression x3 = args[2];
      return sum(product(x1.copy(), mode.getConstant("e1")),
          product(x2.copy(), mode.getConstant("e2")), product(x3.copy(), mode.getConstant("e3")));
    } else {
      throw new IllegalArgumentException("The VecE3 function needs three arguments: "
        + "x1, x2 and x3, not: " + args);
    }
  }
}
