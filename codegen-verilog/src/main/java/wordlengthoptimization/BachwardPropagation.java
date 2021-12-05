package wordlengthoptimization;

import datapath.graph.Graph;

/**
 * Performs one pass of backward propagation type of wordlength optimization.
 * The algorithm goes once bottum up over graph (it has to be acyclic) and
 * computes all the wordlength and precision at the inputs from the wordlength
 * and precision at the outputs.
 *
 * @author fs
 */
public class BachwardPropagation implements WordlengthOptimization {

  private Options opts;

  @Override
  public int optimize(Graph graph) {
    return 0;
  }

  @Override
  public void setOptions(Options opts) {
    this.opts = opts;
  }


}
