package de.gaalop.clucalc.algebra;

import java.util.Map;

import de.gaalop.dfg.BaseVector;
import de.gaalop.dfg.Expression;

/**
 * This class represents the algebra mode for the conformal geometric algebra.
 */
public class AlgebraN3 extends AbstractAlgebraMode {

  @Override
  public String getDefinitionMethod() {
    return "DefVarsN3";
  }

  @Override
  public int[] getSignature() {
    return new int[] {
      1, 1, 1, 1, -1
    };
  }

  @Override
  protected void addConstants(Map<String, Expression> constants) {
    // em and ep are aliases for e4 and e5
    constants.put("ep", getConstant("e4"));
    constants.put("em", getConstant("e5"));

    constants.put("einf", new BaseVector("inf"));
    // e and n are aliases for einf
    constants.put("e", getConstant("einf"));
    constants.put("n", getConstant("einf"));

    constants.put("e0", new BaseVector(0));
    constants.put("nbar", getConstant("e0"));
  }

  @Override
  public String toString() {
    return "5D conformal space (" + getDefinitionMethod() + ")";
  }
}
