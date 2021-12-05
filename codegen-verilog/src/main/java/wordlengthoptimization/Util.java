package wordlengthoptimization;

import datapath.graph.Graph;
import datapath.graph.operations.HWInput;
import datapath.graph.operations.HWOutput;
import datapath.graph.operations.Operation;
import datapath.graph.operations.TopLevelInput;
import java.math.BigInteger;
import java.util.HashMap;

/**
 *
 * @author fs
 */
public class Util {

    /**
   * computes the required number of bits to represent an range
   * minValue...maxValue / 0..max(minValue,maxValue)
   * In case on of the minValue < 0 also the bit for the sign is included
   *
   */
  public static int bitsRequired(double minValue, double maxValue) {
    long absmax = (long ) Math.max(Math.abs(minValue), maxValue) +1;

    int bits = 64 - Long.numberOfLeadingZeros(absmax);

    if (minValue < 0.0)
      bits++;


    return bits;
  }

  /**
   * Determines in a Float String how much bits a necessary to represent the
   * given Fraction with less error than the string.
   * It is not checked if the float, is a valid float representation.
   *
   */
  public static int bitsRequiredForFraction(String floatnumber) {
    if (floatnumber.contains("eE")) {
      System.out.println("e float represenation not yet supported!");
    }
   int pos = floatnumber.indexOf(".");
   /* log_2 (10) = 2.30... That means that many bits are enough per decimal digit */
    return (int) Math.ceil((floatnumber.length() - pos -1) * 2.31);
  }

  /**
   * Generates Integer fixed point represenation of a floating point value.
   * @param d Floating point value.
   * @param prec Precision of the integer representation.
   * @return Fixed point integer with a precision of prec representing d
   */
  public static BigInteger fixedPointFromFloat(double d, int prec) {
    d = d * Math.pow(2.0, (double)prec);
    return new BigInteger( String.format("%100.0f", d).trim());
  }

  /**
   * Generates Integer fixed point represenation of a floating point value.
   * @param d Floating point value.
   * @param prec Precision of the integer representation.
   * @return Fixed point integer with a precision of prec representing d
   */
  public static double floatFromfixedPoint(BigInteger integer, int prec) {
    return Math.scalb(integer.doubleValue(), -prec);
  }


      /**
       * Helper function. As the visitor does NOT visit HWInput we walk over all nodes,
       * and get copy the typeinformation from the related ToplevelNodes into the  HWInputs.
       */
  public static void fixHWInputs(Graph graph) {
    for (Operation op : graph.getOperations()) {
      if (op instanceof TopLevelInput) {
        TopLevelInput input = (TopLevelInput) op;
        HWInput pred = (HWInput) input.getSource();
        System.out.println("Input: " + input.getName() + " Type: " + input.getType().toString().replace("\n", ", "));        
        pred.setType(input.getType().clone());
      }
      if (op instanceof HWOutput) {
        HWOutput output = (HWOutput) op;
        System.out.println("Output: " + output.getName() + " Type: " + output.getType().toString().replace("\n", ", "));
      }
    }
  }

  public static final int MINMERGE = 1;
  public static final int MAXMERGE = 2;

  /**
   * Joins to mappings.
   * @param target A mapping which contains already a mapping from operations to values,
   *        Afterwards the mapping contains for each operations the minimum/maximum from its old value and the value
   *  from additional
   * @param additional Mapping which supplies values for operations. Must have the same nodes as target.
   * @param mode   MINMERGE ord MAXMERGE. Merges with Maximum or with Minimum.
   **/
  public static void merge(HashMap<Operation, Double> target, HashMap<Operation, Double> additional, int mode) {
    for (Operation op : target.keySet()) {
      double oldTarget = target.get(op);
      double oldAdd = additional.get(op);
      double newVal = 0.0;
      switch (mode) {
        case MINMERGE:
          newVal = Math.min(oldTarget, oldAdd);
          break;
        case MAXMERGE:
          newVal = Math.max(oldTarget, oldAdd);
          break;
        default:
          throw new UnsupportedOperationException("Not supported");
      }
      target.put(op, newVal);
    }
  }

}
