package de.gaalop.clucalc.algebra;

/**
 * This class implements the algebra mode for 3D euclidean space.
 */
public class AlgebraE3 extends AbstractAlgebraMode {

  @Override
  public String getDefinitionMethod() {
    return "DefVarsE3";
  }

  @Override
  public int[] getSignature() {
    return new int[] {
      1, 1, 1
    };
  }

  @Override
  public String toString() {
    return "3D euclidean space (" + getDefinitionMethod() + ")";
  }
}
