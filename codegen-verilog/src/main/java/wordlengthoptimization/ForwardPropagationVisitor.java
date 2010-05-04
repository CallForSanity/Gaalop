package wordlengthoptimization;

import datapath.graph.OperationVisitor;
import datapath.graph.operations.Absolut;
import datapath.graph.operations.Add;
import datapath.graph.operations.ArcCos;
import datapath.graph.operations.BinaryOperation;
import datapath.graph.operations.BitwidthTransmogrify;
import datapath.graph.operations.ConstantOperation;
import datapath.graph.operations.ConstantShift;
import datapath.graph.operations.Cos;
import datapath.graph.operations.Divide;
import datapath.graph.operations.FromOuterLoop;
import datapath.graph.operations.HWInput;
import datapath.graph.operations.HWOutput;
import datapath.graph.operations.Less;
import datapath.graph.operations.Loop;
import datapath.graph.operations.LoopEnd;
import datapath.graph.operations.LoopInit;
import datapath.graph.operations.MemWrite;
import datapath.graph.operations.Multiplication;
import datapath.graph.operations.Mux;
import datapath.graph.operations.Negation;
import datapath.graph.operations.Nop;
import datapath.graph.operations.Operation;
import datapath.graph.operations.Predicate;
import datapath.graph.operations.Sin;
import datapath.graph.operations.SquareRoot;
import datapath.graph.operations.Subtraction;
import datapath.graph.operations.ToInnerLoop;
import datapath.graph.operations.ToOuterLoop;
import datapath.graph.operations.TopLevelInput;
import datapath.graph.operations.TypeConversion;
import datapath.graph.operations.VariableShift;
import datapath.graph.type.FixedPoint;
import datapath.graph.type.Type;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import datapath.graph.operations.constValue.*;
import wordlengthoptimization.Util;
/**
 * Recursive postorder visitor for the the ForwardProgagation.
 *
 * @author fs
 */
public class ForwardPropagationVisitor implements OperationVisitor {

  /* stores for each operation the maximum and minimum value */
  private HashMap<Operation, Double> maxValues = new HashMap();
  private HashMap<Operation, Double> minValues = new HashMap();

  public ForwardPropagationVisitor(HashMap<Operation, Double> statisticalMinValue,
                                   HashMap<Operation, Double> statisticalMaxvalue ) {
    maxValues = statisticalMaxvalue;
    minValues = statisticalMinValue;
  }


  /* stores the number of required fractional bits for each operation */

  private int changed = 0;

  /**
   * For debugging purpose, outputs the internal data of the visitor.
   */
  public void outputStats() {
    System.out.println("MaxValues: \n" + maxValues.toString());
    System.out.println("MinValues: \n" + minValues.toString());
  }

  /**
   * 
   * @return The number of nodes where the visitor changed the information
   */
  public int getChanged() {
    return changed;
  }

  /**
   * Copies the Optimization informations (like min and maxvalue,
   * precision, wordlength, ...) from one previous node to this node, without any
   * specific change to them
   * @param op Target operation, source is its predecessor. In case of multiple
   * predecessors an exception is thrown 
   */
  private void copyInformation(Operation op) {
    if (op.dependsOnOperations(true).size() != 1) {
      throw new UnsupportedOperationException("More than one predecessor not supported in copyInformation");
    }

    Operation pred = op.dependsOnOperations(true).iterator().next();
    op.setType((Type) pred.getType().clone());
    minValues.put(op, minValues.get(pred));
    maxValues.put(op, maxValues.get(pred));
  }

  @Override
  public void visit(ConstantOperation op) {
    double value = 0;
    assert op.getValue() instanceof FloatValue || op.getValue() instanceof FixedPointValue;
    if(op.getValue() instanceof FloatValue) {
        value = (double)((FloatValue)op.getValue()).getValue();
    }
    if(op.getValue() instanceof FixedPointValue) {
        value = (double)((FixedPointValue)op.getValue()).getValue();
    }
    long frac = Double.doubleToLongBits(value - Math.rint(value)) & 0x000fffffffffffffL;
    //int prec = 65 - Long.numberOfLeadingZeros(frac);
    int prec = Util.bitsRequiredForFraction(op.toString());
    // in case of small values the exponent of value the fractional part could still be < 0
    int exp = Math.getExponent(value-Math.rint(value)) + 1023;
    if (exp < 0)
       prec -= exp;
    //minValues.put(op, value);
    //maxValues.put(op, value);
    op.setType(new FixedPoint(Util.bitsRequired(value, value)+prec, prec, value < 0));
  }

