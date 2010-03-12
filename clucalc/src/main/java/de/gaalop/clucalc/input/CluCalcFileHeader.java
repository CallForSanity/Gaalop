package de.gaalop.clucalc.input;

import de.gaalop.annotation.AnnotationContainer;
import de.gaalop.clucalc.algebra.AlgebraMode;

/**
 * This annotation contains information about the CluCalc header that was used to generate a certain
 * control dataflow graph. It includes the IPNS/OPNS mode and the DefVars that was used.
 */
public class CluCalcFileHeader {

  private static AnnotationContainer<CluCalcFileHeader> container = new AnnotationContainer<CluCalcFileHeader>();

  private AlgebraMode algebraMode;

  private NullSpace nullSpace;

  /**
   * Returns the CLUCalc file header for the given object.
   * 
   * @param object object index to be looked up
   * @return file header of given object
   */
  public static CluCalcFileHeader get(Object object) {
    return container.get(object);
  }

  /**
   * Creates a CLUCalc file header and attaches it to the annotation container.
   * 
   * @param object object to add
   */
  public CluCalcFileHeader(Object object) {
    container.attachAnnotation(object, this);
  }

  /**
   * 
   * @param object object index to be looked up
   * @return whether annotation is present
   */
  public static boolean isAttached(Object object) {
    return container.isAnnotated(object);
  }

  public AlgebraMode getAlgebraMode() {
    return algebraMode;
  }

  public void setAlgebraMode(AlgebraMode algebraMode) {
    this.algebraMode = algebraMode;
  }

  public NullSpace getNullSpace() {
    return nullSpace;
  }

  public void setNullSpace(NullSpace nullSpace) {
    this.nullSpace = nullSpace;
  }

  /**
   * Removes this header from the annotation container.
   */
  public void detach() {
    container.removeAnnotation(this);
  }

}
