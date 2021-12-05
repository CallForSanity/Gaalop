package wordlengthoptimization;

import datapath.graph.Graph;
import datapath.graph.OperationVisitor;
import datapath.graph.operations.*;
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
import datapath.graph.operations.ShiftMode;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Inserts after the word length optimization the required shifts before
 * the operations.
 *
 * @author fs
 */
public class ShiftInserter implements OperationVisitor {

  Graph g;

  public ShiftInserter(Graph g) {
    this.g = g;
  }


  /**
   * Computes the necessary shift to convert from one type ton another
   * @param before Type before shift
   * @param after Result type after shift
   * @return A shifter which performs the necessary operation, or null if no shift
   * is necessary.
   */
  private ConstantShift computeShift(Type before, Type after) {
    if ((!(before instanceof FixedPoint)) ||
        (!(after instanceof FixedPoint))) {
      throw new UnsupportedOperationException("Only Fixed Point Types supported in Word length optimizations / ShiftInserter");
    }
    FixedPoint beforeFp = (FixedPoint) before;
    FixedPoint afterFp = (FixedPoint) after;

    int predDiff = beforeFp.getFractionlength() - afterFp.getFractionlength();
    ConstantShift shifter;

    if (predDiff == 0)
      return null;
    if (predDiff > 0)
      shifter = new ConstantShift(predDiff, beforeFp.isSigned() ? ShiftMode.SignedRight : ShiftMode.UnsignedRight);
    else
      shifter = new ConstantShift(-predDiff, ShiftMode.Left);

    // mark the node as visited, because new inserted must not be visited

    shifter.setVisited();
    return shifter;
  }

  @Override
  public void visit(VariableShift op) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void visit(Negation op) {
    if (op.getType().getBitsize() != op.getData().getType().getBitsize()) {
      BitwidthTransmogrify bt = new BitwidthTransmogrify();
        bt.setType(op.getType().clone());
      bt.setVisited();
      g.insertNode(bt, op.getData(), op);
    }
  }

  @Override
  public void visit(Multiplication op) {
      /** multiplication contains desired target resolution,
       * but multiplier is always bitsize(a) + bitsize(b)
       */
      /* save the old type, and reset the multtype to the expected result */
      FixedPoint targetType = (FixedPoint) op.getType().clone();
      FixedPoint multType = (FixedPoint) op.getType();
      FixedPoint fpl = (FixedPoint) op.getLhs().getType();
      FixedPoint fpr = (FixedPoint) op.getRhs().getType();
      multType.setFractionlength(fpl.getFractionlength() + fpr.getFractionlength());

      /* if multiplier is signed, both inputs have to be signed */
      if (op.isSigned()) {
        if (!fpl.isSigned()) {
          BitwidthTransmogrify bt = new BitwidthTransmogrify();
          /* insert two, one would do a signextension, which should not happen */
          FixedPoint newType = new FixedPoint(fpl.getBitsize() + 1, fpl.getFractionlength(), false);
          bt.setType(newType);
          g.insertNode(bt, op.getLhs(), op);
          bt.setVisited();

          bt = new BitwidthTransmogrify();
          g.insertNode(bt, op.getLhs(), op);
          bt.setVisited();
          newType = new FixedPoint(fpl.getBitsize() + 1, fpl.getFractionlength(), true);
          bt.setType(newType);
          fpl = newType;
        }
        if (!fpr.isSigned()) {
          BitwidthTransmogrify bt = new BitwidthTransmogrify();
          g.insertNode(bt, op.getRhs(), op);
          bt.setVisited();
          FixedPoint newType = new FixedPoint(fpr.getBitsize() + 1, fpr.getFractionlength(), false);
          bt.setType(newType);

          bt = new BitwidthTransmogrify();
          g.insertNode(bt, op.getRhs(), op);
          bt.setVisited();
          newType = new FixedPoint(fpr.getBitsize() + 1, fpr.getFractionlength(), true);
          bt.setType(newType);
          fpr = newType;
        }
      }



      int totalBitSize = fpl.getBitsize() + fpr.getBitsize();
      if (op.isSigned())
        totalBitSize--;
      multType.setBitsize(totalBitSize);



      /* insert shifter which removes lower bits then transmogrify to select lower bits */
      ConstantShift shifter = new ConstantShift(multType.getFractionlength() - targetType.getFractionlength(), (targetType.isSigned()? ShiftMode.SignedRight: ShiftMode.UnsignedRight));
      shifter.setType(targetType.clone());
      shifter.isVisited();
      BitwidthTransmogrify bt = new BitwidthTransmogrify();
      bt.setType(targetType);
      bt.isVisited();
      for (Operation ops :  op.getUse().toArray(new Operation[0])) {
            ops.replace(op, bt);
        }
      g.addOperation(bt);
      bt.setData(op);
      g.insertNode(shifter, op, bt);

  }

