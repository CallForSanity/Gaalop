package wordlengthoptimization;

import datapath.graph.OperationVisitor;
import datapath.graph.operations.*;
import java.util.HashMap;

/**
 * Visitor, that computes the complete Dataflowgraph
 * Values at the nodes can be retrieved, inital values have to be set
 * @author fs
 */
public class ComputeValueVisitor implements OperationVisitor {

  HashMap<Operation, Double> values;

  public HashMap<Operation, Double> getValues() {
    return values;
  }

  /**
   * Constructor
   * @param initalValues Mapping which assign the Parentinputs (TopLevelInputs)
   *        double values.
   */
  public ComputeValueVisitor(HashMap<ParentInput, Double> initalValues) {
    values = (HashMap<Operation, Double>) initalValues.clone();
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
    values.put(op, Double.parseDouble(op.getValue().toString()));
  }

  @Override
  public void visit(Add op) {
    values.put(op, values.get(op.getLhs()) + values.get(op.getRhs()));
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
    values.put(op, -values.get(prev));
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
    values.put(op, values.get(op.getLhs()) * values.get(op.getRhs()));
  }

  @Override
  public void visit(Subtraction op) {
    values.put(op, values.get(op.getLhs()) - values.get(op.getRhs()));
  }

  @Override
  public void visit(Divide op) {
    values.put(op, values.get(op.getLhs()) / values.get(op.getRhs()));
  }

  @Override
  public void visit(Absolut op) {
    values.put(op, Math.abs(values.get(op.getData())));
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
    double oldValue = values.get(op.getData());
    double faktor = 1;
    switch (op.getMode()) {
      case Left:
        faktor =  1l << op.getShiftAmount();
        break;
      case Right:
      case SignedRight:
      case UnsignedRight:
        faktor = 1.0 / (1l << op.getShiftAmount());
        break;
      default:
        throw new UnsupportedOperationException("Not supported yet.");
    }
    values.put(op, oldValue * faktor);
  }

  @Override
  public void visit(SquareRoot op) {
    values.put(op, Math.sqrt(values.get(op.getData())));
  }

  @Override
  public void visit(BitwidthTransmogrify op) {
    //throw new UnsupportedOperationException("Not supported yet.");
    values.put(op, values.get(op.getData()));

  }

  @Override
  public void visit(Predicate op) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void visit(TypeConversion op) {
    //throw new UnsupportedOperationException("Not supported yet.");
    values.put(op, values.get(op.getData()));
  }

    @Override
    public void visit(ConstantMultiplication op) {
        visit((Multiplication)op);
    }

}
