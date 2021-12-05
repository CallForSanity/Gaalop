package wordlengthoptimization;

import datapath.graph.type.FixedPoint;

/**
 * Assigns all unassigned types a fixed point type with a constant value for
 * word and fractions lengths.
 *
 * @author fs
 */
public class Basic extends UniqueWordlength  {

  /**
   * Constructor. Takes as parameters the default values which should
   * be assigned to the nodes that were uninitialized.
   * @param defaultwordlength Word length for nodes without.
   * @param defaultfraction Fractional length for nodes without. Should be
   *   smaller than defaultwordlenght, otherwise deaultwordlength increases.
   * @param signed True if uninitialized nodes should be signed.
   */
  public Basic(int defaultwordlength, int defaultfraction, boolean signed) {
    if (defaultfraction > defaultwordlength) {
      defaultwordlength = defaultfraction;
    }
    typeForAll = new FixedPoint(defaultwordlength, defaultfraction, signed);
  }

  @Override
  public String toString() {
    return "Fixed Fixed Point Size (Default 32:16)";
  }

}
