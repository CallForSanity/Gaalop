package datapath.graph.operations;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents operations without input data
 * @author jh
 */
public abstract class NaryOperation extends Operation implements Producer {

    private int outputBitsize;

    @Override
    public void replace(Operation oldOp, Operation newOp) {
        assert false : "no incoming operation";
    }

    @Override
    public Set<Operation> dependsOnOperations(boolean includeBackedges) {
        return new HashSet<Operation>();
    }

    @Override
    public void setOutputBitsize(int outputBitsize)  {
        this.outputBitsize = outputBitsize;
    }

    @Override
    public int getOutputBitsize() {
        if(getType() == null)
            return outputBitsize;
        return getType().getBitsize();
    }




}
