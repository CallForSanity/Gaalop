package datapath.graph.operations;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author jh
 */
public abstract class BinaryOperation extends Operation {

    protected Operation lhs;
    protected Operation rhs;

    public void setLHS(Operation op) {
        removeLHS();
        lhs = op;
        op.addUse(this);
    }

    public void setRHS(Operation op) {
        removeRHS();
        rhs = op;
        op.addUse(this);
    }

    public void removeLHS() {
        if (lhs != null) {
            lhs.removeUse(this);
            lhs = null;
        }
    }

    public void removeRHS() {
        if (rhs != null) {
            rhs.removeUse(this);
            rhs = null;
        }
    }

    public Operation getLhs() {
        return lhs;
    }

    public Operation getRhs() {
        return rhs;
    }

    @Override
    public void replace(Operation oldOp, Operation newOp) {
        assert oldOp == lhs || oldOp == rhs;
        if (lhs == rhs && lhs == oldOp) {
            removeLHS();
            setLHS(newOp);
            setRHS(newOp);
        } else if (oldOp == lhs) {
            removeLHS();
            setLHS(newOp);
        } else {
            removeRHS();
            setRHS(newOp);
        }
    }

    @Override
    public Set<Operation> dependsOnOperations(boolean includeBackedges) {
        HashSet<Operation> ops = new HashSet<Operation>();
        ops.add(lhs);
        ops.add(rhs);
        return ops;
    }

    @Override
    public int getOutputBitsize() {
        if (getType() == null) {
            return lhs.getOutputBitsize();
        }
        return getType().getBitsize();
    }
}
