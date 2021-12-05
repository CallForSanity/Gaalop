package wordlengthoptimization;
import datapath.graph.type.Float;

/**
 * Assigns all unassigned types a 32 bit floating point type.
 *
 * @author fs
 */
public class AllFloat extends UniqueWordlength {

  /**
   * Constructor. 
   */
  public AllFloat() {

    typeForAll = new Float();
    typeForAll.setBitsize(32);
  }

  @Override
  public String toString() {
    return "All Operations Float";
  }


}