  @Override
  public void visit(Add op) {
    double min, max;
    min = minValues.get(op.getLhs()) + minValues.get(op.getRhs());
    max = maxValues.get(op.getLhs()) + maxValues.get(op.getRhs());
    //minValues.put(op, min);
    //maxValues.put(op, max);
    min = minValues.get(op);
    max = maxValues.get(op);

    FixedPoint fpl = (FixedPoint) op.getLhs().getType();
    FixedPoint fpr = (FixedPoint) op.getRhs().getType();
    int prec = Math.max(fpl.getFractionlength(), fpr.getFractionlength());
    op.setType(new FixedPoint(Util.bitsRequired(min, max) + prec, prec, min < 0));
  }

  @Override
  public void visit(MemWrite op) {
    
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void visit(Less op) {
    
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void visit(FromOuterLoop op) {
    
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void visit(ToInnerLoop op) {
    
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void visit(HWInput op) {
    
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void visit(VariableShift op) {
    
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void visit(Loop op) {
    
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void visit(Nop op) {
    
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void visit(ToOuterLoop op) {
    
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void visit(Negation op) {
    Operation pred = op.getData();
    double minValue = -maxValues.get(pred);
    double maxValue = -minValues.get(pred);

    //minValues.put(op, minValue);
    //maxValues.put(op, maxValue);

    int prec = ((FixedPoint) pred.getType()).getFractionlength();
    op.setType(new FixedPoint(Util.bitsRequired(minValue, maxValue) +prec, prec, minValue < 0.0));
  }

  @Override
  public void visit(LoopEnd op) {
    
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void visit(LoopInit op) {
    // do nothing
  }

  @Override
  public void visit(HWOutput op) {
    copyInformation(op);
  }

  @Override
  public void visit(TopLevelInput op) {
    if (op.getType() == null) {
      op.setType(new FixedPoint(32, 16, true));
    }
    //minValues.put(op, op.getType().getMinValue());
    //maxValues.put(op, op.getType().getMaxValue());
  }

  @Override
  public void visit(Multiplication op) {
    double min, max;
    double v1, v2, v3, v4;


    /* because of signedness we  can not just multiply the min (max)
     * values to get the resulting min (max) value  */
    v1 = maxValues.get(op.getLhs()) * maxValues.get(op.getRhs());
    v2 = maxValues.get(op.getLhs()) * minValues.get(op.getRhs());
    v3 = minValues.get(op.getLhs()) * maxValues.get(op.getRhs());
    v4 = minValues.get(op.getLhs()) * minValues.get(op.getRhs());
    min = Math.min(v1, Math.min(v2, Math.min(v3, v4)));
    max = Math.max(v1, Math.max(v2, Math.max(v3, v4)));
    min = minValues.get(op);
    max = maxValues.get(op);
    //minValues.put(op, min);
    //maxValues.put(op, max);
    FixedPoint fpl = (FixedPoint) op.getLhs().getType();
    FixedPoint fpr = (FixedPoint) op.getRhs().getType();
    int prec = Math.min(24,fpl.getFractionlength() +fpr.getFractionlength());
    //int prec = Math.max(fpl.getFractionlength(), fpr.getFractionlength()) + 2;
    op.setType(new FixedPoint(Util.bitsRequired(min, max) + prec, prec, min < 0));
  }

   
  @Override
  public void visit(Subtraction op) {
    double min, max;
    min = minValues.get(op.getLhs()) - maxValues.get(op.getRhs());
    max = maxValues.get(op.getLhs()) - minValues.get(op.getRhs());
    min = minValues.get(op);
    max = maxValues.get(op);
    //minValues.put(op, min);
    //maxValues.put(op, max);
    FixedPoint fpl = (FixedPoint) op.getLhs().getType();
    FixedPoint fpr = (FixedPoint) op.getRhs().getType();
    int prec = Math.max(fpl.getFractionlength(), fpr.getFractionlength());
    op.setType(new FixedPoint(Util.bitsRequired(min, max) + prec, prec, min < 0));
  }


  @Override
  public void visit(Divide op) {
    double min, max;
    double v1,v2,v3,v4;
/*    v1 = maxValues.get(op.getLhs()) / maxValues.get(op.getRhs());
    v2 = maxValues.get(op.getLhs()) / minValues.get(op.getRhs());
    v3 = minValues.get(op.getLhs()) / maxValues.get(op.getRhs());
    v4 = minValues.get(op.getLhs()) / minValues.get(op.getRhs());
 * */
    v1 = maxValues.get(op.getLhs())*10;
    v2 = minValues.get(op.getLhs());
    v3 = maxValues.get(op.getRhs())*10;
    v4 = minValues.get(op.getRhs());

    min = Math.min(v1, Math.min(v2, Math.min(v3, v4)));
    max = Math.max(v1, Math.max(v2, Math.max(v3, v4)));
    min = minValues.get(op);
    max = maxValues.get(op);

    /* special treatment when we find that it is a normalization
     *  atm it is recognized by the variable name,
     * better would be, to flag when a value results from abs() call,
     * and we check it here */
    if (op.isNormalization()) {
      min = 0.0;
      max = 1.0;
    }
    //minValues.put(op, min);
    //maxValues.put(op, max);
    FixedPoint fpl = (FixedPoint) op.getLhs().getType();
    FixedPoint fpr = (FixedPoint) op.getRhs().getType();
    int prec = Math.max(fpl.getFractionlength(), fpr.getFractionlength());
    if (op.isNormalization()) {
      prec = fpl.getFractionlength();
    }
    op.setType(new FixedPoint(32, prec, fpl.isSigned() || fpr.isSigned()));
  }

  @Override
  public void visit(Sin op) {
    double min, max;
    min = -1.0;
    max = 1.0;
    minValues.put(op, min);
    maxValues.put(op, max);
    FixedPoint inputType = (FixedPoint) op.getData().getType();
    op.setType(new FixedPoint(Util.bitsRequired(min, max), inputType.getFractionlength(), true));
  }

  @Override
  public void visit(Cos op) {
    double min, max;
    min = -1.0;
    max = 1.0;
    minValues.put(op, min);
    maxValues.put(op, max);
    FixedPoint inputType = (FixedPoint) op.getData().getType();
    op.setType(new FixedPoint(Util.bitsRequired(min, max), inputType.getFractionlength(), true));
  }

  @Override
  public void visit(Operation op) {
    throw new UnsupportedOperationException("Not supported yet." + op.getClass().toString());
  }

  @Override
  public void visit(BinaryOperation op) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void visit(Mux op) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void visit(ConstantShift op) {
    copyInformation(op);
  }

  @Override
  public void visit(Absolut op) {
    double min, max;
    min = 0.0;
    max = Math.max(Math.abs(maxValues.get(op.getData())),Math.abs(minValues.get(op.getData())));
    //minValues.put(op, min);
    //maxValues.put(op, max);
    FixedPoint inputType = (FixedPoint) op.getData().getType();
    int prec = inputType.getFractionlength();
    op.setType(new FixedPoint(Util.bitsRequired(min, max) + prec, prec , false));

  }

  @Override
  public void visit(ArcCos op) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void visit(SquareRoot op) {
    double min, max;
    min = 0.0;
    max = Math.sqrt(maxValues.get(op.getData()));
    //minValues.put(op, min);
    //maxValues.put(op, max);
    FixedPoint fp = (FixedPoint) op.getData().getType();
    /* half as many frac bits, we round up, because in ShiftInserter
     * an additonal left shift is performed to make the number of bits of the
     * input even */
    int prec = (fp.getFractionlength() +1 )/ 2;
    op.setType(new FixedPoint(Math.min(32, Util.bitsRequired(min, max) + prec), prec, false));
  }

  @Override
  public void visit(BitwidthTransmogrify op) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

    @Override
    public void visit(Predicate op) {
        // do nothing
    }

  @Override
  public void visit(TypeConversion op) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

}
