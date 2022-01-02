package wordlengthoptimization;

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

/**
 * Implements a pass that cutoffs the wordlength at a given limit.
 * Also certain operations support only a small set of bitwidths, these operations are
 * also set to working bitwidths. This is not applied to inputs, outputs and constants.
 * Also collects as last wordlength pass some statistics about word and fraction lengths.
 * @author fs
 */
public class LimitBitwidth implements OperationVisitor {

  int maxWordlength;
  int minFractionlength;

  long wordlengthSum;
  long fractionlengthSum;
  long count;
  int wordlengthMax;
  int cutOffs;

  public LimitBitwidth(int maxWordlength, int minFractionlength) {
    this.maxWordlength = maxWordlength;
    this.minFractionlength = minFractionlength;
    wordlengthSum = 0;
    fractionlengthSum = 0;
    wordlengthMax = 0;
    count = 0;
    cutOffs = 0;
  }

  @Override
  public void visit(Operation op) {
    Type type = op.getType();
    assert (type instanceof FixedPoint);
    FixedPoint fp = (FixedPoint) type;

    /* pump it up to get minimum fractionlength */
    if (fp.getFractionlength() < minFractionlength) {
       int missing = minFractionlength - fp.getFractionlength();
       fp.setBitsize(fp.getBitsize()+missing);
       fp.setFractionlength(minFractionlength);
    }

    /* reduce the whole type in case it is too big */
    if (fp.getBitsize() > maxWordlength) {
      System.out.println("Wordlength too big, cut off to " + maxWordlength + " bits");
      fp.setBitsize(maxWordlength);
      cutOffs++;
    }

    /* sum up stats */
    wordlengthSum += fp.getBitsize();
    fractionlengthSum += fp.getFractionlength();
    count++;
    if (fp.getBitsize() > wordlengthMax)
      wordlengthMax = fp.getBitsize();
  }

  @Override
  public void visit(BinaryOperation op) {
    visit((Operation)op);
  }

  @Override
  public void visit(Mux op) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void visit(ConstantOperation op) {
    // constants dont get changed
  }

  @Override
  public void visit(Add op) {
    visit((Operation) op);
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
    visit((Operation) op);
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
    visit((Operation) op);
  }

  @Override
  public void visit(TopLevelInput op) {
    /* inputs get not changed */
  }

  @Override
  public void visit(Multiplication op) {
    visit((Operation) op);
  }

  @Override
  public void visit(Subtraction op) {
    visit((Operation) op);
  }

  @Override
  public void visit(Divide op) {
    visit((Operation) op);
  }

  @Override
  public void visit(Absolut op) {
    visit((Operation) op);
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
    visit((Operation) op);
  }

  @Override
  public void visit(SquareRoot op) {
    visit((Operation) op);
  }

  @Override
  public void visit(BitwidthTransmogrify op) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public String getStats() {
    return (count > 0 ) ? "Average Wordlength: " + wordlengthSum / count + 
            "  Average Fractionlength: " + fractionlengthSum / count +
            "  max. Wordlength: " + wordlengthMax +
            "  performed Cut-Offs: " + cutOffs
            : "No wordlength information available ";
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
