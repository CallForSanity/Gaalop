/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wordlengthoptimization;

import datapath.graph.Graph;
import datapath.graph.operations.Operation;
import datapath.graph.operations.ParentInput;
import datapath.graph.operations.ParentOutput;
import java.util.HashMap;
import java.util.Random;

/**
 * Performs one pass of forward propagation type of wordlength optimization.
 * The algorithm goes once top down over graph (it has to be acyclic) and
 * computes all the wordlength and precision at the outputs from the wordlength
 * and precision at the inputs.
 *
 * @author fs
 */
public class ForwardPropagation implements WordlengthOptimization {

  private Options opts;
  private Graph graph;

  HashMap<Operation, Double> minValues = new HashMap<Operation, Double>();
  HashMap<Operation, Double> maxValues = new HashMap<Operation, Double>();


  /*
   * adds to startValues the other Values from monte carlo computation */
  private HashMap<Operation, Double> performMonteCarloIter(HashMap<ParentInput, Double> startValues) {

    ComputeValueVisitor computeValue = new ComputeValueVisitor(startValues);
    Operation.nextVisit();
    for (ParentOutput outputNode : graph.getOutput()) {
        outputNode.postOrderUpwardVisit(computeValue);
    }
    return computeValue.getValues();
  }


  private void monteCarlo() {
    boolean firstRun = true;

    /* add the ranges from the pragmas
       Important: ALL INPUTS MUST HAVE SPECIFIED RANGES VIA PRAGMAS */
     for (ParentInput input : graph.getInput()) {
       String strMin = opts.getStartVariableMinValues().get(input.getName());
       String strMax = opts.getStartVariableMaxValues().get(input.getName());
       minValues.put(input, Double.parseDouble(strMin));
       maxValues.put(input, Double.parseDouble(strMax));

    }

    for (int iter = 0; iter< opts.getMonteCarloIterations(); iter++) {
      HashMap<Operation, Double> startValues = new HashMap<Operation, Double>();
      // init with random values
      for (ParentInput input : graph.getInput()) {
        double minValue = minValues.get(input);
        double maxValue = maxValues.get(input);
        double range = maxValue - minValue;
        double randomValue = minValue + Math.random() * range;
        startValues.put(input, randomValue);
      }
      HashMap<Operation, Double> result = performMonteCarloIter(((HashMap) startValues));
      if (firstRun) {
        /* not so easy, get the input min and max back into the first result */
        HashMap<Operation, Double> tmp = (HashMap<Operation, Double>)result.clone();
        tmp.putAll(maxValues);
        maxValues = tmp;
        tmp = ((HashMap<Operation, Double>) result.clone());
        tmp.putAll(minValues);
        minValues = tmp;
        firstRun = false;
      } else {
        Util.merge(maxValues, result, Util.MAXMERGE);
        Util.merge(minValues, result, Util.MINMERGE);
      }
    }

    for (Operation op: minValues.keySet()) {
      System.out.println("Variable " + op.getDebugMessage() + " " + op.getDisplayName() +
              " MC-Value Range: [" + minValues.get(op) + ", " +
              maxValues.get(op) + "]");
    }
  }

  @Override
  public int optimize(Graph graph) {
    int changed = 0;
    this.graph = graph;

    /* monte carlo simulation */
    monteCarlo();
    
    /* apply forward propagation recursivle to all nodes */
    Operation.nextVisit();
    ForwardPropagationVisitorNewTypeCast forward = new ForwardPropagationVisitorNewTypeCast(minValues, maxValues);
    for (ParentOutput outputNode : graph.getOutput()) {
      outputNode.postOrderUpwardVisit(forward);
    }


    /* restrict all nodes to the maximum wordlength */
    Operation.nextVisit();
    LimitBitwidthNewTypeCast limit = new LimitBitwidthNewTypeCast(opts.getMaxWordlength(), opts.getMinFractionlength());
    for (ParentOutput outputNode : graph.getOutput()) {
      outputNode.postOrderUpwardVisit(limit);
    }
    System.out.println(limit.getStats());
    
    /* insert shift if necessary between nodes */
    Operation.nextVisit();
    ShiftInserterNewTypeCast shifts = new ShiftInserterNewTypeCast(graph);
    for (ParentOutput outputNode : graph.getOutput()) {
      outputNode.postOrderUpwardVisit(shifts);
    }

    RemoveTypeConversion rtc = new RemoveTypeConversion();
    rtc.removeTypeConversions(graph);

    return forward.getChanged();

  }

  @Override
  public String toString() {
    return "Forward Propagation";
  }

  @Override
  public void setOptions(Options opts) {
    this.opts = opts;
  }
  
}
