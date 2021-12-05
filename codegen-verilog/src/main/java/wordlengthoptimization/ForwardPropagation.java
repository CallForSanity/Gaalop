package wordlengthoptimization;

import datapath.graph.Graph;
import datapath.graph.display.dot.DotDisplayFactory;
import datapath.graph.operations.Operation;
import datapath.graph.operations.ParentInput;
import datapath.graph.operations.ParentOutput;
import datapath.graph.type.FixedPoint;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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

  ArrayList<HashMap<ParentInput, Double>> monteCarloInputsFloat = new ArrayList<HashMap<ParentInput, Double>>();
  ArrayList<HashMap<ParentInput, BigInteger>> monteCarloInputsFix = new ArrayList<HashMap<ParentInput, BigInteger>>();
  ArrayList<HashMap<Operation, Double>> monteCarloResultsFloat = new ArrayList<HashMap<Operation, Double>>();
  ArrayList<HashMap<Operation, BigInteger>> monteCarloResultsFix = new ArrayList<HashMap<Operation, BigInteger>>();

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

  private void initStartVariableRanges() {
    /* add the ranges from the pragmas
    Important: ALL INPUTS MUST HAVE SPECIFIED RANGES VIA PRAGMAS */
    for (ParentInput input : graph.getInput()) {
      String strMin = opts.getStartVariableMinValues().get(input.getName());
      String strMax = opts.getStartVariableMaxValues().get(input.getName());
      minValues.put(input, Double.parseDouble(strMin));
      maxValues.put(input, Double.parseDouble(strMax));
    }
  }

  private HashMap<ParentInput, Double> generateTrace(double pwx, double pwy, double pwz) {
    HashMap<ParentInput, Double> traceValue = new HashMap<ParentInput, Double>();
    for (ParentInput input : graph.getInput()) {
      if (input.getName().equals("pwx"))
        traceValue.put(input, 0.0);
      if (input.getName().equals("pwy"))
        traceValue.put(input, 1.4);
      if (input.getName().equals("pwz"))
        traceValue.put(input, -1.0);
    }
    return traceValue;
  }

  private void generateMonteCarloFloatInputs() {

    /* first we add a trace value */
    monteCarloInputsFloat.add(generateTrace(0.0, 1.4, -1.0));

    for (int iter = 0; iter < opts.getMonteCarloIterations(); iter++) {
      HashMap<ParentInput, Double> startValues = new HashMap<ParentInput, Double>();
      // init with random values
      for (ParentInput input : graph.getInput()) {
        double minValue = minValues.get(input);
        double maxValue = maxValues.get(input);
        double range = maxValue - minValue;
        double randomValue = minValue + Math.random() * range;
        startValues.put(input, randomValue);
      }
      monteCarloInputsFloat.add(startValues);
    }
  }

  private void generateMonteCarloFixInputsFromFloats() {
    /* the float inputs shold have not higher precision as the fixed inputs => so recreate them */
    ArrayList<HashMap<ParentInput, Double>> newFloatValues = new ArrayList<HashMap<ParentInput, Double>>();
    for (HashMap<ParentInput, Double> floatValues : monteCarloInputsFloat) {
      HashMap<ParentInput, BigInteger> startValues = new HashMap<ParentInput, BigInteger>();
      HashMap<ParentInput, Double> newFloatStartValues = new HashMap<ParentInput, Double>();
      for (ParentInput input : floatValues.keySet()) {
        FixedPoint fpt = (FixedPoint) input.getType();
        startValues.put(input, Util.fixedPointFromFloat(floatValues.get(input), fpt.getFractionlength()));
        newFloatStartValues.put(input, Util.floatFromfixedPoint(startValues.get(input), fpt.getFractionlength()));
      }
      monteCarloInputsFix.add(startValues);
      newFloatValues.add(newFloatStartValues);
    }
    monteCarloInputsFloat = newFloatValues;
  }

  private void generateMonteCarloInputs() {
    initStartVariableRanges();
    generateMonteCarloFloatInputs();
    generateMonteCarloFixInputsFromFloats();
  }

  private void monteCarloFix() {
    int i = 0;
    for (HashMap<ParentInput, BigInteger> startValues : monteCarloInputsFix) {
      HashMap<Operation, BigInteger> result;
      Operation.nextVisit();
      ComputeIntegerValueVisitor fixpointValue = new ComputeIntegerValueVisitor(startValues);
      try {
        for (ParentOutput outputNode : graph.getOutput()) {
          outputNode.postOrderUpwardVisit(fixpointValue);
        }
        monteCarloResultsFix.add(fixpointValue.getValues());
      } catch (java.lang.ArithmeticException arithException) {
        // catch divide by zero, neg square which can happen due to invalid random input
        System.err.println("div by zero dataset: " + startValues);
        /* debug for annotatting dot graph with values

         for (Operation op : fixpointValue.getValues().keySet()) {
          op.setDebugMessage(fixpointValue.getValues().get(op).toString());
          monteCarloResultsFloat = new ...
          return
        } */
        //System.err.println(fixpointValue.getValues());
        monteCarloResultsFloat.remove(i);
        i--;
      }
      i++;
    }
  }

  private void monteCarloFloat() {
    boolean firstRun = true;

    generateMonteCarloInputs();

    for (HashMap<ParentInput, Double> startValues : monteCarloInputsFloat) {
      HashMap<Operation, Double> result = performMonteCarloIter((startValues));
      monteCarloResultsFloat.add(result);
      /* merge the fixpoint runs for value range analysis */
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
/*
    for (Operation op: minValues.keySet()) {
      System.out.println("Variable " + op.getDebugMessage() + " " + op.getDisplayName() +
              " MC-Value Range: [" + minValues.get(op) + ", " +
              maxValues.get(op) + "]");
    }  */
  }

  private void monteCarloErrorAnalysis() {
    HashMap<Operation, Double> maxAbsError = new HashMap<Operation, Double>();
    HashMap<Operation, Double> maxRelError = new HashMap<Operation, Double>();

    Iterator<HashMap<Operation, BigInteger>> iter  = monteCarloResultsFix.iterator();
    for (HashMap<Operation, Double> floatValues : monteCarloResultsFloat) {
      HashMap<Operation, BigInteger> fixValues = iter.next();
      for (Operation op : floatValues.keySet()) {
        FixedPoint fpt = (FixedPoint) op.getType();
        double floatValue = floatValues.get(op);
        double fixValue = Util.floatFromfixedPoint(fixValues.get(op), fpt.getFractionlength());
        double newDifference = Math.abs(floatValue - fixValue);
        if (!maxAbsError.containsKey(op) || maxAbsError.get(op) < newDifference) {
          maxAbsError.put(op, newDifference);
          maxRelError.put(op, newDifference/floatValue);
        } 
      }
    }
    for (Operation op : maxAbsError.keySet()) {
      //op.setDebugMessage(op.getDebugMessage() + "   Error: " + maxAbsError.get(op));
      if (!op.getDebugMessage().isEmpty()) {
      System.out.println("Difference in node " + op.getDebugMessage() + 
              "=> Abs: " + maxAbsError.get(op) +
              "   Rel: " + maxRelError.get(op));
      }
    }

  }

  private void traceValues() {
    HashMap<Operation, Double> floatVals = monteCarloResultsFloat.get(0);

    for (Operation op : monteCarloResultsFix.get(0).keySet()) {
      BigInteger val = monteCarloResultsFix.get(0).get(op);
      double doubleval = (floatVals.get(op) == null) ? 0 : floatVals.get(op);
      FixedPoint fp = (FixedPoint) op.getType();
      if (!op.getDebugMessage().isEmpty()) {
        System.out.println(op.getDebugMessage() + " " +op + "    BigIntVal: " + val + " (" +
                Util.floatFromfixedPoint(val, fp.getFractionlength()) +
                ")   Float: " + doubleval);
      }
      op.setDebugMessage(op.getDebugMessage() +
              "\\nBigInteger: " + val + " (" + Util.floatFromfixedPoint(val, fp.getFractionlength()) + ")" +
              "\\nFloat: " + doubleval +
              "\\nError: " + Math.abs(doubleval - Util.floatFromfixedPoint(val, fp.getFractionlength())));
    }

  }

  @Override
  public int optimize(Graph graph) {
    int changed = 0;
    this.graph = graph;

    /* monte carlo simulation */
    monteCarloFloat();

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

    /* perform fixed point simulation */
    monteCarloFix();

    monteCarloErrorAnalysis();
    traceValues();
    graph.display(new DotDisplayFactory(),"beforetypecast");

    /* translate TypeConversions into appropiate shifts/bitselects */
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
