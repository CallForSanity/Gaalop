package wordlengthoptimization;
import datapath.graph.type.Float;

/**
 * Assigns all unassigned types a 64 bit floating point type.
 *
 * @author fs
 */
public class AllDouble extends UniqueWordlength {

  /**
   * Constructor. 
   */
  public AllDouble() {

    typeForAll = new Float();
    typeForAll.setBitsize(64);
  }

  @Override
  public String toString() {
    return "All Operations Double Type";
  }


}
