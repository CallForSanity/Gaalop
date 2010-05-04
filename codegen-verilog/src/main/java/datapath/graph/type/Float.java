package datapath.graph.type;

import datapath.graph.operations.constValue.Value;


/**
 *
 * @author jh
 */
public class Float extends Type {

  @Override
  public double getMaxValue() {
    double result;
    switch (this.getBitsize()) {
      case 16: 
        result = java.lang.Float.MAX_VALUE;
        break;
      case 32: 
        result = Double.MAX_VALUE;
        break;
      default:
            throw new UnsupportedOperationException("Not supported yet.");
    }
    return result;
  }

  @Override
  public double getMinValue() {
       double result;
    switch (this.getBitsize()) {
      case 16:
        result = java.lang.Float.MIN_VALUE;
        break;
      case 32:
        result = Double.MIN_VALUE;
        break;
      default:
            throw new UnsupportedOperationException("Not supported yet.");
    }
    return result;
  }

  @Override
  public String toHex(Value value) {
    java.lang.Float f = (java.lang.Float)value.getValue();

    return java.lang.Integer.toHexString(java.lang.Float.floatToRawIntBits(f));
  }


  @Override
  public String toString() {
    return new String(getBitsize() + "-bit " +
            (isSigned() ? "signed" : "unsigned") + 
            " Floating Point\nValue Range: [" +
            getMinValue() + "..." + getMaxValue() + "]");
  }

}
