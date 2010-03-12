package de.gaalop.clucalc.algebra;

/**
 * This class implements the algebra mode for the 2D-conic space as defined by the DefVarsC2 function.
 */
public class AlgebraC2 extends AbstractAlgebraMode {

    @Override
    public String getDefinitionMethod() {
        return "DefVarsC2";
    }

    @Override
    public int[] getSignature() {
        return new int[]{1, 1, 1, 1, 1, 1};
    }
    
    @Override
    public String toString() {
      return "2D conic space (" + getDefinitionMethod() + ")";
    }

}
