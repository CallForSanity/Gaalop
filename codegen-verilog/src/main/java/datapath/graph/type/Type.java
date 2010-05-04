package datapath.graph.type;

import datapath.graph.operations.constValue.Value;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jh
 */
public abstract class Type implements Cloneable {

    private int bitsize;
    private boolean signed;

    public int getBitsize() {
        return bitsize;
    }

    public boolean isSigned() {
        return signed;
    }

    public void setBitsize(int bitsize) {
        this.bitsize = bitsize;
    }

    public void setSigned(boolean signed) {
        this.signed = signed;
    }

  /**
   * @return The biggest number which can be represented by this fixed point type.
   */
  public abstract double getMaxValue();


  /**
   * @return The smallest number which can be represented by this fixed point type.
   */
  public abstract double getMinValue();

  @Override
  public Type clone() {
    try {
      return (Type) super.clone();
    } catch (CloneNotSupportedException ex) {
      Logger.getLogger(Type.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }

  /**
   * Outputs a value as hex represenation string
   * @param value
   * @return Hex string
   */
  public abstract String toHex(Value value);

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Type other = (Type) obj;
        if (this.bitsize != other.bitsize) {
            return false;
        }
        if (this.signed != other.signed) {
            return false;
        }
        return true;
    }
    
}
