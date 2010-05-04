package datapath.graph.type;

import datapath.graph.operations.constValue.Value;

/**
 *
 * @author jh
 */
public class FixedPoint extends Type {

	private int fractionlength;

	public void setFractionlength(int fractionlength) {
		this.fractionlength = fractionlength;
	}

	public int getFractionlength() {
		return fractionlength;
	}

  /**
   * Constructor which creates fixed point type with given parameters.
   * @param fractionlength Lenght of fractional part in bits.
   * @param bitsize Total length in bits.
   * @param signedness True if the value can be signed, false if unsigned.
   */
  public FixedPoint(int bitsize, int fractionlength, boolean signedness) {
    this.setFractionlength(fractionlength);
    this.setBitsize(bitsize);
    this.setSigned(signedness);
  }

  /**
   * @return The biggest number which can be represented by this fixed point type.
   */
  public double getMaxValue() {
    int bits = this.getBitsize();
    double max;

    if (this.isSigned())
      bits--;
    bits -= this.getFractionlength();
    max = (1l << (bits)) + 1 - 1.0 / (1 << fractionlength);

    return max;
  }

 /**
   * @return The smallest number which can be represented by this fixed point type.
   */
  public double getMinValue() {
    double min;

    if (this.isSigned())
      min = -getMaxValue()-1.0;
    else
      min = 0.0;

    return min;
  }

  @Override
  public String toHex(Value value) {
    java.lang.Float f  = (java.lang.Float)value.getValue();
    long iv = (long) (f * Math.pow(2.0,this.fractionlength));
    for (int i = this.getBitsize(); i < 64; i++)
      iv &= ~(1l << i);
    return java.lang.Long.toHexString(iv);
  }

  @Override
  public String toString() {
    return new String(getBitsize() + "-bit " +
            (isSigned() ? "signed" : "unsigned") +
            "Fixed Point with " + getFractionlength() +
            " bit fraction\nValue Range: [" +
            getMinValue() + "..." + getMaxValue() + "]");
  }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if(!super.equals(obj))
            return false;
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FixedPoint other = (FixedPoint) obj;
        if (this.fractionlength != other.fractionlength) {
            return false;
        }
        return true;
    }

  /**
   * Cuts off lowest value bits, so precision gets reduced
   * @param maxBidwidth new maximum Bidwdith.
   **/
  public void restrictBitwidth(int maxBidwidth) {
    if (this.getBitsize() > maxBidwidth) {
      int diff = this.getBitsize() - maxBidwidth;
      this.setFractionlength(Math.max(0, this.getFractionlength() - diff));
      this.setBitsize(maxBidwidth);
    }
  }

  /**
   * Increases total bitwidth so precision increase
   * if it is already greater nothing happens
   * @param newBidwidth new total bidwdith.
   **/
  public void increaseFractionlength(int newBidwidth) {
    if (this.getBitsize() < newBidwidth) {
      int missing = newBidwidth - this.getBitsize();
      this.setBitsize(this.getBitsize() + missing);
      this.setFractionlength(this.getFractionlength() + missing);
    }
  }

  /**
   * Sets new total wordlength. The fractionlength is changed according to the
   * change of the bitwidth, so the integer part stays the same
   * @param newBidwith
   */
  public void setToBitwidth(int newBidwith) {
    restrictBitwidth(newBidwith);
    increaseFractionlength(newBidwith);
  }

}
