package datapath.graph.type;

import datapath.graph.operations.constValue.Value;

/**
 *
 * @author jh
 */
public class Integer extends Type {

  @Override
  public double getMaxValue() {
    double result;
    if (isSigned())
      result = (1 << getBitsize()) -1.0;
    else
      result = (1 << (getBitsize() + 1)) -1.0;
    return result;
  }

  @Override
  public double getMinValue() {
    if (isSigned())
      return (0.0-getMaxValue() - 1.0);
    else
      return 0.0;
  }

  @Override
  public String toHex(Value value) {
    java.lang.Integer iv = (java.lang.Integer)value.getValue();
    return java.lang.Integer.toHexString(iv);
  }

  @Override
  public String toString() {
    return new String(getBitsize() + "-bit "+
            (isSigned() ? "signed" : "unsigned") +" Integer\nValue Range: [" +
            getMinValue() + "..." + getMaxValue() + "]");
  }

}
