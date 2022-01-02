package datapath.graph;

import datapath.graph.operations.*;

/**
 *
 * @author Jens
 */
public interface OperationVisitor {

    public void visit(Operation op);
    public void visit(BinaryOperation op);
    public void visit(Mux op);
    public void visit(ConstantOperation op);
    public void visit(Add op);
    public void visit(MemWrite op);
    public void visit(Less op);
    public void visit(FromOuterLoop op);
    public void visit(ToInnerLoop op);
    public void visit(HWInput op);
    public void visit(VariableShift op);
    public void visit(Loop op);
    public void visit(Nop op);
    public void visit(ToOuterLoop op);
    public void visit(Negation op);
    public void visit(LoopEnd op);
    public void visit(LoopInit op);
    public void visit(HWOutput op);
    public void visit(TopLevelInput op);
    public void visit (Multiplication op);
    public void visit (Subtraction op);
    public void visit (Divide op);
    public void visit(Absolut op);
    public void visit(Sin op);
    public void visit(Cos op);
    public void visit(ArcCos op);
    public void visit(ConstantShift op);
    public void visit(SquareRoot op);
    public void visit(BitwidthTransmogrify op);
    public void visit(Predicate op);
    public void visit(TypeConversion op);
    public void visit(ConstantMultiplication op);
    
}
