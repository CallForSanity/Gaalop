package datapath.graph.operations;

import datapath.graph.OperationVisitor;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author jh
 */
public abstract class AryOperation extends Operation{

    protected Set<Operation> operands;

    protected AryOperation() {
        operands = new HashSet<Operation>();
    }

    public void addOperand(Operation op){
        assert !operands.contains(op);
        operands.add(op);
        op.addUse(this);
    }
    
    public void removeOperand(Operation op){
        assert operands.contains(op);
        op.removeUse(this);
        operands.remove(op);
    }

    @Override
    public void replace(Operation oldOp, Operation newOp) {
        removeOperand(oldOp);
        addOperand(newOp);
    }

    public Set<Operation> getOperands() {
        return Collections.unmodifiableSet(operands);
    }

  public void visit(OperationVisitor visitor) {
    visitor.visit(this);
  }

}
