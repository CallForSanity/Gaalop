package datapath.graph.operations;

import datapath.graph.Graph;
import datapath.graph.OperationVisitor;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 *
 * @author jh
 */
public class Mux extends AryOperation {

    @Override
    public String getDisplayLabel() {
        return "MUX " + debugMessage;
    }

    @Override
    public void addOperand(Operation op) {
        assert op instanceof Predication : "Only Predication allowed as inputs of mux";
        super.addOperand(op);
    }

    @Override
    public Set<Operation> dependsOnOperations(boolean includeBackedges) {
        HashSet<Operation> ops = new HashSet<Operation>();
        for (Operation op : operands) {
            Predication p = (Predication) op;
            if(!includeBackedges && Graph.isBackEdge(op, this))
                continue;
            //ops.addAll(p.dependsOnOperations(false));
            ops.add(p);
        }
        return ops;
    }

    private Iterator<Operation> outputBitsizeIter = null;

    @Override
    public int getOutputBitsize() {
        if(getType() != null)
            return getType().getBitsize();
        if(operands.size() == 0)
            return -1;
        if(outputBitsizeIter == null) {
            outputBitsizeIter = operands.iterator();
        }
        int Bitsize = -1;
        if(outputBitsizeIter.hasNext()){
            Bitsize = outputBitsizeIter.next().getOutputBitsize();
        }
        outputBitsizeIter = null;
        return Bitsize;
    }

    @Override
    public void visit(OperationVisitor visitor) {
        visitor.visit(this);
    }



}
