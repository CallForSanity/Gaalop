package datapath.graph;

import datapath.graph.operations.LoopEnd;
import datapath.graph.operations.Operation;
import datapath.graph.operations.ParentOutput;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author fs
 */
public class DeadTreeElimination extends Optimization {
  private HashSet<String> outputVariables;

  public DeadTreeElimination(Graph g, Set<String> outputVariables) {
    super(g);
    this.outputVariables = new HashSet<String>(outputVariables);
  }

  @Override
  public void perform() {
    /* if no output pragma specified, we must assume all HWOutputs as outputs */
    if (outputVariables.size() == 0)
      return;
    DeadTreeEliminationVisitor dte = new DeadTreeEliminationVisitor();
    Operation.nextVisit();

    HashSet<Operation> realOutputs = new HashSet<Operation>();
    for (ParentOutput op: graph.getOutput()) {
      if (outputVariables.contains(op.getName()))
        realOutputs.add(op);
    }

    if (realOutputs.size() == 0) {
      System.out.println("None of the pragma output variables found");
      return;
    }

    /* find loop end node, as this is always and output */
    for (Operation op : graph.getOperations()) {
      if (op instanceof LoopEnd)  {
        realOutputs.add(op);
      }
    }

    for (Operation op : realOutputs) {
      op.postOrderUpwardVisit(dte);
    }

    /* kill all non visited nodes */
   for (Operation op : graph.getOperations().toArray(new Operation[0])) {
     if (!op.isVisited()) {
       graph.remove(op);
       for (Operation pred : op.dependsOnOperations(true)) {
         pred.removeUse(op);
       }
     }
    }

  }
  
}