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
import datapath.graph.operations.UnaryOperation;
import datapath.graph.operations.VariableShift;
import datapath.graph.type.FixedPoint;
import datapath.graph.type.Type;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Inserts required type conversions between operations
 *
 * @author fs
 */
public class ShiftInserterNewTypeCast implements OperationVisitor {

  Graph g;

  /* inserts a node after op in to the Graph g 
     the node toInsert is also added to the graph and set as visited */
  protected void addToOutput(Operation op, UnaryOperation toInsert) {
    for (Operation ops : op.getUse().toArray(new Operation[0])) {
      ops.replace(op, toInsert);
    }
    toInsert.setData(op);
    g.addOperation(toInsert);
    toInsert.setVisited();
  }

  /* inserts a node before op, after prev into the Graph g
     the node toInsert is also added to the graph and set as visited */
  protected void addToInput(Operation op, Operation prev, UnaryOperation toInsert) {
    g.insertNode(toInsert, prev, op);
    toInsert.setVisited();
  }

  public ShiftInserterNewTypeCast(Graph g) {
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
    /* Negation must be always defined as signed, so the output has also to be fixed */
    FixedPoint oldType = (FixedPoint) op.getType();
    if (!oldType.isSigned()) {
      FixedPoint newType = new FixedPoint(oldType.getBitsize() + 1, oldType.getFractionlength(), true);
      TypeConversion typeCast = new TypeConversion(oldType);
      op.setType(newType);
      addToOutput(op, typeCast);
      oldType = newType;
    }

    /* now fix input */
    FixedPoint inputType = (FixedPoint)oldType.clone();
    TypeConversion typeCast = new TypeConversion(inputType);
    addToInput(op, op.getData(), typeCast);
  }

  @Override
  public void visit(Multiplication op) {
      /** multiplication contains desired target resolution,
       * but multiplier is always bitsize(a) + bitsize(b)
       */

    FixedPoint oldType = (FixedPoint) op.getType();
    FixedPoint fpl = (FixedPoint) op.getLhs().getType();
    FixedPoint fpr = (FixedPoint) op.getRhs().getType();

    /* if multiplier is signed, both inputs have to be signed */
    if (op.isSigned()) {
      if (!fpl.isSigned()) {
        fpl = new FixedPoint(fpl.getBitsize() + 1, fpl.getFractionlength(), true);
        TypeConversion typeCast = new TypeConversion(fpl);
        addToInput(op, op.getLhs(), typeCast);
      }
      if (!fpr.isSigned()) {
        fpr = new FixedPoint(fpr.getBitsize() + 1, fpr.getFractionlength(), true);
        TypeConversion typeCast = new TypeConversion(fpr);
        addToInput(op, op.getRhs(), typeCast);
      }
    }

    int totalBitSize = fpl.getBitsize() + fpr.getBitsize();
    if (op.isSigned())
      totalBitSize--;
 
    FixedPoint newType = new FixedPoint(totalBitSize, fpl.getFractionlength() + fpr.getFractionlength(), oldType.isSigned());

    TypeConversion typeCast = new TypeConversion(oldType);
    op.setType(newType);
    addToOutput(op, typeCast);
  }


  @Override
  public void visit(Add op) {
    /* if signed, we sign extend the inputs */
    FixedPoint input = (FixedPoint) op.getType().clone();
    input.setBitsize(input.getBitsize());
    input.setSigned(op.isSigned());
    TypeConversion typeCast = new TypeConversion(input.clone());
    addToInput(op, op.getLhs(), typeCast);
    typeCast = new TypeConversion(input.clone());
    addToInput(op, op.getRhs(), typeCast);
  }

  @Override
  public void visit(Subtraction op) {
    /* if signed, we sign extend the inputs */
    FixedPoint input = (FixedPoint) op.getType().clone();
    input.setBitsize(input.getBitsize());
    input.setSigned(op.isSigned());
    TypeConversion typeCast = new TypeConversion(input.clone());
    addToInput(op, op.getLhs(), typeCast);
    typeCast = new TypeConversion(input.clone());
    addToInput(op, op.getRhs(), typeCast);
  }

  /* in a divide it is tried to balance the left and right inputs for their 
   * precision, in case the inputs must be adjusted to match the desired output
   * length */
  private void increaseDivisionPrecision(FixedPoint left, FixedPoint right) {
    /* check which has lower precision */
  }


