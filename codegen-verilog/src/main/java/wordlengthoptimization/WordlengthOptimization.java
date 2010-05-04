package wordlengthoptimization;

import datapath.graph.Graph;

/**
 * Wordlengthoptimization Interface. Optimization has to provide a method
 * optimize which works on a datapath.graph.Graph object. In the graph uninitialized
 * word and fraction lengths are represented by null objects;
 *
 * @author fs
 */
public interface WordlengthOptimization {

  public void setOptions(Options opts);

/**
 * Performs wordlength optimization on DFG graph.
 * @param graph
 * @return number of changed  word-/fractions lengths.
 * I.e. 0 means no optimization was performed
 */
int optimize(Graph graph);

}
