package de.gaalop.clucalc.input.function;

import de.gaalop.clucalc.algebra.AlgebraMode;
import de.gaalop.clucalc.algebra.AlgebraN3;
import de.gaalop.clucalc.input.Function;
import de.gaalop.dfg.Expression;
import static de.gaalop.dfg.ExpressionFactory.*;
import de.gaalop.dfg.FloatConstant;

/**
 * Implements the VecN3 function as defined by CluCalc.
 */
public class VecN3 implements Function {

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

    if (args.length != 3) {
      throw new IllegalArgumentException("Invalid number of arguments for VecN3: " + args.length);
    }

    return createVecN3(mode, args[0], args[1], args[2]);
  }

  /**
   * Converts the CluCalc function VecN3 to a DFG.
   * 
   * @param x The x component of the euclidean vector.
   * @param y The y component of the euclidean vector.
   * @param z The z component of the euclidean vector.
   * @return An Expression that represents this functions result with the given parameters.
   */
  private Expression createVecN3(AlgebraMode mode, Expression x, Expression y, Expression z) {
    // x * e1
    Expression op1 = product(x.copy(), mode.getConstant("e1"));
    // y * e2
    Expression op2 = product(y.copy(), mode.getConstant("e2"));
    // z * e3
    Expression op3 = product(z.copy(), mode.getConstant("e3"));
    // (x1*x1 + x2*x2 + x3*x3)
    Expression metric = sum(square(x.copy()), square(y.copy()), square(z.copy()));
    Expression op4 = product(new FloatConstant(0.5f), metric, mode.getConstant("einf"));
    Expression op5 = mode.getConstant("e0");

    return sum(op1, op2, op3, op4, op5);

  }
}