  @Override
  public void visit(Divide op) {
    /** inputs must adjusted to 32 bits */
    FixedPoint fpl = (FixedPoint) op.getLhs().getType().clone();
    FixedPoint fpr = (FixedPoint) op.getRhs().getType().clone();
    FixedPoint oldType = (FixedPoint) op.getType();
    FixedPoint resultType = (FixedPoint)oldType.clone();
    oldType.setBitsize(64);
    oldType.setFractionlength(32);


    /* in case they are bigger, reduce them */
    //fpl.restrictBitwidth(32);
    fpr.restrictBitwidth(32);

    /* in case they are are smaller than 32 bit, set them to 32 bit */
    fpl.setBitsize(32);
    fpr.setBitsize(32);

    /* divs are always signed */
    fpl.setSigned(true);
    fpr.setSigned(true);


    /* left is always 1 */
    fpl.setFractionlength(30);

    if (fpl.getFractionlength() > fpr.getFractionlength()) {
      oldType.setFractionlength(32 + (fpl.getFractionlength() - fpr.getFractionlength()));
    } else {
      fpr.setFractionlength(fpl.getFractionlength());
    }
    //resultType = new FixedPoint(1+ oldType.getFractionlength() + resultType.getIntBits(), oldType.getFractionlength(), true);

    /* cheat, TODO: program a sophisticated algorithm, or a lookuptable
        for all 32 x 32 values
    if (fpl.getFractionlength() == 14 && fpr.getFractionlength() == 14) {
      fpl.setFractionlength(22);
      fpr.setFractionlength(8);
    } else     if (fpr.getFractionlength() >= 22) {
      fpl.setFractionlength(22);
      fpr.setFractionlength(8);
    }else  if (fpr.getFractionlength() >= 20) {
      fpl.setFractionlength(20);
      fpr.setFractionlength(6);
    }
     else {
       System.out.println("DIV bitwdith determination not finished");
       System.out.println("Missing parameter for combination: " + fpl + " and " +fpr);
       assert(false);
    } */

    /*
    int dividePrecision = fpl.getFractionlength() - fpr.getFractionlength();
    int missingPrecision = oldType.getFractionlength() - dividePrecision;
    int fixright = missingPrecision;
    int fixleft = missingPrecision/2;
    fixright -=fixleft;

    fpr.setFractionlength(fpr.getFractionlength() + fixright);
    fpl.setFractionlength(fpl.getFractionlength() - fixleft);

    if (fpl.getFractionlength() < 0) {
      fpr.setFractionlength(-fpl.getFractionlength());
      fpl.setFractionlength(0);
    }
    if (fpr.getFractionlength() < 0) {
      fpl.setFractionlength(-fpr.getFractionlength());
      fpr.setFractionlength(0);
    }

   */

    TypeConversion typeCast = new TypeConversion(fpl);
    addToInput(op, op.getLhs(), typeCast);
    typeCast = new TypeConversion(fpr);
    addToInput(op, op.getRhs(), typeCast);

    /* fix output */
    typeCast = new TypeConversion(resultType);
    addToOutput(op, typeCast);
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
    FixedPoint input = (FixedPoint) op.getType().clone();
    FixedPoint prev = (FixedPoint) op.getData().getType();
    input.setFractionlength(prev.getFractionlength());
    if (op.getMode() == ShiftMode.ZeroShiftRight &&
        input.getIntBits() == 0) {
      input.setFractionlength(input.getFractionlength() - op.getShiftAmount());
    }
    TypeConversion typeCast = new TypeConversion(input);
    addToInput(op, op.getData(), typeCast);
  }

  @Override
  public void visit(Absolut op) {
    FixedPoint input = (FixedPoint) op.getType().clone();
    input.setSigned(true);

    TypeConversion typeCast = new TypeConversion(input);
    addToInput(op, op.getData(), typeCast);
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
    /** input must be adjusted to 32 bits, and prec of input must be a multiple of 2 */
    FixedPoint oldType = (FixedPoint) op.getType();

    FixedPoint input = (FixedPoint) op.getData().getType().clone();

    input.setToBitwidth(32);
    if (input.getFractionlength() % 2 == 1) {
      input.setFractionlength(input.getFractionlength() - 1);
    }

    TypeConversion typeCast = new TypeConversion(input);
    addToInput(op, op.getData(), typeCast);

    /* now fix the output */
    typeCast = new TypeConversion(oldType.clone());
    addToOutput(op, typeCast);


    oldType.setFractionlength(input.getFractionlength() / 2);
    oldType.setBitsize(32);
    assert(input.getFractionlength() <= 32);

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
