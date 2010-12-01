package de.gaalop.clucalc.input.function;

import de.gaalop.clucalc.algebra.AlgebraMode;
import de.gaalop.clucalc.input.Function;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.Multiplication;
import de.gaalop.dfg.Negation;

/**
 * Represents the function to dualize an expression via expr*I.
 * 
 */
public class Dual implements Function {

  /**
   * Returns true since this function is defined in every algebra mode.
   */
  @Override
  public boolean isDefinedIn(AlgebraMode mode) {
    return true;
  }

  @Override
  public Expression createExpression(AlgebraMode mode, Expression... args) {
    if (args.length != 1) {
      throw new IllegalArgumentException("The dual is only defined for one operand.");
    }

    return new Multiplication(new Negation(args[0]), mode.getConstant("I").copy());
  }
}
