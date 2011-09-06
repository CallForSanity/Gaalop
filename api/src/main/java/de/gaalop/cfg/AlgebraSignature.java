package de.gaalop.cfg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.gaalop.dfg.BaseVector;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.ExpressionFactory;
import de.gaalop.dfg.FloatConstant;

/**
 * This class models the signature of a Clifford algebra.
 * <p/>
 * In particular, the signature of a Clifford algebra is represented by the list of its base vectors
 * squares. The dimension of the algebra represented by this class corresponds to the length of such
 * an array. A list of the default blades for this algebra is also calculated. For the special case
 * of 5D conformal algebra, the default blades are set manually.
 * 
 * @author Sebastian Hartte
 * @author Christian Schwinn
 * @version 1.1
 * @since 1.0
 */
public class AlgebraSignature {

  private final int[] baseSquares;

  private final Expression[] defaultBladeList;

  /**
   * Constructs a new algebra signature with a given list of base vector squares.
   * 
   * @param baseSquares An array that corresponds to {(e<sub>1</sub>)<sup>2</sup>, ...,
   * (e<sub>n</sub>)<sup>2</sup>}. A copy of the array is stored in the created instance of this
   * class.
   * @param N3 whether to use e0, einf in 5D conformal algebra or standard base vectors e<sub>i
   */
  public AlgebraSignature(int[] baseSquares, boolean N3) {
    this.baseSquares = baseSquares.clone();
    if (N3) {
    	this.defaultBladeList = handleN3();
    } else {
    	this.defaultBladeList = BladeListBuilder.createDefaultBladeList(baseSquares.length);
    }
  }

  public AlgebraSignature(int[] baseSquares, Expression[] bladelist) {
    this.baseSquares = baseSquares.clone();
    
    this.defaultBladeList = bladelist;
    
  }

  
	/**
	 * Special treatment of 5D conformal algebra. The basis blades are assigned
	 * "by hand" in order to more easily treat e0 and einf as special cases.
	 * 
	 * @return
	 */
  private Expression[] handleN3() {
	  Expression[] blades = new Expression[(int) Math.pow(2, baseSquares.length)];
	  
	  blades[0] = new FloatConstant(1.0f);
	  
	  blades[1] = new BaseVector(1);
	  blades[2] = new BaseVector(2);
	  blades[3] = new BaseVector(3);
	  blades[4] = new BaseVector("inf");
	  blades[5] = new BaseVector(0);
	  
	  blades[6] = ExpressionFactory.wedge(new BaseVector(1), new BaseVector(2));
	  blades[7] = ExpressionFactory.wedge(new BaseVector(1), new BaseVector(3));
	  blades[8] = ExpressionFactory.wedge(new BaseVector(1), new BaseVector("inf"));
	  blades[9] = ExpressionFactory.wedge(new BaseVector(1), new BaseVector(0));
	  blades[10] = ExpressionFactory.wedge(new BaseVector(2), new BaseVector(3));
	  blades[11] = ExpressionFactory.wedge(new BaseVector(2), new BaseVector("inf"));
	  blades[12] = ExpressionFactory.wedge(new BaseVector(2), new BaseVector(0));
	  blades[13] = ExpressionFactory.wedge(new BaseVector(3), new BaseVector("inf"));
	  blades[14] = ExpressionFactory.wedge(new BaseVector(3), new BaseVector(0));
	  blades[15] = ExpressionFactory.wedge(new BaseVector("inf"), new BaseVector(0));
	  
	  blades[16] = ExpressionFactory.wedge(new BaseVector(1), new BaseVector(2), new BaseVector(3));
	  blades[17] = ExpressionFactory.wedge(new BaseVector(1), new BaseVector(2), new BaseVector("inf"));
	  blades[18] = ExpressionFactory.wedge(new BaseVector(1), new BaseVector(2), new BaseVector(0));
	  blades[19] = ExpressionFactory.wedge(new BaseVector(1), new BaseVector(3), new BaseVector("inf"));
	  blades[20] = ExpressionFactory.wedge(new BaseVector(1), new BaseVector(3), new BaseVector(0));
	  blades[21] = ExpressionFactory.wedge(new BaseVector(1), new BaseVector("inf"), new BaseVector(0));
	  blades[22] = ExpressionFactory.wedge(new BaseVector(2), new BaseVector(3), new BaseVector("inf"));
	  blades[23] = ExpressionFactory.wedge(new BaseVector(2), new BaseVector(3), new BaseVector(0));
	  blades[24] = ExpressionFactory.wedge(new BaseVector(2), new BaseVector("inf"), new BaseVector(0));
	  blades[25] = ExpressionFactory.wedge(new BaseVector(3), new BaseVector("inf"), new BaseVector(0));
	  
	  blades[26] = ExpressionFactory.wedge(new BaseVector(1), new BaseVector(2), new BaseVector(3), new BaseVector("inf"));
	  blades[27] = ExpressionFactory.wedge(new BaseVector(1), new BaseVector(2), new BaseVector(3), new BaseVector(0));
	  blades[28] = ExpressionFactory.wedge(new BaseVector(1), new BaseVector(2), new BaseVector("inf"), new BaseVector(0));
	  blades[29] = ExpressionFactory.wedge(new BaseVector(1), new BaseVector(3), new BaseVector("inf"), new BaseVector(0));
	  blades[30] = ExpressionFactory.wedge(new BaseVector(2), new BaseVector(3), new BaseVector("inf"), new BaseVector(0));
	  
	  blades[31] = ExpressionFactory.wedge(new BaseVector(1), new BaseVector(2), new BaseVector(3), new BaseVector("inf"), new BaseVector(0));
	  
	  return blades;
  }

