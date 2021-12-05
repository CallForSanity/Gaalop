package wordlengthoptimization;

import datapath.graph.Graph;
import datapath.graph.operations.Operation;
import datapath.graph.type.Type;

/**
 * Assigns all unassigned types the same type.
 * After optimization they have the same type object (i.e. not cloned, but the same)
 *
 * @author fs
 */
public class UniqueWordlength implements WordlengthOptimization {

  protected Type typeForAll;
  private Options opts;

  /**
   * Constructor. Takes as parameters the default values which should
   * be assigned to the nodes that were uninitialized.
   *
   * @param type Type for all unassigned nodes.
   */
  public UniqueWordlength(Type type) {
    typeForAll = type;

  }

  public UniqueWordlength() {
    typeForAll = null;
  }

  @Override
  public int optimize(Graph graph) {
    int changed = 0;
    
    for (Operation iterable_element : graph.getOperations()) {
      if (iterable_element.getType() == null) {
        iterable_element.setType(typeForAll);
        changed++;
      }
    }

    return changed;
  }

 @Override
  public void setOptions(Options opts) {
    this.opts = opts;
  }
 
}
