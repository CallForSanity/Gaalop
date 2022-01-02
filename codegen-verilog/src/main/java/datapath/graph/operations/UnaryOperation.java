package datapath.graph.operations;

import datapath.graph.OperationVisitor;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author jh
 */
public abstract class UnaryOperation extends Operation {
    
    private Operation data = null;
    
    public void setData(Operation op){
        removeData();
        data = op;
        op.addUse(this);
    }

    public void removeData() {
        if(data != null){
            data.removeUse(this);
            data = null;
        }
    }

    public Operation getData() {
        return data;
    }

    @Override
    public void replace(Operation oldOp, Operation newOp) {
        assert oldOp == data;
        removeData();
        setData(newOp);
    }

    @Override
    public Set<Operation> dependsOnOperations(boolean includeBackedges) {
        HashSet<Operation> ops = new HashSet<Operation>();
        assert data != null: this.getClass() + "has no data set";
        ops.add(data);
        return ops;
    }

   public void visit(OperationVisitor visitor) {
        visitor.visit(this);
    }


}