  /**
   * Gets a copy of the squares of the base vectors for this algebra.
   * 
   * @return An array that equals {(e<sub>1</sub>)<sup>2</sup>, ..., (e<sub>n</sub>)<sup>2</sup>}.
   */
  public int[] getBaseSquares() {
    return baseSquares.clone();
  }

  /**
   * Gets the dimension of this algebra.
   * 
   * @return The number of base vectors in this algebra signature.
   */
  public int getDimension() {
    return baseSquares.length;
  }

  /**
   * Returns a copy of the default blade list. The default blade list is the list of blades this
   * algebra has. It is automatically computed when this algebra signature is created. It is used
   * when Multivectors are represented as arrays. In that case, the array elements of such a
   * multivector correspond to the coefficients for the blades in this list.
   * 
   * @return A copy of the array that contains every possible blade for this algebra. It has 2^n
   * elements where n is the dimension of this algebra.
   * @see BladeListBuilder For the algorithm that is used to build this list.
   */
  public Expression[] getDefaultBladeList() {
    return defaultBladeList.clone();
  }

  /**
   * This internal class provides methods to compute a list of blades for clifford algebras.
   * 
   * @author Sebastian Hartte
   */
  private final static class BladeListBuilder {
    /**
     * Compute a list of blades for a Clifford Algebra.
     * 
     * @param dimension The dimension of the clifford algebra.
     * @return An array that contains 2<sup>dimension</sup> dataflow graphs modelling each blade.
     */
    public static Expression[] createDefaultBladeList(int dimension) {
      List<BaseVector> baseVectors = getBaseVectors(dimension);

      List<List<BaseVector>> powerset = getPowerset(baseVectors);

      Collections.sort(powerset, new BladeComparator());

      Expression[] bladeList = new Expression[powerset.size()];
      for (int i = 0; i < powerset.size(); ++i) {
        bladeList[i] = createBladeExpression(powerset.get(i));
      }

      return bladeList;
    }

    /**
     * Creates a dataflow graph representing a blade.
     * 
     * @param bladeBaseVectors The list of base vectors that make up the resulting blade.
     * @return If <code>bladeBaseVectors</code> is empty, an expression modelling identity is
     * returned. If it only contains a single base vector, that base vector is returned. Otherwise
     * the outer product of all base vectors in <code>bladeBaseVectors</code> is returned.
     */
    private static Expression createBladeExpression(List<BaseVector> bladeBaseVectors) {
      Expression blade;
      if (bladeBaseVectors.isEmpty()) {
        blade = new FloatConstant(1.0f);
      } else if (bladeBaseVectors.size() == 1) {
        blade = bladeBaseVectors.get(0);
      } else {
        blade = ExpressionFactory.wedge(bladeBaseVectors.toArray(new Expression[bladeBaseVectors
            .size()]));
      }
      return blade;
    }

    /**
     * Creates a list of base vectors for a clifford algebra.
     * 
     * @param dimension The dimension of the clifford algebra.
     * @return A list that contains the following elements:
     * <code>{e<sub>1</sub>, ..., e<sub>dimension</sub>}</code>.
     */
    private static List<BaseVector> getBaseVectors(int dimension) {
      List<BaseVector> baseVectors = new ArrayList<BaseVector>(dimension);

      for (int i = 0; i < dimension; ++i) {
        baseVectors.add(new BaseVector(i + 1));
      }

      return baseVectors;
    }

    /**
     * Creates a powerset (as a list) of a given list of elements.
     * 
     * @param elements The list to create a powerset of.
     * @param <T> The type parameter of <code>elements</code>.
     * @return A list of lists that models the powerset of <code>elements</code>.
     */
    private static <T> List<List<T>> getPowerset(List<T> elements) {
      assert elements.size() <= 31; // To avoid overflow, we only use 31 bit

      int numberOfSets = 1 << elements.size(); // 2^n
      List<List<T>> powerset = new ArrayList<List<T>>(numberOfSets);
      int bitfield = 0;

      for (int i = 0; i < numberOfSets; ++i) {
        List<T> subset = selectElements(elements, bitfield);
        powerset.add(subset);
        bitfield++;
      }

      return powerset;
    }

    /**
     * This method selects elements from a list and returns a new list that contains those elements.
     * The element selction is done using a bitfield, where the least significant bit represents the
     * first element of the list.
     * 
     * @param list The list to select elements from.
     * @param bitfield The bitfield that models the selection criteria for the list.
     * @param <T> The type parameter of <code>list</code>.
     * @return A new list that contains only those elements of <code>list</code> for which the
     * corresponding bit in <code>bitfield</code> was 1.
     */
    private static <T> List<T> selectElements(List<T> list, int bitfield) {
      List<T> subset = new ArrayList<T>();

      for (int i = 0; i < list.size(); ++i) {
        int bitmask = 1 << i; // We are selecting the i-th bit from the right

        if ((bitfield & bitmask) != 0) {
          subset.add(list.get(i));
        }
      }

      return subset;
    }

    /**
     * This class compares two blades according to the following criteria:
     * <ol>
     * <li>The number of base vectors in the blade.</li>
     * <li>The indices of the base vectors in the blade.</li>
     * </ol>
     */
    private static class BladeComparator implements Comparator<List<BaseVector>> {

      public BladeComparator() {
      }

	@Override
      public int compare(List<BaseVector> o1, List<BaseVector> o2) {
        if (o1.size() < o2.size()) {
          return -1;
        } else if (o1.size() > o2.size()) {
          return 1;
        } else {
          for (int i = 0; i < o1.size(); ++i) {
            int index1 = o1.get(i).getOrder();
            int index2 = o2.get(i).getOrder();
            if (index1 < index2) {
              return -1;
            } else if (index1 > index2) {
              return 1;
            }
          }
          return 0;
        }
      }

    }
  }

}
