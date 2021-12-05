package datapath.graph.operations;

import datapath.graph.OperationVisitor;


/**
 *
 * @author jh
 */
public class Dummy extends NaryOperation {

    private Object dummy;

    @Override
    public String getDisplayLabel() {
        return "DUMMY: " + (dummy == null ? debugMessage : dummy.toString());
    }

    public Dummy(Object dummy) {
        this.dummy = dummy;
    }

    @Override
    public void visit(OperationVisitor visitor) {
        visitor.visit(this);
    }

    public void replaceWith(Operation op){
        Operation[] uses = getUse().toArray(new Operation[0]);
        for(Operation use :uses){
            use.replace(this, op);
        }
        for(Predicate p : getPredicates()){
            op.addPredicate(p);
        }
        assert this.getUse().size() == 0;
    }

    @Override
    public String toString() {
        return "DUMMY OP"+getNumber()+": " + (dummy == null ? debugMessage : dummy.toString());
        //return "DUMMY: " + (dummy == null ? "" : dummy.toString());
    }


}