  @Override
  public void visit(Add op) {
    Operation pred = op.getLhs();
    ConstantShift shift = computeShift(pred.getType(), op.getType());
    /* only insert shift if necessary */
    if (shift != null)
      g.insertNode(shift, pred, op);
    pred = op.getRhs();
    shift = computeShift(pred.getType(), op.getType());
    /* only insert shift if necessary */
    if (shift != null)
      g.insertNode(shift, pred, op);
  }

  @Override
  public void visit(Subtraction op) {
    Operation pred = op.getLhs();
    ConstantShift shift = computeShift(pred.getType(), op.getType());
    /* only insert shift if necessary */
    if (shift != null)
      g.insertNode(shift, pred, op);
    pred = op.getRhs();
    shift = computeShift(pred.getType(), op.getType());
    /* only insert shift if necessary */
    if (shift != null)
      g.insertNode(shift, pred, op);
  }

  @Override
  public void visit(Divide op) {
    FixedPoint fpl = (FixedPoint) op.getLhs().getType();
    FixedPoint fpr = (FixedPoint) op.getRhs().getType();
    FixedPoint fpdiv = (FixedPoint) op.getType();

    BitwidthTransmogrify bt = new BitwidthTransmogrify();
    /** inputs must adjusted to 32 bits */
    bt.setType(new FixedPoint(32, fpl.getFractionlength(), op.isSigned()));
    bt.setVisited();
    g.insertNode(bt, op.getLhs(), op);
    int dividePrecision = fpl.getFractionlength() - fpr.getFractionlength();
    int necessaryShift = fpdiv.getFractionlength() - dividePrecision;
    if (necessaryShift > 0) {
      ConstantShift shifter = new ConstantShift(necessaryShift , ShiftMode.Left);
      shifter.setVisited();
      g.insertNode(shifter, bt, op);
    }
    bt = new BitwidthTransmogrify();
    bt.setVisited();
    bt.setType(new FixedPoint(32, 0, op.isSigned()));
    g.insertNode(bt, op.getRhs(), op);
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
    // Nothing to do
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
    // Nothing to do
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
  public void visit(LoopEnd op) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void visit(LoopInit op) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void visit(HWOutput op) {
    // Nothing to do
  }

  @Override
  public void visit(TopLevelInput op) {
    // Nothing to do
  }

  @Override
  public void visit(ConstantShift op) {

  }

  @Override
  public void visit(Absolut op) {
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
  public void visit(SquareRoot op) {
    BitwidthTransmogrify bt = new BitwidthTransmogrify();
    /** input must be adjusted to 32 bits, and prec of input must be a multiple of 2 */
    FixedPoint prev = (FixedPoint) op.getData().getType();
    bt.setType(new FixedPoint(32, prev.getFractionlength(), op.isSigned()));
    bt.setVisited();
    g.insertNode(bt, op.getData(), op);

    /* in case input is no multiple of 2 add shift */
    if ((prev.getFractionlength() %2 ) == 1) {
      ConstantShift shift = new ConstantShift(1, ShiftMode.Left);
      g.insertNode(shift, bt, op);
      shift.setVisited();
    }

    /* now fix the output */
    FixedPoint fpsqrt = (FixedPoint) op.getType();
    if (fpsqrt.getBitsize() < 32) {
        bt = new BitwidthTransmogrify();
        bt.setType(op.getType().clone());
        bt.setVisited();
        for (Operation ops : op.getUse().toArray(new Operation[0])) {
          ops.replace(op, bt);
        }
        bt.setData(op);
        g.addOperation(bt);
        fpsqrt.setBitsize(32);
    }
  }

  @Override
  public void visit(BitwidthTransmogrify op) {
  }

    @Override
    public void visit(Predicate op) {
        // do nothing
    }

  @Override
  public void visit(TypeConversion op) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

   @Override
    public void visit(ConstantMultiplication op) {
        visit((Multiplication)op);
    }

}
