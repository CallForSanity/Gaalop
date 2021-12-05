package datapath.graph;

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
import datapath.graph.operations.Predicate;
import datapath.graph.operations.Sin;
import datapath.graph.operations.SquareRoot;
import datapath.graph.operations.Subtraction;
import datapath.graph.operations.ToInnerLoop;
import datapath.graph.operations.ToOuterLoop;
import datapath.graph.operations.TopLevelInput;
import datapath.graph.operations.TypeConversion;
import datapath.graph.operations.VariableShift;
import java.util.HashSet;

/**
 *
 * @author fs
 */
class DeadTreeEliminationVisitor implements OperationVisitor {

  @Override
  public void visit(Operation op) {
  }

  @Override
  public void visit(BinaryOperation op) {
  }

  @Override
  public void visit(Mux op) {
  }

  @Override
  public void visit(ConstantOperation op) {
  }

  @Override
  public void visit(Add op) {
  }

  @Override
  public void visit(MemWrite op) {
  }

  @Override
  public void visit(Less op) {
  }

  @Override
  public void visit(FromOuterLoop op) {
  }

  @Override
  public void visit(ToInnerLoop op) {
  }

  @Override
  public void visit(HWInput op) {
  }

  @Override
  public void visit(VariableShift op) {
  }

  @Override
  public void visit(Loop op) {
  }

  @Override
  public void visit(Nop op) {
  }

  @Override
  public void visit(ToOuterLoop op) {
  }

  @Override
  public void visit(Negation op) {
  }

  @Override
  public void visit(LoopEnd op) {
      for(Predicate p : op.getPredicates()) {
          p.postOrderUpwardVisit(this);
      }
  }

  @Override
  public void visit(LoopInit op) {
  }

  @Override
  public void visit(HWOutput op) {
  }

  @Override
  public void visit(TopLevelInput op) {
  }

  @Override
  public void visit(Multiplication op) {
  }

  @Override
  public void visit(Subtraction op) {
  }

  @Override
  public void visit(Divide op) {
  }

  @Override
  public void visit(Absolut op) {
  }

  @Override
  public void visit(Sin op) {
  }

  @Override
  public void visit(Cos op) {
  }

  @Override
  public void visit(ArcCos op) {
  }

  @Override
  public void visit(ConstantShift op) {
  }

  @Override
  public void visit(SquareRoot op) {
  }

  @Override
  public void visit(BitwidthTransmogrify op) {
  }

  @Override
  public void visit(Predicate op) {
  }

  @Override
  public void visit(TypeConversion op) {
  }

    @Override
    public void visit(ConstantMultiplication op) {
        visit((Multiplication)op);
    }
  
}
