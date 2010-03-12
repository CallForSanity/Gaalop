package de.gaalop.clucalc.input;

import de.gaalop.clucalc.algebra.AlgebraMode;
import de.gaalop.dfg.Expression;

/**
 * Represents a CLUCalc function. Some functions are only available in certain algebra modes, so
 * this can be queried by {@link #isDefinedIn(AlgebraMode)}. For convenience
 * {@link #createExpression(AlgebraMode, Expression...)} offers the functionality to create a DFG
 * description of the function.
 * 
 */
public interface Function {

  /**
   * Checks whether this function is defined in the given algebra mode.
   * 
   * @param mode The algebra mode or null if no specific algebra mode is set.
   * @return True if this function is defined in <code>mode</code> or should <code>mode</code> be
   * null, if this function is always defined.
   */
  boolean isDefinedIn(AlgebraMode mode);

  /**
   * Creates an expression that implements this function in a DFG.
   * 
   * @param mode The algebra mode that is activated. This function must be defined in this algebra
   * mode.
   * @param args The arguments for this function.
   * @return An expression that includes copies of the arguments.
   */
  Expression createExpression(AlgebraMode mode, Expression... args);
}
