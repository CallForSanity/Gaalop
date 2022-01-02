package wordlengthoptimization;

import datapath.graph.OperationVisitor;
import datapath.graph.operations.Absolut;
import datapath.graph.operations.Add;
import datapath.graph.operations.ArcCos;
import datapath.graph.operations.BinaryOperation;
import datapath.graph.operations.BitwidthTransmogrify;
import datapath.graph.operations.ConstantMultiplication;
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
import datapath.graph.operations.ParentInput;
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
import java.math.BigInteger;
import java.util.HashMap;

/**
 * Visitor, that computes the complete Dataflowgraph with Integers (for FixedPoint)
 * Values at the nodes can be retrieved, inital values have to be set
 * @author fs
 */
public class ComputeIntegerValueVisitor implements OperationVisitor {

  HashMap<Operation, BigInteger> values;

  public HashMap<Operation, BigInteger> getValues() {
    return values;
  }

  /**
   * Constructor
   * @param initalValues Mapping which assign the Parentinputs (TopLevelInputs)
   *        BigInteger values.
   */
  public ComputeIntegerValueVisitor(HashMap<ParentInput, BigInteger> initalValues) {
    values = (HashMap<Operation, BigInteger>) initalValues.clone();
  }

  @Override
  public void visit(Operation op) {
    throw new UnsupportedOperationException("Not supported yet.");
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
  public void visit(ConstantOperation op) {
    FixedPoint fp = (FixedPoint) op.getType();
    double value = (Float) op.getValue().getValue();
    values.put(op, Util.fixedPointFromFloat(value, fp.getFractionlength()));
  }

  @Override
  public void visit(Add op) {
    values.put(op, values.get(op.getLhs()).add(values.get(op.getRhs())));
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
    Operation prev = op.getData();
    values.put(op, BigInteger.ZERO.subtract(values.get(prev)));
  }

  @Override
  public void visit(LoopEnd op) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void visit(LoopInit op) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void visit(HWOutput op) {
    values.put(op, values.get(op.getData()));
  }

  @Override
  public void visit(TopLevelInput op) {
    // should already be in
  }

  @Override
  public void visit(Multiplication op) {
    values.put(op, values.get(op.getLhs()).multiply(values.get(op.getRhs())));
  }

  @Override
  public void visit(Subtraction op) {
    values.put(op, values.get(op.getLhs()).subtract(values.get(op.getRhs())));
  }

  @Override
  public void visit(Divide op) {
    /* divider has 32 fractionial width, adjust here for the fixpoint simulation */
    BigInteger lhs = values.get(op.getLhs());
    lhs = lhs.shiftLeft(32);
    values.put(op, lhs.divide(values.get(op.getRhs())));
  }

  @Override
  public void visit(Absolut op) {
    values.put(op, values.get(op.getData()).abs());
  }

  @Override
  public void visit(Sin op) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void visit(Cos op) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void visit(ArcCos op) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void visit(ConstantShift op) {
    BigInteger oldValue = values.get(op.getData());
    BigInteger newValue = BigInteger.ONE;
    switch (op.getMode()) {
      case Left:
        newValue = oldValue.shiftLeft(op.getShiftAmount());
        break;
      case Right:
      case SignedRight:
      case UnsignedRight:
        newValue = oldValue.shiftRight(op.getShiftAmount());
        break;
      case ZeroShiftRight:
      case ZeroShiftLeft:
        newValue = oldValue;
        break;
      default:
        throw new UnsupportedOperationException("Not supported yet.");
    }
    values.put(op, newValue);
  }

  @Override
  public void visit(SquareRoot op) {
    values.put(op, Util.fixedPointFromFloat(Math.sqrt(values.get(op.getData()).doubleValue()), 0));
  }

  @Override
  public void visit(BitwidthTransmogrify op) {
    //nothing as BigInteger does not care about number of bits
    values.put(op, values.get(op.getData()));
  }

  @Override
  public void visit(Predicate op) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void visit(TypeConversion op) {
    // can change fraction length
    BigInteger result;
    FixedPoint thisType = (FixedPoint) op.getType();
    FixedPoint prevType = (FixedPoint) op.getData().getType();

    result = values.get(op.getData()).shiftLeft(thisType.getFractionlength() - prevType.getFractionlength());
    values.put(op, result);
  }

    @Override
    public void visit(ConstantMultiplication op) {
        visit((Multiplication)op);
    }


}
