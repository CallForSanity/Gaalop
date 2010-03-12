package de.gaalop.clucalc.algebra;

/**
 * This class defines the algebra mode for the projective space.
 */
public class AlgebraP3 extends AbstractAlgebraMode {
  @Override
  public String getDefinitionMethod() {
    return "DefVarsP3";
  }

  @Override
  public int[] getSignature() {
    return new int[] {
      1, 1, 1, 1
    };
  }

  @Override
  public String toString() {
    return "4D projective space (" + getDefinitionMethod() + ")";
  }
}
